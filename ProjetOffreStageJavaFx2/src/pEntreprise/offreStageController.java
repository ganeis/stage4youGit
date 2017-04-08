package pEntreprise;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.Connexion;
import application.OffreStage;
import application.Utilisateur;
import application.enumDomaineOffre;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class offreStageController implements Initializable {
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
    private Label errorDuree;
	
	@FXML
	private Button btnValider;
	
	@FXML
	private Button btnPrecedent;
	
	public static Connection conn = null;
	
	private String user;
	private String nomEntreprise;
    private String libelleOffre;
    private String domaineOffre;
    private Date dateDebutOffre;
    private String descriptifOffre;
    private int duree;
 
    
    private int numeroCandidature;
	
    public void precedent(ActionEvent event) throws IOException{
		Stage stage; 
	    Parent root;
		
	    stage=(Stage) btnPrecedent.getScene().getWindow();
	    root = FXMLLoader.load(getClass().getResource("/pEntreprise/viewEntrepriseMenu.fxml"));
	    Scene scene = new Scene(root);
		   stage.setScene(scene);
		   stage.show();
	}
    
    public void poster(ActionEvent event){
		
	    libelleOffre=fieldLibelle.getText();
	    descriptifOffre=fieldDescriptif.getText();
	    
	  
	    if(verifchamp()){
	    	dateDebutOffre=java.sql.Date.valueOf(dateDebut.getValue());
	    	domaineOffre=domaineBox.getSelectionModel().getSelectedItem().toString();
	    	OffreStage o=new OffreStage(nomEntreprise,libelleOffre,descriptifOffre,domaineOffre,dateDebutOffre,Integer.parseInt(fieldDuree.getText()));
	    	o.posterOffre();
	    	init();
	    	vider();
	    }
	}
	
	public void vider(){
		
	    fieldLibelle.setText("");
	    fieldDescriptif.setText("");
	    fieldDuree.setText("");
	}
	
	
	public boolean verifchamp(){
		boolean rep=false;
		
		if(libelleOffre.isEmpty()){setError(errorLibelle,"Champ vide");}else{ errorLibelle.setText("");}
		if(domaineBox.getSelectionModel().getSelectedItem()==null)setError(errorDomaine,"Champ vide"); else errorDomaine.setText("");
		if(dateDebut.getValue()==null)setError(errorDate,"Champ vide");else errorDate.setText("");
		if(fieldDuree.getText().isEmpty())setError(errorDuree,"Champ vide");else errorDuree.setText("");
		if(descriptifOffre.isEmpty())setError(errorDescriptif,"Champ vide");else errorDescriptif.setText("");
	    
		if(!libelleOffre.isEmpty()&&domaineBox.getSelectionModel().getSelectedItem()!=null&&dateDebut.getValue()!=null&&
				!fieldDuree.getText().isEmpty()&&!descriptifOffre.isEmpty()){
			rep=true;
		}
		
		return rep;
		
		}
	
	public static void setError(Label err,String msg){
    	err.setTextFill(Color.RED);
    	err.setText(msg);
    }
	
	// Initialiser le tableView
	public void init(){
		Offres.setCellValueFactory(new PropertyValueFactory<>("libelleOffre")); // on recupere les libelle de la liste et on ajoute ala colonne mes offres
		date.setCellValueFactory(new PropertyValueFactory<>("dateDebutOffre")); // on recepure les date
		Numero.setCellValueFactory(new PropertyValueFactory<>("numeroOffre")); // on recupere les numero
		mesOffres.setItems(getOffreStage());
		
	}
	

	
	// On récupere tous les offres de stage
	public ObservableList<OffreStage> getOffreStage(){
		ObservableList<OffreStage> offres=FXCollections.observableArrayList();
		try{
		conn=Connexion.ConnexionBD();
		PreparedStatement ps=conn.prepareStatement("SELECT * FROM OffreStage");
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			if(nomEntreprise.contains(rs.getString(2))){ // On affiche les offres seulement de l'entreprise
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
	
	
	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		domaineBox.getItems().setAll(enumDomaineOffre.values());
		user=MenuLoginController.getUser();
		nomEntreprise=Utilisateur.getBdInfo("SELECT `nom` FROM `Entreprise`WHERE id='"+user+"'");
		init();
		LabelNomEntreprise.setText(nomEntreprise);
	}
}

