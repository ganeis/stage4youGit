package controller;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Alerte {
	public static void display(String title,String message){
		
		Stage alerteFenetre = new Stage();
		alerteFenetre.initModality(Modality.APPLICATION_MODAL);
		alerteFenetre.setTitle(title);
		
		Label l=new Label();
		l.setText(message);
		Button btnOk= new Button("Ok");
		btnOk.setOnAction(e->alerteFenetre.close()); // rajouter ici test level + fenetre admin/etudiant/entreprise
		
		VBox box=new VBox(10);
		box.getChildren().addAll(l,btnOk);
		Scene scene = new Scene(box);
		alerteFenetre.setScene(scene);
		alerteFenetre.show();
		
	}
}
