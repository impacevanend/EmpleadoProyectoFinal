/*
 * Programa	: Fachada.java
 * Fecha	: 25/08/2021
 * Objetivo	: Manejar la conexión a la BD
 * Programador	: Luis Yovany Romo Portilla
 */

package utils;
import java.sql.*;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
/**
 *
 * @author Admin
 */
public class Fachada {
    
    private static Connection con = null;
    
    public static Connection getConnection() throws ClassNotFoundException{
        try
        {
            if(con == null){
                //Determina cuando se termina el programa
                Runtime.getRuntime().addShutdownHook(new MiShDwnHook());
                
                //Recupera los parámetros de conexión del archivo 
                //jdbc.properties
                ResourceBundle rb = ResourceBundle.getBundle("utils.jdbc");
                String driver = rb.getString("driver");
                String url = rb.getString("url");
                String pwd = rb.getString("pwd");
                String usr = rb.getString("usr");
                
                Class.forName(driver);
                con = DriverManager.getConnection(url, usr, pwd);
            }
                         
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Error : " + 
                    ex.getMessage());
        }
        return con;
    }
    
    static class MiShDwnHook extends Thread{
        //Justo antes de finaliza el programa la JVM invocará
        //este método donde podemos cerra la conexión
        @Override
        public void run(){
            try{
                Connection con = Fachada.getConnection();
                con.close();                     
            }
            catch (SQLException ex){
                JOptionPane.showMessageDialog(null,"Error : " + 
                        ex.getMessage());
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null,"Error : " +  ex);
            }
        }
    }
}
