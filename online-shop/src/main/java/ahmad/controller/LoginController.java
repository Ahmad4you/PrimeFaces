package ahmad.controller;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.net.URL;

/**
 * 
 * @author Ahmad Alrefai
 */

@Named("loginBean")
@SessionScoped
public class LoginController implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String email;
    private String passwort;
    
    // Beispiel-Credentials 
    private static final String VALID_EMAIL = "test@example.de";
    private static final String VALID_PASSWORD = "P$assword123";
    
    @PostConstruct
	public void init() {
		// Überprüfen den Classpath 
		URL url = this.getClass().getClassLoader().getResource("messages.properties");
		System.out.println("Resource URL: " + url);
	}
    
    // Getter und Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    // Login-Methode
    public String submit() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        // Überprüfen der Anmeldedaten
        if (VALID_EMAIL.equals(email) && VALID_PASSWORD.equals(passwort)) {
            // Erfolgreiche Anmeldung
            // Benutzer in die Session speichern
            context.getExternalContext().getSessionMap().put("user", email);
            
            // Weiterleitung zur index.xhtml mit Redirect
            return "index?faces-redirect=true";
        } else {
            // Fehlgeschlagene Anmeldung
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Ungültige Anmeldedaten", "Email oder Passwort ist falsch.");
            context.addMessage(null, message);
            return null; // Auf der gleichen Seite bleiben
        }
    }

    // Logout-Methode
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }

    // Methode zur Überprüfung, ob ein Benutzer angemeldet ist
    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext()
            .getSessionMap().get("user") != null;
    }
    
    public void saveUser() {
        try {
            // Geschäftslogik
        		
        	
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Fehler beim Speichern", "Der Benutzer konnte nicht gespeichert werden.");
            context.addMessage(null, message);
        }
    }
}