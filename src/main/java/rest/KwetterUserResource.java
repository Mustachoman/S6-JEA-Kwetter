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
     *
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

            usersDTO.add(this.setDTOUris(uriInfo, userDTO));
        });
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

    /**
     * POST request to create new KwetterUser
     *
     * @param uriInfo
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
     *
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

        KwetterUserDTO returnUserDTO = new KwetterUserDTOMapper().mapKwetterUser(updateUser);
        return this.setDTOUris(uriInfo, returnUserDTO);
    }

    /**
     * GET request of a single KwetterUser by Id
     *
     * @param userId
     * @return KwetterUserDTO
     */
    @GET
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public KwetterUserDTO getUser(@Context UriInfo uriInfo, @PathParam("userId") Long userId) {
        KwetterUser foundUser = kwetterUserService.findUser(userId);

        KwetterUserDTO returnUserDTO = new KwetterUserDTOMapper().mapKwetterUser(foundUser);
        return this.setDTOUris(uriInfo, returnUserDTO);
    }

    /**
     * GET request to get following of a KwetterUser found by Id
     *
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

            foundUserFollowingDTO.add(this.setDTOUris(uriInfo, userDTO));
        });
        return foundUserFollowingDTO;
    }

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
    
    private KwetterUserDTO setDTOUris(UriInfo uriInfo, KwetterUserDTO userDTO){
        String userId = userDTO.getId().toString();
        userDTO.setUri(getUserUri(uriInfo,userId));
        userDTO.setFollowersUri(getUserFollowersUri(uriInfo,userId));
        userDTO.setFollowingUri(getUserFollowingUri(uriInfo,userId));
        return userDTO;
    }
    
    private String getUserUri(UriInfo uriInfo, String userId){
        return uriInfo.getBaseUriBuilder()
                .path(KwetterUserResource.class)
                .path(userId).toString();
    }
    
    private String getUserFollowersUri(UriInfo uriInfo, String userId){
        return uriInfo.getBaseUriBuilder()
                .path(KwetterUserResource.class)
                .path(userId + "/followers").toString();
    }
    
    private String getUserFollowingUri(UriInfo uriInfo, String userId){
        return uriInfo.getBaseUriBuilder()
                .path(KwetterUserResource.class)
                .path(userId + "/following").toString();
    }
}
