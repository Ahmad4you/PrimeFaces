package home.ahmad.model;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author Ahmad Alrefai
 */
@Cacheable
@Entity
@NamedQueries({
    @NamedQuery(name = "findByLogin", query = "SELECT c FROM Customer c WHERE c.login = :login"),
    @NamedQuery(name = "findByLoginAndPassword", query = "SELECT c FROM Customer c WHERE c.login = :login AND c.password = :password")
})
@Table(name = "registered_customer")
public class Customer extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 7975789629699126629L;

    public static final String FIND_BY_LOGIN = "findByLogin";
    public static final String FIND_BY_LOGIN_PASSWORD = "findByLoginAndPassword";
    

	@NotNull
	@Size(min = 2, max = 50, message = "must be 2-50 letters and spaces")
	private String aliasName;
	
	@NotNull
	@NotEmpty
	@Email(message = "invalid email format")
	private String email;

	@NotNull
	@Size(min = 1, max = 10, message = "must be 1-10 letters and spaces")
	private String login;

	@NotNull
	@Size(min = 1, max = 256, message = "must be 1-256 letters and spaces")
	private String password;

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "guest")
	private Set<Recommendation> recommendation = new HashSet<Recommendation>();


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


	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(aliasName).append(", ");
		sb.append(email);
		return sb.toString();
	}
}
