package home.ahmad.rest.dto;

import java.io.Serializable;

import home.ahmad.model.TimesOfService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class NestedTimesOfServiceDTO implements Serializable {

	private static final long serialVersionUID = 3396919858228233993L;
	private String startTime;
	private Long id;
	private String days;
	private String endTime;

	public NestedTimesOfServiceDTO() {
	}

	public NestedTimesOfServiceDTO(final TimesOfService entity) {
		if (entity != null) {
			this.startTime = entity.getStartTime();
			this.id = entity.getId();
			this.days = entity.getDays();
			this.endTime = entity.getEndTime();
		}
	}

	public TimesOfService fromDTO(TimesOfService entity, EntityManager em) {
		if (entity == null) {
			entity = new TimesOfService();
		}
		if (this.id != null) {
			TypedQuery<TimesOfService> findByIdQuery = em.createQuery(
					"SELECT DISTINCT t FROM TimesOfService t WHERE t.id = :entityId", TimesOfService.class);
			findByIdQuery.setParameter("entityId", this.id);
			try {
				entity = findByIdQuery.getSingleResult();
			} catch (Exception nre) {
				entity = null;
			}
			return entity;
		}
		entity.setStartTime(this.startTime);
		entity.setDays(this.days);
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

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(final String endTime) {
		this.endTime = endTime;
	}
}