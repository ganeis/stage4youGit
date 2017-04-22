package pEtudiant;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;

import application.Connexion;
import application.Etudiant;
import application.Test;
import controller.Alerte;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class etudiantController {

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
    private Button btnValider;
	@FXML
    private Label errorDate;
	
    @FXML
    private ImageView photo;

    @FXML
    private DatePicker pickerDate;

    @FXML
    private ImageView quitter;
    String nom ;
	String prenom;
	String login;
	String password ;
	Date dateNaissance;
	File maPhoto;
	boolean addPhoto=false;
	
	public void quitter(MouseEvent event){
		System.exit(1);
	}
	@FXML
	public void precedent(ActionEvent event) throws IOException{
		Stage stage; 
	    Parent root;
		
	    stage=(Stage) btnPrecedent.getScene().getWindow();
	    root = FXMLLoader.load(getClass().getResource("/controller/MenuLogin.fxml"));
	    Scene scene = new Scene(root);
		   stage.setScene(scene);
		   stage.show();
	}
	
    public void valider(ActionEvent event) throws IOException {
		nom = nomF.getText();
		prenom = prenomF.getText();
		login = loginF.getText();
		password = passF.getText();
		if(!addPhoto){
			String path=new File("").getAbsolutePath();
			maPhoto=new File(path+"/src/image/defautPhoto.jpeg");
		}
		
		if(verifchamp() && Test.loginDispo(login,errorlogin) && Test.verifTaillePassword(password,errorMdp)){
			dateNaissance=java.sql.Date.valueOf(pickerDate.getValue());
			Etudiant etu=new Etudiant(login,password,nom,prenom,dateNaissance,maPhoto);
			
			etu.creerCompte();
			etu.addEtudiant();
			precedent(null);
		};
	}
		



	public boolean verifchamp(){
			boolean rep=false;
			if(login.isEmpty()){setError(errorlogin,"Champ vide");} else{ errorlogin.setText("");}
			if(nom.isEmpty()){setError(errornom,"Champ vide");}else{ errornom.setText("");}
			if(prenom.isEmpty())setError(errorprenom,"Champ vide"); else errorprenom.setText("");
			if(pickerDate.getValue()==null)setError(errorDate,"Champ vide");else errorDate.setText("");
			if(!nom.isEmpty()&&!login.isEmpty()&&!prenom.isEmpty()&&pickerDate.getValue()!=null){
				return true;
				
			}
			
				return rep;
	}
	  
	  @FXML
	    void ajoutPhoto(ActionEvent event) throws IOException {

		  FileChooser fc = new FileChooser();
		  FileChooser.ExtensionFilter extFilter = 
					new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG", "jpg files (*.jpg)", "*.jpg",
							"PNG files (*.PNG)", "*.PNG","png files (*.png)", "*.png",
							"JPEG files (*.JPEG)", "*.JPEG","jpeg files (*.jpeg)", "*.jpeg");
			fc.getExtensionFilters().addAll(extFilter);
			File f=fc.showOpenDialog(null);
			String filename = f.getAbsolutePath();
			maPhoto=new File(filename);
		    BufferedImage bufferedImage = ImageIO.read(f);
			Image img = SwingFXUtils.toFXImage(bufferedImage, null);
			photo.setImage(img);
			addPhoto=true;
	    }
	  
	public static void setError(Label err,String msg){
	    	err.setTextFill(Color.RED);
	    	err.setText(msg);
	}

	

}