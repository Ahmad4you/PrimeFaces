package ahmad.validator;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * 
 * @author Ahmad Alrefai
 */

@Named
@SessionScoped
public class UserValidatorBean implements Serializable{
    
	private static final long serialVersionUID = -2674832832702928659L;

	@NotEmpty(message = "Name darf nicht leer sein")
    @Size(min = 2, max = 30, message = "Name muss zwischen 2 und 30 Zeichen lang sein")
    private String name;
    
    @NotEmpty(message = "Email darf nicht leer sein")
    @Email(message = "Ungültiges Email-Format")
    private String email;
    
    // Custom Validator Methode
    public void validateName(FacesContext context, UIComponent component, Object value) {
        String name = (String) value;
        if (name != null && name.contains("admin")) {
            throw new ValidatorException (
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Name darf nicht 'admin' enthalten", null));
        }
    }

    // Getter und Setter
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
    
//    @FacesValidator("customNameValidator")
//    public class CustomNameValidator implements Validator<Object> {
//        @Override
//        public void validate(FacesContext context, UIComponent component, Object value) 
//            throws ValidatorException {
//            // Validierungslogik hier
//        }
//    }
//    
//    <h:inputText>
//    <f:validator validatorId="customNameValidator" />
//    </h:inputText>
//    
    
}