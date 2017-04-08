package pAdministrateur;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.Connexion;
import application.Etudiant;
import application.OffreStage;
import application.Test;
import controller.Alerte;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AdminMonCompteController implements Initializable{

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
    private Button btnModifier;
    
    String nom ;
	String prenom;
	String login;
	String password ;
	
	@FXML
	public void precedent(ActionEvent event) throws IOException{
		Stage stage; 
	    Parent root;
		
	    stage=(Stage) btnPrecedent.getScene().getWindow();
	    root = FXMLLoader.load(getClass().getResource("/pAdministrateur/viewAdmin.fxml"));
	    Scene scene = new Scene(root);
		   stage.setScene(scene);
		   stage.show();
	}
	
    // modifier les infos du compte de l'admin
    public void modifier(ActionEvent event) {
		nom = nomF.getText();
		prenom = prenomF.getText();
		login = loginF.getText();
		password = passF.getText();
		
		if(verifchamp() && Test.verifTaillePassword(password,errorMdp)){
			try{
				Connection conn = Connexion.ConnexionBD();
				PreparedStatement ps=conn.prepareStatement("UPDATE `Administrateur` SET `nom`='"+nom+"',`prenom`='"+prenom+"' WHERE id='"+login+"'"); 
				ps.executeUpdate();
			    ps.close();
			    
				ps=conn.prepareStatement("UPDATE `Utilisateur` SET `password`='"+password+"' WHERE id='"+login+"'"); 
				ps.executeUpdate();
			    ps.close();
			    Alerte.display("Modification","Le compte Admin est modifié");	
			   } 
			
			catch (SQLException e) {
					e.printStackTrace();
			}
		}
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
	
	public void setChamp(){
		try{
			Connection conn = Connexion.ConnexionBD();
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM Administrateur"); // on recup id,nom, prenom de bd admin
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				loginF.setText(rs.getString(1));
				nomF.setText(rs.getString(2));
				prenomF.setText(rs.getString(3));
			}	
			ps.close();
			
			String login=loginF.getText();
			ps=conn.prepareStatement("SELECT password FROM Utilisateur where id='"+login+"'");
			rs=ps.executeQuery();
			while(rs.next()){
				passF.setText(rs.getString(1));
				
			}	
			ps.close();
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setChamp();
	}

   


}