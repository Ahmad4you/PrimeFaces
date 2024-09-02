package home.ahmad.rest.dto;

import java.io.Serializable;

import home.ahmad.model.Country;
import jakarta.persistence.EntityManager;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CountryDTO implements Serializable {

	private static final long serialVersionUID = -7582082835700466622L;
	private Long id;
	private String printableName;
	private String numcode;
	private String name;
	private String iso3;
	private String isoCode;

	public CountryDTO() {
	}

	public CountryDTO(final Country entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.printableName = entity.getPrintableName();
			this.numcode = entity.getNumcode();
			this.name = entity.getName();
			this.iso3 = entity.getIso3();
			this.isoCode = entity.getIsoCode();
		}
	}

	public Country fromDTO(Country entity, EntityManager em) {
		if (entity == null) {
			entity = new Country();
		}
		entity.setPrintableName(this.printableName);
		entity.setNumcode(this.numcode);
		entity.setName(this.name);
		entity.setIso3(this.iso3);
		entity.setIsoCode(this.isoCode);
		entity = em.merge(entity);
		return entity;
	}
	
	public Country toEntity() {
	    Country entity = new Country();
	    entity.setIsoCode(this.getIsoCode());
	    entity.setName(this.getName());
	    entity.setPrintableName(this.getPrintableName());
	    entity.setIso3(this.getIso3());
	    entity.setNumcode(this.getNumcode());
	    return entity;
	}


	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getPrintableName() {
		return this.printableName;
	}

	public void setPrintableName(final String printableName) {
		this.printableName = printableName;
	}

	public String getNumcode() {
		return this.numcode;
	}

	public void setNumcode(final String numcode) {
		this.numcode = numcode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getIso3() {
		return this.iso3;
	}

	public void setIso3(final String iso3) {
		this.iso3 = iso3;
	}

	public String getIsoCode() {
		return this.isoCode;
	}

	public void setIsoCode(final String isoCode) {
		this.isoCode = isoCode;
	}
}