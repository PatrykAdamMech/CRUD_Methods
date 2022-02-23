package com.patryk.firstworkingproject.controller;

import com.patryk.firstworkingproject.entity.EmployeeEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("employee")
public class EmployeeController {

    static EntityManagerFactory entityManagerFactory;
    static EntityManager entityManager;
    static {
        entityManagerFactory =
                Persistence.createEntityManagerFactory("default");

    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public EmployeeEntity setEmployee(EmployeeEntity employee) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
        entityManager.close();

        return employee;
    }

    @DELETE
    @Path("/{ID}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deleteEmployeeById(@PathParam("ID") String id) {
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
            Object employee = entityManager.find(EmployeeEntity.class, ID);
            if(employee == null) {
                return Response.status(404,"NotFound: Employee with id = "+ID+" not found in the database!").build();
            }
            entityManager.getTransaction().begin();
            entityManager.remove(employee);
            entityManager.getTransaction().commit();
            entityManager.close();
            return Response.ok(employee).build();
        }
        catch (Exception e) {
            return Response.status(500, "InternalServerError").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateEmployee(EmployeeEntity employee, @PathParam("id") String id) {
        int ID = 0;
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
            EmployeeEntity employeeToReplace = entityManager.find(EmployeeEntity.class, ID);
            if (employeeToReplace == null || employee == null) {
                return Response.status(404, "NotFound: Employee with id = " + ID + " not found in the database!").build();
            }
            entityManager.getTransaction().begin();
            Query query = entityManager.createNativeQuery("UPDATE employee SET firstname=\'" + employee.getFirstName() + "\', lastname=\'" + employee.getLastName() +
                    "\' WHERE id=" + ID);
            query.executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.close();
            return Response.ok(employee).build();
        } catch (Exception e) {
            return Response.status(500,"InternalServerError").build();
        }



    }

    @GET
    @Path("/get/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getEmployeeById(@PathParam("id") String id) {
        int ID = -0;
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
            Object employee = entityManager.find(EmployeeEntity.class, ID);
            if(employee == null) {
                return Response.status(404,"NotFound: Employee with id = "+ID+" not found in the database!").build();
            }
            entityManager.close();
            return Response.ok(employee).build();
        }
        catch (Exception e) {
            return Response.status(500, "InternalServerError").build();
        }
    }

    @GET
    @Path("/range/{fromid}/{toid}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getEmployeesInRange(@PathParam("fromid") int fromId, @PathParam("toid") int toId) {
        try {
            if(fromId < 1 || toId < 1) {
                return Response.status(400,"BadRequest: Id should be positive integer greater than 0").build();
            }
            if(fromId > toId) {
                return Response.status(400,"Range is defined incorrectly").build();
            }
        }
        catch(Exception e) {
            return Response.status(400,"BadRequest: Id should be positive integer greater than 0").build();
        }
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
             List checks = entityManager.createNativeQuery("SELECT * FROM employee e WHERE e.id >= "+fromId+
                    " AND e.id <= " + toId).getResultList();
            entityManager.close();
            return Response.ok(checks).build();
        } catch (Exception e) {
            return Response.status(500,"InternalServerError ").entity(e.getMessage()).build();

        }
    }

    @GET
    @Path("/askme/{someSenselessQuestion}")
    public Response redirectToGoogle(@PathParam("someSenselessQuestion") String query){
        try{
            URI redirectLink = new URI("https://letmegooglethat.com/?q=" + query);
            return Response.temporaryRedirect(redirectLink).build();
        } catch (Exception e){
            return Response.status(500, "InternalServerError").entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/hrdepartment/{id}")
    public Response redirectToEmployee(@PathParam("id") String query){
        try{
            URI redirectLink = new URI("/employee/get/" + query);
            return Response.temporaryRedirect(redirectLink).build();
        } catch (Exception e){
            return Response.status(500, "InternalServerError").entity(e.getMessage()).build();
        }
    }

    //
    @GET
    @Path("/getimage")
    @Produces("image/png")
    public Response getImage() {
        try {
            return Response.ok(getClass().getClassLoader().getResourceAsStream("rick-astley.png")).build();
        } catch (Exception e) {
            return Response.status(404,"Image not found!").build();
        }
    }
}
