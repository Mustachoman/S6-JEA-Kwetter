/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import domain.KwetterUser;
import dto.KwetterUserDTO;
import dto.KwetterUserDTOMapper;
import filter.JWTTokenNeeded;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    @Path("/")
    public List<KwetterUserDTO> allUsers() {
        List<KwetterUser> users = kwetterUserService.allUsers();
        List<KwetterUserDTO> usersDTO = new ArrayList<>();
        users.forEach(user -> usersDTO.add(new KwetterUserDTOMapper().mapKwetterUser(user)));
        return usersDTO;
    }
    
    @GET
    @JWTTokenNeeded    
    @Path("/secure")
    public List<KwetterUserDTO> getMe() {
        List<KwetterUser> users = kwetterUserService.allUsers();
        List<KwetterUserDTO> usersDTO = new ArrayList<>();
        users.forEach(user -> usersDTO.add(new KwetterUserDTOMapper().mapKwetterUser(user)));
        return usersDTO;
    }
    

    @GET
    @Path("{id}")
    public KwetterUserDTO getUser(@PathParam("id") Long id) {
        KwetterUser foundUser = kwetterUserService.findUser(id);
        KwetterUserDTO foundUserDTO = new KwetterUserDTOMapper().mapKwetterUser(foundUser);
        return foundUserDTO;
    }

    

    @GET
    @Path("following/{id}")
    public List<KwetterUserDTO> userFollowing(@PathParam("id") Long id) {
        KwetterUser foundUser = kwetterUserService.findUser(id);
        List<KwetterUser> foundUserFollowing = foundUser.getFollowing();
        List<KwetterUserDTO> foundUserFollowingDTO = new ArrayList<>();
        foundUserFollowing.forEach(user -> foundUserFollowingDTO.add(new KwetterUserDTOMapper().mapKwetterUser(user)));
        return foundUserFollowingDTO;
    }
    @GET
    @Path("followers/{id}")
    public List<KwetterUserDTO> getTweetsFromFollowers(@PathParam("id") Long id) {
        KwetterUser foundUser = kwetterUserService.findUser(id);
        List<KwetterUser> foundUserFollowers = foundUser.getFollowers();
        List<KwetterUserDTO> foundUserFollowersDTO = new ArrayList<>();
        foundUserFollowers.forEach(user -> foundUserFollowersDTO.add(new KwetterUserDTOMapper().mapKwetterUser(user)));
        return foundUserFollowersDTO;
    }

    @PUT
    @Path("follow/{followerId}/{followingId}")
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
    @Path("unfollow/{followerId}/{followingId}")
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

    @POST
    @Path("new")
    public KwetterUserDTO newUser(KwetterUserDTO newUserDTO) {
        KwetterUser newUser = new KwetterUserDTOMapper().mapKwetterUserDTO(newUserDTO);
        
        newUser = kwetterUserService.newUser(newUser);

        return new KwetterUserDTOMapper().mapKwetterUser(newUser);
    }
    
    @PUT
    @Path("update")
    public KwetterUserDTO updateUser(KwetterUserDTO updateUserDTO) {
        KwetterUser updateUser = kwetterUserService.findUser(updateUserDTO.getId());
        
        updateUser.setName(updateUserDTO.getName());
        updateUser.setUsername(updateUserDTO.getUsername());
        updateUser.setPhoto(updateUserDTO.getPhoto());
        updateUser.setBio(updateUserDTO.getBio());
        updateUser.setLocation(updateUserDTO.getLocation());
        updateUser.setWebsite(updateUserDTO.getWebsite());
        
        updateUser = kwetterUserService.updateUser(updateUser);

        return new KwetterUserDTOMapper().mapKwetterUser(updateUser);
    }

}
