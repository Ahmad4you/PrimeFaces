package ahmad.beans;


import java.io.Serializable;
import java.util.List;

import ahmad.hodel.Artikel;
import ahmad.hodel.Bewertung;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

/**
 * 
 * @author Ahmad Alrefai
 */

@Named
@SessionScoped
public class BewertungController implements Serializable{
	private static final long serialVersionUID = -4687696944572568134L;

	@PersistenceContext(unitName = "AhmadPU")
    private EntityManager em;

    private Bewertung bewertung;
    private UIComponent selectMenu;
    private Long artikelId; // ID des Artikels aus dem Frontend
    private List<Bewertung> bewertungList;
    
    @PostConstruct
    public void init() {
        bewertung = new Bewertung(); // Standard-Bewertung initialisieren
    }
    
    public void onArtikelChange() {
        if (artikelId != null) {
            Artikel artikel = em.find(Artikel.class, artikelId);
            if (artikel != null) {
                bewertung = new Bewertung(); // Neues Bewertungsobjekt
                bewertung.setArtikel(artikel); // Artikel zuweisen
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Artikel nicht gefunden!", null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Keine Artikel-ID ausgewählt!", null));
        }
    }


    public Long getArtikelId() {
        return artikelId;
    }

    public void setArtikelId(Long artikelId) {
    	System.out.println("Setze Artikel-ID: " + artikelId);
        this.artikelId = artikelId;
    }

    public Bewertung getBewertung() {
        return bewertung;
    }

    public void setBewertung(Bewertung bewertung) {
        this.bewertung = bewertung;
    }

    @Transactional
    public void saveBewertung() {
        try {
            if (bewertung.getArtikel() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kein Artikel ausgewählt!", null));
                return; // Abbrechen, wenn kein Artikel ausgewählt wurde
            }

            if (bewertung.getId() == null) {
                em.persist(bewertung); // Neue Bewertung speichern
            } else {
                em.merge(bewertung); // Existierende Bewertung aktualisieren
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Bewertung gespeichert!"));
            bewertung = new Bewertung(); // Neues Bewertungsobjekt erstellen
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Speichern der Bewertung!", e.getMessage()));
            System.err.println(e.getMessage());
        }
    }
    

    public UIComponent getSelectMenu() {
        return selectMenu;
    }

    public void setSelectMenu(UIComponent selectMenu) {
        this.selectMenu = selectMenu;
        System.out.println("SelectMenu: " + selectMenu);
    }
    
    public List<Bewertung> getBewertungList() {
    	 TypedQuery<Bewertung> query = em.createQuery("SELECT a FROM Bewertung a", Bewertung.class);
    	 bewertungList = query.getResultList();
    	
        return bewertungList;
    }
    
    @Transactional
    public void entferneBewertung(Bewertung bewertung) {
        try {
            Bewertung managedBewertung = em.find(Bewertung.class, bewertung.getId());
            if (managedBewertung != null) {
                em.remove(managedBewertung);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Bewertung erfolgreich entfernt."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Bewertung konnte nicht gefunden werden!", null));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Entfernen der Bewertung!", e.getMessage()));
        }
    }
    
    
}