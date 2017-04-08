package pEntreprise;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.Connexion;
import application.OffreStage;
import application.Utilisateur;
import application.enumDomaineOffre;
import application.enumRecherche;
import application.enumSecteur;
import controller.MenuLoginController;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class consulterEntrepriseController implements Initializable{
	@FXML
	private TableView<OffreStage> mesOffres;
	
	@FXML
	private TableColumn Colonne;
	
	@FXML 
	private Button btnOk;
	
	@FXML 
	private Button btnTrier;
	
	@FXML 
	private Button btnOkSignaler;
	
	@FXML 
	private Button btnSignaler;
	
	@FXML 
	private Button btnPrecedent;
	
	@FXML
	public ComboBox <enumSignalementEntreprise> signalerBox=new ComboBox<>();
	
	@FXML 
	private TextField textfieldRecherche;
	
	@FXML
	public ComboBox <enumRecherche> rechercheBox=new ComboBox<>();

	@FXML
	private Text textNomEntreprise;
	
	@FXML
	private Text textLibelle;
	
	@FXML
	private Text textDescriptif;
	
	@FXML
	private Text textDate ;
	
	@FXML
	private Text textDomaine;
	
	@FXML
	private Text textDuree ;
	
	@FXML
	private Text textNumeroOffre ;
	
	@FXML
	private TextField textFieldCommentaire ;
	
	 
	private int numeroSignalement;
	
	
	
	
	public void precedent(ActionEvent event) throws IOException{
		Stage stage; 
	    Parent root;
		
	    stage=(Stage) btnPrecedent.getScene().getWindow();
	    root = FXMLLoader.load(getClass().getResource("/pEntreprise/viewEntrepriseMenu.fxml"));
	    Scene scene = new Scene(root);
		   stage.setScene(scene);
		   stage.show();
	}
	
	public void signaler(ActionEvent event){
		btnOkSignaler.setVisible(true);
		signalerBox.setVisible(true);
		textFieldCommentaire.setVisible(true);
		
		if(event.getSource()==btnOkSignaler){
			OffreStage o=mesOffres.getSelectionModel().getSelectedItem(); // on recupere le numero de l'offre qu'on veut signaler
			String signalement=signalerBox.getSelectionModel().getSelectedItem().toString();
			Connection conn=Connexion.ConnexionBD();
			try {PreparedStatement ps=conn.prepareStatement("insert into Signalement (numero,numeroOffre,signalement,commentaire) values (?,?,?,?)");
			ps.setInt(1,numeroSignalement);
			ps.setInt(2,o.getNumeroOffre()); //car id s'incremente tout seule dans la BD
			ps.setString(3,signalement);
			ps.setString(4,textFieldCommentaire.getText());
			ps.executeUpdate();
			ps.close();
			
			btnOkSignaler.setVisible(false);
			signalerBox.setVisible(false);
			textFieldCommentaire.setVisible(false);
			textFieldCommentaire.setText("");
			
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}
	
	public void afficher(){
			btnSignaler.setVisible(true);
			OffreStage o=mesOffres.getSelectionModel().getSelectedItem(); // on recupere l'offre sur laquel on clik
			
			textNomEntreprise.setText(o.getNomEntreprise());
			textNumeroOffre.setText(Integer.toString(o.getNumeroOffre())); 
			textLibelle.setText(o.getLibelleOffre());
			textDescriptif.setText(o.getDescriptifOffre());
			textDate.setText(o.getDateDebutOffre().toString());
			textDuree.setText(Integer.toString(o.getDureeOffre()));
			textDomaine.setText(o.getDomaineOffre());
			textDuree.setText(Integer.toString(o.getDureeOffre()));
	}
	
//  Pour rechercher avec nomEntreprise / duree  / libelle
	public void buttonHandleAction(ActionEvent event){
		
		
		if((rechercheBox.getSelectionModel().getSelectedItem().toString()).equals("Libelle")){
			btnTrier.setVisible(true);
			textfieldRecherche.setVisible(true);
			btnOk.setVisible(false);
			
			if(event.getSource()==btnTrier){
				String libelle=textfieldRecherche.getText();
				ObservableList<OffreStage> trieLibelle=FXCollections.observableArrayList();
				for (OffreStage e: getOffreStage()){ // on parcourt la liste des offres recupere ds la BD
					if(e.getLibelleOffre().contains(libelle)){ // on recupere les libelle correpondant ala recherche
					trieLibelle.add(e);
					}
				}
			Colonne.setCellValueFactory(new PropertyValueFactory<>("libelleOffre"));
			mesOffres.setItems(trieLibelle);
			textfieldRecherche.setVisible(false);
			btnOk.setVisible(true);
			}
		}	
		
		if((rechercheBox.getSelectionModel().getSelectedItem().toString()).equals("NomEntreprise")){
			btnTrier.setVisible(true);
			textfieldRecherche.setVisible(true);
			btnOk.setVisible(false);
			
			if(event.getSource()==btnTrier){
				String nomEntreprise=textfieldRecherche.getText();
				ObservableList<OffreStage> trieNomEntreprise=FXCollections.observableArrayList();
				for (OffreStage e: getOffreStage()){ // on parcourt la liste des offres recupere ds la BD
					if(e.getNomEntreprise().contains(nomEntreprise)){ // on recupere les noms des entreprise correpondant ala recherche
					trieNomEntreprise.add(e);
					}
				}
			Colonne.setCellValueFactory(new PropertyValueFactory<>("nomEntreprise"));
			mesOffres.setItems(trieNomEntreprise);
			textfieldRecherche.setVisible(false);
			btnOk.setVisible(true);
			}
			
		}
		
		if((rechercheBox.getSelectionModel().getSelectedItem().toString()).equals("Duree")){
			btnTrier.setVisible(true);
			textfieldRecherche.setVisible(true);
			btnOk.setVisible(false);
			
			if(event.getSource()==btnTrier){
				String duree=textfieldRecherche.getText();
				ObservableList<OffreStage> trieDuree=FXCollections.observableArrayList();
				for (OffreStage e: getOffreStage()){ // on parcourt la liste des offres recupere ds la BD
					if(e.getDureeOffre()>=(Integer.parseInt(duree))){ // on recupere les noms des entreprise correpondant ala recherche
					trieDuree.add(e);
					}
				}
			Colonne.setCellValueFactory(new PropertyValueFactory<>("dureeOffre"));
			mesOffres.setItems(trieDuree);
			textfieldRecherche.setVisible(false);
			btnOk.setVisible(true);
			}
		}
		
		
		
	
		 
		
	}
	
	// Initialiser le tableView
	public void init(){
		Colonne.setCellValueFactory(new PropertyValueFactory<>("libelleOffre")); // on recupere les libelle de la liste et on ajoute ala colonne mes offres
		mesOffres.setItems(getOffreStage());
	}
	
	// On récupere tous les offres de stage
	public ObservableList<OffreStage> getOffreStage(){
		ObservableList<OffreStage> offres=FXCollections.observableArrayList();
		try{
		String user=MenuLoginController.getUser();
		String nomEntreprise=Utilisateur.getBdInfo("SELECT `nom` FROM `Entreprise`WHERE id='"+user+"'");
		Connection conn = Connexion.ConnexionBD();
		PreparedStatement ps=conn.prepareStatement("SELECT * FROM OffreStage");
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			if(rs.getBoolean(8)){ // on ajoute seulement les offres deja valider par l'administateur
				if(nomEntreprise.contains(rs.getString(2))){ // seulement de l'entrperise concerné
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
			}	
		};
				
		ps.close();
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return offres;	
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rechercheBox.getItems().setAll(enumRecherche.values());
		signalerBox.getItems().setAll(enumSignalementEntreprise .values());
		init(); // on ouvre la fenettre avec les offre de stage de la BD
		
	}
}