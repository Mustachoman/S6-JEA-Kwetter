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
import javax.ws.rs.POST;
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
    
    /**
     * GET request to get all KwetterUsers
     * @return List of KwetterUserDTO
     */
    @GET
    @Path("")
    public List<KwetterUserDTO> allUsers() {
        List<KwetterUser> users = kwetterUserService.allUsers();
        List<KwetterUserDTO> usersDTO = new ArrayList<>();
        users.forEach(user -> usersDTO.add(new KwetterUserDTOMapper().mapKwetterUser(user)));
        return usersDTO;
    }
    
    /**
     * POST request to create new KwetterUser
     * @param newUserDTO
     * @return KwetterUserDTO
     */
    @POST
    @Path("")
    public KwetterUserDTO newUser(KwetterUserDTO newUserDTO) {
        KwetterUser newUser = new KwetterUserDTOMapper().mapKwetterUserDTO(newUserDTO);
        
        newUser = kwetterUserService.newUser(newUser);

        return new KwetterUserDTOMapper().mapKwetterUser(newUser);
    }
    
    /**
     * PUT request to update existing KwetterUser
     * @param updateUserDTO
     * @return KwetterUserDTO (updated)
     */
    @PUT
    @Path("")
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

    /**
     * GET request of a single KwetterUser by Id
     * @param userId
     * @return KwetterUserDTO
     */
    @GET
    @Path("{userId}")
    public KwetterUserDTO getUser(@PathParam("userId") Long userId) {
        KwetterUser foundUser = kwetterUserService.findUser(userId);
        KwetterUserDTO foundUserDTO = new KwetterUserDTOMapper().mapKwetterUser(foundUser);
        return foundUserDTO;
    }

    /**
     * GET request to get following of a KwetterUser found by Id
     * @param userId
     * @return List of KwetterUserDTO
     */
    @GET
    @Path("{userId}/following")
    public List<KwetterUserDTO> userFollowing(@PathParam("userId") Long userId) {
        KwetterUser foundUser = kwetterUserService.findUser(userId);
        List<KwetterUser> foundUserFollowing = foundUser.getFollowing();
        List<KwetterUserDTO> foundUserFollowingDTO = new ArrayList<>();
        foundUserFollowing.forEach(user -> foundUserFollowingDTO.add(new KwetterUserDTOMapper().mapKwetterUser(user)));
        return foundUserFollowingDTO;
    }

    /**
     * GET request to get followers of a KwetterUser found by Id
     * @param userId
     * @return List of KwetterUserDTO
     */
    @GET
    @Path("{userId}/followers")
    public List<KwetterUserDTO> userFollowers(@PathParam("userId") Long userId) {
        KwetterUser foundUser = kwetterUserService.findUser(userId);
        List<KwetterUser> foundUserFollowers = foundUser.getFollowers();
        List<KwetterUserDTO> foundUserFollowersDTO = new ArrayList<>();
        foundUserFollowers.forEach(user -> foundUserFollowersDTO.add(new KwetterUserDTOMapper().mapKwetterUser(user)));
        return foundUserFollowersDTO;
    }

    /**
     * PUT request to update following of a KwetterUser found by Id
     * following/un-following another KwetterUser
     * @param userId
     * @param followingUserDTO
     * @return 
     */
    @PUT
    @Path("{userId}/following/{followId}")
    public KwetterUserDTO updateFollowing(@PathParam("userId") Long userId, @PathParam("followId") Long followId) {
        KwetterUser user = kwetterUserService.findUser(userId);
        KwetterUser follow = kwetterUserService.findUser(followId);

        if (user != null && follow != null) {
            if (!user.getFollowing().contains(follow)){
                user.followUser(follow);
            }else user.unfollowUser(follow);
            kwetterUserService.updateUser(user);
            KwetterUserDTO followerUserDTO = new KwetterUserDTOMapper().mapKwetterUser(user);
            return followerUserDTO;
        } else {
            return null;
        }
    }
}
