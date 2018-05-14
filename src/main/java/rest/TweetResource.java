/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import domain.KwetterUser;
import domain.Tweet;
import dto.KwetterUserDTO;
import dto.KwetterUserDTOMapper;
import dto.TweetDTO;
import dto.TweetDTOMapper;
import java.util.ArrayList;
import static java.util.Collections.reverseOrder;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.KwetterUserService;
import service.TweetService;




/**
 *
 * @author Roy
 */
@Path("/tweets")
@Stateless
public class TweetResource {

    @Inject
    TweetService tweetService;
    
    @Inject
    KwetterUserService kwetterUserService;

    @GET
    @Path("/")
    public List<TweetDTO> allTweets() {
        List<Tweet> tweets = tweetService.allTweets();
        List<TweetDTO> tweetDTO = new ArrayList<>();
        tweets.forEach(tweet -> tweetDTO.add(new TweetDTOMapper().mapTweets(tweet)));
        return tweetDTO;
        
    }
    
    @GET
    @Path("allTweets/{id}")
    public List<TweetDTO> allTweetsFromUser(@PathParam("id") Long id) {
        List<Tweet> tweets = tweetService.allTweets();
        List<TweetDTO> tweetDTO = new ArrayList<>();
        
        tweets.stream().filter((tweet) -> (Objects.equals(tweet.getOwner().getId(), id))).forEachOrdered((tweet) -> {
            tweetDTO.add(new TweetDTOMapper().mapTweets(tweet));
        });
       
        return tweetDTO;
    }
    @GET
    @Path("allTweetsUsername/{username}")
    public List<TweetDTO> allTweetsFromUsername(@PathParam("username") String username) {
        List<Tweet> tweets = tweetService.allTweets();
        List<TweetDTO> tweetDTO = new ArrayList<>();
        
        tweets.stream().filter((tweet) -> (Objects.equals(tweet.getOwner().getUsername(), username))).forEachOrdered((tweet) -> {
            tweetDTO.add(new TweetDTOMapper().mapTweets(tweet));
        });
       
        return tweetDTO;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TweetDTO getTweet(@PathParam("id") Long id) {
        Tweet foundTweet = tweetService.findTweet(id);
        TweetDTO tweetDTO = new TweetDTOMapper().mapTweets(foundTweet);
        return tweetDTO;
    }
    
    @GET
    @Path("allHearts/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<KwetterUserDTO> getHearts(@PathParam("id") Long id){
        Tweet foundTweet = tweetService.findTweet(id);
        List<KwetterUser> foundHearts = foundTweet.getHearts();
        List<KwetterUserDTO> foundHeartsDTO = new ArrayList<>();
        foundHearts.forEach(user -> foundHeartsDTO.add(new KwetterUserDTOMapper().mapKwetterUser(user)));
        return foundHeartsDTO;
    }
    
    @POST
    @Path("post")
    public Response postTweet(TweetDTO tweet) {
        Date date = new Date();
        TweetDTO newTweetDTO = tweet;
        KwetterUserDTO tweetOwnerDTO = tweet.getOwner();
        KwetterUser tweetOwner = kwetterUserService.findUser(tweetOwnerDTO.getId());
        Tweet newTweet = new Tweet(tweetOwner,newTweetDTO.getContent(),date);
        tweetService.postTweet(newTweet);
        
        return Response.ok(newTweetDTO).build();
    }
    @PUT
    @Path("heart/{id}")
    public TweetDTO heartTweet(KwetterUserDTO heartingUserDTO,@PathParam("id") Long id) {
        
        KwetterUser heartingUser = kwetterUserService.findUser(heartingUserDTO.getId());
        Tweet tweetToHeart = tweetService.findTweet(id);
        tweetToHeart.heartTweet(heartingUser);
        tweetService.updateTweet(tweetToHeart);
        return new TweetDTOMapper().mapTweets(tweetToHeart);
    }

    
    @DELETE
    @Path("{id}")
    public void deleteTweet(@PathParam("id") Long id) {
        tweetService.deleteTweet(id);
        
    }
    
    @GET
    @Path("timeline/{id}")
    public Response getTweetsFromFollowers(@PathParam("id") Long id) {
        KwetterUser foundUser = kwetterUserService.findUser(id);
        List<KwetterUser> foundUserFollowing = foundUser.getFollowing();
        List<Tweet> timeLineTweets = new ArrayList<>();
        
        timeLineTweets.addAll(tweetService.allTweetsFromUser(foundUser.getId()));
        
        foundUserFollowing.forEach(user -> {
            timeLineTweets.addAll(tweetService.allTweetsFromUser(user.getId()));
        });
        
        List<TweetDTO> tweetDTO = new ArrayList<>();
        timeLineTweets.forEach(tweet -> tweetDTO.add(new TweetDTOMapper().mapTweets(tweet)));
        
        
        //Order ze op Date
        tweetDTO.sort(reverseOrder(Comparator.comparing((tweet)->tweet.getDate())));
        
        return Response.ok(tweetDTO).build();
    }
}
