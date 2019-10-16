package signinsignup;

import exception.BadPasswordException;
import exception.DatabaseException;
import exception.EmailNotUniqueException;
import exception.LoginExistingException;
import exception.LoginNotExistingException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para el acceso a datos en las tablas de la BD signinsignup.
 * Gestiona las conexiones a BD partir de un pool de conexiones  
 * que se almacena como parámetro de contexto de la aplicación y de los datos 
 * de un fichero de configuración.
 * @author Ekaitz
 */
public class DAOImplementation implements DAO {
    
    private final     BasicConnectionPool   poolBD;
    private final     ResourceBundle  configFile;
    private final     String          driverBD;
    private final     String          urlBD;
    private final     String          userBD;
    private final     String          passwordBD;
    private final     String          database;
    protected   Logger     logger;
    protected   Connection      conexionBD;
    /**
     * Inicializa las propiedades del DAO
     * @param poolBD El pool de conexiones a utilizar.
     * @param configFile El archivo de configuración donde están los datos necesarios
     * para las conexiones a la BD.
     */
    public DAOImplementation(BasicConnectionPool poolBD,String configFile){

        this.poolBD=poolBD;
        this.configFile=ResourceBundle.getBundle(configFile);
        this.driverBD= this.configFile.getString("Driver");
        this.urlBD= this.configFile.getString("Conn");
        this.userBD= this.configFile.getString("DBUser");
        this.passwordBD= this.configFile.getString("DBPass");
        this.database= this.configFile.getString("DB");
        //obtenemos un logger
        this.logger=Logger.getLogger("");
        
    }
    /**
     * Obtiene conexión con BD.
     * @throws DAOException Si existe cualquier error al obtener la conexión.
     */
    private void connect() throws Exception{
        logger.info("Obteniendo conexión con la BD.");
        try{
            //Si hay pool lo usamos
           if(poolBD!=null){
               this.conexionBD=poolBD.extraerConexion();
           }
           //Si no creamos una nueva conexión mediante DriverManager
           else{
               Class.forName(this.driverBD);
               this.conexionBD = 
                    DriverManager.getConnection(urlBD,userBD,passwordBD);        
           }
           //por defecto ponemos el autocommit de la conexión a true
           this.conexionBD.setAutoCommit(true);
        }catch(SQLException e){
            logger.severe("Error al crear Conexión con BD."+
                    "No se puede obtener conexión:"+e.getMessage());
            throw new DatabaseException("Error al crear Conexión con BD."+
                           "No se puede obtener conexión:"+e.getMessage());
            
        }catch(ClassNotFoundException e){
            logger.severe("Error al crear Conexión con BD:"+
                           "No se puede cargar la clase del Driver.");
            throw new DatabaseException("Error al crear Conexión con BD:"+
                           "No se puede cargar la clase del Driver.");
        }

    }
    /**
     * Libera conexión con BD. 
     * @throws DAOException Si existe cualquier error al liberar la conexión.
     */
    private void disconnect() throws Exception{
       logger.info("Liberando conexión con la BD.");
       try{
            //Si hay pool liberamos la conexión hacia el pool
           if(poolBD!=null){
               poolBD.liberarConexion(conexionBD);
               this.conexionBD=null;
           }
           //Si no cerramos la conexión creada mediante el DriverManager
           else{
               this.conexionBD.close();
               this.conexionBD=null;
           }
        }catch(SQLException e){
            logger.severe("Error al liberar Conexión con BD:\n"+
                           "SQLERROR="+e.getMessage());
            throw new DatabaseException("Error al liberar Conexión con BD:\n"+
                           "SQLERROR="+e.getMessage());
            
        }
        
    }
    
    
    
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
	
