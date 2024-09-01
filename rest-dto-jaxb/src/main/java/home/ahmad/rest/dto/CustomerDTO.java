package home.ahmad.rest.dto;

import java.io.Serializable;

import home.ahmad.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomerDTO implements Serializable {

	private static final long serialVersionUID = 887622583809104980L;
	private String aliasName;
	private Long id;
	private String email;
	private String login;
	private String password;

	public CustomerDTO() {
	}

	public CustomerDTO(final Customer entity) {
		if (entity != null) {
			this.aliasName = entity.getAliasName();
			this.id = entity.getId();
			this.email = entity.getEmail();
			this.login = entity.getLogin();
			this.password = entity.getPassword();
		}
	}

	public Customer fromDTO(Customer entity, EntityManager em) {
		if (entity == null) {
			entity = new Customer();
		}
		entity.setAliasName(this.aliasName);
		entity.setEmail(this.email);
		entity.setLogin(this.login);
		entity.setPassword(this.password);
		entity = em.merge(entity);
		return entity;
	}

	public String getAliasName() {
		return this.aliasName;
	}

	public void setAliasName(final String aliasName) {
		this.aliasName = aliasName;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}