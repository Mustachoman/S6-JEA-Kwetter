/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 *
 * @author roy_s
 */
@Entity
public class Tweet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private KwetterUser owner;
    private String Content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    @ManyToMany
    private List<KwetterUser> hearts;
    
    @ManyToMany
    private List<KwetterUser> mentions;
    
    
    private List<String> trends;
    
    public Tweet() {
    }

    public Tweet(KwetterUser owner, String Content, Date date) {
        
        this.owner = owner;
        this.Content = Content;
        this.date = date;
        hearts = new ArrayList<KwetterUser>();
        mentions = new ArrayList<KwetterUser>();
    }

    public KwetterUser getOwner() {
        return owner;
    }

    public void setOwner(KwetterUser owner) {
        this.owner = owner;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<KwetterUser> getHearts() {
        return hearts;
    }

    public void setHearts(List<KwetterUser> hearts) {
        this.hearts = hearts;
    }

    public List<KwetterUser> getMentions() {
        return mentions;
    }

    public void setMentions(List<KwetterUser> mentions) {
        this.mentions = mentions;
    }

    public List<String> getTrends() {
        return trends;
    }

    public void setTrends(List<String> trends) {
        this.trends = trends;
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
        if (!(object instanceof Tweet)) {
            return false;
        }
        Tweet other = (Tweet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domain.Tweet[ id=" + id + " ]";
    }

    public void heartTweet(KwetterUser user) {
        hearts.add(user);
    }
    public void mentionUser(KwetterUser user) {
        mentions.add(user);
    }

   
    
}
