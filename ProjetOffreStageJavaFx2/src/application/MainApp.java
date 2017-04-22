package application;

import java.io.IOException;

import controller.MenuLoginController;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MainApp extends Application {
	private static Stage stage;
	private AnchorPane root;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
	primaryStage.initStyle(StageStyle.UNDECORATED);
	this.stage=primaryStage;
	lancement();
	
	


	
	//acepter refuser marche pas
	// apres accepter ou refusuer enlever de l'affichage du tableview( page entreprise candidature) avec init
	
	// Creation admin par défaut 
	String test=Utilisateur.getBdInfo("SELECT id FROM `Utilisateur` WHERE level=2");
	if(test==null){
		Administrateur a=new Administrateur();
		a.creerCompte();
		a.addAdministrateur();
	}                        
		
	}
	
	  public void lancement() {
	        try {
	        	
	        	root = FXMLLoader.load(getClass().getResource("/controller/viewLancement.fxml"));
	        	Scene scene = new Scene(root);
	            stage.setScene(scene);
	            stage.show();
	            
	            FadeTransition apparait=new FadeTransition(Duration.seconds(3),root);
				apparait.setFromValue(0);
				apparait.setToValue(1);
				apparait.setCycleCount(1); // pour l'afficher qu'une fois
				
				
				FadeTransition disparait=new FadeTransition(Duration.seconds(2),root);
				disparait.setFromValue(1);
				disparait.setToValue(0);
				disparait.setCycleCount(1); // pour l'afficher qu'une fois
				
				apparait.play();
				apparait.setOnFinished((e)->{
					disparait.play();
				});
				
				
				disparait.setOnFinished((e)->{
					try {
					menuLogin();
					} 
					
					catch(Exception e1) {
				e1.printStackTrace();
				}
				 
				});
				
				
	        } catch (IOException e2) {
	            e2.printStackTrace();
	        }
	 }

	  
	public void menuLogin(){
		try {
			AnchorPane MenuLogin = FXMLLoader.load(getClass().getResource("/controller/MenuLogin.fxml"));
			Scene sceneMenuLogin = new Scene(MenuLogin);
			stage.setScene(sceneMenuLogin);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public static void setStage(Stage stage) {
		MainApp.stage = stage;
	}

	public static Stage getStage() {
		return stage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
