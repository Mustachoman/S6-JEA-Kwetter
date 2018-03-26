/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tweet;

import KwetterUser.KwetterUserTest;
import domain.KwetterUser;
import domain.Tweet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import util.DatabaseCleaner;

/**
 *
 * @author roy_s
 */
public class UserToTweetTest {
    
    private KwetterUser testMarijn;
    private KwetterUser testRoy;
    private KwetterUser testIvo;
    
    private Tweet eersteTweet;
    private Tweet tweedeTweet;
    private Date now;
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestKwetterPU");
    private EntityManager em;
    private EntityManager emRetrieve;
    private EntityTransaction tx;
    
   
    
    
    public UserToTweetTest() {
        this.testMarijn = new KwetterUser("Marijn", "Spamturtle");
        this.testRoy = new KwetterUser("Roy", "DaCowGoesMoo");
        this.testRoy = new KwetterUser("Ivo", "DevNebulae");
        this.now = new Date();
        
        this.eersteTweet = new Tweet(testMarijn,"Hallo",now);
        this.tweedeTweet = new Tweet(testRoy,"Hoi",now);
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try {
            new DatabaseCleaner(emf.createEntityManager()).clean();
        } catch (SQLException ex) {
            Logger.getLogger(KwetterUserTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        em = emf.createEntityManager();
        tx = em.getTransaction();
        
        testMarijn = new KwetterUser("testMarijn", "testSpamturtle");
        testRoy = new KwetterUser("testRoy", "testDaCowGoesMoo");
        testIvo = new KwetterUser("testIvo", "testGamerkeeperson");
        
        eersteTweet = testMarijn.postTweet("Hello");
        tweedeTweet = testRoy.postTweet("Hoi");
        
        
        eersteTweet.heartTweet(testRoy);
        testIvo.followUser(testMarijn);
        
        tx.begin();
        em.persist(testMarijn);
        em.persist(testRoy);
        em.persist(testIvo);
        em.persist(eersteTweet);
        em.persist(tweedeTweet);
        tx.commit();
    }
    
    @After
    public void tearDown() {
    }
    

     @Test
     public void testAddHeart() {
         
         Assert.assertEquals(this.getTweet(eersteTweet.getId()).getHearts().contains(testRoy), true);
         tx.begin();
         eersteTweet.heartTweet(testIvo);
         em.merge(eersteTweet);
         tx.commit();
         
         eersteTweet = this.getTweet(eersteTweet.getId());
         Assert.assertEquals(eersteTweet.getHearts().contains(testIvo), true);
         
         
         
         
     }
     
     @Test
     public void testAddMention() {
         
         Assert.assertEquals(this.getTweet(eersteTweet.getId()).getMentions().contains(testIvo), false);
         tx.begin();
         eersteTweet.mentionUser(testIvo);
         em.merge(eersteTweet);
         tx.commit();
         
         eersteTweet = this.getTweet(eersteTweet.getId());
         Assert.assertEquals(eersteTweet.getMentions().contains(testIvo), true);
         
     }
     
     @Test
     public void testPostTweet() {
         
         Tweet testTweet = testMarijn.postTweet("Mijn eerste tweet");
         tx.begin();
         em.persist(testTweet);
         tx.commit();
         Assert.assertTrue(this.getTweet(testTweet.getId()).equals(testTweet));
         
         
         
     }
//     @Test(expected = IllegalArgumentException.class)
//     public void testIllegalArgumentException(){
//         testMarijn.postTweet("Mijn eerste tweet, Dit is langer dan 140 karakters dus zou een error moeten geven als het goed is. Maar het kan ook zo zijn dat het fout gaat en er geen error optreed.");
//     }
     
     public KwetterUser getUser(Long searchId){
        emRetrieve = emf.createEntityManager();
        return (KwetterUser) emRetrieve.createNamedQuery("KwetterUser.getUser").setParameter("id", searchId).getSingleResult();
    }
    public Tweet getTweet(Long searchId){
        emRetrieve = emf.createEntityManager();
        return (Tweet) emRetrieve.createNamedQuery("Tweet.getTweet").setParameter("id", searchId).getSingleResult();
    }
}
