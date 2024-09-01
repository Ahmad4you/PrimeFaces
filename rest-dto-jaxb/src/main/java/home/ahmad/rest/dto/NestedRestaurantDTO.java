package home.ahmad.rest.dto;

import java.io.Serializable;

import home.ahmad.model.Restaurant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class NestedRestaurantDTO implements Serializable {

	private static final long serialVersionUID = -1994190636866497876L;
	private Long id;
	private AddressDTO address;
	private String website;
	private String location;
	private String name;
	private String linkGooglemaps;
	private String linkMenu;
	private String dressCode;

	public NestedRestaurantDTO() {
	}

	public NestedRestaurantDTO(final Restaurant entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.address = new AddressDTO(entity.getAddress());
			this.website = entity.getWebsite();
			this.location = entity.getLocation();
			this.name = entity.getName();
			this.linkGooglemaps = entity.getLinkGooglemaps();
			this.linkMenu = entity.getLinkMenu();
			this.dressCode = entity.getDressCode();
		}
	}

	public Restaurant fromDTO(Restaurant entity, EntityManager em) {
		if (entity == null) {
			entity = new Restaurant();
		}
		if (this.id != null) {
			TypedQuery<Restaurant> findByIdQuery = em
					.createQuery("SELECT DISTINCT r FROM Restaurant r WHERE r.id = :entityId", Restaurant.class);
			findByIdQuery.setParameter("entityId", this.id);
			try {
				entity = findByIdQuery.getSingleResult();
			} catch (Exception nre) {
				entity = null;
			}
			return entity;
		}
		if (this.address != null) {
			entity.setAddress(this.address.fromDTO(entity.getAddress(), em));
		}
		entity.setWebsite(this.website);
		entity.setLocation(this.location);
		entity.setName(this.name);
		entity.setLinkGooglemaps(this.linkGooglemaps);
		entity.setLinkMenu(this.linkMenu);
		entity.setDressCode(this.dressCode);
		entity = em.merge(entity);
		return entity;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public AddressDTO getAddress() {
		return this.address;
	}

	public void setAddress(final AddressDTO address) {
		this.address = address;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(final String website) {
		this.website = website;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(final String location) {
		this.location = location;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getLinkGooglemaps() {
		return this.linkGooglemaps;
	}

	public void setLinkGooglemaps(final String linkGooglemaps) {
		this.linkGooglemaps = linkGooglemaps;
	}

	public String getLinkMenu() {
		return this.linkMenu;
	}

	public void setLinkMenu(final String linkMenu) {
		this.linkMenu = linkMenu;
	}

	public String getDressCode() {
		return this.dressCode;
	}

	public void setDressCode(final String dressCode) {
		this.dressCode = dressCode;
	}
}