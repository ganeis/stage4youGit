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
import application.enumRecherche;
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

public class AdminCompteEntrepriseController implements Initializable {

	

	    @FXML
	    private Label userError;

	    @FXML
	    private TableColumn LevelCo;

	    @FXML
	    private TextField idField;
	    
	    @FXML
	    private TextField activiteField;


	   @FXML
	   private TextField telephoneField;

	    @FXML
	    private TextField villeField;

	    @FXML
	    private TextField adresseField;

	    @FXML
	    private TextField mailField;
	    
	    @FXML
	    private TextField codePostalField;

	    @FXML
	    private PasswordField MdpField;

	    @FXML
	    private Button btnRecherche;
	   
	    

	    @FXML
	    private TextField rechercheField;

	    @FXML
	    private TableView<Entreprise> Utilisateur;
	    
	    @FXML
	    private Button btnPrecedent;

	    @FXML
	    private TableColumn  userCo;

	    @FXML
	    private TextField nomField;
	    
		@FXML
		private Button btnOk;
		
		@FXML
		public ComboBox rechercheBox;
		
		
		
	    
	    String user=null;
		String password=null;
		String nom=null;
		String adress=null;
		String codepostal=null;
		String ville=null;
		String mail=null;
		String tel=null;
		String secteur=null;
        String oldUser;
	    
        @FXML
	    void afficher() {

	    	userError.setText("");
	  			Entreprise e=Utilisateur.getSelectionModel().getSelectedItem(); 
	  			String mdp=application.Utilisateur.getBdInfo("SELECT password FROM Utilisateur where id='"+e.getID()+"'");
				MdpField.setText(mdp);
	  			nomField.setText(e.getNomEntreprise());
	  			villeField.setText(e.getAdresseVilleEntreprise()); 
	  			idField.setText(e.getID());
	  			oldUser=idField.getText();
	  			mailField.setText(e.getmail());
	  			adresseField.setText(e.getAdresseRueEntreprise());
	  			codePostalField.setText(e.getCodePostal());
	  			telephoneField.setText(e.getTelEntreprise());
	  			activiteField.setText(e.getSecteur());
	  			
	  		
	  	    }
	    

	    @FXML
	    void miseAjour(ActionEvent event) {
            
	    	userError.setText("");
	    	user=idField.getText();
			password=MdpField.getText();
			nom=nomField.getText();
			adress=adresseField.getText();
			codepostal=codePostalField.getText();
			ville=villeField.getText();
			mail=mailField.getText();
			tel=telephoneField.getText();
			secteur=activiteField.getText();
			 if(user.equals(oldUser)||Test.loginDispo(user, userError)){
	  				Entreprise e1=new Entreprise(user,password,nom,adress,ville,codepostal,mail,tel,secteur);
	  		   	    e1.miseAJourCompte(oldUser);
	  		   	    e1.miseAJour(oldUser);
	  		   
	  		   	    	init();
	  		   	    }
			
	    }

	    @FXML
	    void effacer(ActionEvent event) {

	    	user=idField.getText();
	    	delete("Utilisateur",user);
	    	delete("Entreprise",user);
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
					
					ObservableList<Entreprise> trieLogin=FXCollections.observableArrayList();
					for (Entreprise e: getEntreprise()){ // on parcourt la liste des offres recupere ds la BD
						if(e.getID().contains(login)){ // on recupere les libelle correpondant ala recherche
						trieLogin.add(e);
						}
					}
					userCo.setCellValueFactory(new PropertyValueFactory<>("iD"));
					LevelCo.setCellValueFactory(new PropertyValueFactory<>("nomEntreprise"));
					Utilisateur.setItems(trieLogin);
					
					rechercheField.setVisible(false);
					btnRecherche.setVisible(false);
					btnOk.setVisible(true);
			   
				}
			}	
	    	if((rechercheBox.getSelectionModel().getSelectedItem().toString()).equals("Nom Entreprise")){
				btnRecherche.setVisible(true);
				rechercheField.setVisible(true);
				btnOk.setVisible(false);
				
				if(event.getSource()==btnRecherche){
					String nom=rechercheField.getText();
					ObservableList<Entreprise> trieNom=FXCollections.observableArrayList();
					for (Entreprise e: getEntreprise()){ // on parcourt la liste des offres recupere ds la BD
						if(e.getNomEntreprise().contains(nom)){ // on recupere les libelle correpondant ala recherche
						trieNom.add(e);
						}
					}
					userCo.setCellValueFactory(new PropertyValueFactory<>("iD"));
					LevelCo.setCellValueFactory(new PropertyValueFactory<>("nomEntreprise"));
					Utilisateur.setItems(trieNom);
				
					rechercheField.setVisible(false);
					btnRecherche.setVisible(false);
					btnOk.setVisible(true);
				}
			}
			
	    }
	    public ObservableList<Entreprise> getEntreprise(){
	    	ObservableList entreprise=FXCollections.observableArrayList();
			try{
			Connection conn = Connexion.ConnexionBD();
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM Entreprise");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				Entreprise o1=new Entreprise();
				o1.setID(rs.getString(1));
				o1.setNomEntreprise(rs.getString(2));
				o1.setAdresseVilleEntreprise(rs.getString(3));
				o1.setmail(rs.getString(4));
				o1.setAdresseRueEntreprise(rs.getString(5));
				o1.setCodePostal(rs.getString(6));
				o1.setTelEntreprise(rs.getString(7));
				o1.setSecteur(rs.getString(8));
				
				entreprise.add(o1);
					};
			ps.close();
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			return entreprise;	
		}
	    
	    public void init(){
			userCo.setCellValueFactory(new PropertyValueFactory<>("iD"));
			LevelCo.setCellValueFactory(new PropertyValueFactory<>("nomEntreprise"));// on recupere les libelle de la liste et on ajoute ala colonne mes offres
			
			Utilisateur.setItems(getEntreprise());
			
		}

	

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		init();
		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			        "Login",
			        "Nom Entreprise"
			        );
		
		rechercheBox.setItems(options);
		btnRecherche.setVisible(false);
		rechercheField.setVisible(false);
	}

}
