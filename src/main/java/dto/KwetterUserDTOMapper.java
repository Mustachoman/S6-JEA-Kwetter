/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import domain.KwetterUser;

/**
 *
 * @author Marijn
 */
public class KwetterUserDTOMapper {

    public KwetterUserDTOMapper() {

    }

    public KwetterUserDTO mapKwetterUser(KwetterUser kwetterUserToMap) {

        return new KwetterUserDTO(kwetterUserToMap.getId(),
                kwetterUserToMap.getName(),
                kwetterUserToMap.getUsername(),
                kwetterUserToMap.getPhoto(),
                kwetterUserToMap.getBio(),
                kwetterUserToMap.getLocation(),
                kwetterUserToMap.getWebsite());
    }
    
    public KwetterUser mapKwetterUserDTO(KwetterUserDTO kwetterUserDTOToMap) {
        return new KwetterUser(
                kwetterUserDTOToMap.getName(),
                kwetterUserDTOToMap.getUsername(),
                kwetterUserDTOToMap.getPhoto(),
                kwetterUserDTOToMap.getBio(),
                kwetterUserDTOToMap.getLocation(),
                kwetterUserDTOToMap.getWebsite()
        );
    }
}
