/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KwetterUser;

import dao.UserDao;
import domain.KwetterUser;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marijn
 */
public class KwetterUserTest {

    @Inject
    UserDao userDao;
    
    KwetterUser testMarijn;
    KwetterUser testRoy;

    public KwetterUserTest() {
       
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        testMarijn = new KwetterUser("testMarijn", "testSpamturtle");
        testRoy = new KwetterUser("testRoy", "testDaCowGoesMoo");
        userDao.saveUser(testMarijn);
        userDao.saveUser(testMarijn);
    }

    @After
    public void tearDown() {
        
    }


    @Test
    public void testFollowUser() {
        //Test if user is following other users (shouldn't at first)
         Assert.assertTrue(testMarijn.getFollowing().isEmpty());
         testMarijn.followUser(testRoy);
         Assert.assertTrue(testMarijn.getFollowing().contains(testRoy));
    }
    
    @Test
    public void testFollowedBy(){
        Assert.assertTrue(testRoy.getFollowers().isEmpty());
        testMarijn.followUser(testRoy);
        Assert.assertTrue(testRoy.getFollowers().contains(testMarijn));
    }
}
