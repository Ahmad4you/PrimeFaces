package home.ahmad.rest.dto;

import java.io.Serializable;

import home.ahmad.model.PhoneContact;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class NestedPhoneContactDTO implements Serializable {

	private static final long serialVersionUID = -8371519444336438336L;
	private Long id;
	private String phoneNumber;
	private String name;

	public NestedPhoneContactDTO() {
	}

	public NestedPhoneContactDTO(final PhoneContact entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.phoneNumber = entity.getPhoneNumber();
			this.name = entity.getName();
		}
	}

	public PhoneContact fromDTO(PhoneContact entity, EntityManager em) {
		if (entity == null) {
			entity = new PhoneContact();
		}
		if (this.id != null) {
			TypedQuery<PhoneContact> findByIdQuery = em
					.createQuery("SELECT DISTINCT p FROM PhoneContact p WHERE p.id = :entityId", PhoneContact.class);
			findByIdQuery.setParameter("entityId", this.id);
			try {
				entity = findByIdQuery.getSingleResult();
			} catch (Exception nre) {
				entity = null;
			}
			return entity;
		}
		entity.setPhoneNumber(this.phoneNumber);
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

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}