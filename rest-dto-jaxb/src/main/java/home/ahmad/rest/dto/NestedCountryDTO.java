package home.ahmad.rest.dto;

import jakarta.persistence.EntityManager;
import java.io.Serializable;

import home.ahmad.model.Country;

public class NestedCountryDTO implements Serializable {

    private static final long serialVersionUID = -2906596106169627147L;

    private Long id;
    private String printableName;
    private String numcode;
    private String name;
    private String iso3;
    private String isoCode;

    // Default constructor
    public NestedCountryDTO() {
    }

    // Constructor to initialize DTO from an entity
    public NestedCountryDTO(final Country entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.printableName = entity.getPrintableName();
            this.numcode = entity.getNumcode();
            this.name = entity.getName();
            this.iso3 = entity.getIso3();
            this.isoCode = entity.getIsoCode();
        }
    }

    // Method to convert DTO back to entity
    public Country fromDTO(Country entity, EntityManager em) {
        if (entity == null) {
            entity = new Country();
        }

        if (this.id != null) {
            entity = em.find(Country.class, this.id);
            if (entity == null) {
                return null;
            }
        }

        entity.setPrintableName(this.printableName);
        entity.setNumcode(this.numcode);
        entity.setName(this.name);
        entity.setIso3(this.iso3);
        entity.setIsoCode(this.isoCode);

        return em.merge(entity);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrintableName() {
        return printableName;
    }

    public void setPrintableName(String printableName) {
        this.printableName = printableName;
    }

    public String getNumcode() {
        return numcode;
    }

    public void setNumcode(String numcode) {
        this.numcode = numcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }
}
