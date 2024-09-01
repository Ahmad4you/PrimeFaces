package home.ahmad.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
/**
 * @author Ahmad Alrefai
 */
@Entity
@Table(name="sizes")
public class Size extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(nullable=false, length=50)
	private String name;
	
	@Column(name = "description")
	private String description;
	
	public Size() {
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
}