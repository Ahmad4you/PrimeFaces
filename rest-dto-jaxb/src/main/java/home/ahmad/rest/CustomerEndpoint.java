package home.ahmad.rest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import home.ahmad.model.Customer;
import home.ahmad.rest.dto.CustomerDTO;
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
@Path("/customers")
public class CustomerEndpoint {
   
	@PersistenceContext(unitName = "AhmadPU")
	   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(CustomerDTO dto) {
       try {
           Customer entity = dto.fromDTO(null, em);
           em.persist(entity);
           URI createdUri = UriBuilder.fromResource(CustomerEndpoint.class)
                                      .path(String.valueOf(entity.getId()))
                                      .build();
           return Response.created(createdUri).build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error creating customer: " + e.getMessage())
                          .build();
       }
   }


   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id) {
       try {
           Customer entity = em.find(Customer.class, id);
           if (entity == null) {
               return Response.status(Response.Status.NOT_FOUND)
                              .entity("Customer not found")
                              .build();
           }
           em.remove(entity);
           return Response.noContent().build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error deleting customer: " + e.getMessage())
                          .build();
       }
   }


   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/json")
   public Response findById(@PathParam("id") Long id) {
       try {
           TypedQuery<Customer> findByIdQuery = em.createQuery(
               "SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.recommendation WHERE c.id = :entityId ORDER BY c.id", 
               Customer.class
           );
           findByIdQuery.setParameter("entityId", id);
           Customer entity = findByIdQuery.getSingleResult();

           CustomerDTO dto = new CustomerDTO(entity);
           return Response.ok(dto).build();
       } catch (NoResultException e) {
           return Response.status(Response.Status.NOT_FOUND)
                          .entity("Customer not found")
                          .build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error retrieving customer: " + e.getMessage())
                          .build();
       }
   }


   @GET
   @Produces("application/json")
   public Response listAll() {
       try {
           List<Customer> searchResults = em.createQuery(
               "SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.recommendation ORDER BY c.id", 
               Customer.class
           ).getResultList();

           List<CustomerDTO> results = searchResults.stream()
                                                    .map(CustomerDTO::new)
                                                    .collect(Collectors.toList());

           return Response.ok(results).build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error listing customers: " + e.getMessage())
                          .build();
       }
   }


   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Long id, CustomerDTO dto) {
       try {
           Customer entity = em.find(Customer.class, id);
           if (entity == null) {
               return Response.status(Response.Status.NOT_FOUND)
                              .entity("Customer not found")
                              .build();
           }

           entity = dto.fromDTO(entity, em);
           em.merge(entity);
           return Response.noContent().build();
       } catch (NoResultException e) {
           return Response.status(Response.Status.NOT_FOUND)
                          .entity("Customer not found")
                          .build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error updating customer: " + e.getMessage())
                          .build();
       }
   }

}