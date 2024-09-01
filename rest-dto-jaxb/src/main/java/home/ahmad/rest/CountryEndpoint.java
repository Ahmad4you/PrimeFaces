package home.ahmad.rest;

import java.util.ArrayList;
import java.util.List;

import home.ahmad.model.Country;
import home.ahmad.rest.dto.CountryDTO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriBuilder;

/**
 * 
 * @author Ahmad Alrefai
 */


@Stateless
@Path("/countrys")
public class CountryEndpoint {
  
	@PersistenceContext(unitName = "AhmadPU")
	private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(CountryDTO dto) {
       try {
           Country entity = dto.fromDTO(null, em);
           em.persist(entity);
           em.flush(); // Stellt sicher, dass die ID generiert wurde
           return Response.created(UriBuilder.fromResource(CountryEndpoint.class)
               .path(String.valueOf(entity.getId())).build()).build();
       } catch (Exception e) {
           return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error creating country").build();
       }
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id) {
       try {
           Country entity = em.find(Country.class, id);
           if (entity == null) {
               return Response.status(Status.NOT_FOUND).entity("Country not found").build();
           }
           em.remove(entity);
           return Response.noContent().build();
       } catch (Exception e) {
           return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error deleting country").build();
       }
   }


   
   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/json")
   public Response findById(@PathParam("id") Long id) {
       try {
           Country entity = em.find(Country.class, id);
           if (entity == null) {
               return Response.status(Status.NOT_FOUND).build();
           }
           CountryDTO dto = new CountryDTO(entity);
           return Response.ok(dto).build();
       } catch (Exception e) {
           return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error retrieving country").build();
       }
   }


   @GET
   @Produces("application/json")
   public Response listAll() {
       try {
           List<Country> searchResults = em.createQuery("SELECT DISTINCT c FROM Country c ORDER BY c.id", Country.class)
                   .getResultList();
           List<CountryDTO> results = new ArrayList<>();
           for (Country searchResult : searchResults) {
               CountryDTO dto = new CountryDTO(searchResult);
               results.add(dto);
           }
           return Response.ok(results).build();
       } catch (Exception e) {
           return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error retrieving countries").build();
       }
   }


   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Long id, CountryDTO dto) {
       try {
           Country entity = em.find(Country.class, id);
           if (entity == null) {
               return Response.status(Status.NOT_FOUND).entity("Country not found").build();
           }

           entity = dto.fromDTO(entity, em);
           em.merge(entity);

           return Response.noContent().build();
       } catch (Exception e) {
           return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error updating country").build();
       }
   }

}