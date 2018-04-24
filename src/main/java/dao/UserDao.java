/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.KwetterGroup;
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

    public List<KwetterUser> getAllUsers() {
        return em.createNamedQuery("KwetterUser.allUsers").getResultList();
        
        
    }

    public KwetterUser newUser(KwetterUser newKwetterUser) {
        em.persist(newKwetterUser);
        return newKwetterUser;
    }

    public KwetterUser findUser(Long id) {
        return em.find(KwetterUser.class, id);
    }
    
    public KwetterUser findUserByUsername(String username){
        return (KwetterUser) em.createNamedQuery("KwetterUser.getUserByUsername").setParameter("username", username).getResultList().get(0);
    }

    public KwetterUser updateUser(KwetterUser updatedKwetterUser) {
        return em.merge(updatedKwetterUser);
    }
    
    public KwetterGroup newGroup(KwetterGroup group)
    {
        em.persist(group);
        return group;
    }
    public KwetterGroup addUserToGroup(KwetterGroup group,KwetterUser u)
    {
        group.addUser(u);
        em.merge(group);
        return group;
    }
    public KwetterGroup findGroup(KwetterGroup group){
        return em.find(KwetterGroup.class,group.getGroupName());
    }
    
    
}
