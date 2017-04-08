package pEntreprise;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.MainApp;
import controller.MenuLoginController;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class entrepriseMenuController implements Initializable {
	@FXML
    private Label userLabel;
	
	@FXML
	private Button btnDeconnexion;
	
	@FXML
	private ImageView quitter;
	
	@FXML
	private ImageView imgDéconnexion;
	
	@FXML
	private ImageView imgCandidatures;
	
	@FXML
	private ImageView imgPoster;
	
	@FXML
	private ImageView imgConsulter;
	
	private String user;
	
	public void quitter(MouseEvent event){
		System.exit(1);
	}
	
	@FXML
	void ConsulterAction(MouseEvent event) throws IOException {

		ChoixView("/pEntreprise/viewConsulterEntreprise.fxml");
		  
	}

	@FXML
	void CandidaturesAction(MouseEvent event) throws IOException {

		ChoixView("/pEntreprise/viewEntrepriseCandidature.fxml");
		  
	}
	
	@FXML
	void PosterAction(MouseEvent event) throws IOException {

	    ChoixView("/pEntreprise/viewOffreStage.fxml");
	}
	
	@FXML
	void Déconnexion(MouseEvent event) throws IOException {

	    ChoixView("/controller/MenuLogin.fxml");
	}
	
	public void ChoixView(String msg) throws IOException{
			
			Stage stage; 
		    Parent root;
			root = FXMLLoader.load(getClass().getResource(msg));
	        Scene home_page_scene = new Scene(root);
	        stage =MainApp.getStage();
	         
	                stage.hide(); //optional
	                stage.setScene(home_page_scene);
	                stage.show();
	}
	
	public void changeColorIn(MouseEvent event){
		if(event.getSource()==imgPoster)imgPoster.setBlendMode(BlendMode.RED);
		if(event.getSource()==imgConsulter)imgConsulter.setBlendMode(BlendMode.RED);
		if(event.getSource()==imgCandidatures)imgCandidatures.setBlendMode(BlendMode.RED);
		if(event.getSource()==imgDéconnexion)imgDéconnexion.setBlendMode(BlendMode.RED);
		
	}
	
	public void changeColorOut(MouseEvent event){
		if(event.getSource()==imgDéconnexion)imgDéconnexion.setBlendMode(BlendMode.SRC_OVER);
		if(event.getSource()==imgPoster)imgPoster.setBlendMode(BlendMode.SRC_OVER);
		if(event.getSource()==imgConsulter)imgConsulter.setBlendMode(BlendMode.SRC_OVER);
		if(event.getSource()==imgCandidatures)imgCandidatures.setBlendMode(BlendMode.SRC_OVER);
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		user=MenuLoginController.getUser();
		userLabel.setText(user);
	}

}
