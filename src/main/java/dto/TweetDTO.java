/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import domain.*;
import java.io.Serializable;
import java.util.Date;



/**
 *
 * @author roy_s
 */

public class TweetDTO implements Serializable {
   
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    private KwetterUserDTO owner;
    private String content;
    private Date date;
    
    
    
    public TweetDTO() {
    }

    public TweetDTO(Long id ,KwetterUserDTO owner, String Content, Date date) {
        
        this.id = id;
        this.owner = owner;
        this.content = Content;
        this.date = date;
       
       
    }

    public KwetterUserDTO getOwner() {
        return owner;
    }

    public void setOwner(KwetterUserDTO owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String Content) {
        this.content = Content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
  
}
