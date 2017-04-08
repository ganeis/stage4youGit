package controller;





import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.Test;
import application.Utilisateur;
import application.enumSecteur;
import application.Connexion;

import application.MainApp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuLoginController implements Initializable {
@FXML 
private TextField identifiant;

@FXML 
private PasswordField  password;

@FXML
private Label errorMdp;

@FXML
private Label errorLogin;

@FXML
private Button btnPrecedent;

@FXML
private Button btnCreationEntreprise;

@FXML
private Button btnCreationEtudiant;

@FXML
private Button btnSeEnregistrer;

@FXML
private Button btnSeConnecter;

@FXML
private ImageView quitter;

private static String user;


public static String getUser() {
	return user;
}


public void afficher(ActionEvent e){
	 btnCreationEntreprise.setVisible(true);
	 btnCreationEtudiant.setVisible(true);
}

public void quitter(MouseEvent event){
	System.exit(1);
}


public void handleButtonAction(ActionEvent event) throws IOException{
     Stage stage; 
     Parent root;

     if(event.getSource()==btnPrecedent){
       stage=(Stage) btnPrecedent.getScene().getWindow();
       root = FXMLLoader.load(getClass().getResource("/controller/MenuLogin.fxml"));
       Scene scene = new Scene(root);
	   stage.setScene(scene);
	   stage.show();
      }
     
     if(event.getSource()==btnCreationEtudiant){
	       stage=(Stage) btnCreationEtudiant.getScene().getWindow();
	       root = FXMLLoader.load(getClass().getResource("/pEtudiant/viewEtudiant.fxml"));
	       Scene scene = new Scene(root);
		   stage.setScene(scene);
		   stage.show();
	      }
     
     if(event.getSource()==btnCreationEntreprise){
    	 stage=(Stage) btnCreationEntreprise.getScene().getWindow();
    	 root = FXMLLoader.load(getClass().getResource("/pEntreprise/viewEntreprise.fxml"));
    	 Scene scene = new Scene(root);
	     stage.setScene(scene);
	     stage.show();
     }
    	 
     
      
}
//test inkaran
//private AnchorPane root;

	

	public void seConnecter(ActionEvent event) throws IOException{
		
		user=identifiant.getText();
		String pass=password.getText();
		
		if(user.isEmpty())setError(errorLogin,"Champ vide"); // ça montre pas errreur champs vide???
		if(pass.isEmpty())setError(errorMdp,"Champ vide"); // ça marche
		
		if(Utilisateur.seConnecter(user, pass, errorLogin, errorMdp)){
			//Alerte.display("Connexion","Vous êtes connecté");		
		
			int level=Utilisateur.getBdLevel(user);
				if(level==0){
					ChoixView("/pEtudiant/viewEtudiantMenu.fxml");
				}
				if(level==1){
					ChoixView("/pEntreprise/viewEntrepriseMenu.fxml");
				}
				if(level==2){
					ChoixView("/pAdministrateur/viewAdmin.fxml");
				}
		}
		
	}
	
	public static void setError(Label err,String msg){
    	err.setTextFill(Color.RED);
    	err.setText(msg);
    }
	
	public void ChoixView(String msg) throws IOException{
	
		Stage stage; 
	    Parent root;
		root = FXMLLoader.load(getClass().getResource(msg));
        Scene home_page_scene = new Scene(root);
        stage =MainApp.getStage();
         
                stage.hide(); //optional
                stage.setScene(home_page_scene);
                stage.show();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
