package home.ahmad.conversation;

import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ConversationScoped ist nützlich in Szenarien, wo man Daten über mehrere Anfragen hinweg beibehalten müssen, 
 * aber nicht für die gesamte Sitzung. Es ermöglicht eine feinere Kontrolle über den Lebenszyklus von Beans 
 * als @SessionScoped, was besonders in Anwendungen mit mehrschrittigen Prozessen nützlich sein kann.
 * 
 * @author Ahmad Alrefai
 */
@Named
@ConversationScoped // nstanz für die Dauer einer Konversation bestehen bleibt.
public class ShoppingCartBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Conversation conversation;

    private List<String> items = new ArrayList<>();
    private String newItem; 
    
    /**
     * Wenn keine aktive Konversation existiert (isTransient() ist true), wird eine neue Konversation mit conversation.begin() gestartet.
     * 
     * Beim ersten Aufruf von addItem(): isTransient() ist true, also wird begin() aufgerufen.
     * Bei weiteren Aufrufen von addItem(): isTransient() ist false, die bestehende Konversation wird weiterverwendet.
     * Wenn removeItem() den letzten Artikel entfernt oder checkout() aufgerufen wird: conversation.end() beendet die Konversation.
     * 
     */
    public void addItem() {
        if (conversation.isTransient()) { // nur ein conversation erlaubt
            conversation.begin(); // conversation kriegt langlebigen Zustand.
        }
        if (newItem != null && !newItem.trim().isEmpty()) {
            items.add(newItem);
            newItem = ""; // Zurücksetzen des Eingabefelds
        }
    }

    public void removeItem(String item) {
        items.remove(item);
        if (items.isEmpty()) {
            conversation.end();
        }
    }

    public List<String> getItems() {
        return items;
    }

    public String getNewItem() {
        return newItem;
    }

    public void setNewItem(String newItem) {
        this.newItem = newItem;
    }

    public void checkout() {
        // Verarbeitung des Einkaufs
        conversation.end();
    }
    
    
}