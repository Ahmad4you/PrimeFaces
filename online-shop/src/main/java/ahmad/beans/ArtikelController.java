package ahmad.beans;

import java.io.Serializable;
import java.util.List;

import ahmad.hodel.Artikel;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * 
 * @author Ahmad Alrefai
 */

@Named
@SessionScoped
public class ArtikelController implements Serializable {
	private static final long serialVersionUID = -8137618063740831090L;
	
	@Inject
	private Shop shop;
	private int index = 0;
	private String selectedBild;
	private Artikel artikel = new Artikel();
	private Artikel artikelEingabe;
	private List<Artikel> artikelList;
	
	@PostConstruct
    public void init() {
        // Initialisiere selectedBild mit dem Bild des ersten Artikels
        if (!shop.getInstance().getSortiment().isEmpty()) {
            selectedBild = shop.getInstance().getSortiment().get(0).getBild();
        }
        setArtikelEingabe(new Artikel());
    }

	public Artikel getArtikel() {
		return shop.getInstance().getSortiment().get(index);
	}
	
	public void loadArtikelList() {
        artikelList = getArtikelList();
    }
	
	public List<Artikel> getArtikelList() {
		artikelList= shop.getSortiment();
//			for(Artikel ar: artikelList) {
//				System.out.println(ar.getId());
//				System.out.println(ar.getName());
//				System.out.println(ar.getDescription());
//			}
        return artikelList;
    }

	public void vor() {
		if (index < shop.getInstance().getSortiment().size() - 1) {
			index++;
		}
	}

	public void zurueck() {
		if (index > 0) {
			index--;
		}
	}


	public String handleBildKeyEvent(AjaxBehaviorEvent input) {
		
		return "input";
	}

    public void onBildChange() {
        System.out.println("Bild geändert zu: " + selectedBild);
        // Finde den Index des Artikels mit dem ausgewählten Bild
        List<Artikel> sortiment = shop.getInstance().getSortiment();
        for (int i = 0; i < sortiment.size(); i++) {
            if (sortiment.get(i).getBild().equals(selectedBild)) {
                index = i;
                break;
            }
        }
    }
    
    public int getMaxIndex() {
    	return shop.getInstance().getSortiment().size() -1;
    }
    
    public int getIndex() {
    	return index;
    }
    public String getSelectedBild() {
        return selectedBild;
    }

    public void setSelectedBild(String selectedBild) {
        this.selectedBild = selectedBild;
    }
    
    public void setArtikel(Artikel artikel) {
        this.artikel = artikel;
    }

    public void saveArtikel() {
    	shop.saveArtikel(artikelEingabe);
    }

	public Artikel getArtikelEingabe() {
		return artikelEingabe;
	}

	public void setArtikelEingabe(Artikel artikelEingabe) {
		this.artikelEingabe = artikelEingabe;
	}
	
}
