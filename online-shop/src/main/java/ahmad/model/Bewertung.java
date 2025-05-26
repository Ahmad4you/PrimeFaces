package ahmad.model;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * 
 * @author Ahmad Alrefai
 */

@Entity
@Table(name = "Bewertung")
public class Bewertung implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artikel_id", nullable = false)
    private Artikel artikel; // Fremdschlüssel zu Artikel

    @Column(nullable = false)
    private int sterne; // Bewertung (1-5 Sterne)

    @Column(length = 500)
    private String kommentar; // Kommentar (optional)

    public Bewertung() {}

    public Bewertung(Artikel artikel, int sterne, String kommentar) {
        this.artikel = artikel;
        this.sterne = sterne;
        this.kommentar = kommentar;
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Artikel getArtikel() {
        return artikel;
    }

    public void setArtikel(Artikel artikel) {
        this.artikel = artikel;
    }

    public int getSterne() {
        return sterne;
    }

    public void setSterne(int sterne) {
        this.sterne = sterne;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
}
