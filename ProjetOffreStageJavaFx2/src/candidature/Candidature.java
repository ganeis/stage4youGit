package candidature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

import application.Connexion;
import controller.MenuLoginController;

public class Candidature {
	private int numero;
	private String idEtudiant;
	private int numeroOffre;
	private int validation;
	private String commentaire;
	private String chemin;
	private String nomEntreprise;
	


	public Candidature(String i,int n,String co,String ch){
    	
    	this.idEtudiant=i;
    	this.numeroOffre=n;
    	this.commentaire=co;
    	this.chemin=ch;
    	this.validation=0;
    	
    }

    public Candidature() {
    	this.validation=0;
	}
    
	public String getNomEntreprise() {
		return nomEntreprise;
	}

	public void setNomEntreprise(String nomEntreprise) {
		this.nomEntreprise = nomEntreprise;
	}
    
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public String getIdEtudiant() {
		return idEtudiant;
	}
	
	public void setIdEtudiant(String idEtudiant) {
		this.idEtudiant = idEtudiant;
	}

	public int getNumeroOffre() {
		return numeroOffre;
	}

	public void setNumeroOffre(int numeroOffre) {
		this.numeroOffre = numeroOffre;
	}

	public int getValidation() {
		return validation;
	}

	public void setValidation(int validation) {
		this.validation = validation;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public String getChemin() {
		return chemin;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	
	}
	
	

}
