package application;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

public class Entreprise extends Utilisateur {
    
    private String nomEntreprise;
    private String adresseVilleEntreprise;
    private String adresseRueEntreprise;
    private String codePostal;
    private String telEntreprise;
    private String mail;
    private String secteur;
    
    public Entreprise(){
    	this.level=1;
    }
    public String getSecteur() {
		return secteur;
	}
	public void setSecteur(String secteur) {
		this.secteur = secteur;
	}
	public Entreprise(String i,String p,String n,String a,String v,String c,String m,String t,String sec){
    	this.iD=i;
    	this.level=1; // niveau toujours 1 pour entreprise
    	this.password=p;
    	this.nomEntreprise=n;
        this.adresseVilleEntreprise=v;
        this.adresseRueEntreprise=a;
        this.codePostal=c;
        this.telEntreprise=t;
        this.mail=m;
        secteur=sec;
    }
    
    public String getNomEntreprise() {
        return this.nomEntreprise;
    }

 
    public void setNomEntreprise(String value) {
       this.nomEntreprise = value;
    }

    public String getAdresseVilleEntreprise() {
     
        return this.adresseVilleEntreprise;
    }


    public void setAdresseVilleEntreprise(String value) {
     
        this.adresseVilleEntreprise = value;
    }


    public String getAdresseRueEntreprise() {
      
        return this.adresseRueEntreprise;
    }

 
    public void setAdresseRueEntreprise(String value) {
     
        this.adresseRueEntreprise = value;
    }

 
    public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostale) {
		this.codePostal = codePostale;
	}

	public String getTelEntreprise() {
      
        return this.telEntreprise;
    }


    public void setTelEntreprise(String value) {
      
        this.telEntreprise = value;
    }

   
    public String getmail() {
       
        return this.mail;
    }

   
    public void setmail(String value) {
       this.mail = value;
    }

    
    public void supprimerOffre(){}

	
	public void creerCompte() {
		Connection conn=Connexion.ConnexionBD();
		try {PreparedStatement ps=conn.prepareStatement("insert into Utilisateur (id,level,password) values (?,?,?)");
		ps.setString(1, iD);
		ps.setInt(2,1);//level toujours 1
		ps.setString(3,password);
		ps.executeUpdate();
		ps.close();

		} catch (Exception e) {
		System.out.println(e);
		e.printStackTrace();
		}
	}	
	
	public void addEntreprise(){
		Connection conn=Connexion.ConnexionBD();
		try {PreparedStatement ps=conn.prepareStatement("insert into Entreprise (id,nom,ville,mail,rue,codePostal,telephone,secteur) values (?,?,?,?,?,?,?,?)");
		ps.setString(1, iD);
		ps.setString(2,getNomEntreprise());
		ps.setString(3,getAdresseVilleEntreprise());
		ps.setString(5,getAdresseRueEntreprise());
		ps.setString(6,getCodePostal());
		ps.setString(7,telEntreprise);
		ps.setString(4,mail);
		ps.setString(8,secteur);
		ps.executeUpdate();
		ps.close();

		} catch (Exception e) {
		System.out.println(e);
		e.printStackTrace();
		}
	}
	
	public void miseAJour(String user) {
		// TODO Auto-generated method stub
		Connection conn=Connexion.ConnexionBD();
		try {PreparedStatement ps=conn.prepareStatement("UPDATE `Entreprise` SET `id`='"+iD+"',`nom`='"+nomEntreprise+"',`ville`='"+adresseVilleEntreprise+
				"',`mail`='"+mail+"',`rue`='"+adresseRueEntreprise+"',`codePostal`='"+codePostal+"',`telephone`='"+telEntreprise+"',`secteur`='"+secteur+
				"' WHERE id='"+user+"'");
		
	
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
		try {PreparedStatement ps=conn.prepareStatement("UPDATE `Utilisateur` SET `id`='"+iD+"',`password`='"+password+"',`level`='1' WHERE id='"+user+"'");
		ps.executeUpdate();
		
		System.out.println("mise a jour compte ");
		ps.close();

		} catch (Exception e) {
		System.out.println(e);
		e.printStackTrace();
		}
	}
    
}
