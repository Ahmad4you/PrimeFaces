package home.ahmad.flow;

import jakarta.faces.flow.FlowScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 * Zuerst erstellen eine Flow-Definition-config in der Datei faces-config, registration-flow.xml
 * 
 * @FlowScoped ist besonders nützlich für mehrstufige Prozesse oder Wizards, da es den Zustand über mehrere Seiten hinweg beibehält, 
 * ohne den gesamten Session-Scope zu belasten. Es bietet eine saubere Möglichkeit, zusammengehörige Aktionen zu gruppieren 
 * und den Lebenszyklus der damit verbundenen Daten zu verwalten
 * 
 * Der Flow besteht aus zwei Schritten (username/email und password/confirm password).
 * Das Bean behält seinen Zustand während des gesamten Registrierungsprozesses.
 * Nach Abschluss der Registrierung wird der Benutzer zur Erfolgsseite weitergeleitet.
 * Der Flow endet, wenn der Benutzer zur Hauptseite zurückkehrt.
 * 
 * @author Ahmad Alrefai
 */
@Named
@FlowScoped("registration")
public class RegistrationBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    public String moveToStep2() {
        // Validierung für Schritt 1
        return "registration-step2";
    }

    public String register() {
        // Registrierungslogik hier
        return "registrationSuccess";
    }

    public String getReturnValue() {
        return "/flow_starten";
    }

    // Getter und Setter für alle Felder
    // ...

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}