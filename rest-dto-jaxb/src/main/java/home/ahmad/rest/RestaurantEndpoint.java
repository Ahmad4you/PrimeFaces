package home.ahmad.rest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import home.ahmad.model.Restaurant;
import home.ahmad.rest.dto.RestaurantDTO;
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
@Path("/restaurants")
public class RestaurantEndpoint {
   
	@PersistenceContext(unitName = "AhmadPU")
	  private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(RestaurantDTO dto) {
       try {
           Restaurant entity = dto.fromDTO(null, em);
           em.persist(entity);
           URI createdUri = UriBuilder.fromResource(RestaurantEndpoint.class)
                                      .path(String.valueOf(entity.getId()))
                                      .build();
           return Response.created(createdUri).build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error creating restaurant: " + e.getMessage())
                          .build();
       }
   }


   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id) {
       try {
           Restaurant entity = em.find(Restaurant.class, id);
           if (entity == null) {
               return Response.status(Response.Status.NOT_FOUND)
                              .entity("Restaurant not found")
                              .build();
           }
           em.remove(entity);
           return Response.noContent().build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error deleting restaurant: " + e.getMessage())
                          .build();
       }
   }


   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/json")
   public Response findById(@PathParam("id") Long id) {
       try {
           Restaurant entity = em.find(Restaurant.class, id);
           if (entity == null) {
               return Response.status(Response.Status.NOT_FOUND)
                              .entity("Restaurant not found")
                              .build();
           }
           RestaurantDTO dto = new RestaurantDTO(entity);
           return Response.ok(dto).build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error retrieving restaurant: " + e.getMessage())
                          .build();
       }
   }


   @GET
   @Produces("application/json")
   public Response listAll() {
       try {
           List<Restaurant> searchResults = em.createQuery(
               "SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.category LEFT JOIN FETCH r.recommendation LEFT JOIN FETCH r.phoneContact LEFT JOIN FETCH r.timesOfService ORDER BY r.id", 
               Restaurant.class
           ).getResultList();

           List<RestaurantDTO> results = searchResults.stream()
                                                      .map(RestaurantDTO::new)
                                                      .collect(Collectors.toList());

           return Response.ok(results).build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error listing restaurants: " + e.getMessage())
                          .build();
       }
   }


   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Long id, RestaurantDTO dto) {
       try {
           Restaurant entity = em.find(Restaurant.class, id);
           if (entity == null) {
               return Response.status(Response.Status.NOT_FOUND)
                              .entity("Restaurant not found")
                              .build();
           }

           entity = dto.fromDTO(entity, em);
           em.merge(entity);
           return Response.noContent().build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error updating restaurant: " + e.getMessage())
                          .build();
       }
   }

}