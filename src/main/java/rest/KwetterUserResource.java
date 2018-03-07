/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import domain.KwetterUser;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import service.KwetterUserService;

/**
 *
 * @author Marijn
 */
@Path("/users")
@Stateless
public class KwetterUserResource {
    
    @Inject
    KwetterUserService kwetterUserService;

    @GET
    public List<KwetterUser> allUsers() {
        List<KwetterUser> users = kwetterUserService.allUsers();
        return users;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public KwetterUser getUser(@PathParam("id") Long id) {
        return kwetterUserService.findUser(id);
    }

    @GET
    @Path("jsonObject/{id}")
    public JsonObject getUserJsonObject(@PathParam("id") Long id) {

        KwetterUser foundUser = kwetterUserService.findUser(id);
        UriBuilder builder = UriBuilder.fromResource(KwetterUserResource.class)
                .path(KwetterUserResource.class, "getUserJsonObject");
        Link link = Link.fromUri(builder.build(id)).rel("self").build();

        return Json.createObjectBuilder().
                add("name", foundUser.getName()).
                add("username", foundUser.getUsername()).
                add(link.getRel(), link.getUri().getPath()).
                build();

    }
    
    @PUT
    @Path("follow/{followerId}/{followingId}")
    public KwetterUser followUser(@PathParam("followerId") Long followerId, @PathParam("followingId") Long followingId){
        KwetterUser follower = kwetterUserService.findUser(followerId);
        KwetterUser following = kwetterUserService.findUser(followingId);
        
        
        if (follower != null && following != null) {
            follower.followUser(following);
            kwetterUserService.updateUser(follower);
            return follower;
        }
        else return null;
    }
    
}
