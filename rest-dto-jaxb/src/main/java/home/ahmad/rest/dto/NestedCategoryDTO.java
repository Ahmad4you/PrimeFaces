package home.ahmad.rest.dto;

import java.io.Serializable;

import home.ahmad.model.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

/**
 * Diese Klasse wird verwendet, um eine vereinfachte oder verschachtelte Darstellung der Category-Entität zu liefern, 
 * ohne die vollständigen Informationen der verbundenen Restaurant-Entitäten.
 * 
 * @author Ahmad Alrefai
 */
public class NestedCategoryDTO implements Serializable {

    private static final long serialVersionUID = 5784258914330733014L;

    private Long id;
    private String description;
    private String name;

    
    public NestedCategoryDTO() {
    }

    // Constructor to initialize DTO from an entity
    public NestedCategoryDTO(Category entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.description = entity.getDescription();
            this.name = entity.getName();
        }
    }

    // Method to convert DTO back to entity
    public Category fromDTO(Category entity, EntityManager em) {
        if (entity == null) {
            entity = new Category();
        }

        if (this.id != null) {
            entity = findCategoryById(this.id, em);
            if (entity == null) {
                // Optional: Handle the case where the entity was not found
                return null; // or throw an exception
            }
        }

        entity.setDescription(this.description);
        entity.setName(this.name);

        return em.merge(entity);
    }

    // Utility method to find Category by ID
    private Category findCategoryById(Long id, EntityManager em) {
        TypedQuery<Category> query = em.createQuery(
            "SELECT c FROM Category c WHERE c.id = :entityId",
            Category.class
        );
        query.setParameter("entityId", id);
        
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            // Log the exception if needed, and return null if not found
            return null;
        }
    }

    // Getters and setters for DTO fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}