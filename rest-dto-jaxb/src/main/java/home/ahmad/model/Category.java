package home.ahmad.model;

/**
 * @author Ahmad Alrefai
 */
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Cacheable
@Entity
@Table(name = "category")
public class Category extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1271789629777176727L;

	
	@NotNull
	@Size(min = 1, max = 50, message = "must be 1-50 letters and spaces")
	private String name;

	@NotNull
	@Size(min = 1, max = 3000, message = "must be 1-3000 letters and spaces")
	private String description;

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "category")
	private Set<Restaurant> restaurant = new HashSet<Restaurant>();


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

	public Set<Restaurant> getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Set<Restaurant> restaurant) {
		this.restaurant = restaurant;
	}

	
	@Override
	public String toString() {
		return name + ", " + description;
	}
}
