/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signinsignup;

import exception.DatabaseException;
import java.util.ResourceBundle;
/**
 * This class creates a DAOImplementation instance and returns it.
 * @author Gaizka
 */
public class DAOFactory {
    
    private static String urlDB=ResourceBundle.getBundle("config.config").getString("Conn");
    private static String userDB=ResourceBundle.getBundle("config.config").getString("DBUser");
    private static String passwordDB=ResourceBundle.getBundle("config.config").getString("DBPass");
    private static String driverBD=ResourceBundle.getBundle("config.config").getString("Driver");

    private static BasicConnectionPool pool=new BasicConnectionPool(urlDB, userDB, passwordDB,driverBD);

	public synchronized static DAO createDAOImplementation() throws DatabaseException{
		return new DAOImplementation(pool);
	}
}

