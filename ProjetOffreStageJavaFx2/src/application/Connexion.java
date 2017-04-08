package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Connexion {
	
	Connection conn=null;
	public static Connection ConnexionBD(){
		try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3307/Stage4You","root","");
		System.out.println("Connexion à la base de données de Stage4You");
			return con;
		
		 }catch(ClassNotFoundException | SQLException e){
	         JOptionPane.showMessageDialog(null, e);
	         return null;
		 }
	}
}
