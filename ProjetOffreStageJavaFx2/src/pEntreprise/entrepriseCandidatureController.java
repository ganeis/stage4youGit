package pEntreprise;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Connexion;
import application.OffreStage;
import application.Utilisateur;
import candidature.Candidature;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class entrepriseCandidatureController implements Initializable{
	@FXML
	private ImageView quitter;
	
	@FXML
	private Button btnAccepter;
	
	@FXML
	private Button btnRefuser;
	
	@FXML
	private Button btnPrecedent;
	
	@FXML
	private TextField textfieldCommentaire;
	
	@FXML
	private ImageView imgCv;
	
	@FXML
	private TableView<Candidature> candidatures;
	
	@FXML
	private TableColumn cNumeroOffre;
	
	@FXML
	private TableColumn cIdEtudiant;
	
	
	public void precedent(ActionEvent event) throws IOException{
		Stage stage; 
	    Parent root;
		
	    stage=(Stage) btnPrecedent.getScene().getWindow();
	    root = FXMLLoader.load(getClass().getResource("/pEntreprise/viewEntrepriseMenu.fxml"));
	    Scene scene = new Scene(root);
		   stage.setScene(scene);
		   stage.show();
	}
	
	
	public void quitter(MouseEvent event){
		System.exit(1);
	}
	   
	
	@FXML
	    void accepter(ActionEvent event) {

		   Candidature c=candidatures.getSelectionModel().getSelectedItem();
		   if(!textfieldCommentaire.getText().isEmpty()){
		   String comments="Malheuresement";
		   ObservableList<Integer> Arefuser =getCandidatureArefuser(c.getNumeroOffre());
		   for (int i : Arefuser) {
			   setBdInfo(i,1,comments);// 1 c'est refuser 
		   }
		   setBdInfo(c.getNumero(),2,textfieldCommentaire.getText());// 2 c'est accepter
		   }
		   init();
	    }

	    @FXML
	    void refuser(ActionEvent event) {

	    	Candidature c=candidatures.getSelectionModel().getSelectedItem();
			   if(!textfieldCommentaire.getText().isEmpty()){
				   setBdInfo(c.getNumero(),1,textfieldCommentaire.getText());// 1 c'est refuser
			   }
			   init();
	    }
	
	public void afficher(){
		btnAccepter.setVisible(true);
		btnRefuser.setVisible(true);
		textfieldCommentaire.setVisible(true);
		
		Candidature c=candidatures.getSelectionModel().getSelectedItem(); // on recupere la candidature sur laquel on clik
		String s="File:"+c.getChemin();	
		System.out.println(s);
		Image img=new Image(s);
		StackPane sp = new StackPane();
		ImageView imgView = new ImageView(img);
		sp.getChildren().add(imgView);
		imgCv.setImage(img);
		Stage s2=new Stage();
		Scene scene = new Scene(sp);
		s2.setScene(scene);
		s2.show();
	}

	// Initialiser le tableView
	public void init(){
			cNumeroOffre.setCellValueFactory(new PropertyValueFactory<>("numeroOffre"));
			cIdEtudiant.setCellValueFactory(new PropertyValueFactory<>("idEtudiant"));
			candidatures.setItems(getCandidatures());
	}
	
	  public void setBdInfo(int num,int valide,String com){
		  try{Connection conn = Connexion.ConnexionBD();
			PreparedStatement ps=conn.prepareStatement("UPDATE `Candidature` SET `validation`='"+valide+"',`commentaire`='"+com+"' WHERE numero='"+num+"'"); 
			ps.executeUpdate();
		    ps.close();

			} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			}
		}
	
		
	
	public ObservableList<Candidature> getCandidatures(){
		ObservableList<Candidature> candidatures=FXCollections.observableArrayList();
		try{
		String user=MenuLoginController.getUser();
		String nomEntreprise=Utilisateur.getBdInfo("SELECT `nom` FROM `Entreprise`WHERE id='"+user+"'");
		Connection conn = Connexion.ConnexionBD();
		PreparedStatement ps=conn.prepareStatement("SELECT numero,numeroOffre,idEtudiant,chemin FROM Candidature WHERE nomEntreprise='"+nomEntreprise+"' and validation=0" );
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
					Candidature c1=new Candidature();
					c1.setNumero(rs.getInt(1));
					c1.setNumeroOffre(rs.getInt(2));
					c1.setIdEtudiant(rs.getString(3));
					c1.setChemin(rs.getString(4));
					candidatures.add(c1);
				
				
		};
				
		ps.close();
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return candidatures;	
	}
	
	public ObservableList<Integer> getCandidatureArefuser(int num){
		ObservableList<Integer> numeroCan=FXCollections.observableArrayList();
		try{
		
		Connection conn = Connexion.ConnexionBD();
		PreparedStatement ps=conn.prepareStatement("SELECT numero FROM Candidature WHERE numeroOffre='"+num+"'");
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
					
					numeroCan.add(rs.getInt(1));
				
				
		};
				
		ps.close();
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return numeroCan;	
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
		
	}
	
	
	
	
}
