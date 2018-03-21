/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
@Entity
@Table(name = "KWETTERGROUP")
public class KwetterGroup implements Serializable {

    @Id
    private String groupname;

    @ManyToMany(targetEntity = KwetterUser.class, cascade = CascadeType.REMOVE)
    @JoinTable(name = "KWETTERUSER_GROUP", joinColumns = @JoinColumn(name = "GROUPNAME",
            referencedColumnName = "GROUPNAME"),
            inverseJoinColumns = @JoinColumn(name = "USERNAME",
                    referencedColumnName = "USERNAME"))
    private List<KwetterUser> users;

    public KwetterGroup() {
    }

    public KwetterGroup(String name) {
        this.groupname = name;
        this.users = new ArrayList<KwetterUser>();
    }

    public List<KwetterUser> getUsers() {
        return users;
    }

    public void setUsers(List<KwetterUser> users) {
        this.users = users;
    }

    public String getGroupName() {
        return groupname;
    }

    public void setGroupName(String groupName) {
        this.groupname = groupName;
    }

    public void addUser(KwetterUser user) {
        this.users.add(user);
    }
}
