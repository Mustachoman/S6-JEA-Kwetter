/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.TweetDao;
import dao.UserDao;
import domain.KwetterUser;
import domain.KwetterGroup;
import domain.Tweet;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author Marijn
 */
@Startup
@Singleton
public class Init {
    @Inject
    UserDao userDao;
    
    @Inject
    TweetDao tweetDao;
    
    @PostConstruct
    public void init(){
        System.out.println("init");
        KwetterUser marijn = new KwetterUser("Marijn", "Dirk");
        KwetterUser roy = new KwetterUser("Roy", "Egbert");
        KwetterGroup group = new KwetterGroup("admin");   
        userDao.newUser(marijn);
        userDao.newUser(roy);
        group.addUser(marijn);
        userDao.newGroup(group);
        Tweet tweet = roy.postTweet("Dit is een tweet");
        Tweet tweet2 = marijn.postTweet("Dit is een tweet2");
        Tweet tweet3 = marijn.postTweet("Was dit maar een tweet");
        tweet.heartTweet(marijn);
        tweet.mentionUser(marijn);
        tweetDao.save(tweet);
        tweetDao.save(tweet2);
        tweetDao.save(tweet3);
    }
}
