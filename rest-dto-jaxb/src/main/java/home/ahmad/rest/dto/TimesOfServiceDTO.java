package home.ahmad.rest.dto;

import java.io.Serializable;

import home.ahmad.model.TimesOfService;
import jakarta.persistence.EntityManager;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TimesOfServiceDTO implements Serializable {

	private static final long serialVersionUID = 1314694281505092693L;
	private String startTime;
	private Long id;
	private String days;
	private NestedRestaurantDTO restaurant;
	private String endTime;

	public TimesOfServiceDTO() {
	}

	public TimesOfServiceDTO(final TimesOfService entity) {
		if (entity != null) {
			this.startTime = entity.getStartTime();
			this.id = entity.getId();
			this.days = entity.getDays();
			this.restaurant = new NestedRestaurantDTO(entity.getRestaurant());
			this.endTime = entity.getEndTime();
		}
	}

	public TimesOfService fromDTO(TimesOfService entity, EntityManager em) {
		if (entity == null) {
			entity = new TimesOfService();
		}
		entity.setStartTime(this.startTime);
		entity.setDays(this.days);
		if (this.restaurant != null) {
			entity.setRestaurant(this.restaurant.fromDTO(entity.getRestaurant(), em));
		}
		entity.setEndTime(this.endTime);
		entity = em.merge(entity);
		return entity;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(final String startTime) {
		this.startTime = startTime;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getDays() {
		return this.days;
	}

	public void setDays(final String days) {
		this.days = days;
	}

	public NestedRestaurantDTO getRestaurant() {
		return this.restaurant;
	}

	public void setRestaurant(final NestedRestaurantDTO restaurant) {
		this.restaurant = restaurant;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(final String endTime) {
		this.endTime = endTime;
	}
}