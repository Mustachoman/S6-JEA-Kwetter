package rest;

import filter.JWTTokenNeeded;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
@Path("/echo")
public class EchoEndpoint {

    @GET
    public Response echo(@QueryParam("message") String message) {
        return Response.ok(message == null ? "no message" : message).build();
    }

    @JWTTokenNeeded
    @GET
    @Path("jwt")
    public Response echoWithJWTToken(@QueryParam("message") String message) {
        return Response.ok(message == null ? "no message" : message).build();
    }
}
