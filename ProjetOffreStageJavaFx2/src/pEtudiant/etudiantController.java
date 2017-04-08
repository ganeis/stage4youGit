package pEtudiant;

import java.io.IOException;

import javax.swing.JOptionPane;

import application.Etudiant;
import application.Test;
import controller.Alerte;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class etudiantController {

    @FXML
    private TextField prenomF;

    @FXML
    private TextField nomF;

    @FXML
    private TextField loginF;

    @FXML
    private PasswordField passF;
    
    @FXML
    private Label errornom;

    @FXML
    private Label errorprenom;

    @FXML
    private Label errorMdp;

    @FXML
    private Label errorlogin;
    
    @FXML
    private Button btnPrecedent;
    
    @FXML
    private Button btnValider;

    String nom ;
	String prenom;
	String login;
	String password ;
	
	@FXML
	public void precedent(ActionEvent event) throws IOException{
		Stage stage; 
	    Parent root;
		
	    stage=(Stage) btnPrecedent.getScene().getWindow();
	    root = FXMLLoader.load(getClass().getResource("/controller/MenuLogin.fxml"));
	    Scene scene = new Scene(root);
		   stage.setScene(scene);
		   stage.show();
	}
	
    public void valider(ActionEvent event) throws IOException {
		nom = nomF.getText();
		prenom = prenomF.getText();
		login = loginF.getText();
		password = passF.getText();
		Etudiant etu=new Etudiant(login,password,nom,prenom);
		if(verifchamp() && Test.loginDispo(login,errorlogin) && Test.verifTaillePassword(password,errorMdp)){
			etu.creerCompte();
			etu.addEtudiant();
			precedent(null);
		};
	}
		



	public boolean verifchamp(){
			boolean rep=false;
			if(login.isEmpty()){setError(errorlogin,"Champ vide");} else{ errorlogin.setText("");}
			if(nom.isEmpty()){setError(errornom,"Champ vide");}else{ errornom.setText("");}
			if(prenom.isEmpty())setError(errorprenom,"Champ vide"); else errorprenom.setText("");
			
			if(!nom.isEmpty()&&!login.isEmpty()&&!prenom.isEmpty()){
				return true;
				
			}
			
				return rep;
	}
	  
	public static void setError(Label err,String msg){
	    	err.setTextFill(Color.RED);
	    	err.setText(msg);
	}

   


}