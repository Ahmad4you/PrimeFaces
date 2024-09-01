package home.ahmad.rest.dto;

import jakarta.persistence.EntityManager;
import java.io.Serializable;

import home.ahmad.model.Customer;

public class NestedCustomerDTO implements Serializable {

    private static final long serialVersionUID = -6115662922230218949L;

    private Long id;
    private String aliasName;
    private String email;
    private String login;
    private String password;

    public NestedCustomerDTO() {
    }

    // Constructor to initialize DTO from an entity
    public NestedCustomerDTO(final Customer entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.aliasName = entity.getAliasName();
            this.email = entity.getEmail();
            this.login = entity.getLogin();
            this.password = entity.getPassword();
        }
    }

    // Method to convert DTO back to entity
    public Customer fromDTO(Customer entity, EntityManager em) {
        if (entity == null) {
            entity = new Customer();
        }

        if (this.id != null) {
            entity = em.find(Customer.class, this.id);
            if (entity == null) {
                return null;
            }
        }

        entity.setAliasName(this.aliasName);
        entity.setEmail(this.email);
        entity.setLogin(this.login);
        entity.setPassword(this.password);

        return em.merge(entity);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
