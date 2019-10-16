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
public class EmailNotUniqueException extends Exception {

    /**
     * Creates a new instance of <code>EmailNotUniqueException</code> without
     * detail message.
     */
    public EmailNotUniqueException() {
    }

    /**
     * Constructs an instance of <code>EmailNotUniqueException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EmailNotUniqueException(String msg) {
        super(msg);
    }
}
