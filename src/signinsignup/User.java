/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signinsignup;

import java.awt.Image;
import java.sql.Timestamp;

/**
 *
 * @author usuario
 */
public class User {
    
    private String login;
    private String email;
    private String fullName;
    private UserStatus status;
    private UserPrivilege privilege;
    private String password;
    private Timestamp lastAcces;
    private Timestamp lastPasswordChange;
    private Image userPhoto;


    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the fullname
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullname the fullname to set
     */
    public void setFullName(String fullname) {
        this.fullName = fullname;
    }

    /**
     * @return the status
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * @return the privilege
     */
    public UserPrivilege getPrivilege() {
        return privilege;
    }

    /**
     * @param privilege the privilege to set
     */
    public void setPrivilege(UserPrivilege privilege) {
        this.privilege = privilege;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the lastAcces
     */
    public Timestamp getLastAcces() {
        return lastAcces;
    }

    /**
     * @param lastAcces the lastAcces to set
     */
    public void setLastAcces(Timestamp lastAcces) {
        this.lastAcces = lastAcces;
    }

    /**
     * @return the lastPasswordChange
     */
    public Timestamp getLastPasswordChange() {
        return lastPasswordChange;
    }

    /**
     * @param lastPasswordChange the lastPasswordChange to set
     */
    public void setLastPasswordChange(Timestamp lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    /**
     * @return the userPhoto
     */
    public Image getUserPhoto() {
        return userPhoto;
    }

    /**
     * @param userPhoto the userPhoto to set
     */
    public void setUserPhoto(Image userPhoto) {
        this.userPhoto = userPhoto;
    }
    
}
