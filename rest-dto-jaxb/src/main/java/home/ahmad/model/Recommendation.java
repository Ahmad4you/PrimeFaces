package home.ahmad.model;

/**
 * @author Ahmad Alrefai
 */

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Cacheable
@Entity
@Table(name = "recommendation")
public class Recommendation extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 7975779629657126329L;

	@NotNull
	@Size(min = 1, max = 100, message = "must be 1-100 letters and spaces")
	private String name;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull
	private Customer guest;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull
	private Restaurant restaurant;
	
	@NotNull
	@Size(min = 1, max = 5000, message = "must be 1-5000 letters and spaces")
	private String description;

    @Column(name = "ranking_type")
    @Enumerated(EnumType.STRING)
    private RankingType rankingType;
    

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}
	
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Customer getGuest() {
		return guest;
	}
	
	public void setGuest(Customer guest) {
		this.guest = guest;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public RankingType getRankingType() {
		return rankingType;
	}

	public void setRankingType(RankingType rankingType) {
		this.rankingType = rankingType;
	}


	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(name);
		return sb.toString();
	}
}
