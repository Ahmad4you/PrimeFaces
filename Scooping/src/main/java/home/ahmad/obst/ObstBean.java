package home.ahmad.obst;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Ahmad Alrefai
 */
@Named
@SessionScoped
public class ObstBean implements Serializable{

	private static final long serialVersionUID = 1L;
	 private String feld1;
	 private String feld2;

	public List<Obst> getObstListe() {
        return Arrays.asList(
            new Obst("Apfel", "bi bi-apple"),
            new Obst("Banane", "bi bi-emoji-smile"),
            new Obst("Kirsche", "bi bi-heart-fill"),
            new Obst("Traube", "bi bi-droplet"),
            new Obst("Orange", "bi bi-sun-fill")
        );
    }
	
	 public void kopiere(Obst obst) {
	        this.feld1 = obst.getName();       
	        this.feld2 = obst.getBootstrapIcon(); 
	    }

	    public String getFeld1() { return feld1; }
	    public void setFeld1(String feld1) { this.feld1 = feld1; }

	    public String getFeld2() { return feld2; }
	    public void setFeld2(String feld2) { this.feld2 = feld2; }
   
}

