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
@Table(name = "restaurant")
public class Restaurant extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 7575789729659127329L;


	@NotNull
	@Size(min = 2, max = 50, message = "must be 2-50 letters and spaces")
	private String name;

	@Size(min = 1, max = 100, message = "must be 1-100 letters and spaces")
	private String location;							   // locationName
	
	@Size(min = 0, max = 500, message = "optional max. 500 letters and spaces")
	private String website;

	@Size(min = 0, max = 100, message = "optional max. 100 letters and spaces")
	private String dressCode;
		
	@Size(min = 0, max = 500, message = "optional max. 500 letters and spaces")
	private String linkGooglemaps;
	
	private Address address = new Address();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull
	private Category category;
	
	@Size(min = 0, max = 200, message = "optional max. 200 letters and spaces")
	private String linkMenu;
	
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "restaurant")
	private Set<Recommendation> recommendation = new HashSet<Recommendation>();
	
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "restaurant")
	private Set<PhoneContact> phoneContact = new HashSet<PhoneContact>();

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "restaurant")
	private Set<TimesOfService> timesOfService = new HashSet<TimesOfService>();


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Set<Recommendation> getRecommendation() {
		return recommendation;
	}
	
	public void setRecommendation(Set<Recommendation> recommendation) {
		this.recommendation = recommendation;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDressCode() {
		return dressCode;
	}

	public void setDressCode(String dressCode) {
		this.dressCode = dressCode;
	}

	public String getLinkMenu() {
		return linkMenu;
	}
	
	public void setLinkMenu(String linkMenu) {
		this.linkMenu = linkMenu;
	}
	
	public String getLinkGooglemaps() {
		return linkGooglemaps;
	}
	
	public void setLinkGooglemaps(String linkGooglemaps) {
		this.linkGooglemaps = linkGooglemaps;
	}
	
	public Set<PhoneContact> getPhoneContact() {
		return phoneContact;
	}

	public void setPhoneContact(Set<PhoneContact> phoneContact) {
		this.phoneContact = phoneContact;
	}

	public Set<TimesOfService> getTimesOfService() {
		return timesOfService;
	}
	
	public void setTimesOfService(Set<TimesOfService> timesOfService) {
		this.timesOfService = timesOfService;
	}


	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(name).append(", ");
		sb.append(location);
		return sb.toString();
	}
}
