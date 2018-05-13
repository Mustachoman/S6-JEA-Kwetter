/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.TweetDao;
import dao.UserDao;
import domain.KwetterUser;
import domain.Tweet;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Marijn
 */
@Stateless
public class TweetService {
    
    @Inject
    TweetDao tweetDao;
    
    public TweetService(){}
    
    public List<Tweet> allTweets() {
        return tweetDao.getAllTweets();
    }
    
    public List<Tweet> allTweetsFromUser(Long userId) {
        return tweetDao.getAllTweetsFromOwner(userId);
    }

    public Tweet findTweet(Long id) {
        return tweetDao.findTweet(id);
    }
    
    public void postTweet(Tweet newTweet){
        tweetDao.save(newTweet);
    }
    
    public void updateTweet(Tweet updatedTweet)
    {
        tweetDao.update(updatedTweet);
    }
     public void deleteTweet(long id)
    {
        tweetDao.delete(id);
    }
     
}
