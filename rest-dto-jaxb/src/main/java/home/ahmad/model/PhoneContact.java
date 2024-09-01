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
@Table(name = "phone_contact")
public class PhoneContact extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 127797175632679629L;


	@NotNull
	@Size(min = 3, max = 20, message = "must be 3-20 letters and spaces")
	private String phoneNumber;
	
    @NotNull
    @Size(min = 2, max = 50, message = "must be 2-50 letters and spaces")
    private String name;
    
	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull
	private Restaurant restaurant;


	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

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
	

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(phoneNumber).append(", ");
		sb.append(name);
		return sb.toString();
	}
}
