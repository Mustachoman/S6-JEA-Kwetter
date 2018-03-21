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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<KwetterUserDTO> allUsers(@Context UriInfo uriInfo) {
        List<KwetterUser> users = kwetterUserService.allUsers();
        List<KwetterUserDTO> usersDTO = new ArrayList<>();
        users.forEach(user -> { 
                KwetterUserDTO userDTO = new KwetterUserDTOMapper().mapKwetterUser(user);
                
                String uri = uriInfo.getBaseUriBuilder()
                    .path(KwetterUserResource.class)
                    .path(user.getId().toString()).toString();
                
                userDTO.setUri(uri);
                usersDTO.add(userDTO);
        });
        return usersDTO;
    }
    
    /**
     * POST request to create new KwetterUser
     * @param newUserDTO
     * @return KwetterUserDTO
     */
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public KwetterUserDTO newUser(@Context UriInfo uriInfo, KwetterUserDTO newUserDTO) {
        KwetterUser newUser = new KwetterUserDTOMapper().mapKwetterUserDTO(newUserDTO);
        
        newUser = kwetterUserService.newUser(newUser);
        
        String uri = uriInfo.getBaseUriBuilder()
                    .path(KwetterUserResource.class)
                    .path(newUser.getId().toString()).toString();
        
        KwetterUserDTO returnUserDTO = new KwetterUserDTOMapper().mapKwetterUser(newUser);
        
        returnUserDTO.setUri(uri);

        return returnUserDTO;
    }
    
    /**
     * PUT request to update existing KwetterUser
     * @param updateUserDTO
     * @return KwetterUserDTO (updated)
     */
    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public KwetterUserDTO updateUser(@Context UriInfo uriInfo, KwetterUserDTO updateUserDTO) {
        KwetterUser updateUser = kwetterUserService.findUser(updateUserDTO.getId());
        
        updateUser.setName(updateUserDTO.getName());
        updateUser.setUsername(updateUserDTO.getUsername());
        updateUser.setPhoto(updateUserDTO.getPhoto());
        updateUser.setBio(updateUserDTO.getBio());
        updateUser.setLocation(updateUserDTO.getLocation());
        updateUser.setWebsite(updateUserDTO.getWebsite());
        
        updateUser = kwetterUserService.updateUser(updateUser);
        
        String uri = uriInfo.getBaseUriBuilder()
                    .path(KwetterUserResource.class)
                    .path(updateUser.getId().toString()).toString();
        
        KwetterUserDTO returnUserDTO = new KwetterUserDTOMapper().mapKwetterUser(updateUser);
        returnUserDTO.setUri(uri);

        return new KwetterUserDTOMapper().mapKwetterUser(updateUser);
    }

    /**
     * GET request of a single KwetterUser by Id
     * @param userId
     * @return KwetterUserDTO
     */
    @GET
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public KwetterUserDTO getUser(@Context UriInfo uriInfo, @PathParam("userId") Long userId) {
        KwetterUser foundUser = kwetterUserService.findUser(userId);
        
        String uri = uriInfo.getBaseUriBuilder()
                    .path(KwetterUserResource.class)
                    .path(foundUser.getId().toString()).toString();
        
        KwetterUserDTO returnUserDTO = new KwetterUserDTOMapper().mapKwetterUser(foundUser);
        returnUserDTO.setUri(uri);
        return returnUserDTO;
    }

    /**
     * GET request to get following of a KwetterUser found by Id
     * @param userId
     * @return List of KwetterUserDTO
     */
    @GET
    @Path("{userId}/following")
    @Produces(MediaType.APPLICATION_JSON)
    public List<KwetterUserDTO> userFollowing(@Context UriInfo uriInfo, @PathParam("userId") Long userId) {
        KwetterUser foundUser = kwetterUserService.findUser(userId);
        List<KwetterUser> foundUserFollowing = foundUser.getFollowing();
        List<KwetterUserDTO> foundUserFollowingDTO = new ArrayList<>();
        foundUserFollowing.forEach(user -> { 
                KwetterUserDTO userDTO = new KwetterUserDTOMapper().mapKwetterUser(user);
                
                String uri = uriInfo.getBaseUriBuilder()
                    .path(KwetterUserResource.class)
                    .path(user.getId().toString()).toString();
                
                userDTO.setUri(uri);
                foundUserFollowingDTO.add(userDTO);
        });
        return foundUserFollowingDTO;
    }

    /**
     * GET request to get followers of a KwetterUser found by Id
     * @param userId
     * @return List of KwetterUserDTO
     */
    @GET
    @Path("{userId}/followers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<KwetterUserDTO> userFollowers(@Context UriInfo uriInfo, @PathParam("userId") Long userId) {
        KwetterUser foundUser = kwetterUserService.findUser(userId);
        List<KwetterUser> foundUserFollowers = foundUser.getFollowers();
        List<KwetterUserDTO> foundUserFollowersDTO = new ArrayList<>();
        foundUserFollowers.forEach(user -> { 
                KwetterUserDTO userDTO = new KwetterUserDTOMapper().mapKwetterUser(user);
                
                String uri = uriInfo.getBaseUriBuilder()
                    .path(KwetterUserResource.class)
                    .path(user.getId().toString()).toString();
                
                userDTO.setUri(uri);
                foundUserFollowersDTO.add(userDTO);
        });
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
    @Produces(MediaType.APPLICATION_JSON)
    public KwetterUserDTO updateFollowing(@Context UriInfo uriInfo, @PathParam("userId") Long userId, @PathParam("followId") Long followId) {
        KwetterUser user = kwetterUserService.findUser(userId);
        KwetterUser follow = kwetterUserService.findUser(followId);

        if (user != null && follow != null) {
            if (!user.getFollowing().contains(follow)){
                user.followUser(follow);
            }else user.unfollowUser(follow);
            
            kwetterUserService.updateUser(user);
            
            String uri = uriInfo.getBaseUriBuilder()
                    .path(KwetterUserResource.class)
                    .path(user.getId().toString()).toString();
            
            KwetterUserDTO followerUserDTO = new KwetterUserDTOMapper().mapKwetterUser(user);
            
            followerUserDTO.setUri(uri);
            
            return followerUserDTO;
        } else {
            return null;
        }
    }
}
