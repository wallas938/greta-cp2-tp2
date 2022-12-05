package fr.greta92.banqueappfx;
	
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import fr.greta92.banqueappfx.model.Banque;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	Banque banque;
	public static void saveBanque(String file, Banque b) throws IOException {
		// ouverture d'un fichier en mode ecriture
		FileOutputStream fos = new FileOutputStream(file);//
		// ouverture d'un flux de type objet en ecriture
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		// ecrire l'objet dans le fichier
		oos.writeObject(b);
	}

	public static Banque readBanque(String file) throws IOException, ClassNotFoundException {
		// ouverture d'un fichier en mode lecture
		FileInputStream fis = new FileInputStream(file);
		// ouverture d'un flux de type objet en lecture
		ObjectInputStream ois = new ObjectInputStream(fis);
		// lire l'objet à partir du fichier
		Banque b = (Banque) ois.readObject();
		return b;
	}
	
	@Override
	public void init() throws Exception {
		super.init();
		//déserialiser Banque.ser
		try {
			banque = readBanque("Banque.ser");
		}
		catch(IOException e) {
			banque = new Banque();
		}
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		//Serialiser Banque.ser
		try {
			saveBanque("Banque.ser", banque);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			//objet de type AnchorPane est créé à partir de 'Main.xml' - version 1
//			AnchorPane root = (AnchorPane)FXMLLoader.<AnchorPane>load(getClass().getResource("Main.fxml"));
			
			//version 2 - On doit utiliser cette methode si on doit passer les donner vers le controller
			//créer un objet URL - chemin vers le fichier XML
			URL resource = getClass().getResource("Main.fxml");
			//objet permet de creer objet java à partir de FXML
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(resource);
			AnchorPane root = (AnchorPane)loader.load();
			
			//renvoie le controller
			MainController controller = (MainController)loader.getController();
			//on passe l'objet banque à notre mainController
			controller.setBanque(banque);
			//création de scène avec AnchorPane
			Scene scene = new Scene(root,400,400);
			//on ajoute à la scène la feuille de style
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//ajout de la scène à l'estrade
			primaryStage.setScene(scene);
			//affiche la fenêtre
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
