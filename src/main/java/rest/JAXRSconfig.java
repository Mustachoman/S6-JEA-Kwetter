/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author Marijn
 */
@ApplicationPath("/api")
public class JAXRSconfig extends Application {
    private final Set<Class<?>> classes;

    public JAXRSconfig() {
        HashSet<Class<?>> c = new HashSet<>();

        c.add(KwetterUserResource.class);
        c.add(TweetResource.class);

        classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}