    @Override
	public User signIn(User user) throws LoginNotExistingException, BadPasswordException, DatabaseException, Exception {
		logger.info("DAOImplementation::signIn: Beginning user sign in.");

		if (user.getLogin() != null) {
			if (user.getPassword() != null) {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
                                this.connect();
				try {
					// Get user from database
					stmt = this.conexionBD.prepareStatement("select * from " + this.database +".USERS where lower(LOGIN) like ?");
					stmt.setString(1, user.getLogin().trim().toLowerCase());
					resultSet = stmt.executeQuery();
					if (resultSet.next()) {
						// If password is correct get user's data and update last access
						if (user.getPassword().trim().equals(resultSet.getString("password").trim())) {
							// Get user's data from database
							user.setFullName(resultSet.getString("fullName"));
							user.setEmail(resultSet.getString("email"));
							// Set photo
							//user.setPhoto(resultSet.getBlob("Photo"));

							// Update last access
							stmt = this.conexionBD.prepareStatement("update " + this.database + ".USERS " + " set LASTACCESS = ? " + " where LOWER(LOGIN) = ?");
							stmt.setDate(1, Date.valueOf(LocalDate.now()));
							stmt.setString(2, user.getLogin().trim().toLowerCase());
							if (stmt.executeUpdate() == 0) {
								logger.log(Level.SEVERE,
									"DAOImplementation::signIn: Could not update last access.");
							}
						} else {
							logger.log(Level.SEVERE,
								"DAOImplementation::signIn: Exception password is wrong.");
							throw new BadPasswordException("Password is wrong!");
						}
					} else {
						logger.log(Level.SEVERE,
							"DAOImplementation::signIn: Exception user was not found.");
						throw new LoginNotExistingException("Login was not found!");
					}
				} catch (SQLException ex) {
					logger.log(Level.SEVERE,
						"DAOImplementation::signIn: Exception connecting to database",
						ex.getMessage());
					throw new DatabaseException("Problem signing up user: " + user.getLogin());
				} finally {
					// Close connections
					try {
						if (resultSet != null) {
							resultSet.close();
						}
						if (stmt != null) {
							stmt.close();
						}
						if (this.conexionBD != null) {
							this.disconnect();
						}
					} catch (SQLException ex) {
						logger.log(Level.SEVERE,
							"DAOImpentation::signIn: Exception closing the connection to database.",
							ex.getMessage());
					}
				}
			} else {
				logger.log(Level.SEVERE,
					"DAOImplementation::signIn: Exception password is null.");
				throw new BadPasswordException("Password is wrong!");
			}
		} else {
			logger.log(Level.SEVERE,
				"DAOImplementation::signIn: Exception login is null.");
			throw new LoginNotExistingException("Login was not found!");
		}
		return user;
	}
    
    /**
	 * Signs up a user. If the login or the email are already used does not sign
	 * up the user.
	 *
	 * @param user User to sign up.
	 * @throws LoginExistingException Login is already used.
	 * @throws EmailNotUniqueException Email is already used.
	 * @throws DatabaseException Problems connecting to database.
	 */
    @Override
	public void signUp(User user) throws LoginExistingException, EmailNotUniqueException, DatabaseException, Exception {
		logger.info("DAOImplementation::signUp: Beginning user sign up.");
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
                this.connect();
		try {
			// Check the login and email are not used
			stmt = this.conexionBD.prepareStatement("select count(*) as loginCount from " + this.database + ".USERS where lower(LOGIN) like ?");
			stmt.setString(1, user.getLogin().trim().toLowerCase());
			resultSet = stmt.executeQuery();

			resultSet.next();

			// If the login exists throw exception else check email
			if (resultSet.getInt(1) == 0) {
				stmt = this.conexionBD.prepareStatement("select count(EMAIL) as emailCount from " + this.database + ".USERS where lower(EMAIL) like ?");
				stmt.setString(1, user.getEmail().trim().toLowerCase());
				resultSet = stmt.executeQuery();
				resultSet.next();

				// If the email exists throw exception else insert it
				if (resultSet.getInt("emailCount") == 0) {
					// Insert new user
					stmt = this.conexionBD.prepareStatement("insert into users(login,fullName,email,status,privilege,password,lastAccess,lastPasswordChange) values(?,?,?,?,?,?,?,?)");

					stmt.setString(1, user.getLogin().trim().toLowerCase());
					stmt.setString(2, user.getFullName().trim().toLowerCase());
					stmt.setString(3, user.getEmail().trim().toLowerCase());
					stmt.setInt(4, user.getStatus().ordinal() + 1);
					stmt.setInt(5, user.getPrivilege().ordinal() + 1);
					stmt.setString(6, user.getPassword().trim());
					stmt.setDate(7, Date.valueOf(LocalDate.now()));
					stmt.setDate(8, Date.valueOf(LocalDate.now()));

					stmt.executeUpdate();
				} else {
					logger.log(Level.SEVERE,
						"DAOImplementation::signUp: Exception email is already used");
					throw new EmailNotUniqueException("Email already used!");
				}
			} else {
				logger.log(Level.SEVERE,
					"DAOImplementation::signUp: Exception login is already used");
				throw new LoginExistingException("Login already used!");
			}
		} catch (SQLException ex) {
			logger.log(Level.SEVERE,
				"DAOImplementation::signUp: Exception connecting to database",
				ex.getMessage());
			throw new DatabaseException("Problem signing up user!");
		} finally {
			// Close connections
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (this.conexionBD != null) {
					this.disconnect();
				}
			} catch (SQLException ex) {
				logger.log(Level.SEVERE,
					"DAOImpentation::signUp: Exception closing the connection to database.",
					ex.getMessage());
			}
		}
	}

       
    
}
    
   