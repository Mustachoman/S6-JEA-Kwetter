/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.UserDao;
import domain.KwetterUser;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author Marijn
 */
@Startup
@Singleton
public class Init {
    @Inject
    UserDao userDao;
    
    @PostConstruct
    public void init() {
        System.out.println("init");
        KwetterUser marijn = new KwetterUser("Marijn", "Spamturtle");
        KwetterUser roy = new KwetterUser("Roy", "DaCowGoesMoo");
        userDao.save(marijn);
        userDao.save(roy);
    }
}
