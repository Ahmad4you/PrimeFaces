package home.ahmad.rest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import home.ahmad.model.Category;
import home.ahmad.rest.dto.CategoryDTO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

/**
 * http://localhost:8080/jakartaee/api/categorys
 * 
 * @author Ahmad Alrefai
 */

@Stateless
@Path("/categorys")
public class CategoryEndpoint {
   
   @PersistenceContext(unitName = "AhmadPU")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(CategoryDTO dto) {
       try {
           Category entity = dto.fromDTO(null, em);
           em.persist(entity);
           em.flush();
           URI createdUri = UriBuilder.fromResource(CategoryEndpoint.class)
                                      .path(String.valueOf(entity.getId()))
                                      .build();
           return Response.created(createdUri).build();
       } catch (PersistenceException pe) {
           // Fehler im Zusammenhang mit der Datenbank persistenz
           return Response.status(Response.Status.BAD_REQUEST)
                          .entity("Datenbankfehler: " + pe.getMessage())
                          .build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Allgemeiner Fehler: " + e.getMessage())
                          .build();
       }
   }


   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id) {
       try {
           Category entity = em.find(Category.class, id);
           if (entity == null) {
               return Response.status(Response.Status.NOT_FOUND)
                              .entity("Category not found")
                              .build();
           }
           em.remove(entity);
           return Response.noContent().build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error deleting category: " + e.getMessage())
                          .build();
       }
   }
   /**
    * @Path("/{id:[0-9][0-9]*}") was bedeutet, dass die URL eine id als Teil des Pfades erwartet, nicht als Query-Parameter. 
    * http://localhost:8080/jakartaee/api/categorys/1
    * 
    * @param id
    * @return
    */

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/json")
   public Response findById(@PathParam("id") Long id) {
       try {
           TypedQuery<Category> findByIdQuery = em.createQuery(
               "SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.restaurant WHERE c.id = :entityId ORDER BY c.id", 
               Category.class
           );
           findByIdQuery.setParameter("entityId", id);
           Category entity = findByIdQuery.getSingleResult();
           if (entity == null) {
               return Response.status(Response.Status.NOT_FOUND)
                              .entity("Category not found")
                              .build();
           }
           CategoryDTO dto = new CategoryDTO(entity);
           return Response.ok(dto).build();
       } catch (NoResultException e) {
           return Response.status(Response.Status.NOT_FOUND)
                          .entity("Category not found")
                          .build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error retrieving category: " + e.getMessage())
                          .build();
       }
   }


   @GET
   @Produces("application/json")
   public Response listAll() {
       try {
           List<Category> searchResults = em.createQuery(
               "SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.restaurant ORDER BY c.id", 
               Category.class
           ).getResultList();

           List<CategoryDTO> results = searchResults.stream()
                                                    .map(CategoryDTO::new)
                                                    .collect(Collectors.toList());
           return Response.ok(results).build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error listing categories: " + e.getMessage())
                          .build();
       }
   }


   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Long id, CategoryDTO dto) {
       try {
           Category entity = em.find(Category.class, id);
           if (entity == null) {
               return Response.status(Response.Status.NOT_FOUND)
                              .entity("Category not found")
                              .build();
           }

           entity = dto.fromDTO(entity, em);
           em.merge(entity);
           return Response.noContent().build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Error updating category: " + e.getMessage())
                          .build();
       }
   }

}