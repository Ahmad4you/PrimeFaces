package ahmad.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ahmad.model.Artikel;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * 
 * @author Ahmad Alrefai
 */

@Named // Macht diese Klasse zu einer CDI-Managed Bean
@SessionScoped // Definiert den Scope der Bean als Session-Scope
public class ArtikelController implements Serializable {
	private static final long serialVersionUID = -8137618063740831090L;

	@Inject // Injiziert eine Instanz der Shop-Klasse (muss selbst eine CDI-Bean sein, z.B. @ApplicationScoped)
	private Shop shop;

	private int index = 0; // Aktueller Index des angezeigten Artikels im Sortiment
	private String selectedBild; // Speichert den Wert des ausgewählten Bildes aus dem p:selectOneMenu
	private Artikel artikel; // Der aktuell in der View angezeigte Artikel. Wird in init(), vor(), zurueck(), onBildChange() gesetzt.
	private Artikel artikelEingabe; // Artikel-Objekt für die Eingabe/Erfassung neuer Artikel
	private List<Artikel> artikelList; // Eine gecachte Liste des Sortiments, um unnötige Aufrufe zu vermeiden

	@PostConstruct // Diese Methode wird nach der Konstruktion und Injektion der Bean aufgerufen
    public void init() {
        // Sicherstellen, dass der Shop injiziert ist und ein Sortiment vorhanden ist
        if (shop != null && shop.getSortiment() != null && !shop.getSortiment().isEmpty()) {
            artikelList = shop.getSortiment(); // Sortiment einmal laden und cachen
            index = 0; // Sicherstellen, dass der Index auf den ersten Artikel zeigt
            this.artikel = artikelList.get(index); // Den ersten Artikel laden
            selectedBild = this.artikel.getBild(); // selectedBild mit dem Bild des ersten Artikels initialisieren
        } else {
            // Fallback, wenn der Shop oder das Sortiment leer/null ist
            System.err.println("WARNUNG: Shop oder Sortiment ist leer bei Initialisierung des ArtikelControllers.");
            this.artikel = new Artikel(); // Leeren Artikel anzeigen, um NullPointerExceptions in der UI zu vermeiden
            this.selectedBild = ""; // Leeren String für das Bild
            this.artikelList = new ArrayList<>(); // Leere Liste
            this.index = 0;
        }
        setArtikelEingabe(new Artikel()); // Initialisiert das Eingabe-Artikel-Objekt
    }

    /**
     * Gibt den aktuell in der View angezeigten Artikel zurück.
     * Dieser Getter holt den Artikel nicht direkt aus dem Shop, sondern gibt den
     * im Controller gehaltenen 'artikel'-Member zurück, der in den Navigationsmethoden
     * und in onBildChange() aktualisiert wird.
     * @return Der aktuell angezeigte Artikel.
     */
	public Artikel getArtikel() {
        // Falls 'artikel' aus irgendeinem Grund noch nicht gesetzt wurde (z.B. bei unerwarteten Szenarien),
        // versuchen wir, ihn zu initialisieren oder einen leeren Artikel zurückzugeben.
        if (this.artikel == null) {
            if (shop != null && shop.getSortiment() != null && !shop.getSortiment().isEmpty() && index >= 0 && index < shop.getSortiment().size()) {
                this.artikel = shop.getSortiment().get(index);
            } else {
                return new Artikel(); // Fallback: Leeren Artikel zurückgeben
            }
        }
		return this.artikel;
	}

    /**
     * Setter für den Artikel. Wird von JSF verwendet, wenn der Artikel in der UI geändert wird.
     * @param artikel Das Artikel-Objekt, das gesetzt werden soll.
     */
    public void setArtikel(Artikel artikel) {
        this.artikel = artikel;
    }

    /**
     * Lädt die Artikelliste neu. Diese Methode könnte von einer Action-Methode aufgerufen werden.
     * Der Getter getArtikelList() wird in der Regel von JSF verwendet, um die Liste zu rendern.
     */
	public void loadArtikelList() {
        // Stellen Sie sicher, dass der Shop nicht null ist, bevor Sie darauf zugreifen
        if (shop != null) {
            artikelList = shop.getSortiment();
        } else {
            artikelList = new ArrayList<>(); // Fallback
            System.err.println("WARNUNG: Shop ist null in loadArtikelList().");
        }
    }

    /**
     * Gibt die Liste aller Artikel im Sortiment zurück.
     * Wird von JSF verwendet, um z.B. p:selectOneMenu zu befüllen.
     * @return Die Liste der Artikel.
     */
	public List<Artikel> getArtikelList() {
		// Wenn artikelList noch nicht initialisiert ist oder aktualisiert werden muss
        if (artikelList == null || artikelList.isEmpty()) { // Optional: !artikelList.equals(shop.getSortiment()) für dynamische Updates
            if (shop != null) {
                artikelList = shop.getSortiment();
            } else {
                artikelList = new ArrayList<>(); // Fallback
                System.err.println("WARNUNG: Shop ist null in getArtikelList().");
            }
        }
        return artikelList;
    }

