package home.ahmad.bean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import org.primefaces.event.RowEditEvent;

import home.ahmad.rest.dto.CountryDTO;

@Named
@SessionScoped
public class CountryBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(CountryBean.class.getName());
	private Client client;
	private String apiUrl = "http://localhost:8080/ahmad_restapp/api/countrys"; // URL für die REST-Services

	private List<CountryDTO> countries;
	private CountryDTO selectedCountry;
	private CountryDTO newCountry;

	@PostConstruct
	public void init() {
		client = ClientBuilder.newClient();
		loadCountries();
		newCountry = new CountryDTO();
		selectedCountry = new CountryDTO();
	}

	public void loadCountries() {
		Response response = client.target(apiUrl).request(MediaType.APPLICATION_JSON).get();
		if (response.getStatus() == 200) {
			countries = response.readEntity(new GenericType<List<CountryDTO>>() {
			});
		} else {

			log.severe("Fehler beim Laden der Länder: " + response.getStatus());
		}
	}

	public void createCountry() {
		if (newCountry.getName() == null) {
			log.severe("newCountry in createCountry() ist null ");
			return;
		}
		Response response = client.target(apiUrl).request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(newCountry, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 201) {
			loadCountries();
		} else {
			log.severe("Fehler beim Erstellen des Landes: " + response.getStatus());
		}
	}

	public void updateCountry() {
		if (selectedCountry != null) {
			Response response = client.target(apiUrl).path("/{id}").resolveTemplate("id", selectedCountry.getId())
					.request(MediaType.APPLICATION_JSON)
					.put(Entity.entity(selectedCountry, MediaType.APPLICATION_JSON));
			if (response.getStatus() == 204) {
				loadCountries();
			} else {
				log.severe("Fehler beim Aktualisieren des Landes: " + response.getStatus());
			}
		}
	}

	public void deleteCountry() {
		if (selectedCountry != null && selectedCountry.getId() != null) {
			Response response = client.target(apiUrl).path("/{id}").resolveTemplate("id", selectedCountry.getId())
					.request().delete();
			if (response.getStatus() == 204) {
				loadCountries();
			} else {
				log.severe("Fehler beim Löschen des Landes: " + response.getStatus());
			}
		} else {
			log.severe("Fehler: Keine gültige ID für das zu löschende Land.");
		}
	}

	public List<CountryDTO> getCountries() {
		return countries;
	}

	public CountryDTO getSelectedCountry() {
		return selectedCountry;
	}

	public void setSelectedCountry(CountryDTO selectedCountry) {
		this.selectedCountry = selectedCountry;
	}

	public CountryDTO getNewCountry() {
		return newCountry;
	}

	public void setNewCountry(CountryDTO newCountry) {
		this.newCountry = newCountry;
	}

	public void onRowEdit(RowEditEvent<?> event) {
		CountryDTO editedCountry = (CountryDTO) event.getObject();

		if (editedCountry.getId() == null) {
			// new Country to persist
			Response response = client.target(apiUrl).request(MediaType.APPLICATION_JSON)
					.post(Entity.entity(editedCountry, MediaType.APPLICATION_JSON));
			if (response.getStatus() == 201) {
				loadCountries();
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("new Coutry wurde erfolgreich gespeichert!"));
			} else {
				log.severe("Fehler beim Erstellen des Landes: " + response.getStatus());
			}
			return;
		}

		// REST API Aufruf zur Aktualisierung des Landes
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(apiUrl).path(String.valueOf(editedCountry.getId()));
		Response response = target.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(editedCountry, MediaType.APPLICATION_JSON));

		if (response.getStatus() == 204) { // No Content, indicating success
			FacesMessage msg = new FacesMessage("Land erfolgreich bearbeitet", "ID: " + editedCountry.getId());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			log.severe("Fehler beim Bearbeiten des Landes: " + response.getStatus());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Bearbeiten des Landes",
					"Status: " + response.getStatus());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void onRowEditCancel(RowEditEvent<?> event) {
		FacesMessage msg = new FacesMessage("Bearbeitung abgebrochen",
				"ID: " + ((CountryDTO) event.getObject()).getId());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}


	public void addEmptyCountryRow() {
		// Erstelle eine leere Instanz von CountryDTO
		CountryDTO emptyCountry = new CountryDTO();

		countries.add(emptyCountry);

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Leere Zeile hinzugefügt"));
	}

}
