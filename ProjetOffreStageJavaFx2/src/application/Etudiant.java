package application;

import java.sql.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Etudiant extends Utilisateur {
    private String nom;
    public String prenom;

    public Etudiant(){
    	this.level=0;
    }
    public Etudiant(String i,String p,String n,String pr){
    	this.iD=i;
    	this.level=0; // niveau toujours 0 pour étudiant
    	this.password=p;
    	this.nom=n;
    	this.prenom=pr;
    }
    
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
    
    public void repondreOffre(){}

	
	public void creerCompte() {
		Connection conn=Connexion.ConnexionBD();
		try {PreparedStatement ps=conn.prepareStatement("insert into Utilisateur (id,level,password) values (?,?,?)");
		ps.setString(1, getID());
		ps.setInt(2,0);//level toujours 0
		ps.setString(3,getPassword());
		ps.executeUpdate();
		ps.close();

		} catch (Exception e) {
		System.out.println(e);
		e.printStackTrace();
		}
	}	
		
	public void addEtudiant(){
		Connection conn=Connexion.ConnexionBD();
		try {PreparedStatement ps=conn.prepareStatement("insert into Etudiant (id,nom,prenom) values (?,?,?)");
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
		
	public void miseAJour(String user) {
		// TODO Auto-generated method stub
		Connection conn=Connexion.ConnexionBD();
		try {PreparedStatement ps=conn.prepareStatement("UPDATE `Etudiant` SET `id`='"+iD+"',`nom`='"+nom+"',`prenom`='"+prenom+"' WHERE id='"+user+"'");
		ps.executeUpdate();
			
		
		ps.close();

		} catch (Exception e) {
		System.out.println(e);
		e.printStackTrace();
		}
	}	
	
	public void miseAJourCompte(String user) {
		// TODO Auto-generated method stub
		Connection conn=Connexion.ConnexionBD();
		try {PreparedStatement ps=conn.prepareStatement("UPDATE `Utilisateur` SET `id`='"+iD+"',`password`='"+password+"',`level`='0' WHERE id='"+user+"'");
		ps.executeUpdate();
		
		System.out.println("mise a jour compte ");
		ps.close();

		} catch (Exception e) {
		System.out.println(e);
		e.printStackTrace();
		}
	}
	
}
	

	


