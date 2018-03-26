/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.KwetterGroup;
import domain.KwetterUser;
import domain.Tweet;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import service.KwetterUserService;
import service.TweetService;

/**
 *
 * @author Roy Siebers
 */
@Named
//@RequestScoped
@SessionScoped
public class UserListController implements Serializable {

    @Inject
    private TweetService tweetService;

    @Inject
    private KwetterUserService kwetterUserService;

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private List<KwetterUser> users;

    private List<Tweet> tweets;

    public UserListController() {

    }

    @PostConstruct
    public void init() {
        users = kwetterUserService.allUsers();
        tweets = tweetService.allTweets();

    }

    public List<KwetterUser> getUsers() {
        return users;
    }

    public void setUsers(List<KwetterUser> users) {
        this.users = users;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public String getGroup(String id) {
        KwetterGroup g = new KwetterGroup("admin");
        g = kwetterUserService.findGroup(g);
        for (KwetterUser u : users) {
            if (u.getId().toString().equals(id)) {
                if (g.getUsers().contains(u)) {
                    return "Admin";
                }

            }
        }
        return "User";
    }

    public void grantPrivilege() {
        KwetterGroup g = new KwetterGroup("admin");
        g = kwetterUserService.findGroup(g);
        String content = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("content");
        for (KwetterUser u : users) {
            if (u.getId().toString().equals(content)) {
                if (!g.getUsers().contains(u)) {
                    kwetterUserService.addToGroup(g, u);
                }

            }
        }
    }

    public void onSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
    }

}
