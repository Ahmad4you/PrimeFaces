package home.ahmad.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author Ahmad Alrefai
 */


@Embeddable
public class Address implements Serializable {

	private static final long serialVersionUID = -378509685027672910L;

	@NotNull
	@Size(min = 5, max = 50, message = "must be 5-50 letters and spaces")
    private String street1;
    
    private String street2;
    
    @NotNull
    @Size(min = 2, max = 50, message = "must be 2-50 letters and spaces")
    private String city;
    
    @NotNull
    @Size(min = 2, max = 50, message = "must be 2-50 letters and spaces")
    private String state;
    
    @NotNull
    @Size(min = 1, max = 10, message = "must be 1-10 letters and spaces")
    @Column(name = "zip_code")
    private String zipCode;
    
    @ManyToOne
    private Country country;
    
	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Country getCountry() {
		return country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}
	
    /* toString(), equals() and hashCode() for Address, using the natural identity of the object */
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Address address = (Address) o;

        if (city != null ? !city.equals(address.city) : address.city != null)
            return false;
        if (state != null ? !state.equals(address.state) : address.state != null)
            return false;
        if (country != null ? !country.getIsoCode().equals(address.country.getIsoCode()) : address.country.getIsoCode() != null)
        	return false;
        if (street1 != null ? !street1.equals(address.street1) : address.street1 != null)
            return false;
        if (zipCode != null ? !zipCode.equals(address.zipCode) : address.zipCode != null)
        	return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = street1 != null ? street1.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + (country.getIsoCode() != null ? country.getIsoCode().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Delivery Address: ");
        sb.append(street1).append('\'');
        if (street2 != null && !street2.isEmpty()) {
        	sb.append(", ").append(street2).append('\'');
        }
        sb.append(", ").append(zipCode).append('\'');
        sb.append(" ").append(city).append('\'');
        sb.append(", ").append(state).append('\'');
        sb.append(", ").append(country.getPrintableName()).append('\'');
        return sb.toString();
    }
}
