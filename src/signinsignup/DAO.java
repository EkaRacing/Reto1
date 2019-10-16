/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signinsignup;

import exception.BadPasswordException;
import exception.DatabaseException;
import exception.EmailNotUniqueException;
import exception.LoginExistingException;
import exception.LoginNotExistingException;

/**
 *
 * @author usuario
 */
public interface DAO {

    /**
     * Signs in a user. If the login does not exist or the password is wrong
     * does not sign in the user.
     *
     * @param user User to sign in.
     * @return Signed in user.
     * @throws LoginNotExistingException User does not exist.
     * @throws BadPasswordException Password is wrong.
     * @throws DatabaseException Problems connecting to database.
     */
    User signIn(User user) throws LoginNotExistingException, BadPasswordException, DatabaseException, Exception;

    /**
     * Signs up a user. If the login or the email are already used does not sign
     * up the user.
     *
     * @param user User to sign up.
     * @throws LoginExistingException Login is already used.
     * @throws EmailNotUniqueException Email is already used.
     * @throws DatabaseException Problems connecting to database.
     */
    void signUp(User user) throws LoginExistingException, EmailNotUniqueException, DatabaseException, Exception;
    
}
