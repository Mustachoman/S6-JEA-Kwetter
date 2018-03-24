/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tweet;

import KwetterUser.*;
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
public class TweetREST {

    private String baseURL;

    public TweetREST() {
        this.baseURL = "http://localhost:8080/JEA-Kwetter/api/tweets";
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
    public void testGetTweets() throws ClientProtocolException, IOException {
        HttpUriRequest request = new HttpGet(this.baseURL);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        String json = EntityUtils.toString(response.getEntity());
        
        Assert.assertTrue(json.contains("[{\"content\":"));
    }
    
    @Test
    public void testGetSingleTweet() throws ClientProtocolException, IOException {
        HttpUriRequest request = new HttpGet(this.baseURL + "/1");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        String json = EntityUtils.toString(response.getEntity());
        
        Assert.assertTrue(json.contains("{\"id\":1"));
    }
    
    @Test
    public void testGetHearts() throws ClientProtocolException, IOException {
        HttpUriRequest request = new HttpGet(this.baseURL + "/allHearts/1");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        String json = EntityUtils.toString(response.getEntity());
        
        Assert.assertTrue(json.contains("testRoy"));
    }
    
}
