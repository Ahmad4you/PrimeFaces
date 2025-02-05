package ahmad.exception;

import jakarta.faces.FacesException;
import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerWrapper;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ExceptionQueuedEvent;
import jakarta.faces.event.ExceptionQueuedEventContext;

import java.util.Iterator;

/**
 * Der Fehler jakarta.faces.application.ViewExpiredException tritt auf, wenn eine JSF-Ansicht (ViewScoped) nach Ablauf der HTTP-Session 
 * nicht wiederhergestellt werden kann. Dies geschieht typischerweise, wenn der Benutzer inaktiv war und die Session abgelaufen ist, 
 * oder wenn die Seite in einem ungültigen Zustand neu geladen wird.
 * 
 * @author Ahmad Alrefai
 */

public class CustomExceptionHandler extends ExceptionHandlerWrapper {

    private final ExceptionHandler wrapped;

    public CustomExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {
        Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents().iterator();

        while (events.hasNext()) {
            ExceptionQueuedEvent event = events.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            Throwable throwable = context.getException();

            if (throwable instanceof jakarta.faces.application.ViewExpiredException) { // handle ViewExpiredException -> Session timeout
                try {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    facesContext.getExternalContext().redirect("login.xhtml");
                    facesContext.responseComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    events.remove(); // Entferne das Ereignis aus der Warteschlange
                }
            }
        }

        getWrapped().handle();
    }
}
