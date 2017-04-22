package pAdministrateur;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import application.Connexion;
import application.Entreprise;
import application.Etudiant;
import application.OffreStage;
import application.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class adminCompteEtudiant implements Initializable {

	    @FXML
	    private TableView<Etudiant> Utilisateur;
	
	    @FXML
	    private TableColumn LevelCo;

	    @FXML
	    private Button btnRecherche;

	    @FXML
	    private TextField rechercheField;
	   
	    @FXML
	    private Button btnOk;

	    @FXML
	    private TableColumn userCo;
	    
	    @FXML
	    private TextField idField;

	    @FXML
	    private TextField prenomField;
	    
	    @FXML
	    private TextField nomField;
	    
	    @FXML
	    private PasswordField MdpField;
	    
	    @FXML
	    private Label userError;
	    
	    @FXML
	    private Button btnPrecedent;
	    
	    @FXML
		public ComboBox rechercheBox;
		@FXML
	    private Label dateLabel;
	    
	    @FXML
	    private DatePicker pickerDate;
	    @FXML
	    private ImageView photo;
	    @FXML
	    private ImageView quitter;

	    String nom;
	    String prenom;
	    String user;
	    String Mdp;
	    String oldUser;
	    Date dateNaissance;
	    File file;
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
		    root = FXMLLoader.load(getClass().getResource("/pAdministrateur/viewAdmin.fxml"));
		    Scene scene = new Scene(root);
			   stage.setScene(scene);
			   stage.show();
		}
	    
	    @FXML
	    void recherche(ActionEvent event) throws IOException {

	    	if((rechercheBox.getSelectionModel().getSelectedItem().toString()).equals("Login")){
				btnRecherche.setVisible(true);
				rechercheField.setVisible(true);
				btnOk.setVisible(false);
				
				String login=rechercheField.getText();
				if(event.getSource()==btnRecherche){
					
					ObservableList<Etudiant> trieLogin=FXCollections.observableArrayList();
					for (Etudiant e: getEtudiant()){ // on parcourt la liste des offres recupere ds la BD
						if(e.getID().contains(login)){ // on recupere les libelle correpondant ala recherche
						trieLogin.add(e);
						}
					}
					userCo.setCellValueFactory(new PropertyValueFactory<>("iD"));
					LevelCo.setCellValueFactory(new PropertyValueFactory<>("nom"));
					Utilisateur.setItems(trieLogin);
					
					rechercheField.setVisible(false);
					btnRecherche.setVisible(false);
					btnOk.setVisible(true);
			   
				}
			}	
	    	if((rechercheBox.getSelectionModel().getSelectedItem().toString()).equals("Nom")){
				btnRecherche.setVisible(true);
				rechercheField.setVisible(true);
				btnOk.setVisible(false);
				
				if(event.getSource()==btnRecherche){
					String nom=rechercheField.getText();
					ObservableList<Etudiant> trieNom=FXCollections.observableArrayList();
					for (Etudiant e: getEtudiant()){ // on parcourt la liste des offres recupere ds la BD
						if(e.getNom().contains(nom)){ // on recupere les libelle correpondant ala recherche
						trieNom.add(e);
						}
					}
					userCo.setCellValueFactory(new PropertyValueFactory<>("iD"));
					LevelCo.setCellValueFactory(new PropertyValueFactory<>("nom"));
					Utilisateur.setItems(trieNom);
				
				rechercheField.setVisible(false);
				btnRecherche.setVisible(false);
				btnOk.setVisible(true);
			
				}
			}
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
	    @FXML
	    void miseAjour(ActionEvent event) throws IOException {

//	    	if(!addPhoto){
//				String path=new File("").getAbsolutePath();
//				maPhoto=new File(path+"/src/image/profilPhoto.jpeg");
//			}
	    	userError.setText("");
	    	   nom=nomField.getText();
	   	    prenom=prenomField.getText();
	   	    user=idField.getText();
	   	    Mdp=MdpField.getText();
	   	
	   	    if(pickerDate.getValue()!=null){
	   		 dateNaissance=java.sql.Date.valueOf(pickerDate.getValue());
		    }
	   	
	   	    
	   	    if(user.equals(oldUser)||Test.loginDispo(user, userError)){
	   	    Etudiant e=new Etudiant(user,Mdp,nom,prenom,dateNaissance);
	   	    e.setMaPhoto(maPhoto);
	   	    e.miseAJourCompte(oldUser);
	   	    e.miseAJour(oldUser);
	   
	   	    	init();
	   	    	pickerDate.setValue(null);
	   	    }
	    }

	    @FXML
	    void effacer(ActionEvent event) throws IOException {

	    	user=idField.getText();
	    	delete("Utilisateur",user);
	    	delete("Etudiant",user);
	    	init();
	    }

	    private void delete(String table,String user) {
			// TODO Auto-generated method stub
	    	Connection conn=Connexion.ConnexionBD();
			try {PreparedStatement ps=conn.prepareStatement("DELETE FROM `"+table+"` WHERE id='"+user+"'");
			ps.executeUpdate();
			
			
			ps.close();

			} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			}
		}
	    public Image getPhoto(String oldUser) throws SQLException, IOException{
			Image img = null;
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

	
	    
	    public void afficher() throws IOException, SQLException{
	    	userError.setText("");
			Etudiant e=Utilisateur.getSelectionModel().getSelectedItem(); 
			
			nomField.setText(e.getNom());
			prenomField.setText(e.getPrenom()); 
			idField.setText(e.getID());
			oldUser=idField.getText();
			dateLabel.setText(e.getDateNaissance().toString());
			dateNaissance=(Date) e.getDateNaissance();
			String mdp=application.Utilisateur.getBdInfo("SELECT password FROM Utilisateur where id='"+e.getID()+"'");
			MdpField.setText(mdp);
				photo.setImage(getPhoto(oldUser));
				String path=new File("").getAbsolutePath();
				 maPhoto=new File(path+"/src/image/profilPhoto.jpeg");
	    }
	    public void init() throws IOException{
			userCo.setCellValueFactory(new PropertyValueFactory<>("iD"));
			LevelCo.setCellValueFactory(new PropertyValueFactory<>("nom"));// on recupere les libelle de la liste et on ajoute ala colonne mes offres
			
			Utilisateur.setItems(getEtudiant());
			
		}

	    public ObservableList<Etudiant> getEtudiant() throws IOException{
	    	ObservableList etudiant=FXCollections.observableArrayList();
			try{
				
			Connection conn = Connexion.ConnexionBD();
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM Etudiant");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				Etudiant o1=new Etudiant();
				o1.setID(rs.getString(1));
				o1.setNom(rs.getString(2));
				o1.setPrenom(rs.getString(3));
				o1.setDateNaissance(rs.getDate(4));
				etudiant.add(o1);

					};
					
			ps.close();
			
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			return etudiant;	
		}
	    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			        "Login",
			        "Nom"
			        );
		
		rechercheBox.setItems(options);
		btnRecherche.setVisible(false);
		rechercheField.setVisible(false);
	}

}
