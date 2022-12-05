package fr.greta92.banqueappfx.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Banque implements Serializable {
	private int numeroCompte;
//	private ArrayList<Compte> comptes;
	/**
	 * on stocke les comptes dans une ObservableList car elle est utilisée par ListView comme Model
	 */
	transient private ObservableList<Compte> comptes;
	
	/**
	 * @return the comptes
	 */
	public ObservableList<Compte> getComptes() {
		return comptes;
	}

	/**
	 * @param comptes the comptes to set
	 */
	public void setComptes(ObservableList<Compte> comptes) {
		this.comptes = comptes;
	}
	

	public Banque() {
		numeroCompte = 1001;
//		comptes = new ArrayList<>();// initialise l'objet ArrayList
		comptes = FXCollections.observableArrayList();// initialise l'objet ArrayList
	
	}
	
//	/**
//	 * @return the comptes
//	 */
//	public ArrayList<Compte> getComptes() {
//		return comptes;
//	}
//
//	/**
//	 * @param comptes the comptes to set
//	 */
//	public void setComptes(ArrayList<Compte> comptes) {
//		this.comptes = comptes;
//	}

	public int ouvrirCompte(String titulaire, double solde) {
		// on crée le compte
		Compte compte = new Compte(numeroCompte, solde, titulaire);
		// ajouter le compte dans la liste
		comptes.add(compte);
		numeroCompte++;// incremente le numéro de compte
//		return numeroCompte-1;
		return compte.getNumeroCompte();
	}

	public double getSolde(int num) {
		Compte c = comptes.get(num - 1001); // simplification!
		return c.getSolde(); // delegation
	}

	public boolean depot(int num, double m) {
		if (!isNumCompte(num))
			return false;
		Compte c = comptes.get(num - 1001);
		c.depot(m);// délégation
		return true;
	}

	private boolean isNumCompte(int num) {
		int tailleListe = comptes.size();
		if (num - 1001 >= tailleListe || num < 1001) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String toString() {
		return "Banque [numeroCompte=" + numeroCompte + ", comptes=" + comptes + "]";
	}
	public Compte getCompte(int numeroCompte) {
		return comptes.get(numeroCompte - 1001);
	}
	
	public void trierComptes() {
		Collections.sort(comptes);
	}
	//classe interne
	class ComparatorAsc implements Comparator<Compte>{
		@Override
		public int compare(Compte o1, Compte o2) {
			if(o1.getNumeroCompte() > o2.getNumeroCompte()) {return 1;}
			if(o1.getNumeroCompte() < o2.getNumeroCompte()) return -1;
			return 0;
		}
	}
	public void trierComptesAsc() {
		Collections.sort(comptes, new ComparatorAsc());
	}
	//classe anonyme
	public void trierComptesDesc() {
		Collections.sort(comptes, new Comparator<Compte>() {
			@Override
			public int compare(Compte o1, Compte o2) {
				if(o1.getNumeroCompte() > o2.getNumeroCompte()) return -1;
				if(o1.getNumeroCompte() < o2.getNumeroCompte()) return 1;
				return 0;
			}
		});
	}
	//expressions lambda
//	public void trierComptesDesc() {
//		Collections.sort(comptes, (o1, o2)->{
//			if(o1.getNumeroCompte() > o2.getNumeroCompte()) return -1;
//			if(o1.getNumeroCompte() < o2.getNumeroCompte()) return 1;
//			return 0;
//		});
//	}
	/**
	 * on customise la sérialisation
	 * comme les propriétés JavaFX ne sont pas sérialisable, 
	 * nous fournissons cette méthode qui permet de sérialiser notre objet.
	 * cette méthode est appelée par la méthode 'writeObject' lors de la sérialisation de l'objet Banque
	 * cette implémentation crée une ArrayList en utilisant comptes qui est une ObservableList et 
	 * l'écrit sur le disque 
	 * @param oos
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeObject(new ArrayList<Compte>(comptes));
	}

	/**
	 * on customise la désérialisation car nous avons utilisé une méthode customisée pour sérialiser notre objet.
	 * 
	 * cette méthode est appelée par la méthode 'readObject' lors de la désérialisation de l'objet Banque
	 * cette implémentation initialise 'comptes' qui est un ObservableList avec ArrayList lu du disque 
	 * @param ois
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
		ArrayList<Compte> comptes = (ArrayList<Compte>)ois.readObject();
		setComptes(FXCollections.observableArrayList(comptes));
	}
}