    /**
     * Navigiert zum nächsten Artikel im Sortiment.
     */
	public void vor() {
        // Sicherstellen, dass das Sortiment vorhanden und nicht leer ist
        if (shop != null && shop.getSortiment() != null && !shop.getSortiment().isEmpty()) {
            if (index < shop.getSortiment().size() - 1) {
                index++;
                this.artikel = shop.getSortiment().get(index); // Aktuellen Artikel aktualisieren
            }
        }
	}

    /**
     * Navigiert zum vorherigen Artikel im Sortiment.
     */
	public void zurueck() {
        // Sicherstellen, dass das Sortiment vorhanden und nicht leer ist
        if (shop != null && shop.getSortiment() != null && !shop.getSortiment().isEmpty()) {
            if (index > 0) {
                index--;
                this.artikel = shop.getSortiment().get(index); // Aktuellen Artikel aktualisieren
            } 
        }
	}

    /**
     * Listener-Methode für das keyup-Event des Bild-Inputfeldes.
     * WICHTIG: Muss 'void' als Rückgabetyp haben für AjaxBehaviorEvent Listener.
     * @param event Das AjaxBehaviorEvent-Objekt.
     */
	public void handleBildKeyEvent(AjaxBehaviorEvent event) {
		// Diese Methode wird bei jedem Tastendruck aufgerufen.
        // Der Wert des Inputfeldes ist bereits im 'artikel.bild' Property gebunden.
        // Du kannst hier zusätzliche Logik hinzufügen, z.B. Validierung oder Logging.
        System.out.println("handleBildKeyEvent aufgerufen. Aktueller Bildpfad: " + artikel.getBild());
        // Kein 'return' Statement für void-Methoden.
	}

    /**
     * Listener-Methode, die aufgerufen wird, wenn sich die Auswahl im Bild-Dropdown ändert.
     */
    public void onBildChange() {
        System.out.println("Bild geändert zu: " + selectedBild);
        // Sicherstellen, dass das Sortiment vorhanden und nicht leer ist
        if (shop != null && shop.getSortiment() != null && !shop.getSortiment().isEmpty() && selectedBild != null) {
            List<Artikel> sortiment = shop.getSortiment();
            for (int i = 0; i < sortiment.size(); i++) {
                Artikel currentArtikel = sortiment.get(i);
                // Sicherstellen, dass das Bild des Artikels nicht null ist, bevor equals aufgerufen wird
                if (currentArtikel.getBild() != null && currentArtikel.getBild().equals(selectedBild)) {
                    index = i;
                    this.artikel = currentArtikel; // Aktuellen Artikel im Controller aktualisieren
                    break;
                }
            }
        }
    }

    /**
     * Gibt den maximalen Index des Sortiments zurück.
     * @return Der maximale Index.
     */
    public int getMaxIndex() {
        // Sicherstellen, dass das Sortiment vorhanden und nicht leer ist
        if (shop == null || shop.getSortiment() == null || shop.getSortiment().isEmpty()) {
            return 0; // Wenn kein Sortiment vorhanden ist, ist der maximale Index 0 (oder -1, je nach Logik)
        }
    	return shop.getSortiment().size() - 1;
    }

    /**
     * Gibt den aktuellen Index zurück.
     * @return Der aktuelle Index.
     */
    public int getIndex() {
    	return index;
    }

    /**
     * Gibt das aktuell ausgewählte Bild zurück (für p:selectOneMenu).
     * @return Der Pfad des ausgewählten Bildes.
     */
    public String getSelectedBild() {
        // Fallback, falls selectedBild aus irgendeinem Grund null ist, aber ein Artikel geladen ist
        if (selectedBild == null && artikel != null) {
            return artikel.getBild();
        }
        return selectedBild;
    }

    /**
     * Setzt das aktuell ausgewählte Bild (von p:selectOneMenu).
     * @param selectedBild Der Pfad des ausgewählten Bildes.
     */
    public void setSelectedBild(String selectedBild) {
        this.selectedBild = selectedBild;
    }

    /**
     * Speichert den eingegebenen Artikel über den Shop-Dienst.
     */
    public void saveArtikel() {
    	if (shop != null && artikelEingabe != null) {
            shop.saveArtikel(artikelEingabe);
            System.out.println("Artikel gespeichert: " + artikelEingabe.getName());
            // Optional: Nach dem Speichern den aktuellen Artikel in der Ansicht aktualisieren
            // oder das Sortiment neu laden, um den neuen Artikel anzuzeigen.
            // this.artikelList = shop.getSortiment(); // Sortiment neu laden
            // this.artikel = artikelEingabe; // Den neu gespeicherten Artikel anzeigen
            // this.index = artikelList.indexOf(artikelEingabe); // Index des neuen Artikels finden
        } else {
            System.err.println("Fehler: Shop oder artikelEingabe ist null beim Speichern.");
        }
    }

    /**
     * Gibt das Artikel-Objekt für die Eingabeformulare zurück.
     * @return Das Artikel-Objekt für die Eingabe.
     */
	public Artikel getArtikelEingabe() {
		return artikelEingabe;
	}

    /**
     * Setzt das Artikel-Objekt für die Eingabeformulare.
     * @param artikelEingabe Das Artikel-Objekt für die Eingabe.
     */
	public void setArtikelEingabe(Artikel artikelEingabe) {
		this.artikelEingabe = artikelEingabe;
	}
}