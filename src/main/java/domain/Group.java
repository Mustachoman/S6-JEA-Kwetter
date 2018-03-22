/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author roy_s
 */
//@Entity
//@Table(name = "UserGroup")
public class Group implements Serializable
{
//    @Id
    private String groupName;
//    @ManyToMany
//    @JoinTable(name="USER_GROUP",
//    joinColumns = @JoinColumn(name = "groupName", 
//        referencedColumnName = "groupName"), 
//    inverseJoinColumns = @JoinColumn(name = "userName", 
//        referencedColumnName = "userName"))
    private List<KwetterUser> users;
    // getters, setters, no‐arg constructor

    public List<KwetterUser> getUsers() {
        return users;
    }

    public void setUsers(List<KwetterUser> users) {
        this.users = users;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
