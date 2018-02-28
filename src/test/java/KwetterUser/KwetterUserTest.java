/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KwetterUser;

import domain.KwetterUser;
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
    
    private KwetterUser testMarijn;
    private KwetterUser testRoy;
    
    public KwetterUserTest() {
        this.testMarijn = new KwetterUser("Marijn", "Spamturtle");
        this.testRoy = new KwetterUser("Roy", "DaCowGoesMoo");
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

    @Test
    public void testFollow() {
         //Test if user is following other users
         Assert.assertTrue(testMarijn.getFollowing().isEmpty());
         testMarijn.follow(testRoy);
         Assert.assertTrue(testMarijn.getFollowing().contains(testRoy));
    }
}
