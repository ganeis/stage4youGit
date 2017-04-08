package application;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Administrateur extends Utilisateur {
    private String nom;
    private String prenom;
    
    
    public Administrateur(){
    	this.iD="admin";
    	this.level=2; // niveau toujours 2 pour l'admin
    	this.password="admin";
    	this.nom="ADMINNom";
    	this.prenom="ADMINPrenom";
    }
    
    // mettre tous les methodes set que pour admin ? changement nom prenom seulement l'admin peu le faire ?
    
    public void setNom(String value) {
        this.nom = value;
    }

    public String getNom() {
        return this.nom;
    }

    public void setPrenom(String value) {
        this.prenom = value;
    }

    public String getPrenom() {
        return this.prenom;
    }
   
	public void creerCompte() {
		Connection conn=Connexion.ConnexionBD();
		try {PreparedStatement ps=conn.prepareStatement("insert into Utilisateur (id,password,level) values (?,?,?)");
		ps.setString(1, getID());
		ps.setString(2,getPassword());
		ps.setInt(3,2);//level toujours 2
		ps.executeUpdate();
		ps.close();

		} catch (Exception e) {
		System.out.println(e);
		e.printStackTrace();
		}
	}
	
	public void addAdministrateur(){
		Connection conn=Connexion.ConnexionBD();
		try {PreparedStatement ps=conn.prepareStatement("insert into Administrateur (id,nom,prenom) values (?,?,?)");
		ps.setString(1, getID());
		ps.setString(2,getNom());
		ps.setString(3,getPrenom());
		ps.executeUpdate();
		ps.close();
		// rjaoute une fenetre qui affiche que le compte est crée JDialog??ou??
		
		} catch (Exception e) {
		System.out.println(e);
		e.printStackTrace();
		}
	}
		
    
    // si signaler par etudiant que loffre est plu disponible ou erroné
    public void supprimerOffre(){}
    
    public void supprimerCompte(){}
    
    // si demande d'une entreprise ou etudiant ou oublie et renvoie mot de passe ?
    public void modifierCompte(){}


	
    
    
}