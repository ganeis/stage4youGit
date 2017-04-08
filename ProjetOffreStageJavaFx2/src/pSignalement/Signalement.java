package pSignalement;



import java.util.Date;

public class Signalement {
	private int numero;
    private int numeroOffre;
    private String signalement;
    private String commentaire;
  
    public Signalement(int n,String s,String c){
    	
    	this.setNumeroOffre(n);
    	this.setSignalement(s);
    	this.setCommentaire(c);
     }

    public Signalement() {
    	
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getNumeroOffre() {
		return numeroOffre;
	}

	public void setNumeroOffre(int numeroOffre) {
		this.numeroOffre = numeroOffre;
	}

	public String getSignalement() {
		return signalement;
	}

	public void setSignalement(String signalement) {
		this.signalement = signalement;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
}
