package pEtudiant;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pAdministrateur.validationController;

public class candidatureEtudiantController implements Initializable {

    @FXML
    private Text textVille;

    @FXML
    private Text textDomaine;

    @FXML
    private Text textAdresse;

    @FXML
    private Text textPostal;

    @FXML
    private Text textLibelle;

    @FXML
    private TableView<Candidature> mesOffres;

    @FXML
    private Text textMail;

    @FXML
    private Text textNumeroOffre;

    @FXML
    private Text textTel;

    @FXML
    private Button btnSupp;

    @FXML
    private TableColumn<?, ?> numCo;

    @FXML
    private Text textNomEntreprise;

    @FXML
    private Text textDuree;

    @FXML
    private Text textDescriptif;

    @FXML
    private TableColumn<?, ?> nomCo;

    @FXML
    private Button btnPrecedent;

    @FXML
    private Text textDate;
    
    @FXML
    private ImageView imgAccepter;
    
    @FXML
    private ImageView imgReject;
    

    @FXML
    private ImageView imgAttent;
    
    @FXML
    private AnchorPane anchorReponse;
    
    
    
    public void quitter(MouseEvent event){
    	System.exit(1);
    }

    @FXML
    void afficher() throws SQLException {
    	anchorReponse.setVisible(true);
    	imgAttent.setVisible(false);
    	imgAccepter.setVisible(false);
		imgReject.setVisible(false);
    	Candidature c=mesOffres.getSelectionModel().getSelectedItem();
    	Connection conn = Connexion.ConnexionBD();
		PreparedStatement ps=conn.prepareStatement("SELECT ville,mail,rue,codePostal,telephone FROM Entreprise where nom='"+c.getNomEntreprise()+"'");
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			
				textVille.setText(rs.getString(1));
				textMail.setText(rs.getString(2));
				textAdresse.setText(rs.getString(3));
				textPostal.setText(Integer.toString(rs.getInt(4)));
				textTel.setText(rs.getString(5));
			}
		textDescriptif.setText(c.getCommentaire());
		setTextField(c.getNumeroOffre());
		
		// si la canditature est refusé
		if(c.getValidation()==1){
			imgReject.setVisible(true);
		}
		
		// si la canditature est accepté
		if(c.getValidation()==2){
			imgAccepter.setVisible(true);
		}
		
		// si la canditature est en attente
		if(c.getValidation()==0){
			imgAttent.setVisible(true);
		}
    }
    

    @FXML
    void precedent(ActionEvent event) throws IOException {
    	Stage stage; 
	    Parent root;
		
	    stage=(Stage) btnPrecedent.getScene().getWindow();
	    root = FXMLLoader.load(getClass().getResource("/pEtudiant/viewEtudiantMenu.fxml"));
	    Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void supprimer(ActionEvent event) {
    	Candidature c=mesOffres.getSelectionModel().getSelectedItem();
    	delete("Candidature",c.getNumero());
    	init();
    }
    
    public void init(){
		numCo.setCellValueFactory(new PropertyValueFactory<>("numero"));
		nomCo.setCellValueFactory(new PropertyValueFactory<>("nomEntreprise"));
		mesOffres.setItems(getCandidatures());
}
    
    public ObservableList<Candidature> getCandidatures(){
		ObservableList<Candidature> candidatures=FXCollections.observableArrayList();
		try{
		String user=MenuLoginController.getUser();
		Connection conn = Connexion.ConnexionBD();
		PreparedStatement ps=conn.prepareStatement("SELECT numero,numeroOffre,idEtudiant,commentaire,validation,nomEntreprise FROM Candidature WHERE idEtudiant='"+user+"'");
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
					Candidature c1=new Candidature();
					c1.setNumero(rs.getInt(1));
					c1.setNumeroOffre(rs.getInt(2));
					c1.setIdEtudiant(rs.getString(3));
					c1.setCommentaire(rs.getString(4));
					c1.setValidation(rs.getInt(5));
					c1.setNomEntreprise(rs.getString(6));
					candidatures.add(c1);
				
				
		};
				
		ps.close();
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return candidatures;	
	}
    
    public void setTextField(int num) throws SQLException{
    	
    		Connection conn = Connexion.ConnexionBD();
    		PreparedStatement ps=conn.prepareStatement("SELECT * FROM OffreStage WHERE numero='"+num+"'");
    		ResultSet rs=ps.executeQuery();
    		while(rs.next()){
    				
    				textNomEntreprise.setText(rs.getString(2));
    				textNumeroOffre.setText(Integer.toString(rs.getInt(1))); 
    				textLibelle.setText(rs.getString(3));
    				textDate.setText(rs.getDate(6).toString());
    				textDuree.setText(Integer.toString(rs.getInt(7)));
    				textDomaine.setText(rs.getString(5));
    				textDuree.setText(Integer.toString(rs.getInt(7)));
    			}	
    
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
    
  

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		init();
		imgAccepter.setVisible(false);
		imgReject.setVisible(false);
		imgAttent.setVisible(false);
	}

}