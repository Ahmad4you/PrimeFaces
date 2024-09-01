package home.ahmad.rest.dto;

import java.io.Serializable;

import home.ahmad.model.RankingType;
import home.ahmad.model.Recommendation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class NestedRecommendationDTO implements Serializable {

	private static final long serialVersionUID = 2166302226235663402L;
	private Long id;
	private RankingType rankingType;
	private String description;
	private String name;

	public NestedRecommendationDTO() {
	}

	public NestedRecommendationDTO(final Recommendation entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.rankingType = entity.getRankingType();
			this.description = entity.getDescription();
			this.name = entity.getName();
		}
	}

	public Recommendation fromDTO(Recommendation entity, EntityManager em) {
		if (entity == null) {
			entity = new Recommendation();
		}
		if (this.id != null) {
			TypedQuery<Recommendation> findByIdQuery = em.createQuery(
					"SELECT DISTINCT r FROM Recommendation r WHERE r.id = :entityId", Recommendation.class);
			findByIdQuery.setParameter("entityId", this.id);
			try {
				entity = findByIdQuery.getSingleResult();
			} catch (Exception nre) {
				entity = null;
			}
			return entity;
		}
		entity.setRankingType(this.rankingType);
		entity.setDescription(this.description);
		entity.setName(this.name);
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
}