package home.ahmad.rest.dto;

import java.io.Serializable;

import home.ahmad.model.RankingType;
import home.ahmad.model.Recommendation;
import jakarta.persistence.EntityManager;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RecommendationDTO implements Serializable {

	private static final long serialVersionUID = -3351119754418585892L;
	private Long id;
	private RankingType rankingType;
	private String description;
	private String name;
	private NestedCustomerDTO guest;
	private NestedRestaurantDTO restaurant;

	public RecommendationDTO() {
	}

	public RecommendationDTO(final Recommendation entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.rankingType = entity.getRankingType();
			this.description = entity.getDescription();
			this.name = entity.getName();
			this.guest = new NestedCustomerDTO(entity.getGuest());
			this.restaurant = new NestedRestaurantDTO(entity.getRestaurant());
		}
	}

	public Recommendation fromDTO(Recommendation entity, EntityManager em) {
		if (entity == null) {
			entity = new Recommendation();
		}
		entity.setRankingType(this.rankingType);
		entity.setDescription(this.description);
		entity.setName(this.name);
		if (this.guest != null) {
			entity.setGuest(this.guest.fromDTO(entity.getGuest(), em));
		}
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

	public RankingType getRankingType() {
		return this.rankingType;
	}

	public void setRankingType(final RankingType rankingType) {
		this.rankingType = rankingType;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public NestedCustomerDTO getGuest() {
		return this.guest;
	}

	public void setGuest(final NestedCustomerDTO guest) {
		this.guest = guest;
	}

	public NestedRestaurantDTO getRestaurant() {
		return this.restaurant;
	}

	public void setRestaurant(final NestedRestaurantDTO restaurant) {
		this.restaurant = restaurant;
	}
}