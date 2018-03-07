/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Marijn
 */
public class KwetterUserDTO {
    private String name;
    private String username;
    private String photo;
    private String bio;
    private String location;
    private String website;
    
    public KwetterUserDTO(String name, String username, String photo, String bio, String location, String website) {
        this.name = name;
        this.username = username;
        this.photo = photo;
        this.bio = bio;
        this.location = location;
        this.website = website;
    }

    public KwetterUserDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    
}
