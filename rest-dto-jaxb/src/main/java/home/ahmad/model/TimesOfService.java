package home.ahmad.model;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author Ahmad Alrefai
 */

@Cacheable
@Entity
@Table(name = "times_of_service")
public class TimesOfService extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 7278971756722267965L;

	@NotNull
	@Size(min = 2, max = 100, message = "must be 2-100 letters and spaces")
	private String days;
	
	@NotNull
	@Size(min = 4, max = 10, message = "must be 4-10 letters and spaces")
	private String startTime;
	
	@NotNull
	@Size(min = 4, max = 10, message = "must be 4-10 letters and spaces")
	private String endTime;
	
	@ManyToOne(fetch = FetchType.EAGER )
	@NotNull
	private Restaurant restaurant;

	
	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(days).append(", ");
		sb.append(startTime);
		if (endTime != null) {
			sb.append(", ");
			sb.append(endTime);
		}
		return sb.toString();
	}
}
