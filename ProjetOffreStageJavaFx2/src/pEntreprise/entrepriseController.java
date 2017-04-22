package pEntreprise;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.Entreprise;
import application.Test;
import application.enumSecteur;
import controller.Alerte;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public  class entrepriseController implements Initializable {
	
	@FXML
    private AnchorPane mapage;
	
    @FXML
    private Label errorTel;

    @FXML
    public Label errorMdp;


    @FXML
    private Label errorVille;

    @FXML
    private TextField nomEntrepriseField;

    @FXML
    private Button btnPrecedent;

    @FXML
    private Label errorCP;

    @FXML
    private Button btnValider;

    @FXML
    private TextField codePostalField;

    @FXML
    private Label errorAdr;

    @FXML
    private Label errorNom;

    @FXML
    private TextField telephoneField;

    @FXML
    private TextField villeField;

    @FXML
    private TextField adresseField;

    @FXML
    private TextField mailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorlogin;

    @FXML
    private TextField loginField;

    @FXML
    private Label errorMail;
    @FXML
   ComboBox <enumSecteur> secteurbox=new ComboBox<>();
   @FXML
   private ImageView quitter;
   
   private AnchorPane root;


    
    
  
    
    String login=null;
	String password=null;
	String nom=null;
	String adress=null;
	String codepostal=null;
	String ville=null;
	String mail=null;
	String tel=null;
	String secteur=null;
	public void quitter(MouseEvent event){
		System.exit(1);
	}
	public void precedent(ActionEvent event) throws IOException{
		Stage stage; 
	    Parent root;
		
	    stage=(Stage) btnPrecedent.getScene().getWindow();
	    root = FXMLLoader.load(getClass().getResource("/controller/MenuLogin.fxml"));
	    Scene scene = new Scene(root);
		   stage.setScene(scene);
		   stage.show();
	}
	
	public void Validation(ActionEvent event) throws IOException {


		login=loginField.getText();
		password=passwordField.getText();
		nom=nomEntrepriseField.getText();
		adress=adresseField.getText();
		codepostal=codePostalField.getText();
		ville=villeField.getText();
		mail=mailField.getText();
		tel=telephoneField.getText();
		String secteur;
		


	if(verifchamp() && Test.loginDispo(login,errorlogin) && Test.verifTaillePassword(password,errorMdp) 
			&& Test.verifCodePostal(codepostal,errorCP)&& Test.verifTel(tel,errorTel)){
		secteur=secteurbox.getSelectionModel().getSelectedItem().toString();
		Entreprise e1=new Entreprise(login,password,nom,adress,ville,codepostal,mail,tel,secteur);
		e1.creerCompte();
		e1.addEntreprise();
		
	    precedent(null); 
	}

}

  
	
	@FXML
    public boolean verifchamp(){
		boolean rep=false;
		
		if(login.isEmpty()){setError(errorlogin,"Champ vide");} else{ errorlogin.setText("");}
		if(nom.isEmpty()){setError(errorNom,"Champ vide");}else{ errorNom.setText("");}
		if(adress.isEmpty())setError(errorAdr,"Champ vide"); else errorAdr.setText("");
		if(codepostal.isEmpty())setError(errorCP,"Champ vide");else errorCP.setText("");
		if(ville.isEmpty())setError(errorVille,"Champ vide");else errorVille.setText("");
		if(mail.isEmpty())setError(errorMail,"Champ vide");else errorMail.setText("");
		if(tel.isEmpty())setError(errorTel,"Champ vide");else errorTel.setText("");
		if(password.isEmpty())setError(errorMdp,"Champ vide");else errorMdp.setText("");
		
		
		if(!login.isEmpty()&&!nom.isEmpty()&&!adress.isEmpty()&&!codepostal.isEmpty()&&!ville.isEmpty()&&!mail.isEmpty()&&!tel.isEmpty()){
			rep=true;
			
		}
		
		return rep;
		
		}
    @FXML
    void verifflogin(ActionEvent event) {
    	if(!login.isEmpty()&&!nom.isEmpty()&&!adress.isEmpty()&&!codepostal.isEmpty()&&!ville.isEmpty()&&!mail.isEmpty()&&!tel.isEmpty()){
			errorlogin.setText("champ vide");
			
		}
    }
    
  
    
    public static void setError(Label err,String msg){
    	err.setTextFill(Color.RED);
    	err.setText(msg);
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		secteurbox.getItems().setAll(enumSecteur.values());
	}
    
 

}

