package pEtudiant;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;


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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class consulterController implements Initializable{
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
	private Button btnPostuler;
		
	@FXML
	public ComboBox <enumSignalementEtudiant> signalerBox=new ComboBox<>();
	
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
	 
	private int numeroSignalement;
	
	@FXML
	private Text textAdresse;
	
	@FXML
	private Text textVille;
	
	@FXML
	private Text textPostal;
	
	@FXML
	private Text textMail;
	
	@FXML
	private Text textTel;
	
	@FXML
    private AnchorPane anchorOffre;
    
	
	private int numeroCandidature=Utilisateur.getBdInfoInt("SELECT MAX(numero) FROM `Candidature`");
	
	private String nom;
	
	
	public void precedent(ActionEvent event) throws IOException{
		Stage stage; 
	    Parent root;
		
	    stage=(Stage) btnPrecedent.getScene().getWindow();
	    root = FXMLLoader.load(getClass().getResource("/pEtudiant/viewEtudiantMenu.fxml"));
	    Scene scene = new Scene(root);
		   stage.setScene(scene);
		   stage.show();
	}
	
	
	public void postuler(ActionEvent event) throws IOException{
		
		
	    // Selectionner avec file chooser le cv et recuperer son chemin
	    FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("PDF Files","*.pdf"));
		File f=fc.showOpenDialog(null);
		String filename = f.getAbsolutePath();
		
		
		int pageCounter = 0;
		PDDocument doc = PDDocument.load(new File(filename));
		PDFRenderer renderer = new PDFRenderer(doc);
		PDPage page = (PDPage)doc.getPages().get( 0 );
		BufferedImage image = renderer.renderImageWithDPI(pageCounter, 80, ImageType.RGB); // on convertit le pdf en image
	
		String path=new File("").getAbsolutePath(); 
		File outputfile = new File(path+"/src/candidature/" + "Candidature" + numeroCandidature++  +".png");
		ImageIO.write(image, "png", outputfile); // on crée l'image 
		doc.close();
		
	
		
		String user=MenuLoginController.getUser();
		Connection conn=Connexion.ConnexionBD();
		try {PreparedStatement ps=conn.prepareStatement("insert into candidature (numero,idEtudiant,numeroOffre,validation,commentaire,chemin,nomEntreprise) values (?,?,?,?,?,?,?)");
		ps.setInt(1,numeroCandidature);
		ps.setString(2,MenuLoginController.getUser()); //on recupere le id de l'étudiant
		ps.setInt(3,Integer.parseInt(textNumeroOffre.getText()));
		ps.setBoolean(4,false);
		ps.setString(5,""); // vide car c entreprise qui mettra si accepter ou refus
		ps.setString(6, outputfile.toString()); 
		ps.setString(7,nom );
		ps.executeUpdate();
		ps.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		
	}
	
	public void signaler(ActionEvent event){
		btnOkSignaler.setVisible(true);
		signalerBox.setVisible(true);
		
		if(event.getSource()==btnOkSignaler){
			OffreStage o=mesOffres.getSelectionModel().getSelectedItem(); // on recupere le numero de l'offre qu'on veut signaler
			String signalement=signalerBox.getSelectionModel().getSelectedItem().toString();
			Connection conn=Connexion.ConnexionBD();
			try {PreparedStatement ps=conn.prepareStatement("insert into Signalement (numero,numeroOffre,signalement,commentaire) values (?,?,?,?)");
			ps.setInt(1,numeroSignalement);
			ps.setInt(2,o.getNumeroOffre()); //car id s'incremente tout seule dans la BD
			ps.setString(3,signalement);
			ps.setString(4,"Aucun Commentaire");
			ps.executeUpdate();
			ps.close();
			
			btnOkSignaler.setVisible(false);
			signalerBox.setVisible(false);
			
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}
	
	public void afficher() throws SQLException{
			anchorOffre.setVisible(true);
			btnSignaler.setVisible(true);
			btnPostuler.setVisible(true);
			
			OffreStage o=mesOffres.getSelectionModel().getSelectedItem(); // on recupere l'offre sur laquel on clik
			textNomEntreprise.setText(o.getNomEntreprise());
			textNumeroOffre.setText(Integer.toString(o.getNumeroOffre())); 
			textLibelle.setText(o.getLibelleOffre());
			textDescriptif.setText(o.getDescriptifOffre());
			textDate.setText(o.getDateDebutOffre().toString());
			textDuree.setText(Integer.toString(o.getDureeOffre()));
			textDomaine.setText(o.getDomaineOffre());
			textDuree.setText(Integer.toString(o.getDureeOffre()));
			
			nom=o.getNomEntreprise();
			Connection conn = Connexion.ConnexionBD();
			PreparedStatement ps=conn.prepareStatement("SELECT ville,mail,rue,codePostal,telephone FROM Entreprise where nom='"+nom+"'");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				
					textVille.setText(rs.getString(1));
					textMail.setText(rs.getString(2));
					textAdresse.setText(rs.getString(3));
					textPostal.setText(Integer.toString(rs.getInt(4)));
					textTel.setText(rs.getString(5));
					
			
				}
			
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
			btnTrier.setVisible(false);
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
			btnTrier.setVisible(false);
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
			btnTrier.setVisible(false);
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
		Connection conn = Connexion.ConnexionBD();
		PreparedStatement ps=conn.prepareStatement("SELECT * FROM OffreStage");
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			if(rs.getBoolean(8)){ // on ajoute seulement les offres deja valider par l'administateur
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rechercheBox.getItems().setAll(enumRecherche.values());
		signalerBox.getItems().setAll(enumSignalementEtudiant.values());
		init(); // on ouvre la fenettre avec les offre de stage de la BD
		
	}
}
