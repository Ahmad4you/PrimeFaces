package ahmad.model;
import jakarta.persistence.*;

/**	
 * In JPA kannst du Vererbung mit Hilfe der Annotation @Inheritance realisieren, die auf einer Basisklasse angewendet wird. 
 * JPA bietet drei Strategien für die Speicherung von vererbten Klassen in einer Datenbank:
 * 
 * SINGLE_TABLE (Standard): Alle Klassen einer Hierarchie werden in einer einzigen Tabelle gespeichert.
 * @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
 * @DiscriminatorColumn(name = "person_type", discriminatorType = DiscriminatorType.STRING)
 * In der Datenbank gibt es eine einzige Tabelle Person, mit einer zusätzlichen Spalte person_type zur Unterscheidung der Subklassen.

 * TABLE_PER_CLASS: Jede konkrete Klasse hat ihre eigene Tabelle.
 * @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
 * Hier hat jede konkrete Klasse ihre eigene Tabelle:
 * 
 * JOINED: Es wird eine Tabelle für jede Klasse erstellt, wobei die Tabellen über Fremdschlüssel verbunden werden.
 * @Inheritance(strategy = InheritanceType.JOINED)
 * Hier wird jede Klasse in einer eigenen Tabelle gespeichert, wobei die Tabellen über Fremdschlüssel (id) miteinander verbunden sind
 * Beste Wahl für normale Beziehungen mit sauberer Normalisierung.
 * 
 * @author Ahmad Alrefai
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
