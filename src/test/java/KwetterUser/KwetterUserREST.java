/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KwetterUser;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import org.junit.Assert;
;
/**
 *
 * @author Marijn
 */
public class KwetterUserREST {
    
    private String baseURL; 
    
    public KwetterUserREST() {
        this.baseURL = "http://localhost:9905/JEA-Kwetter/users";
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
    public void getUsers() {
//        Client client = ClientBuilder.newClient();
//        Response res = client.target(this.baseURL)
//                .path("/all")
//                .request("application/json")
//                .get();
//
//        Assert.assertTrue(res.readEntity(String.class).contains("Jordy"));
    }
}
