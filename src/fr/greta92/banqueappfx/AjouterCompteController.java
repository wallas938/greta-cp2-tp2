package fr.greta92.banqueappfx;

import fr.greta92.banqueappfx.model.Banque;
import fr.greta92.banqueappfx.model.Compte;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * <h1>Controlleur de la view 'AjouterCompte.fxml'</h1>
 *
 * <p>Exercice 1: ajouter une methode permettant de recevoir l'objet bank dans AjouterCompteController.
 * Note: inspirez-vous du code de MainController
 *
 * </p>
 * <p>Exercice 2: ajouter une méthode permettant de gérer l'evenement de type 'Action' sur le bouton 'Ajouter'.
 * cette methode doit afficher un message d'erreur si les données sont invalide
 * sinon elle doit ajouter le compte dans l'objet bank.
 * Note: la méthode 'ouvrirCompte' de la Banque permet de créer le compte</p>
 *
 * <p>Exercice 3: ajouter une méthode permettant de gérer l'evenement de type 'Action' sur le bouton 'Annuler'.
 * cette methode doit fermer la fenetre modale.
 * Note: voici le code permettant de récuperer le stage depuis la méthode callback
 * <pre>
 * Node node = (Node) event.getSource();
 * Stage thisStage = (Stage) node.getScene().getWindow();
 * thisStage.hide();
 * </pre>
 * </p>
 */
public class AjouterCompteController {
    @FXML
    Label titulaireSoldeError;
    @FXML
    TextField titulaireName;
    @FXML
    Label titulaireNameError;
    @FXML
    TextField titulaireSolde;

    public Banque getBank(String bankFilename) throws IOException, ClassNotFoundException {
        return Main.readBanque(bankFilename);
    }

    public void saveBank(String bankFilename, Banque bank) throws IOException, ClassNotFoundException {
        Main.saveBanque(bankFilename, bank);
        Banque banque = Main.readBanque(bankFilename);
        System.out.println(banque.toString() + " line: 54, saveBank 'AjouterCompteController Method'");
    }

    public void onAddNewAccount(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Banque bank = getBank("Banque.ser");
//        if ((!isStringValueIsEmpty(titulaireName.getText()) || !isStringValueIsEmpty(titulaireSolde.getText())) &&
//                isDoubleValueIsValid(titulaireSolde.getText())) {
//            bank.ouvrirCompte(titulaireName.getText(), Double.parseDouble(titulaireSolde.getText()));
//            onCloseAccountSavingForm(actionEvent);
//            saveBank("Banque.ser", bank);
//            return;
//        }
    }

    public boolean isDoubleValueIsValid(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isStringValueIsEmpty(String value) {
        return value.trim().isEmpty();
    }

    public void onCloseAccountSavingForm(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.hide();
    }

}
