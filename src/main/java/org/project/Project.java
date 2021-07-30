package org.project;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.model.Stuff;
import org.repository.ProjectRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

@Path("/project")
@SpringBootApplication
public class Project {

//    @GET
//    @PUT
//    @POST
//    @Produces(MediaType.TEXT_PLAIN)
//    public String hello() {
//        return "Hello, Good morning?? ";
//    }
//	
//	public static void main(String[] args) {
//		SpringApplication.run(GreetingResource.class, args);
//	}
	
	
    ProjectRepository proRepo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        return Response
                .ok(proRepo.findAll())
                .build();
    }

	@POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Stuff stuff) {
        try {
            Stuff id = proRepo.save(stuff);
            return Response // 200
                    .ok(
                            new HashMap() {{
                                put("id", id);
                            }})
                    .build();
        } catch (Exception ex) {
//            return Response // error 400
//                    .status(400).entity(
//                            new HashMap() {{
//                                put("error", "failed to insert");
//                            }})
//                    .build();
        }
		return null;
}
}