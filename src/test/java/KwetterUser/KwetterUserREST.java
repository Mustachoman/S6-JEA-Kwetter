/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KwetterUser;

import dto.KwetterUserDTO;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;

;

/**
 *
 * @author Marijn
 */
public class KwetterUserREST {

    private String baseURL;

    public KwetterUserREST() {
        this.baseURL = "http://localhost:9905/JEA-Kwetter/api/users";
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
    public void testDefaultResponseCode() throws ClientProtocolException, IOException {
        HttpUriRequest request = new HttpGet(this.baseURL);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void testJSONResponse() throws IOException {
        String jsonMimeType = "application/json";
        HttpUriRequest request = new HttpGet(this.baseURL);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        Assert.assertEquals(jsonMimeType, mimeType);
    }

    @Test
    public void testGetUsers() throws ClientProtocolException, IOException {
        HttpUriRequest request = new HttpGet(this.baseURL);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        String json = EntityUtils.toString(response.getEntity());
        
        Assert.assertTrue(json.contains("[{\"id\":"));
    }
    
    @Test
    public void testGetSingleUser() throws ClientProtocolException, IOException {
        HttpUriRequest request = new HttpGet(this.baseURL + "/10");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        String json = EntityUtils.toString(response.getEntity());
        
        Assert.assertTrue(json.contains("{\"id\":10"));
    }
    
    @Test
    public void testGetFollowingUser() throws ClientProtocolException, IOException {
        HttpUriRequest request = new HttpGet(this.baseURL + "/following/10");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        String json = EntityUtils.toString(response.getEntity());
        
        Assert.assertTrue(json.contains("{\"id\":11"));
    }
}
