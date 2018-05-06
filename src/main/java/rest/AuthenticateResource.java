/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import domain.Credentials;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import javax.ws.rs.core.UriInfo;
import service.KwetterUserService;
import util.KeyGenerator;

/**
 *
 * @author Marijn
 */
@Path("/authentication")
@Stateless
public class AuthenticateResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    KwetterUserService kwetterUserService;

    @POST
    public Response authenticateUser(Credentials credentials) {
        try {
            // Authenticate the user using the credentials provided
            authenticate(credentials.getUsername(), credentials.getPassword());

            // Issue a token for the user
            String token = issueToken(credentials.getUsername());

            HashMap userToken = new HashMap();
            userToken.put("username", credentials.getUsername());
            userToken.put("AuthToken", token);

            // Return the token on the response
            return Response.ok(userToken).header(AUTHORIZATION, "Bearer " + token).build();

        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }

    private void authenticate(String username, String password) throws Exception {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
        kwetterUserService.authenticateUser(username, password);
    }

    private String issueToken(String login) {
        Key key = KeyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return jwtToken;
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
