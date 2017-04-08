package pSignalement;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.Connexion;
import application.OffreStage;
import application.Utilisateur;
import application.enumDomaineOffre;
import application.enumSecteur;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class signalementController implements Initializable {
	
	@FXML
	private TableView<Signalement> mesSignalements;
	
	@FXML
	private TableColumn numero;
	
	@FXML
	private TableColumn signalement;
	
	@FXML
	private Text textCommentaire;
	
	@FXML
	private TableView<OffreStage> mesOffres;
	
	@FXML
	private TableColumn Offres;
	
	@FXML
	private TableColumn date;
	
	@FXML
	private TableColumn Numero;
	
	@FXML
	private Label LabelNomEntreprise;
	
    @FXML
    private Label errorNom;
    
	@FXML
	private TextField fieldLibelle;
	
	@FXML
    private Label errorLibelle;
	
	@FXML
	private TextArea fieldDescriptif;
	
	@FXML
    private Label errorDescriptif;
	
	@FXML
	private DatePicker dateDebut ;
	
	@FXML
    private Label errorDate;
	
	@FXML
	ComboBox <enumDomaineOffre> domaineBox =new ComboBox<>(); 
	
	@FXML
    private Label errorDomaine;
	
	@FXML
	private TextField fieldDuree ;
	
	@FXML
	private TextField textfieldTelephone ;
	
	@FXML
	private TextField textfieldMail ;
	
	@FXML
	private TextField textfieldDomaine ;
	
	
	@FXML
    private Label errorDuree;
	
	@FXML
	private Button btnMiseAJour;
	
	@FXML
	private Button btnSupprimer;
	
	
	@FXML
	private Button btnPrecedent;
	
	@FXML
    private Label dateLabel;
	
	public static Connection conn = null;
	
	private String user;
	private String nomEntreprise;
    private String libelleOffre;
    private String domaineOffre;
    private Date dateDebutOffre;
    private String descriptifOffre;
    private int duree;
    private int numeroOffre;
 
	
    public void precedent(ActionEvent event) throws IOException{
		Stage stage; 
	    Parent root;
		
	    stage=(Stage) btnPrecedent.getScene().getWindow();
	    root = FXMLLoader.load(getClass().getResource("/pAdministrateur/viewAdmin.fxml"));
	    Scene scene = new Scene(root);
		   stage.setScene(scene);
		   stage.show();
	}
    
    public void afficherSignalement() throws SQLException{
    	Signalement s=mesSignalements.getSelectionModel().getSelectedItem(); 
    	textCommentaire.setText(s.getCommentaire());
    	int numero=s.getNumeroOffre();
    	//dateDebut=new DatePicker(LocalDate.of(9,10, 2008));
    	
    	//a voir pour dateDebut sql
    	conn = Connexion.ConnexionBD();
		PreparedStatement ps=conn.prepareStatement("SELECT nomEntreprise,libelle,descriptif,domaine,duree,numero,dateDebut FROM OffreStage where numero='"+numero+"'");
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			LabelNomEntreprise.setText(rs.getString(1));
			fieldLibelle.setText(rs.getString(2));
			fieldDescriptif.setText(rs.getString(3));
			textfieldDomaine.setText(rs.getString(4));
			fieldDuree.setText(Integer.toString(rs.getInt(5)));
			numeroOffre=rs.getInt(6);
			dateDebutOffre=rs.getDate(7);
			dateLabel.setText(dateDebutOffre.toString());
			
		}
	
		
		String nom=LabelNomEntreprise.getText();
		ps=conn.prepareStatement("SELECT mail,telephone FROM Entreprise where nom='"+nom+"'");
		rs=ps.executeQuery();
		while(rs.next()){
			textfieldMail.setText(rs.getString(1));
			textfieldTelephone.setText(rs.getString(2));
		}
		ps.close();
    	
    }
    
    // Pour supprimer une offre
    public void supprimer(){
    	OffreStage o=mesOffres.getSelectionModel().getSelectedItem(); 
    	if(mesOffres.getSelectionModel().getSelectedItem() != null){
	    	conn=Connexion.ConnexionBD();
			try {PreparedStatement ps=conn.prepareStatement("DELETE FROM offrestage WHERE numero='"+o.getNumeroOffre()+"'");
			ps.executeUpdate();
			ps.close();
			init();
			} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			}
    	}
    }
    
    public void effacerSignalement(ActionEvent event){
    	
    	Signalement s=mesSignalements.getSelectionModel().getSelectedItem(); 
    	if(mesSignalements.getSelectionModel().getSelectedItem() != null){
	    	conn=Connexion.ConnexionBD();
			try {PreparedStatement ps=conn.prepareStatement("DELETE FROM Signalement WHERE numero='"+s.getNumero()+"'");
			ps.executeUpdate();
			ps.close();
			initSignalement();
			} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			}
    	}
    }
    
    public void miseAJour(ActionEvent event){
    	
	    libelleOffre=fieldLibelle.getText();
	    descriptifOffre=fieldDescriptif.getText();
	   //dateDebutOffre=dateDebutOffre=java.sql.Date.valueOf(dateDebutOffre);
	 
	    
	    if(dateDebut.getValue()!=null){
	    	dateDebutOffre=java.sql.Date.valueOf(dateDebut.getValue());
	    }
	    
	    System.out.println(dateDebutOffre);
	    if(verifchamp()){
	    	
	    	domaineOffre=textfieldDomaine.getText();
	    	
	    	OffreStage o=new OffreStage(nomEntreprise,libelleOffre,descriptifOffre,domaineOffre,dateDebutOffre,Integer.parseInt(fieldDuree.getText()));
	    	o.setNumeroOffre(numeroOffre);
	    	o.miseAJour();
	    	init();
	    	dateDebut.setValue(null);
	    }
	}
	
	
	
	
	public boolean verifchamp(){
		boolean rep=false;
		
		if(libelleOffre.isEmpty()){setError(errorLibelle,"Champ vide");}else{ errorLibelle.setText("");}
		if(textfieldDomaine.getText().isEmpty())setError(errorDomaine,"Champ vide"); else errorDomaine.setText("");
		//if(dateDebut.getValue()==null)setError(errorDate,"Champ vide");else errorDate.setText("");
		if(fieldDuree.getText().isEmpty())setError(errorDuree,"Champ vide");else errorDuree.setText("");
		if(descriptifOffre.isEmpty())setError(errorDescriptif,"Champ vide");else errorDescriptif.setText("");
	    
		if(!libelleOffre.isEmpty()&&!textfieldDomaine.getText().isEmpty()/*&&dateDebut.getValue()!=null*/&&
				!fieldDuree.getText().isEmpty()&&!descriptifOffre.isEmpty()){
			rep=true;
		}
		
		return rep;
		
		}
	
	public static void setError(Label err,String msg){
    	err.setTextFill(Color.RED);
    	err.setText(msg);
    }
	
	// Initialiser le tableView des offres
	public void init(){
		Offres.setCellValueFactory(new PropertyValueFactory<>("libelleOffre")); // on recupere les libelle de la liste et on ajoute ala colonne mes offres
		date.setCellValueFactory(new PropertyValueFactory<>("dateDebutOffre")); // on recepure les date
		Numero.setCellValueFactory(new PropertyValueFactory<>("numeroOffre")); // on recupere les numero
		mesOffres.setItems(getOffreStage());
		
	}
	
	// Initialiser le tableView des signalements
		public void initSignalement(){
			numero.setCellValueFactory(new PropertyValueFactory<>("numeroOffre"));
			signalement.setCellValueFactory(new PropertyValueFactory<>("signalement"));
		    mesSignalements.setItems(getSignalement());
			
		}
		
	
	// On récupere tous les offres de stage
	public ObservableList<OffreStage> getOffreStage(){
		ObservableList<OffreStage> offres=FXCollections.observableArrayList();
		try{
		conn=Connexion.ConnexionBD();
		PreparedStatement ps=conn.prepareStatement("SELECT * FROM OffreStage");
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
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
		
		};
					
		ps.close();
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return offres;	
	}
	
	// On récupere tous les signalement
		public ObservableList<Signalement> getSignalement(){
			ObservableList<Signalement> signalements=FXCollections.observableArrayList();
			try{
			conn=Connexion.ConnexionBD();
			PreparedStatement ps=conn.prepareStatement("SELECT numeroOffre,signalement,commentaire,numero FROM Signalement");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
					Signalement s=new Signalement();
					s.setNumeroOffre(rs.getInt(1));
					s.setSignalement(rs.getString(2));
					s.setCommentaire(rs.getString(3));
					s.setNumero(rs.getInt(4));
					signalements.add(s);
				
			};
						
			ps.close();
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			return signalements;	
		}
	
	
	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		domaineBox.getItems().setAll(enumDomaineOffre.values());
		init();
		initSignalement();
	}
}

