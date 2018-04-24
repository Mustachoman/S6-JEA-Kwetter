/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import domain.KwetterUser;
import domain.Tweet;

/**
 *
 * @author Marijn
 */
public class TweetDTOMapper {

    public TweetDTOMapper() {
        
    }

    public TweetDTO mapTweets(Tweet tweetToMap) {
        KwetterUser o=tweetToMap.getOwner();
        KwetterUserDTO kwetterUserDTO = new KwetterUserDTO(o.getId(),o.getName(),o.getUsername(),o.getPhoto(),o.getBio(),o.getLocation(),o.getWebsite());
        return new TweetDTO(tweetToMap.getId(),kwetterUserDTO,
                tweetToMap.getContent(),
                tweetToMap.getDate());
    }
}
