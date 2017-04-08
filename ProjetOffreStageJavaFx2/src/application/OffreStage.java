package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

public class OffreStage {
	private int numeroOffre;
	private String nomEntreprise;
    private String libelleOffre;
    private String descriptifOffre;
    private String domaineOffre;
    private Date dateDebutOffre;
    private int dureeOffre;
    private boolean valide;
    
    public OffreStage(String n,String lib,String descp,String dom,Date date,int dur){
    	
    	this.nomEntreprise=n;
    	this.libelleOffre=lib;
    	this.descriptifOffre=descp;
    	this.domaineOffre=dom;
    	this.dateDebutOffre=date;
    	this.dureeOffre=dur;
    	this.valide=false;
    	
    }

    public OffreStage() {
    	this.valide=false;
		// TODO Auto-generated constructor stub
	}

	public int getNumeroOffre() {
		return numeroOffre;
	}


	public String getNomEntreprise() {
		return nomEntreprise;
	}


	public void setNomEntreprise(String nomEntreprise) {
		this.nomEntreprise = nomEntreprise;
	}


	public void setNumeroOffre(int numeroOffre) {
		this.numeroOffre = numeroOffre;
	}


	public String getLibelleOffre() {
        return this.libelleOffre;
    }

 
    public void setLibelleOffre(String value) {
        this.libelleOffre = value;
    }

    public String getDescriptifOffre() {
    	return this.descriptifOffre;
    }


    public void setDescriptifOffre(String value) {
    	this.descriptifOffre = value;
    }
    
    public String getDomaineOffre() {
    	return this.domaineOffre;
    }


    public void setDomaineOffre(String value) {
       this.domaineOffre = value;
    }

    public Date getDateDebutOffre() {

        return this.dateDebutOffre;
    }
    
    public void setDateDebutOffre(Date value) {
    	this.dateDebutOffre = value;
    }

    public int getDureeOffre() {
    	return this.dureeOffre;
    }

    public void setDureeOffre(int value) {
        this.dureeOffre = value;
    }

    public boolean getValide() {
        return this.valide;
    }

    public void setValide(boolean value) {
        this.valide = value;
    }
    
	public void miseAJour() {
		// TODO Auto-generated method stub
		Connection conn=Connexion.ConnexionBD();
		try {PreparedStatement ps=conn.prepareStatement("UPDATE `OffreStage` SET `libelle`='"+libelleOffre+"',`descriptif`='"+descriptifOffre+"',`duree`='"+dureeOffre+"',`dateDebut`='"+dateDebutOffre+"',`domaine`='"+domaineOffre+"' WHERE numero='"+numeroOffre+"'");
		ps.executeUpdate();
			
		
		ps.close();

		} catch (Exception e) {
		System.out.println(e);
		e.printStackTrace();
		}
	}
		
    public void posterOffre(){
    	Connection conn=Connexion.ConnexionBD();
		try {PreparedStatement ps=conn.prepareStatement("insert into OffreStage (numero,nomEntreprise,libelle,descriptif,"
				+ "domaine,dateDebut,duree,valide) values (?,?,?,?,?,?,?,?)");
		ps.setInt(1,numeroOffre); //car id s'incremente tout seule dans la BD
		ps.setString(2,nomEntreprise);
		ps.setString(3,libelleOffre);
		ps.setString(4,descriptifOffre);
		ps.setString(5,domaineOffre.toString()); // on le met en string car c un enum de base
		ps.setDate(6,new java.sql.Date(dateDebutOffre.getTime()));
		ps.setInt(7,dureeOffre);
		ps.setBoolean(8,valide);
		ps.executeUpdate();
		ps.close();
		
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
    }
    public void validerOffre(){
    	Connection conn=Connexion.ConnexionBD();
		try {PreparedStatement ps=conn.prepareStatement("UPDATE `OffreStage` SET `valide`=1 WHERE numero='"+numeroOffre+"'");
		ps.executeUpdate();
		
		System.out.println("valider");
		ps.close();

		} catch (Exception e) {
		System.out.println(e);
		e.printStackTrace();
		}
    }
    
    
}
