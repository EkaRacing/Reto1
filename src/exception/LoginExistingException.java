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
public class LoginExistingException extends Exception {

    /**
     * Creates a new instance of <code>LoginExistingException</code> without
     * detail message.
     */
    public LoginExistingException() {
    }

    /**
     * Constructs an instance of <code>LoginExistingException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LoginExistingException(String msg) {
        super(msg);
    }
}
