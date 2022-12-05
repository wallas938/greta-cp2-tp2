package fr.greta92.banqueappfx;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import fr.greta92.banqueappfx.model.Banque;
import fr.greta92.banqueappfx.model.Compte;
import fr.greta92.banqueappfx.model.SoldeInsuffisantException;
import javafx.application.Platform;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
//	@FXML
//	ListView<String> compteListe;
	/**
	 * JavaFX injecte l'objet de type ListView<Compte> dans la variable 'compteListe'
	 * l'élément ListView déclaré dans 'Main.java' doit posséder l'identifiant 'compteListe' : fx:id="compteListe"
	 */
	@FXML
	ListView<Compte> compteListe;
	/**
	 * JavaFX injecte l'objet de type SplitPane dans la variable 'splitPane'
	 * l'élément SplitPane déclaré dans 'Main.java' doit posséder l'identifiant 'splitPane' : fx:id="splitPane"
	 */
	@FXML
	SplitPane splitPane;
	/**
	 * JavaFX injecte l'objet de type AnchorPane dans la variable 'leftPane'
	 * l'élément AnchorPane déclaré dans 'Main.java' doit posséder l'identifiant 'leftPane' : fx:id="leftPane"
	 */
	@FXML
	AnchorPane leftPane;
	/**
	 * JavaFX injecte l'objet de type TextField dans la variable 'numeroCompteTF'
	 * l'élément TextField déclaré dans 'Main.java' doit posséder l'identifiant 'numeroCompteTF' : fx:id="numeroCompteTF"
	 */
	@FXML
	TextField numeroCompteTF;
	/**
	 * JavaFX injecte l'objet de type TextField dans la variable 'titulaireTF'
	 * l'élément TextField déclaré dans 'Main.java' doit posséder l'identifiant 'titulaireTF' : fx:id="titulaireTF"
	 */
	@FXML
	TextField titulaireTF;
	/**
	 * JavaFX injecte l'objet de type TextField dans la variable 'soldeTF'
	 * l'élément TextField déclaré dans 'Main.java' doit posséder l'identifiant 'soldeTF' : fx:id="soldeTF"
	 */
	@FXML
	TextField soldeTF;
	/**
	 * JavaFX injecte l'objet de type Button dans la variable 'supprimerCompteBtn'
	 * l'élément Button déclaré dans 'Main.java' doit posséder l'identifiant 'supprimerCompteBtn' : fx:id="supprimerCompteBtn"
	 */
	@FXML
	Button supprimerCompteBtn;
	/**
	 * JavaFX injecte l'objet de type Button dans la variable 'modifierCompteBtn'
	 * l'élément Button déclaré dans 'Main.java' doit posséder l'identifiant 'modifierCompteBtn' : fx:id="modifierCompteBtn"
	 */
	@FXML
	Button modifierCompteBtn;
	/**
	 * eventhandler du button 'ajouterCompteBtn'
	 * l'élément Button déclaré dans 'Main.java' doit posséder l'attribut 'onAction=#ajouterCompte' 
	 */

	@FXML
	public void ajouterCompte(ActionEvent event) throws IOException {
		//créer un objet URL - chemin vers le fichier XML
		URL resource = getClass().getResource("AjouterCompte.fxml");

		//objet permet de creer objet java à partir de FXML
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(resource);
		AnchorPane root = (AnchorPane)loader.load();
		
		//A FAIRE
		//récupérer le controller associé à 'AjouterCompte.fxml'
		//passer l'objet banque au controller 
		
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}
	@FXML
	public void fermer(ActionEvent event) {
		Platform.exit();
	}
	@FXML
	public void supprimerCompte(ActionEvent event) {
		System.out.println(event);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText("confirmer la suppresion ?");
		Optional<ButtonType> resultat = alert.showAndWait();
		//si resultat n'est pas vide
		if(!resultat.isEmpty()) {
			//on reupere le type de bouton
			ButtonType buttonType = resultat.get();
			if(buttonType == ButtonType.OK) {
				int selectedIndex = 
						compteListe.getSelectionModel().getSelectedIndex();
				compteListe.getItems().remove(selectedIndex);
				compteListe.refresh();
			}
		}
	}
	@FXML
	public void modifierCompte(ActionEvent event) {
		System.out.println(event);
		Compte compte = 
				compteListe.getSelectionModel().getSelectedItem();
		compte.setTitulaire(titulaireTF.getText());
		try {
			compte.setSolde(Double.valueOf(soldeTF.getText()));
		}
		catch (SoldeInsuffisantException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText(e.getMessage());
			alert.show();
		}
		
		compteListe.refresh();
	}
	
	
	@FXML
	public void initialize() {
		//initialiser les widgets
//		Banque b = new Banque();
//		b.ouvrirCompte("titulaire 1", 1000);
//		b.ouvrirCompte("titulaire 2", 2000);
//		b.ouvrirCompte("titulaire 3", 3000);
		
//		compteListe.getItems().addAll("java", "Python", "C++");
		//ecouter pour les changments sur la liste des elements selectionnée
		compteListe.getSelectionModel()
					.getSelectedItems()
					.addListener(new ListSelectionListener());
		//désactiver le champ numeroCompte (en lecture seule)
		numeroCompteTF.setEditable(false);
		numeroCompteTF.setDisable(true);
		
		//désactiver les boutons si aucun element selectionné
		BooleanBinding equalToBinding = 
				Bindings
					.size(compteListe.getSelectionModel().getSelectedItems())
					.isEqualTo(0);
		supprimerCompteBtn
			.disableProperty()
			.bind(equalToBinding);
		modifierCompteBtn
		.disableProperty()
		.bind(equalToBinding);
		
		leftPane.maxWidthProperty()
			.bind(splitPane.widthProperty().multiply(0.3));
		leftPane.minWidthProperty()
			.bind(splitPane.widthProperty().multiply(0.3));	
	}
	
	class ListSelectionListener implements ListChangeListener<Compte>{

		@Override
		public void onChanged(Change<? extends Compte> change) {
			System.out.println(change.getList());
			ObservableList<? extends Compte> list = change.getList();
			if(list.isEmpty()) {
				numeroCompteTF.setText("");
				soldeTF.setText("");
				titulaireTF.setText("");
			}
			else {
				Compte compte = list.get(0);
				titulaireTF.setText(compte.getTitulaire());
				soldeTF.setText(""+compte.getSolde());
				numeroCompteTF.setText(""+compte.getNumeroCompte());
			}
		}
		
	}

	
	public void setBanque(Banque banque) {
		compteListe.setItems(banque.getComptes());
		compteListe.refresh();
	}
	
	
}
