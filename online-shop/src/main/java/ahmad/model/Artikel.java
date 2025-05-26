package ahmad.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

/**
 * 
 * @author Ahmad Alrefai
 */

@Entity
@Table(name = "Artikel")
public class Artikel implements Serializable{
	private static final long serialVersionUID = 4822991030680861409L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	private String name;
	private String description;
	private String bild;
	
	@Temporal(TemporalType.DATE)
    private Date verfuegbarAb;
	
	@OneToMany(mappedBy = "artikel", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Bewertung> bewertungen = new ArrayList<>();

	
	
	public Artikel(String name, String description, String bild) {
		this(name, description, bild, new Date(0));
	}
	
	public Artikel(String name, String description, String bild, Date verfuegbarAb) {
		this.name = name;
		this.description = description;
		this.bild = bild;
		this.verfuegbarAb = verfuegbarAb;
	}
	
	public Artikel() {
		// TODO Auto-generated constructor stub
	}
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getBild() {
		return bild;
	}

	public void setBild(String bild) {
		this.bild = bild;
	}
	
	public Date getVerfuegbarAb() {
		return verfuegbarAb;
	}

	public void setVerfuegbarAb(Date verfuegbarAb) {
		this.verfuegbarAb = verfuegbarAb;
	}
	
	public List<Bewertung> getBewertungen() {
	    return bewertungen;
	}

	public void setBewertungen(List<Bewertung> bewertungen) {
	    this.bewertungen = bewertungen;
	}

}
