package pAdministrateur;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AdminController {
	
	@FXML
	private ImageView imgModifierMonCompte;
	
	@FXML
	private ImageView imgModifierCompte;
	
	@FXML
	private ImageView imgModifierEtudiant;
	
	@FXML
	private Label labelEtudiant;
	
	@FXML
	private ImageView imgModifierEntreprise;
	
	@FXML
	private Label labelEntreprise;
	
	@FXML
	private ImageView imgConsulter;
	
	@FXML
	private ImageView imgValider;
	
	@FXML
	private ImageView imgSignalements;
	
	@FXML
	private ImageView imgDéconnexion;
	
	@FXML
	private Button btnDeconnexion;
	
	@FXML
	private ImageView quitter;
	
	
	public void quitter(MouseEvent event){
		System.exit(1);
	}

	public void changeColorIn(MouseEvent event){
		if(event.getSource()==imgValider)imgValider.setBlendMode(BlendMode.RED);
		if(event.getSource()==imgConsulter)imgConsulter.setBlendMode(BlendMode.RED);
		if(event.getSource()==imgModifierEtudiant)imgModifierEtudiant.setBlendMode(BlendMode.RED);
		if(event.getSource()==imgModifierEntreprise)imgModifierEntreprise.setBlendMode(BlendMode.RED);
		if(event.getSource()==imgModifierCompte)imgModifierCompte.setBlendMode(BlendMode.RED);
		if(event.getSource()==imgModifierMonCompte)imgModifierMonCompte.setBlendMode(BlendMode.RED);
		if(event.getSource()==imgSignalements)imgSignalements.setBlendMode(BlendMode.RED);
		if(event.getSource()==imgDéconnexion)imgDéconnexion.setBlendMode(BlendMode.RED);
		
	}
	
	public void changeColorOut(MouseEvent event){
		if(event.getSource()==imgDéconnexion)imgDéconnexion.setBlendMode(BlendMode.SRC_OVER);
		if(event.getSource()==imgValider)imgValider.setBlendMode(BlendMode.SRC_OVER);
		if(event.getSource()==imgConsulter)imgConsulter.setBlendMode(BlendMode.SRC_OVER);
		if(event.getSource()==imgModifierEtudiant)imgModifierEtudiant.setBlendMode(BlendMode.SRC_OVER);
		if(event.getSource()==imgModifierEntreprise)imgModifierEntreprise.setBlendMode(BlendMode.SRC_OVER);
		if(event.getSource()==imgModifierCompte)imgModifierCompte.setBlendMode(BlendMode.SRC_OVER);
		if(event.getSource()==imgModifierMonCompte)imgModifierMonCompte.setBlendMode(BlendMode.SRC_OVER);
		if(event.getSource()==imgSignalements)imgSignalements.setBlendMode(BlendMode.SRC_OVER);
		
	}
	
/*	public void deconnexion(ActionEvent event) throws IOException{
		Stage stage; 
	    Parent root;
		
	    stage=(Stage) btnDeconnexion.getScene().getWindow();
	    root = FXMLLoader.load(getClass().getResource("/controller/MenuLogin.fxml"));
	    Scene scene = new Scene(root);
		   stage.setScene(scene);
		   stage.show();
	}*/
	
	public void handleMouseAction(MouseEvent event) throws IOException {
		Stage stage; 
	    Parent root;
	    
	    if(event.getSource()==imgModifierCompte){
	       imgModifierEtudiant.setVisible(true);
	       imgModifierEntreprise.setVisible(true);
	       imgModifierMonCompte.setVisible(true);
	       
	   }  
	    
	    if(event.getSource()==imgDéconnexion){
	    	stage=(Stage) imgDéconnexion.getScene().getWindow();
		    root = FXMLLoader.load(getClass().getResource("/controller/MenuLogin.fxml"));
		    Scene scene = new Scene(root);
			   stage.setScene(scene);
			   stage.show();
	    }
	    
	    
	   if(event.getSource()==imgModifierMonCompte){
	       stage=(Stage) imgModifierMonCompte.getScene().getWindow();
	       root = FXMLLoader.load(getClass().getResource("/pAdministrateur/viewAdminMonCompte.fxml"));
	       Scene scene = new Scene(root);
		   stage.setScene(scene);
		   stage.show();
	    }
	   

	   
	   
	   if(event.getSource()==imgModifierEtudiant){
		   stage=(Stage) imgModifierCompte.getScene().getWindow();
		   root = FXMLLoader.load(getClass().getResource("/pAdministrateur/viewAdminCompteEtudiant.fxml"));// met la page ke ta crée 
	       Scene scene = new Scene(root);
		   stage.setScene(scene);
		   stage.show();
		   }
		   
	   if(event.getSource()==imgModifierEntreprise){ 
		   stage=(Stage) imgModifierEntreprise.getScene().getWindow();
	       root = FXMLLoader.load(getClass().getResource("/pAdministrateur/viewAdminCompteEntreprise.fxml"));// met la page ke ta crée 
	       Scene scene = new Scene(root);
		   stage.setScene(scene);
		   stage.show();
		}
	
		
	
	   if(event.getSource()==imgConsulter){ 
		   stage=(Stage) imgConsulter.getScene().getWindow();
	       root = FXMLLoader.load(getClass().getResource("/pAdministrateur/viewConsulterAdmin.fxml"));
	       Scene scene = new Scene(root);
		   stage.setScene(scene);
		   stage.show();
	   }	   
		   
	   
	if(event.getSource()==imgValider){ 
			   stage=(Stage) imgValider.getScene().getWindow();
		       root = FXMLLoader.load(getClass().getResource("/pAdministrateur/viewValidation.fxml"));
		       Scene scene = new Scene(root);
			   stage.setScene(scene);
			   stage.show();
    }
	   

	if(event.getSource()==imgSignalements){ 
		   stage=(Stage) imgSignalements.getScene().getWindow();
	       root = FXMLLoader.load(getClass().getResource("/pSignalement/viewSignalement.fxml"));
	       Scene scene = new Scene(root);
		   stage.setScene(scene);
		   stage.show();
	}
	   
	}

}
