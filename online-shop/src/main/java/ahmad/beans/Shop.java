package ahmad.beans;

import java.util.ArrayList;
import java.util.List;

import ahmad.hodel.Artikel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
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
@ApplicationScoped
public class Shop {

    @PersistenceContext(unitName = "AhmadPU")
    private EntityManager em;

    private static Shop instance;

    public Shop() {
        instance = this;
    }

    public Shop getInstance() {
        if (instance == null) {
            instance = new Shop();
        }
        return instance;
    }

    @Transactional
    public List<Artikel> getSortiment() {
        try {
            // Holen der Artikel aus der Datenbank
            TypedQuery<Artikel> query = em.createQuery("SELECT a FROM Artikel a", Artikel.class);
            List<Artikel> artikeln = query.getResultList();

            if (artikeln.isEmpty()) {
                System.out.println("Initialisiere Artikel-Kollektion.");
                List<Artikel> basisArtikel = basisArtikelKollektion();
                for (Artikel artikel : basisArtikel) {
                    em.persist(artikel); // Neue Artikel persistieren
                }
                em.flush();
                artikeln = query.getResultList(); // Lade die neu erstellten Artikel
            }

            System.out.println("Gefunden: " + artikeln.size() + " Artikel.");
            return new ArrayList<>(artikeln); // Eneue Liste, um Lazy-Loading-Probleme zu vermeiden
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Verwende Fallback-Artikel-Kollektion.");
            return basisArtikelKollektion();
        }
    }

    private List<Artikel> basisArtikelKollektion() {
        List<Artikel> list = new ArrayList<>();
        list.add(new Artikel("Filzschuhe schick", "Schicke Armani-branded Filzschuhe", "img/filzschuhe.jpg"));
        list.add(new Artikel("Handtasche schick", "Schicke Moschino-branded Handtasche", "img/handtasche.jpg"));
        list.add(new Artikel("Süßes Hasenpaar", "Süßes Hasenpaar", "img/hasen.png"));
        return list;
    }
    
    @Transactional
    public void saveArtikel(Artikel artikel) {
        try {
            em.merge(artikel);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Artikel gespeichert!"));
            artikel = new Artikel();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Speichern des Artikels!", e.getMessage()));
            System.err.println(e.getMessage());
        }
    }
}