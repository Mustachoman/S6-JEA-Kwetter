/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KwetterUser;

import domain.KwetterUser;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
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
 * @author Marijn
 */
public class KwetterUserTest {
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestKwetterPU");
    private EntityManager em;
    private EntityManager emRetrieve;
    private EntityTransaction tx;
    
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
    public void setUp() throws NamingException {
        try {
            new DatabaseCleaner(emf.createEntityManager()).clean();
        } catch (SQLException ex) {
            Logger.getLogger(KwetterUserTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        em = emf.createEntityManager();
        tx = em.getTransaction();
        
        testMarijn = new KwetterUser("testMarijn", "testSpamturtle");
        testRoy = new KwetterUser("testRoy", "testDaCowGoesMoo");
        
        tx.begin();
        em.persist(testMarijn);
        em.persist(testRoy);
        tx.commit();
    }


    @After
    public void tearDown() {
        
    }

    @Test
    public void testFollow() {
        //Test if user is following other users (shouldn't at first)
         Assert.assertTrue(testMarijn.getFollowing().isEmpty());
         testMarijn.followUser(testRoy);
         tx.begin();
         em.merge(testMarijn);
         tx.commit();
         
         testMarijn = this.getUser(testMarijn.getId());
         Assert.assertTrue(testMarijn.getFollowing().contains(testRoy));
         
         testRoy = this.getUser(testRoy.getId());
         Assert.assertTrue(testRoy.getFollowers().contains(testMarijn));
    }
    
    public KwetterUser getUser(Long searchId){
        emRetrieve = emf.createEntityManager();
        return (KwetterUser) emRetrieve.createNamedQuery("KwetterUser.getUser").setParameter("id", searchId).getSingleResult();
    }
}
