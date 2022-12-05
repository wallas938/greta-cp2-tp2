package fr.greta92.banqueappfx.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class Compte implements Serializable, Comparable<Compte> {
//	etat interne/données locales/variables d'instance
//	les properties sont observables
//	private int numeroCompte;
	/**
	 * sérialisation echoue si l'on tente de sérialiser l'objet compte les properties JavaFX ne sont pas sérialisable
	 * le mot clé 'transient' - les variables marqué ce mot clé ne sont pas sérialisés ainsi on empeche l'échec de l'application
	 */
	transient private IntegerProperty numeroCompte;
//	private double solde;
	transient private DoubleProperty solde;
//	private String titulaire;
	transient private StringProperty titulaire;
	
	public IntegerProperty numeroCompte() {
		return numeroCompte;
	}
	public DoubleProperty solde() {
		return solde;
	}
	public StringProperty titulaire() {
		return titulaire;
	}
	/**
	 * <h1>constructeur par defaut</h1>
	 * 
	 * @param numeroCompte un nombre positif
	 * @param solde doit être supérieur ou égal à 25
	 * @param titulaire le nom de titulaire
	 * @throws SoldeInsuffisantException si solde < 25
	 */
	public Compte(int numeroCompte, double solde, String titulaire) {
		super();//pas besoin -> appel implicite
		//initialiser les variables d'instances
		this.numeroCompte = new SimpleIntegerProperty();
		this.solde = new SimpleDoubleProperty();
		this.titulaire = new SimpleStringProperty();
		
		setSolde(solde);
		setNumeroCompte(numeroCompte);
		setTitulaire(titulaire);
//		if(solde < 25) {
//			throw new SoldeInsuffisantException();
//		}
////		this.numeroCompte = numeroCompte;
//		this.numeroCompte.set(numeroCompte);
////		this.titulaire = titulaire;
//		this.titulaire.set(titulaire);
////		this.solde=solde;
//		this.solde.set(solde);
	}
	
	
	/**
	 * @return the titulaire
	 */
	public String getTitulaire() {
		return titulaire.get();
	}

	/**
	 * @param titulaire the titulaire to set
	 */
	public void setTitulaire(String titulaire) {
//		this.titulaire = titulaire;
		this.titulaire.set(titulaire);
	}


	/**
	 * @param numeroCompte the numeroCompte to set
	 */
	public void setNumeroCompte(int numeroCompte) {
//		this.numeroCompte = numeroCompte;
		this.numeroCompte.set(numeroCompte);
	}


	/**
	 * @param solde the solde to set
	 */
	public void setSolde(double solde) {
		if(solde < 25) {
			throw new SoldeInsuffisantException();
		}
//		this.solde = solde;
		this.solde.set(solde);
	}

	// opérations/méthodes d'instance
	public int getNumeroCompte() {
		return numeroCompte.get();
	}
	
	public double getSolde() {
		return solde.get();
	}

	public void depot(double montant) {
//		solde = solde + montant;
		solde.set(getSolde()+montant);
	}

	public void retrait(double montant) {
		//vérifier si 'montant' est supérieur à 'solde'
		if(montant > getSolde()) {
			throw new RetraitSuperieurAuSoldeException();
		}
//		solde = solde - montant;
		solde.set(getSolde()-montant);
	}

	public void affiche() {
		System.out.println("Numero: " + numeroCompte);
		System.out.println("Titulaire: " + titulaire);
		System.out.println("Solde: " + solde);
	}
	
	@Override
	public String toString() {
		return getNumeroCompte() 
				+ " " + getTitulaire() 
				+ " " + getSolde();
	}

	@Override
	public int hashCode() {
		//utiliser des attributs immuable dans le calcul de hashcode
		return Objects.hash(getNumeroCompte());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compte other = (Compte) obj;
		return getNumeroCompte() == other.getNumeroCompte();
	}

	@Override
	public int compareTo(Compte o) {
		if(this.getNumeroCompte() > o.getNumeroCompte()) {return -1;}
		if(this.getNumeroCompte() < o.getNumeroCompte()) return 1;
		return 0;
	}	
	/**
	 * on customise la sérialisation
	 * comme les propriétés JavaFX ne sont pas sérialisable, 
	 * nous fournissons cette méthode qui permet de sérialiser notre objet.
	 * cette méthode est appelée par la méthode 'writeObject' lors de la sérialisation de l'objet Banque
	 * cette implémentation écrit les valeurs primitives et string sur le disque mais pas les variables d'instance qui sont des propriétés JavaFX.
	 * @param oos
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeObject(getSolde());
		oos.writeObject(getTitulaire());
		oos.writeObject(getNumeroCompte());
	}
	/**
	 * on customise la désérialisation car nous avons utilisé une méthode customisée pour sérialiser notre objet.
	 * 
	 * cette méthode est appelée par la méthode 'readObject' lors de la désérialisation de l'objet Banque
	 * cette implémentation lit les valeurs primitives et string et 
	 * ces valeurs sont utilisées pour initialiser les propriétés JavaFX.
	 * 
	 * @param ois
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
		Double solde = (Double)ois.readObject();
		String titulaire = (String)ois.readObject();
		Integer numero = (Integer)ois.readObject();
		this.titulaire = new SimpleStringProperty(titulaire);
		this.numeroCompte = new SimpleIntegerProperty(numero);
		this.solde = new SimpleDoubleProperty(solde);
	}
}
