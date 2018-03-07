/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.KwetterUser;
import domain.Tweet;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Marijn
 */
@Stateless
public class TweetDao {
    
    @PersistenceContext
    EntityManager em;
    
    public List<Tweet> getAllTweets(){
        return em.createNamedQuery("Tweets.allTweets").getResultList();
    }
    
    public void save(Tweet t){
        em.persist(t);
    }
    
    public Tweet findTweet(Long id){
        return em.find(Tweet.class, id);
    }
}