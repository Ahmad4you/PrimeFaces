package home.ahmad.demo;

import java.util.logging.Logger;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;


@Path("hello")
public class HelloWorldResource {
	private static final Logger log = Logger.getLogger(HelloWorldResource.class.getName());
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Hello hello(@QueryParam("name") String name) {
		if ((name == null) || name.trim().isEmpty())  {
			name = "world";
		}
		log.info("HelloWorldResource: " + name);
		
		return new Hello(name);
	}
	
	public void anotherMethod() {
	    log.finest("Finest level of detail");
	    log.finer("Finer level of detail");
	    log.fine("Fine level of detail");
	    log.config("Configuration message");
	    log.info("Informational message");
	    log.warning("Warning message");
	    log.severe("Severe error message");
	}

}