package pAdministrateur;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import application.Connexion;
import application.Etudiant;
import application.OffreStage;
import application.Utilisateur;
import application.enumRecherche;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class validationController implements Initializable {



	    @FXML
	    private Text textDomaine;

	    @FXML
	    private Button btnOk;

	    @FXML
	    private TextField textfieldLibelle;

	    @FXML
	    private TextField textfieldNomEntreprise;

	    @FXML
	    private TableColumn Colonne;

	    @FXML
	    private Text textLibelle;

	    @FXML
	    private TextField textfieldDuree;

	    @FXML
	    private Button btnTrier;

	    @FXML
	    private TableView<OffreStage> mesOffres;

	    @FXML
		public ComboBox <enumRecherche> rechercheBox=new ComboBox<>();

	    @FXML
	    private Text textNumeroOffre;

	    @FXML
	    private Text textNomEntreprise;

	    @FXML
	    private Text textDuree;

	    @FXML
	    private Text textDescriptif;
	    
	    @FXML
	    private Button btnPrecedent;

	    @FXML
	    private Text textDate;
	    Date dateDebutOffre;
	    
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
	    void afficher() {

	    	
				OffreStage o=mesOffres.getSelectionModel().getSelectedItem(); // on recupere l'offre sur laquel on clik
				
				textNomEntreprise.setText(o.getNomEntreprise());
				textNumeroOffre.setText(Integer.toString(o.getNumeroOffre())); 
				textLibelle.setText(o.getLibelleOffre());
				textDescriptif.setText(o.getDescriptifOffre());
				textDate.setText(o.getDateDebutOffre().toString());
				textDuree.setText(Integer.toString(o.getDureeOffre()));
				textDomaine.setText(o.getDomaineOffre());
				textDuree.setText(Integer.toString(o.getDureeOffre()));
				dateDebutOffre=o.getDateDebutOffre();
		}
	    

	    @FXML
	    void effacer(ActionEvent event) {
	    	OffreStage e=mesOffres.getSelectionModel().getSelectedItem();
	    	int numero=e.getNumeroOffre();
	    	delete("OffreStage",numero);
	    	init();
	    }
	    

	    private void delete(String table,int user) {
			// TODO Auto-generated method stub
	    	Connection conn=Connexion.ConnexionBD();
			try {PreparedStatement ps=conn.prepareStatement("DELETE FROM `"+table+"` WHERE numero='"+user+"'");
			ps.executeUpdate();
			ps.close();

			} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			}
		}

	    @FXML
	    void valider(ActionEvent event) {

	    	 int numeroOffre;
	    	String nomEntreprise;
	        String libelleOffre;
	        String descriptifOffre;
	         String domaineOffre;
	        int dureeOffre;
	        
	        nomEntreprise=textfieldNomEntreprise.getText();
	        libelleOffre=textfieldLibelle.getText();
	        descriptifOffre=textDescriptif.getText();
	        domaineOffre=textDomaine.getText();
	        numeroOffre=Integer.parseInt(textNumeroOffre.getText());
	        dureeOffre=Integer.parseInt(textDuree.getText());
	        
	        OffreStage o1=new OffreStage(nomEntreprise,libelleOffre,descriptifOffre,domaineOffre,dateDebutOffre,dureeOffre);
	    	o1.setNumeroOffre(numeroOffre);
	    	o1.validerOffre();
	    	init();
	    	vider();
	    	
	    }
	    
	    public void vider(){
	    	textfieldNomEntreprise.setText("");
	    	textNumeroOffre.setText("");
	    	textfieldLibelle.setText("");
	    	textDescriptif.setText("");
	    	textDomaine.setText("");
	    	textDuree.setText("");
	    	
		}
		
	    // Rechercher
	    public void buttonHandleAction(ActionEvent event){
			if((rechercheBox.getSelectionModel().getSelectedItem().toString()).equals("Libelle")){
				btnTrier.setVisible(true);
				textfieldLibelle.setVisible(true);
				btnOk.setVisible(false);
				
				if(event.getSource()==btnTrier){
					String libelle=textfieldLibelle.getText();
					ObservableList<OffreStage> trieLibelle=FXCollections.observableArrayList();
					for (OffreStage e: getOffreStage()){ // on parcourt la liste des offres recupere ds la BD
						if(e.getLibelleOffre().contains(libelle)){ // on recupere les libelle correpondant ala recherche
						trieLibelle.add(e);
						}
					}
				Colonne.setCellValueFactory(new PropertyValueFactory<>("libelleOffre"));
				mesOffres.setItems(trieLibelle);
				textfieldLibelle.setVisible(false);
				btnOk.setVisible(true);
				}
			}	
			
			if((rechercheBox.getSelectionModel().getSelectedItem().toString()).equals("NomEntreprise")){
				btnTrier.setVisible(true);
				textfieldNomEntreprise.setVisible(true);
				btnOk.setVisible(false);
				
				if(event.getSource()==btnTrier){
					String nomEntreprise=textfieldNomEntreprise.getText();
					ObservableList<OffreStage> trieNomEntreprise=FXCollections.observableArrayList();
					for (OffreStage e: getOffreStage()){ // on parcourt la liste des offres recupere ds la BD
						if(e.getNomEntreprise().contains(nomEntreprise)){ // on recupere les noms des entreprise correpondant ala recherche
						trieNomEntreprise.add(e);
						}
					}
				Colonne.setCellValueFactory(new PropertyValueFactory<>("nomEntreprise"));
				mesOffres.setItems(trieNomEntreprise);
				textfieldNomEntreprise.setVisible(false);
				btnOk.setVisible(true);
				}
				
			}
			
			if((rechercheBox.getSelectionModel().getSelectedItem().toString()).equals("Duree")){
				btnTrier.setVisible(true);
				textfieldDuree.setVisible(true);
				btnOk.setVisible(false);
				
				if(event.getSource()==btnTrier){
					String duree=textfieldDuree.getText();
					ObservableList<OffreStage> trieDuree=FXCollections.observableArrayList();
					for (OffreStage e: getOffreStage()){ // on parcourt la liste des offres recupere ds la BD
						if(e.getDureeOffre()>=(Integer.parseInt(duree))){ // on recupere les noms des entreprise correpondant ala recherche
						trieDuree.add(e);
						}
					}
				Colonne.setCellValueFactory(new PropertyValueFactory<>("dureeOffre"));
				mesOffres.setItems(trieDuree);
				textfieldDuree.setVisible(false);
				btnOk.setVisible(true);
				}
			}
			
			
			
		
			 
			
		}
	    
		public ObservableList<OffreStage> getOffreStage(){
			ObservableList<OffreStage> offres=FXCollections.observableArrayList();
			try{
			Connection conn = Connexion.ConnexionBD();
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM OffreStage");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				if(!rs.getBoolean(8)){ // on ajoute seulement les offres deja valider par l'administateur
					OffreStage o1=new OffreStage();
					o1.setNumeroOffre(rs.getInt(1));
					o1.setNomEntreprise(rs.getString(2));
					o1.setLibelleOffre(rs.getString(3));
					o1.setDescriptifOffre(rs.getString(4));
					o1.setDomaineOffre(rs.getString(5));
					o1.setDateDebutOffre(rs.getDate(6));
					o1.setDureeOffre(rs.getInt(7));
					o1.setValide(rs.getBoolean(8));
					offres.add(o1);
					
				}	
			};
					
			ps.close();
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			return offres;	
		}
		
		public void init(){
			
			Colonne.setCellValueFactory(new PropertyValueFactory<>("libelleOffre")); // on recupere les libelle de la liste et on ajoute ala colonne mes offres
			mesOffres.setItems(getOffreStage());
		}

	  

	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		rechercheBox.getItems().setAll(enumRecherche.values());
		init();
		
	}

}
