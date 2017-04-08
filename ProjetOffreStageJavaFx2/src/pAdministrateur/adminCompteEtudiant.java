package pAdministrateur;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.Connexion;
import application.Entreprise;
import application.Etudiant;
import application.OffreStage;
import application.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
	    
	    

	    String nom;
	    String prenom;
	    String user;
	    String Mdp;
	    String oldUser;
	    
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
	    void recherche(ActionEvent event) {

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
	    void miseAjour(ActionEvent event) {

	    	userError.setText("");
	    	   nom=nomField.getText();
	   	    prenom=prenomField.getText();
	   	    user=idField.getText();
	   	    Mdp=MdpField.getText();
	   	    
	   	    if(user.equals(oldUser)||Test.loginDispo(user, userError)){
	   	    Etudiant e=new Etudiant(user,Mdp,nom,prenom);
	   	    e.miseAJourCompte(oldUser);
	   	    e.miseAJour(oldUser);
	   
	   	    	init();
	   	    }
	    }

	    @FXML
	    void effacer(ActionEvent event) {

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


	
	    
	    public void afficher(){
	    	userError.setText("");
			Etudiant e=Utilisateur.getSelectionModel().getSelectedItem(); 
			
			nomField.setText(e.getNom());
			prenomField.setText(e.getPrenom()); 
			idField.setText(e.getID());
			oldUser=idField.getText();
			String mdp=application.Utilisateur.getBdInfo("SELECT password FROM Utilisateur where id='"+e.getID()+"'");
			MdpField.setText(mdp);
	    }
	    public void init(){
			userCo.setCellValueFactory(new PropertyValueFactory<>("iD"));
			LevelCo.setCellValueFactory(new PropertyValueFactory<>("nom"));// on recupere les libelle de la liste et on ajoute ala colonne mes offres
			
			Utilisateur.setItems(getEtudiant());
			
		}

	    public ObservableList<Etudiant> getEtudiant(){
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
		init();
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
