package home.ahmad.rest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import home.ahmad.model.Recommendation;
import home.ahmad.rest.dto.RecommendationDTO;
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
@Path("/recommendations")
public class RecommendationEndpoint {
  
	@PersistenceContext(unitName = "AhmadPU")
	   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(RecommendationDTO dto) {
       try {
           Recommendation entity = dto.fromDTO(null, em);
           em.persist(entity);
           URI createdUri = UriBuilder.fromResource(RecommendationEndpoint.class)
                                      .path(String.valueOf(entity.getId()))
                                      .build();
           return Response.created(createdUri).build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error creating recommendation: " + e.getMessage())
                          .build();
       }
   }


   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id) {
       try {
           Recommendation entity = em.find(Recommendation.class, id);
           if (entity == null) {
               return Response.status(Response.Status.NOT_FOUND)
                              .entity("Recommendation not found")
                              .build();
           }
           em.remove(entity);
           return Response.noContent().build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error deleting recommendation: " + e.getMessage())
                          .build();
       }
   }


   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/json")
   public Response findById(@PathParam("id") Long id) {
       try {
           Recommendation entity = em.find(Recommendation.class, id);
           if (entity == null) {
               return Response.status(Response.Status.NOT_FOUND)
                              .entity("Recommendation not found")
                              .build();
           }
           RecommendationDTO dto = new RecommendationDTO(entity);
           return Response.ok(dto).build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error retrieving recommendation: " + e.getMessage())
                          .build();
       }
   }


   @GET
   @Produces("application/json")
   public Response listAll() {
       try {
           List<Recommendation> searchResults = em.createQuery(
               "SELECT DISTINCT r FROM Recommendation r LEFT JOIN FETCH r.guest LEFT JOIN FETCH r.restaurant ORDER BY r.id", 
               Recommendation.class
           ).getResultList();

           List<RecommendationDTO> results = searchResults.stream()
                                                          .map(RecommendationDTO::new)
                                                          .collect(Collectors.toList());

           return Response.ok(results).build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error listing recommendations: " + e.getMessage())
                          .build();
       }
   }


   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Long id, RecommendationDTO dto) {
       try {
           Recommendation entity = em.find(Recommendation.class, id);
           if (entity == null) {
               return Response.status(Response.Status.NOT_FOUND)
                              .entity("Recommendation not found")
                              .build();
           }

           entity = dto.fromDTO(entity, em);
           em.merge(entity);
           return Response.noContent().build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error updating recommendation: " + e.getMessage())
                          .build();
       }
   }

}