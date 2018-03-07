/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tweet;

import domain.KwetterUser;
import domain.Tweet;
import java.util.Date;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author roy_s
 */
public class UserToTweetTest {
    
    private KwetterUser testMarijn;
    private KwetterUser testRoy;
    private Tweet eersteTweet;
    private Tweet tweedeTweet;
    private Date now;
    
    public UserToTweetTest() {
        this.testMarijn = new KwetterUser("Marijn", "Spamturtle");
        this.testRoy = new KwetterUser("Roy", "DaCowGoesMoo");
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
    }
    
    @After
    public void tearDown() {
    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void testAddHeart() {
         
         Assert.assertEquals(eersteTweet.getHearts().isEmpty(), true);
         eersteTweet.heartTweet(testRoy);
         Assert.assertEquals(eersteTweet.getHearts().contains(testRoy), true);
         
     }
     
//     @Test
//     public void testAddMention() {
//         
//         Assert.assertEquals(eersteTweet.getMentions().isEmpty(), true);
//         eersteTweet.mentionUser(testRoy);
//         Assert.assertEquals(eersteTweet.getMentions().contains(testRoy), true);
//         
//     }
     
     @Test
     public void testPostTweet() {
         Assert.assertTrue(testMarijn.getPostedTweets().isEmpty());
         Tweet testTweet = testMarijn.postTweet("Mijn eerste tweet");
         Assert.assertTrue(testMarijn.getPostedTweets().contains(testTweet));
         
         
         
     }
     @Test(expected = IllegalArgumentException.class)
     public void testIllegalArgumentException(){
         testMarijn.postTweet("Mijn eerste tweet, Dit is langer dan 140 karakters dus zou een error moeten geven als het goed is. Maar het kan ook zo zijn dat het fout gaat en er geen error optreed.");
     }
}
