package home.ahmad.rest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import home.ahmad.model.PhoneContact;
import home.ahmad.rest.dto.PhoneContactDTO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

/**
 * 
 * @author Ahmad Alrefai
 */


@Stateless
@Path("/phonecontacts")
public class PhoneContactEndpoint {
   
	@PersistenceContext(unitName = "AhmadPU")
	   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(PhoneContactDTO dto) {
       try {
           PhoneContact entity = dto.fromDTO(null, em);
           em.persist(entity);
           URI createdUri = UriBuilder.fromResource(PhoneContactEndpoint.class)
                                      .path(String.valueOf(entity.getId()))
                                      .build();
           return Response.created(createdUri).build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error creating phone contact: " + e.getMessage())
                          .build();
       }
   }


   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id) {
       try {
           PhoneContact entity = em.find(PhoneContact.class, id);
           if (entity == null) {
               return Response.status(Response.Status.NOT_FOUND)
                              .entity("Phone contact not found")
                              .build();
           }
           em.remove(entity);
           return Response.noContent().build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error deleting phone contact: " + e.getMessage())
                          .build();
       }
   }


   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/json")
   public Response findById(@PathParam("id") Long id) {
       try {
           TypedQuery<PhoneContact> findByIdQuery = em.createQuery(
               "SELECT DISTINCT p FROM PhoneContact p LEFT JOIN FETCH p.restaurant WHERE p.id = :entityId ORDER BY p.id", 
               PhoneContact.class
           );
           findByIdQuery.setParameter("entityId", id);
           PhoneContact entity = findByIdQuery.getSingleResult();

           PhoneContactDTO dto = new PhoneContactDTO(entity);
           return Response.ok(dto).build();
       } catch (NoResultException e) {
           return Response.status(Response.Status.NOT_FOUND)
                          .entity("Phone contact not found")
                          .build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error retrieving phone contact: " + e.getMessage())
                          .build();
       }
   }


   @GET
   @Produces("application/json")
   public Response listAll() {
       try {
           List<PhoneContact> searchResults = em.createQuery(
               "SELECT DISTINCT p FROM PhoneContact p LEFT JOIN FETCH p.restaurant ORDER BY p.id", 
               PhoneContact.class
           ).getResultList();

           List<PhoneContactDTO> results = searchResults.stream()
                                                        .map(PhoneContactDTO::new)
                                                        .collect(Collectors.toList());

           return Response.ok(results).build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error listing phone contacts: " + e.getMessage())
                          .build();
       }
   }


   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Long id, PhoneContactDTO dto) {
       try {
           PhoneContact entity = em.find(PhoneContact.class, id);
           if (entity == null) {
               return Response.status(Response.Status.NOT_FOUND)
                              .entity("Phone contact not found")
                              .build();
           }

           entity = dto.fromDTO(entity, em);
           em.merge(entity);
           return Response.noContent().build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error updating phone contact: " + e.getMessage())
                          .build();
       }
   }

}