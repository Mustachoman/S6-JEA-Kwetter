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
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
    @Path("")
    public List<TweetDTO> allTweets() {
        List<Tweet> tweets = tweetService.allTweets();
        List<TweetDTO> tweetDTO = new ArrayList<>();
        tweets.forEach(tweet -> tweetDTO.add(new TweetDTOMapper().mapTweets(tweet)));
        return tweetDTO;
    }
    
    @POST
    @Path("")
    public void postTweet(TweetDTO tweet) {
        Date date = new Date();
        TweetDTO newTweetDTO = tweet;
        KwetterUserDTO tweetOwnerDTO = tweet.getOwner();
        KwetterUser tweetOwner = kwetterUserService.findUser(tweetOwnerDTO.getId());
        Tweet newTweet = new Tweet(tweetOwner,newTweetDTO.getContent(),date);
        
        tweetService.postTweet(newTweet);
    }
    
    @GET
    @Path("user/{userId}")
    public List<TweetDTO> allTweetsFromUser(@PathParam("userId") Long userId) {
        List<Tweet> tweets = tweetService.allTweets();
        List<TweetDTO> tweetDTO = new ArrayList<>();
        
        tweets.stream().filter((tweet) -> (Objects.equals(tweet.getOwner().getId(), userId))).forEachOrdered((tweet) -> {
            tweetDTO.add(new TweetDTOMapper().mapTweets(tweet));
        });
       
        return tweetDTO;
    }

    @GET
    @Path("{tweetId}")
    @Produces(MediaType.APPLICATION_JSON)
    public TweetDTO getTweet(@PathParam("tweetId") Long tweetId) {
        Tweet foundTweet = tweetService.findTweet(tweetId);
        TweetDTO tweetDTO = new TweetDTOMapper().mapTweets(foundTweet);
        return tweetDTO;
    }
    
    @GET
    @Path("{tweetId}/hearts")
    @Produces(MediaType.APPLICATION_JSON)
    public List<KwetterUserDTO> getHearts(@PathParam("tweetId") Long tweetId){
        Tweet foundTweet = tweetService.findTweet(tweetId);
        List<KwetterUser> foundHearts = foundTweet.getHearts();
        List<KwetterUserDTO> foundHeartsDTO = new ArrayList<>();
        foundHearts.forEach(user -> foundHeartsDTO.add(new KwetterUserDTOMapper().mapKwetterUser(user)));
        return foundHeartsDTO;
    }
    
    @PUT
    @Path("heart/{tweetId}")
    public TweetDTO heartTweet(KwetterUserDTO heartingUserDTO, @PathParam("tweetId") Long tweetId) {
        
        KwetterUser heartingUser = kwetterUserService.findUser(heartingUserDTO.getId());
        Tweet tweetToHeart = tweetService.findTweet(tweetId);
        tweetToHeart.heartTweet(heartingUser);
        tweetService.updateTweet(tweetToHeart);
        return new TweetDTOMapper().mapTweets(tweetToHeart);
    }
}
