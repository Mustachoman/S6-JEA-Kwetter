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
        group.addUser(marijn);
        userDao.newGroup(group);
        userDao.newUser(marijn);
        userDao.newUser(roy);
        //userDao.addUserToGroup(group, roy);
        Tweet tweet = roy.postTweet("Dit is een tweet");
        tweet.heartTweet(marijn);
        tweet.mentionUser(marijn);
        tweetDao.save(tweet);

    }
}
