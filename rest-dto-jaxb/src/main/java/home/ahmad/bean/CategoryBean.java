package home.ahmad.bean;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import home.ahmad.rest.dto.CategoryDTO;

/**
 * 
 * @author Ahmad Alrefai
 */
//@RequestScoped
@Named
@ViewScoped
public class CategoryBean implements Serializable {

	private static final long serialVersionUID = 9020158170965345394L;
	private static final Logger log = Logger.getLogger(CategoryBean.class.getName());
	private Client client;
	private String apiUrl = "http://localhost:8080/ahmad_restapp/api/categorys"; // Der Basis-URL REST-Services
	private List<CategoryDTO> categories;
	private CategoryDTO selectedCategory;
	private CategoryDTO newCategory;

	@PostConstruct
	public void init() {
		client = ClientBuilder.newClient();
		loadCategories();
		newCategory = new CategoryDTO(); // Initialisiert ein leeres DTO für die Erstellung neuer Kategorien
		selectedCategory = new CategoryDTO();
	}

	public void loadCategories() {
		Response response = client.target(apiUrl).request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == 200) {
			categories = response.readEntity(new GenericType<List<CategoryDTO>>() {
			});
		} else {
		
			log.severe("Fehler beim Laden der Kategorien: " + response.getStatus());
		}
	}

	public void createCategory() {
		
		if(newCategory != null) {
			log.info("Creating category: " + newCategory.getName() + ", " + newCategory.getDescription());

			Response response = client.target(apiUrl).request(MediaType.APPLICATION_JSON)
					.post(Entity.entity(newCategory, MediaType.APPLICATION_JSON));

			if (response.getStatus() == 201) {
				loadCategories(); // Aktualisiert die Liste nach dem Erstellen
			} else {
				log.severe("Fehler beim Erstellen der Kategorie: " + response.getStatus());
			}
		} else {
			log.severe("newCategory ist null!");
		}
		
	}

	public void updateCategory() {
		if (selectedCategory.getId() != null) {

			log.info("Selected Category: " + selectedCategory.getName() + ", " + selectedCategory.getDescription());

			Response response = client.target(apiUrl).path("/{id}").resolveTemplate("id", selectedCategory.getId())
					.request(MediaType.APPLICATION_JSON)
					.put(Entity.entity(selectedCategory, MediaType.APPLICATION_JSON));

			if (response.getStatus() == 204) {
				loadCategories(); // Aktualisiert die Liste nach dem Update
			} else {

				log.severe("Fehler beim Aktualisieren der Kategorie: " + response.getStatus());
			}

		} else {

			log.severe("selectedCategory ist null ..updateCategory()");
		}
	}

	public void deleteCategory(Long id) {
		if (id == null) {
			log.severe("Fehler: Die ID der zu löschenden Kategorie ist null.");
			return;
		}
		Response response = client.target(apiUrl).path("/{id}").resolveTemplate("id", id).request().delete();

		log.info("response.getStatus(): " + response.getStatus());
		if (response.getStatus() == 204) {
			// Erfolgreiches Löschen, jetzt lokal die Liste aktualisieren
			categories.removeIf(category -> category.getId().equals(id));
			loadCategories(); // Aktualisiert die Liste nach dem Löschen
		} else {

			log.severe("Fehler beim Löschen der Kategorie: " + response.getStatus());
		}
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}

	public CategoryDTO getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(CategoryDTO selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public CategoryDTO getNewCategory() {
		return newCategory;
	}

	public void setNewCategory(CategoryDTO newCategory) {
		this.newCategory = newCategory;
	}
}
