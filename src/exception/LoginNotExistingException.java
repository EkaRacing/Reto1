/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author usuario
 */
public class LoginNotExistingException extends Exception {

    /**
     * Creates a new instance of <code>LoginNotExistingException</code> without
     * detail message.
     */
    public LoginNotExistingException() {
    }

    /**
     * Constructs an instance of <code>LoginNotExistingException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LoginNotExistingException(String msg) {
        super(msg);
    }
}
