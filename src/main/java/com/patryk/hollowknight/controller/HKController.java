package com.patryk.hollowknight.controller;

import com.patryk.firstworkingproject.entity.EmployeeEntity;
import com.patryk.hollowknight.entity.CharmsEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/hk")
public class HKController {
    static EntityManagerFactory entityManagerFactory;
    static EntityManager entityManager;
    static {
        entityManagerFactory =
                Persistence.createEntityManagerFactory("default");

    }

    //Ask how to pass date
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public CharmsEntity addNewCharm(CharmsEntity charm) {

        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(charm);
        entityManager.getTransaction().commit();
        entityManager.close();
        return charm;

    }
    @GET
    @Path("/charm/name/{name}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getCharmByName(@PathParam("name") String name) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM charms WHERE name='" + name + "'");
            Object result = query.getSingleResult();
            entityManager.getTransaction().commit();
            entityManager.close();
            return Response.ok(result).build();
        } catch (Exception e) {
            return Response.status(500,"InternalServerError").build();
        }
    }

    @GET
    @Path("/charm/{id}")
    @Produces("application/json")
    public Response getCharmById(@PathParam("id") String id) {
        int ID = -1;
        try {
            ID = Integer.parseInt(id);
            if(ID < 1) {
                return Response.status(400,"BadRequest: Id should be positive integer").build();
            }
        }
        catch(Exception e) {
            return Response.status(400,"BadRequest: Id should be positive integer").build();
        }
        try {
            entityManager = entityManagerFactory.createEntityManager();
            Object charm = entityManager.find(CharmsEntity.class, ID);
            if(charm == null) {
                return Response.status(404,"NotFound: Employee with id = "+ID+" not found in the database!").build();
            }
            entityManager.close();
            return Response.ok(charm).build();
        }
        catch (Exception e) {
            return Response.status(500, "InternalServerError").build();
        }
    }
}
