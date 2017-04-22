package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;


public class Etudiant extends Utilisateur {
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private File maPhoto;

    public Etudiant(){
    	this.level=0;
    }
    public Etudiant(String i,String p,String n,String pr,Date d){
    	this.iD=i;
    	this.level=0; // niveau toujours 0 pour étudiant
    	this.password=p;
    	this.nom=n;
    	this.prenom=pr;
    	dateNaissance=d;
    
    }
    public Etudiant(String i,String p,String n,String pr,Date d,File file){
    	this.iD=i;
    	this.level=0; // niveau toujours 0 pour étudiant
    	this.password=p;
    	this.nom=n;
    	this.prenom=pr;
    	dateNaissance=d;
    	maPhoto=file;
    
    }
    
    public File getMaPhoto() {
		return maPhoto;
	}
	public void setMaPhoto(File maPhoto) {
		this.maPhoto = maPhoto;
	}
	public Date getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
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
	
		FileInputStream fis = null;
		try {
			
			PreparedStatement ps=conn.prepareStatement("insert into Etudiant (id,nom,prenom,dateNaissance,photo) values (?,?,?,?,?)");
			System.out.println(maPhoto);
		fis = new FileInputStream(maPhoto);
		ps.setString(1, getID());
		ps.setString(2,getNom());
		ps.setString(3,getPrenom());
		ps.setDate(4,new java.sql.Date(dateNaissance.getTime()));
		ps.setBinaryStream(5, fis, (int) maPhoto.length());
		ps.executeUpdate();
		ps.close();
		fis.close();
		// rjaoute une fenetre qui affiche que le compte est crée 
		
		} catch (Exception e) {
		System.out.println(e);
		e.printStackTrace();
		}
	}
		
	public void miseAJour(String user) {
		// TODO Auto-generated method stub
		Connection conn=Connexion.ConnexionBD();
		FileInputStream fis = null;
		
		try {PreparedStatement ps=conn.prepareStatement(
				"UPDATE `Etudiant` SET `id`='"+iD+"',`nom`='"+nom+"',`prenom`='"+prenom+"',`dateNaissance`='"+dateNaissance+"',`photo`=? WHERE id='"+user+"'");
		fis = new FileInputStream(maPhoto);
		ps.setBinaryStream(1, fis, (int) maPhoto.length());
		ps.executeUpdate();
			
		fis.close();
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
	
	public static Image getPhoto(String oldUser) throws SQLException, IOException{
		Image img = null;
		File file = null;
		try{String path=new File("").getAbsolutePath();
		 file=new File(path+"/src/image/profilPhoto.jpeg");
		Connection conn = Connexion.ConnexionBD();
		PreparedStatement ps=conn.prepareStatement("SELECT photo FROM Etudiant WHERE id='"+oldUser+"'");
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
            FileOutputStream output = new FileOutputStream(file);
            System.out.println("Writing to file " + file.getAbsolutePath());
           
                InputStream input = rs.getBinaryStream("photo");
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    output.write(buffer);
                }
			input.close();
			output.close();
				};
				
		ps.close();
		
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		BufferedImage bufferedImage = ImageIO.read(file);
		 img = SwingFXUtils.toFXImage(bufferedImage, null);
    	return img;
    	
    }
	
}
	

	


