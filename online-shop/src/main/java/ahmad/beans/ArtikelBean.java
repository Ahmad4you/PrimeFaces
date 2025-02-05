package ahmad.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import ahmad.hodel.Artikel;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

/**
 * 
 * @author Ahmad Alrefai
 */


@Named("simpleBean")
@ViewScoped
public class ArtikelBean implements Serializable {
	private static final long serialVersionUID = -101520898037881436L;
	
	private String name = "Filzpantoffeln 'Rudolph'";
	private Collection<Artikel> artikelList = new ArrayList<>();

	public ArtikelBean() {
		artikelList.add(new Artikel("Artikel 1", "description 1", "img/filzschuhe.jpg"));
		artikelList.add(new Artikel("Artikel 2", "description 2", "img/handtasche.jpg"));
		artikelList.add(new Artikel("Artikel 3", "description 3", "img/hasen.png"));
		
	}
	
	public void remove(Artikel artikel) {
		artikelList.remove(artikel);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Artikel> getArtikelList() {
		return artikelList;
	}

	public void setArtikelList(Collection<Artikel> artikelList) {
		this.artikelList = artikelList;
	}
	
	
}
