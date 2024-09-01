package home.ahmad.rest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import home.ahmad.model.TimesOfService;
import home.ahmad.rest.dto.TimesOfServiceDTO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

/**
 * 
 * @author Ahmad Alrefai
 */


@Stateless
@Path("/timesofservices")
public class TimesOfServiceEndpoint {
   
	@PersistenceContext(unitName = "AhmadPU")
	 private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(TimesOfServiceDTO dto) {
       try {
           TimesOfService entity = dto.fromDTO(null, em);
           em.persist(entity);
           URI createdUri = UriBuilder.fromResource(TimesOfServiceEndpoint.class)
                                      .path(String.valueOf(entity.getId()))
                                      .build();
           return Response.created(createdUri).build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error creating TimesOfService: " + e.getMessage())
                          .build();
       }
   }


   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id) {
       try {
           TimesOfService entity = em.find(TimesOfService.class, id);
           if (entity == null) {
               return Response.status(Response.Status.NOT_FOUND)
                              .entity("TimesOfService not found")
                              .build();
           }
           em.remove(entity);
           return Response.noContent().build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error deleting TimesOfService: " + e.getMessage())
                          .build();
       }
   }


   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/json")
   public Response findById(@PathParam("id") Long id) {
       try {
           TimesOfService entity = em.find(TimesOfService.class, id);
           if (entity == null) {
               return Response.status(Response.Status.NOT_FOUND)
                              .entity("TimesOfService not found")
                              .build();
           }
           TimesOfServiceDTO dto = new TimesOfServiceDTO(entity);
           return Response.ok(dto).build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error retrieving TimesOfService: " + e.getMessage())
                          .build();
       }
   }


   @GET
   @Produces("application/json")
   public Response listAll() {
       try {
           List<TimesOfService> searchResults = em.createQuery(
               "SELECT DISTINCT t FROM TimesOfService t LEFT JOIN FETCH t.restaurant ORDER BY t.id", 
               TimesOfService.class
           ).getResultList();

           List<TimesOfServiceDTO> results = searchResults.stream()
                                                          .map(TimesOfServiceDTO::new)
                                                          .collect(Collectors.toList());

           return Response.ok(results).build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error listing TimesOfServices: " + e.getMessage())
                          .build();
       }
   }


   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Long id, TimesOfServiceDTO dto) {
       try {
           TimesOfService entity = em.find(TimesOfService.class, id);
           if (entity == null) {
               return Response.status(Response.Status.NOT_FOUND)
                              .entity("TimesOfService not found")
                              .build();
           }

           entity = dto.fromDTO(entity, em);
           em.merge(entity);
           return Response.noContent().build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error updating TimesOfService: " + e.getMessage())
                          .build();
       }
   }

}