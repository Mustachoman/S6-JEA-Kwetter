/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import util.Hasher;

/**
 *
 * @author Marijn
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "KwetterUser.allUsers", query = "SELECT u FROM KwetterUser u")
    ,
    @NamedQuery(name = "KwetterUser.getUser", query = "SELECT u FROM KwetterUser u WHERE u.id LIKE :id")
    ,
    @NamedQuery(name = "KwetterUser.getUserByUsername", query = "SELECT u FROM KwetterUser u WHERE u.username LIKE :username")
})
public class KwetterUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String username;
    private String photo;
    private String bio;
    private String location;
    private String website;

    private String password;

    /**
     * The users that follow you
     */
    @ManyToMany(mappedBy = "following")
    private List<KwetterUser> followers;

    /**
     * The users that you follow
     */
    @ManyToMany
    private List<KwetterUser> following;

    @OneToMany(mappedBy = "owner")
    private List<Tweet> postedTweets;

    @ManyToMany(mappedBy = "users")
    private List<KwetterGroup> groups;

    //Default constructor
    public KwetterUser() {
    }

    public KwetterUser(String name, String username, String photo, String bio, String location, String website) {
        this.name = name;
        this.username = username;
        this.photo = photo;
        this.bio = bio;
        this.location = location;
        this.website = website;

        this.following = new ArrayList<KwetterUser>();
        this.followers = new ArrayList<KwetterUser>();
        this.postedTweets = new ArrayList<Tweet>();
    }

    public KwetterUser(String username, String password) {
        this.username = username;
        this.password = Hasher.HashString(password);

        this.following = new ArrayList<KwetterUser>();
        this.followers = new ArrayList<KwetterUser>();
        this.postedTweets = new ArrayList<Tweet>();
    }

    public List<Tweet> getPostedTweets() {
        return postedTweets;
    }

    public void setPostedTweets(List<Tweet> postedTweets) {
        this.postedTweets = postedTweets;
    }

    public Tweet postTweet(String content) {
        if (content.length() > 140) {
            throw new IllegalArgumentException("Character limit cannot exceed 140.");
        } else {
            Date date = new Date();
            Tweet newTweet = new Tweet(this, content, date);
            postedTweets.add(newTweet);
            return newTweet;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<KwetterGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<KwetterGroup> groups) {
        this.groups = groups;
    }

    /**
     * Get the value of following
     *
     * @return the value of following
     */
    public List<KwetterUser> getFollowing() {
        return following;
    }

    /**
     * Set the value of following
     *
     * @param following new value of following
     */
    public void setFollowing(List<KwetterUser> following) {
        this.following = following;
    }

    public List<KwetterUser> getFollowers() {
        return followers;
    }

    public void setFollowers(List<KwetterUser> followers) {
        this.followers = followers;
    }

    /**
     * Get the value of website
     *
     * @return the value of website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Set the value of website
     *
     * @param website new value of website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Get the value of location
     *
     * @return the value of location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set the value of location
     *
     * @param location new value of location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get the value of bio
     *
     * @return the value of bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * Set the value of bio
     *
     * @param bio new value of bio
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Get the value of photo
     *
     * @return the value of photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Set the value of photo
     *
     * @param photo new value of photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Get the value of username
     *
     * @return the value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the value of username
     *
     * @param username new value of username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KwetterUser)) {
            return false;
        }
        KwetterUser other = (KwetterUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domain.User[ id=" + id + " ]";
    }

    public boolean followUser(KwetterUser following) {
        if (!this.following.contains(following)) {
            return this.following.add(following);
        } else {
            return false;
        }
    }

    public boolean unfollowUser(KwetterUser following) {
        if (this.following.contains(following)) {
            return this.following.remove(following);
        } else {
            return false;
        }
    }

}
