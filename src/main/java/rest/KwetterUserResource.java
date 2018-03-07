/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import domain.KwetterUser;
import dto.KwetterUserDTO;
import dto.KwetterUserDTOMapper;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    @Path("all")
    public List<KwetterUserDTO> allUsers() {
        List<KwetterUser> users = kwetterUserService.allUsers();
        List<KwetterUserDTO> usersDTO = new ArrayList<>();
        users.forEach(user -> usersDTO.add(new KwetterUserDTOMapper().mapKwetterUser(user)));
        return usersDTO;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public KwetterUserDTO getUser(@PathParam("id") Long id) {
        KwetterUser foundUser = kwetterUserService.findUser(id);
        KwetterUserDTO foundUserDTO = new KwetterUserDTOMapper().mapKwetterUser(foundUser);
        return foundUserDTO;
    }

    @GET
    @Path("{id}/following")
    public List<KwetterUserDTO> userFollowing(@PathParam("id") Long id) {
        KwetterUser foundUser = kwetterUserService.findUser(id);
        List<KwetterUser> foundUserFollowing = foundUser.getFollowing();
        List<KwetterUserDTO> foundUserFollowingDTO = new ArrayList<>();
        foundUserFollowing.forEach(user -> foundUserFollowingDTO.add(new KwetterUserDTOMapper().mapKwetterUser(user)));
        return foundUserFollowingDTO;
    }
    
    @GET
    @Path("{id}/followers")
    public List<KwetterUserDTO> userFollowers(@PathParam("id") Long id) {
        KwetterUser foundUser = kwetterUserService.findUser(id);
        List<KwetterUser> foundUserFollowers = foundUser.getFollowers();
        List<KwetterUserDTO> foundUserFollowersDTO = new ArrayList<>();
        foundUserFollowers.forEach(user -> foundUserFollowersDTO.add(new KwetterUserDTOMapper().mapKwetterUser(user)));
        return foundUserFollowersDTO;
    }

    @PUT
    @Path("{followerId}/follow/{followingId}")
    public KwetterUserDTO followUser(@PathParam("followerId") Long followerId, @PathParam("followingId") Long followingId) {
        KwetterUser follower = kwetterUserService.findUser(followerId);
        KwetterUser following = kwetterUserService.findUser(followingId);

        if (follower != null && following != null) {
            follower.followUser(following);
            kwetterUserService.updateUser(follower);
            KwetterUserDTO followerUserDTO = new KwetterUserDTOMapper().mapKwetterUser(follower);
            return followerUserDTO;
        } else {
            return null;
        }
    }
    
    @PUT
    @Path("{followerId}/unfollow/{followingId}")
    public KwetterUserDTO unfollowUser(@PathParam("followerId") Long followerId, @PathParam("followingId") Long followingId) {
        KwetterUser follower = kwetterUserService.findUser(followerId);
        KwetterUser following = kwetterUserService.findUser(followingId);

        if (follower != null && following != null) {
            follower.unfollowUser(following);
            kwetterUserService.updateUser(follower);
            KwetterUserDTO followerUserDTO = new KwetterUserDTOMapper().mapKwetterUser(follower);
            return followerUserDTO;
        } else {
            return null;
        }
    }

}
