package ahmad.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

/**
 * 
 * @author Ahmad Alrefai
 */

@Named
@RequestScoped
public class UserBean {
    private String name;
    private String email;
    private int age;

    public String submit() {
        // Verarbeitung der Formulardaten
        return "success";
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
    
    
}
