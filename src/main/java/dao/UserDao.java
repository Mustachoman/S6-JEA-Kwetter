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

    @PersistenceContext(unitName = "KwetterPU")
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

    public KwetterUser updateUser(KwetterUser updatedKwetterUser) {
        return em.merge(updatedKwetterUser);
    }
    
    public KwetterGroup newGroup(KwetterGroup group)
    {
        em.persist(group);
        return group;
    }
}
