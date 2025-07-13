package ahmad.beans;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Named // Macht diese Klasse zu einer CDI-Managed Bean, zugänglich über EL (Expression Language)
@RequestScoped // Definiert den Scope als RequestScoped
public class AnfrageCounterBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // Ein statischer Zähler, um zu sehen, wie oft Instanzen dieser Bean erstellt werden.
    // AtomicInteger ist Thread-sicher für statische Zähler.
    private static final AtomicInteger instanceCount = new AtomicInteger(0);

    private int beanInstanceId; // Eine ID für jede Bean-Instanz
    private LocalDateTime creationTime; // Zeitpunkt der Erstellung der Bean-Instanz

    public AnfrageCounterBean() {
        // Dieser Konstruktor wird jedes Mal aufgerufen, wenn eine neue Instanz erstellt wird
        this.beanInstanceId = instanceCount.incrementAndGet();
        System.out.println("--- AnfrageCounterBean Konstruktor aufgerufen. Instanz-ID: " + beanInstanceId);
    }

    @PostConstruct
    public void init() {
        // Diese Methode wird nach dem Konstruktor und nach der Injektion von Abhängigkeiten aufgerufen.
        this.creationTime = LocalDateTime.now();
        System.out.println("--- AnfrageCounterBean @PostConstruct aufgerufen. Instanz-ID: " + beanInstanceId + " Erstellt um: " + creationTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    @PreDestroy
    public void destroy() {
        // Diese Methode wird aufgerufen, kurz bevor die Bean zerstört wird (am Ende der Anfrage).
        System.out.println("--- AnfrageCounterBean @PreDestroy aufgerufen. Instanz-ID: " + beanInstanceId + " Zerstört nach: " + (System.currentTimeMillis() - creationTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()) + "ms");
    }

    public String getMessage() {
        return "Dies ist Instanz Nr. " + beanInstanceId + " der AnfrageCounterBean, erstellt um " +
               creationTime.format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")) + ".";
    }

    public int getBeanInstanceId() {
        return beanInstanceId;
    }

    public int getTotalInstanceCount() {
        return instanceCount.get(); // Zeigt die Gesamtanzahl der jemals erstellten Instanzen
    }
}