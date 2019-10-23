package signinsignup;


import java.sql.*;
import java.util.*;
 /**
 * Esta clase implementa un pool de conexiones que se almacena el un objeto pila
 * (java.util.Stack).
 * @author Marisa Martín Ruiz/Javier Martín Uría
 */
public class BasicConnectionPool{ 

    protected Stack pool;
    protected String connectionURL;
    protected String userName;
    protected String password;
    protected String driverBD;
    /**
     * Construye un pool de conexiones almacenando los datos pasados como parámetros
     * para usarlos al crear las conexiones.
     * @param aConnectionURL URL de la BD a conectarse.
     * @param aUserName Nombre de usuario de la BD para crear la conexión.
     * @param aPassword Contraseña de usuario de la BD para crear la conexión.
     */
    //public
    BasicConnectionPool(String aConnectionURL, String aUserName, String aPassword, String aDriver){
		connectionURL = aConnectionURL;
		userName = aUserName;
		password = aPassword;
                driverBD=aDriver;
		pool = new Stack();
    } 

    /** Obtiene una conexión del pool de conexiones
     * @return Un objeto java.sql.Connection para la conexión.
     * @throws SQLException si existe error al obtener la conexión.
     */
   
    public synchronized Connection extractConection()   throws SQLException, Exception{ 
	// Si el pool no esta vacio, tomar una conexion  
	   if(!pool.empty()) {
			return (Connection) pool.pop();
           } 
           else {                
                        Class.forName(this.driverBD);
			// Entonces generar una conexion nueva
			return DriverManager.getConnection(connectionURL, userName, password);
	   } 
    }
    /** Devuelve una conexión al pool de conexiones.
     * @param conn La conexión a liberar.
     * @throws java.lang.Exception
     */
    public synchronized void liberateConection(Connection conn) throws Exception{ 
        pool.push(conn);
    } 
}
 

