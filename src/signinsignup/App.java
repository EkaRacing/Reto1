/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signinsignup;

import java.util.Scanner;

/**
 *
 * @author usuario
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
        String entradaTeclado;
        String login;
        String email;
        String password;
        String fullName;
        String status;
        String privilege;
        User user=new User();
        
        DAO dao=new DAOFactory().createDAOImplementation();
               
        do{
        do{
         Thread.sleep(1500);
         System.out.println ("Por favor introduzca una opción:\nA.Sign up\nB.Sign in\nS.Salir");
         entradaTeclado = entradaEscaner.nextLine ().toUpperCase(); //Invocamos un método sobre un objeto Scanner
        }while(!(entradaTeclado.equals("A")|| entradaTeclado.equals("B") || entradaTeclado.equals("S")));
        
        switch(entradaTeclado){
             case "A":
             //SIGN UP
             System.out.println ("Por favor introduzca el login: ");
             login= entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
             System.out.println ("Por favor introduzca el nombre completo: ");
             fullName= entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
             System.out.println ("Por favor introduzca el email: ");
             email= entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
            do{ 
             System.out.println ("Por favor introduzca el estado: (ENABLED/DISABLED) ");
             status= entradaEscaner.nextLine ().toUpperCase(); //Invocamos un método sobre un objeto Scanner
            }while(!(status.equals("ENABLED")||status.equals("DISABLED")));
            do{  
            System.out.println ("Por favor introduzca el privilegio: (USER/ADMIN) ");
             privilege= entradaEscaner.nextLine ().toUpperCase(); //Invocamos un método sobre un objeto Scanner
           }while(!(privilege.equals("USER")||privilege.equals("ADMIN")));
             System.out.println ("Por favor introduzca la contraseña: ");
             password= entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
             user=new User();
             user.setLogin(login);
             user.setFullName(fullName);
             user.setEmail(email);
             user.setStatus(UserStatus.valueOf(status));
             user.setPrivilege(UserPrivilege.valueOf(privilege));
             user.setPassword(password);
             dao.signUp(user);
             break;  
                 
             
             case "B":
             //SIGN IN
             System.out.println ("Por favor introduzca el login: ");
             login= entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
             System.out.println ("Por favor introduzca la contraseña: ");
             password= entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
             user=new User();
             user.setLogin(login);
             user.setPassword(password);
             User nuevo;
             nuevo=dao.signIn(user);
             System.out.println(nuevo.getFullName()+", has iniciado la sesión");
             break;   
             
             case "S":
                 System.out.println("HASTA LUEGO!");
                 break;
             
         }
        }while(!(entradaTeclado.equals("S")));
    }
    
   
    
}
