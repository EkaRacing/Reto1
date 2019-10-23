/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signinsignup;

import exception.DatabaseException;
/**
 * This class creates a DAOImplementation instance and returns it.
 * @author Gaizka
 */
public class DAOFactory {
    
    static BasicConnectionPool pool= new BasicConnectionPool("jdbc:mysql://localhost:3306/reto1", "root", "ekaitzlejar");

	public synchronized static DAO createDAOImplementation() throws DatabaseException{
		return new DAOImplementation(pool,"config.config");
	}
}

