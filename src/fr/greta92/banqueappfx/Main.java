package fr.greta92.banqueappfx;

import java.io.*;
import java.net.URL;

import fr.greta92.banqueappfx.model.Banque;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
    Banque banque;
    static MainController mainController;
    public static void saveBanque(String file, Banque b) throws IOException {
        // ouverture d'un fichier en mode ecriture
        FileOutputStream fos = new FileOutputStream(file);//
        // ouverture d'un flux de type objet en ecriture
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        // ecrire l'objet dans le fichier
        oos.writeObject(b);
        // on passe l'objet banque à notre mainController
        mainController.setBanque(b);
    }

    public static Banque readBanque(String file) throws IOException, ClassNotFoundException {
        FileInputStream fis;
        ObjectInputStream ois;
        Banque b;
        try {
            // ouverture d'un fichier en mode lecture
            fis = new FileInputStream(file);
            // ouverture d'un flux de type objet en lecture
            ois = new ObjectInputStream(fis);
            // lire l'objet à partir du fichier
            b = (Banque) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            FileOutputStream fos = new FileOutputStream(file);//
            // ouverture d'un flux de type objet en ecriture
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // ecrire l'objet dans le fichier
            b = new Banque();
            oos.writeObject(b);
        }

        return b;
    }

    @Override
    public void init() throws Exception {
        super.init();
        //déserialiser Banque.ser
        try {
            banque = readBanque("Banque.ser");
        } catch (IOException e) {
           banque = new Banque();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        //Serialiser Banque.ser
        try {
            saveBanque("Banque.ser", banque);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            //objet de type AnchorPane est créé à partir de 'Main.xml' - version 1
//			AnchorPane root = (AnchorPane)FXMLLoader.<AnchorPane>load(getClass().getResource("Main.fxml"));

            //version 2 - On doit utiliser cette methode si on doit passer les donner vers le mainController
            //créer un objet URL - chemin vers le fichier XML
            URL resource = getClass().getResource("Main.fxml");
            //objet permet de creer objet java à partir de FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            AnchorPane root = (AnchorPane) loader.load();

            //renvoie le mainController
            mainController = (MainController) loader.getController();
            //on passe l'objet banque à notre mainController
            mainController.setBanque(banque);
            //création de scène avec AnchorPane
            Scene scene = new Scene(root, 400, 400);
            //on ajoute à la scène la feuille de style
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            //ajout de la scène à l'estrade
            primaryStage.setScene(scene);
            //affiche la fenêtre
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
