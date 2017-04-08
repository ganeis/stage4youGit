package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import controller.MenuLoginController;
import javafx.scene.control.Label;

public abstract class Utilisateur {
	// proteced pour que les heriter entreprise etudiant admin 
    protected String iD;
    protected int level;
    protected String password;
    
    // pas neccesiare le set vu qu'on itinitialse ala contruction donc on supprime ?
    public void setID(String value) {
    	this.iD = value;
    }
    
    public String getID() {
    	return iD;
    }

    // seulement admin qui peut ???? private ?
    public void setLevel(int value) {
     
        this.level = value;
    }

 
    public int getLevel() {
       
        return this.level;
    }

    
    public void setPassword(String value) {
       
        this.password = value;
    }

    // ???? private ??
    public String getPassword() {
        return this.password;
    }
    
    //tester si un des champs n'existe pas deja exemple 
    public abstract void creerCompte(); // on le redefini dans entreprise/etudiant à cause du level
    	
    // tester avvec la base de donnée si ID et mdp vrai.
    public static boolean seConnecter(String id, String password,Label err,Label err2){
    	if(!Test.verifLogin(id,err)){
			if(Test.verifPassword(password,id,err2)){
				return true; 
				
			}
		};
		return false;
    }
    public static int getBdLevel(String user){
    	int rep=0;
    	Connection conn=Connexion.ConnexionBD();
		
		try {
		PreparedStatement ps=conn.prepareStatement("SELECT `level` FROM `Utilisateur`WHERE id='"+user+"'");
		
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			rep=rs.getInt(1);
				
				
			
		}ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return rep;
	}
    
    public static String getBdInfo(String msg){
    	String rep = null;
    	Connection conn=Connexion.ConnexionBD();
		
		try {
		PreparedStatement ps=conn.prepareStatement(msg);
		
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			rep=rs.getString(1);
				
				
			
		}ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return rep;
	}
    
    public static int getBdInfoInt(String msg){
    	int rep = 0;
    	Connection conn=Connexion.ConnexionBD();
		
		try {
		PreparedStatement ps=conn.prepareStatement(msg);
		
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			rep=rs.getInt(1);
				
				
			
		}ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return rep;
	}
    
    public void consulterOffre(){}
    
    public void signalerOffre(){}
    

}
