package home.ahmad.rest.dto;

import java.io.Serializable;
import jakarta.persistence.EntityManager;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Set;
import java.util.stream.Collectors;

import home.ahmad.model.Category;
import home.ahmad.model.Restaurant;
import java.util.HashSet;

/**
 * @XmlRootElement ist eine Annotation aus dem Java Architecture for XML Binding (JAXB) API, 
 * die angibt, dass eine Klasse in ein XML-Dokument serialisiert werden kann und das Wurzelelement dieses XML-Dokuments darstellt.
 * 
 * Sie dient dazu, alle relevanten Informationen über eine Kategorie und ihre assoziierten Restaurants zu übertragen.
 * @author Ahmad Alrefai
 */



@XmlRootElement
public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = 1609262954312325373L;
    private Long id;
    private String description;
    private String name;
    private Set<NestedRestaurantDTO> restaurant = new HashSet<>();

    // Default constructor
    public CategoryDTO() {
    }

    // Constructor to initialize DTO from an entity
    public CategoryDTO(final Category entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.description = entity.getDescription();
            this.name = entity.getName();
            //  Java Streams verwendet, um durch die Listen und Sets zu iterieren.
            this.restaurant = entity.getRestaurant().stream()
                .map(NestedRestaurantDTO::new)
                .collect(Collectors.toSet());
        }
    }

    // Method to convert DTO back to entity
    public Category fromDTO(Category entity, EntityManager em) {
        if (entity == null) {
            entity = new Category();
        }

        entity.setDescription(this.description);
        entity.setName(this.name);

        // Remove restaurants that are no longer in the DTO
        Set<Long> dtoRestaurantIds = this.restaurant.stream()
            .map(NestedRestaurantDTO::getId)
            .collect(Collectors.toSet());

        entity.getRestaurant().removeIf(restaurant -> !dtoRestaurantIds.contains(restaurant.getId()));

        // Add or update restaurants from the DTO
        for (NestedRestaurantDTO dtoRestaurant : this.restaurant) {
            if (entity.getRestaurant().stream().noneMatch(r -> r.getId().equals(dtoRestaurant.getId()))) {
                Restaurant restaurantEntity = em.find(Restaurant.class, dtoRestaurant.getId());
                if (restaurantEntity != null) {
                    entity.getRestaurant().add(restaurantEntity);
                }
            }
        }

        return em.merge(entity);
    }


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

    public Set<NestedRestaurantDTO> getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Set<NestedRestaurantDTO> restaurant) {
        this.restaurant = restaurant;
    }
}
