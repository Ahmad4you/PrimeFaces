package home.ahmad.rest.dto;

import java.io.Serializable;

import home.ahmad.model.Address;
import jakarta.persistence.EntityManager;



public class AddressDTO implements Serializable {

   /**
	 * 
	 */
	private static final long serialVersionUID = 805484066843741810L;
private String street2;
   private String street1;
   private String zipCode;
   private String state;
   private NestedCountryDTO country;
   private String city;

   public AddressDTO()
   {
   }

   public AddressDTO(final Address entity)
   {
      if (entity != null)
      {
         this.street2 = entity.getStreet2();
         this.street1 = entity.getStreet1();
         this.zipCode = entity.getZipCode();
         this.state = entity.getState();
         this.country = new NestedCountryDTO(entity.getCountry());
         this.city = entity.getCity();
      }
   }

   public Address fromDTO(Address entity, EntityManager em)
   {
      if (entity == null)
      {
         entity = new Address();
      }
      entity.setStreet2(this.street2);
      entity.setStreet1(this.street1);
      entity.setZipCode(this.zipCode);
      entity.setState(this.state);
      if (this.country != null)
      {
         entity.setCountry(this.country.fromDTO(entity.getCountry(), em));
      }
      entity.setCity(this.city);
      return entity;
   }

   public String getStreet2()
   {
      return this.street2;
   }

   public void setStreet2(final String street2)
   {
      this.street2 = street2;
   }

   public String getStreet1()
   {
      return this.street1;
   }

   public void setStreet1(final String street1)
   {
      this.street1 = street1;
   }

   public String getZipCode()
   {
      return this.zipCode;
   }

   public void setZipCode(final String zipCode)
   {
      this.zipCode = zipCode;
   }

   public String getState()
   {
      return this.state;
   }

   public void setState(final String state)
   {
      this.state = state;
   }

   public NestedCountryDTO getCountry()
   {
      return this.country;
   }

   public void setCountry(final NestedCountryDTO country)
   {
      this.country = country;
   }

   public String getCity()
   {
      return this.city;
   }

   public void setCity(final String city)
   {
      this.city = city;
   }
}