package home.ahmad.rest.dto;

import java.io.Serializable;

import home.ahmad.model.PhoneContact;
import jakarta.persistence.EntityManager;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PhoneContactDTO implements Serializable {

	private static final long serialVersionUID = -2004174164706963454L;
	private Long id;
	private String phoneNumber;
	private String name;
	private NestedRestaurantDTO restaurant;

	public PhoneContactDTO() {
	}

	public PhoneContactDTO(final PhoneContact entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.phoneNumber = entity.getPhoneNumber();
			this.name = entity.getName();
			this.restaurant = new NestedRestaurantDTO(entity.getRestaurant());
		}
	}

	public PhoneContact fromDTO(PhoneContact entity, EntityManager em) {
		if (entity == null) {
			entity = new PhoneContact();
		}
		entity.setPhoneNumber(this.phoneNumber);
		entity.setName(this.name);
		if (this.restaurant != null) {
			entity.setRestaurant(this.restaurant.fromDTO(entity.getRestaurant(), em));
		}
		entity = em.merge(entity);
		return entity;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public NestedRestaurantDTO getRestaurant() {
		return this.restaurant;
	}

	public void setRestaurant(final NestedRestaurantDTO restaurant) {
		this.restaurant = restaurant;
	}
}