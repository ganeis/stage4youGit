package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import com.sun.prism.paint.Color;

import controller.MenuLoginController;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pEntreprise.entrepriseController;

public class Test {

	// Test si identifiant dispo
	public static boolean verifLogin(String a,Label err){
		Connection conn=Connexion.ConnexionBD();
		boolean rep=true;
		try {
		PreparedStatement ps=conn.prepareStatement("SELECT `id` FROM `Utilisateur`");
		
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			if(rs.getString(1).equals(a)){
				MenuLoginController.setError(err,"");
				return false;
				
			};
		}ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		MenuLoginController.setError(err,"Nom utilisateur erroné");
		return rep;
	}
	
	//Test si mot de passe dans BD
	public static boolean verifPassword(String a,String user,Label err){
		Connection conn=Connexion.ConnexionBD();
		boolean rep=false;
		try {
		PreparedStatement ps=conn.prepareStatement("SELECT `password` FROM `Utilisateur`WHERE id='"+user+"'");
		
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			if(rs.getString(1).equals(a)){
				MenuLoginController.setError(err,"");
				return true;
				
			};
		}ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MenuLoginController.setError(err,"Mot de passe erroné");
		return rep;
	}
	
	public static boolean loginDispo(String a,Label err){
		entrepriseController.setError(err,"");
		Connection conn=Connexion.ConnexionBD();
		boolean rep=true;
		try {
		PreparedStatement ps=conn.prepareStatement("SELECT `id` FROM `Utilisateur`");
		
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			if(rs.getString(1).equals(a)){
				
				entrepriseController.setError(err,"Nom utilisateur déja pris !");
				rep=false;
			};
		}ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rep;
	}
	
	//test taille du mot de passe correct
	
	public static boolean verifTaillePassword(String p,Label err){
		entrepriseController.setError(err,"");
		boolean rep=false;
		if(p.length()>=8){
			return true;
			
		}
		
		
		entrepriseController.setError(err,"Mot de passe trop court");
		
			return rep;
		}

				
	public static boolean verifCodePostal(String p,Label err){
		boolean rep=false;
		entrepriseController.setError(err,"");
		if(p.length()==5){
			return true;
			
		}
		entrepriseController.setError(err, "Code Postal erroné ");
			return rep;
	}
	
	public static boolean verifTel(String p,Label err){
		boolean rep=false;
		entrepriseController.setError(err,"");
		if(p.length()==10){
			return true;
			
		}entrepriseController.setError(err,"Telephone erroné");
			return rep;
	}

}
