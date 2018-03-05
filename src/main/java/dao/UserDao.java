/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.KwetterUser;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Marijn
 */
@Stateless
public class UserDao {
    
    @PersistenceContext
    EntityManager em;
    
    public List<KwetterUser> getAllUsers(){
        return em.createNamedQuery("KwetterUser.allUsers").getResultList();
    }
    
    public void saveUser(KwetterUser u){
        em.persist(u);
    }
    
    public KwetterUser findUser(Long id){
        return em.find(KwetterUser.class, id);
    }
    
    public KwetterUser updateUser(KwetterUser newKwetterUser){
        return em.merge(newKwetterUser);
    }
}
