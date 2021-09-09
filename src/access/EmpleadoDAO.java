/*
 * Modulo Dao permite las operaciones con la base de datos 
 * Insertar, Borrar, Actualizar 
 * Jose Bravo, John Contreras sep/2021
 */
package access;

import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;


import modelo.EmpleadoModelo;
import utils.Fachada;


/**
 *
 * @author JJ
 */
public class EmpleadoDAO {
    Connection conn = null;    
        
 
/*--------------------------------------------------------------------
 *   
 *     Metodo que revisa si un usuario ya existe
 * 
 ---------------------------------------------------------------------*/    
    
    
    public boolean checkIfExists(int cedula){
        ArrayList<EmpleadoModelo> listadoEmpleado = new ArrayList<>();
        
        try {
            
            try {
                conn = Fachada.getConnection();
                System.out.println("Conectado");                
            }catch (ClassNotFoundException ex) {
                Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            String sql = "SELECT emp_cedula FROM empleado WHERE emp_cedula = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, cedula);
                 
            ResultSet result = statement.executeQuery();
            EmpleadoModelo empleado = null;
            while (result.next()) {
                
                empleado = new EmpleadoModelo();
                empleado.setEmp_cedula(result.getInt("emp_cedula"));    
                listadoEmpleado.add(empleado);                
            }
                   
        }catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try{
        if (listadoEmpleado.get(0).getEmp_cedula() == cedula)       
            return true;
        }catch (IndexOutOfBoundsException execption){}     
            return false;
    }  

    
    
    public ArrayList<EmpleadoModelo> obtenerEmpleados(){
        ArrayList<EmpleadoModelo> listadolEmpleados = new ArrayList<>();
        
        try {
            
            try {
                conn = Fachada.getConnection();
                System.out.println("Conectado");                
            }catch (ClassNotFoundException ex) {
                Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            Statement statement = conn.createStatement();
            String sql = "SELECT empleado.emp_cedula ,empleado.emp_apellidos ,"
                    + "empleado.emp_nombres ,cargo.sed_nombre "
                    + " FROM empleado JOIN cargo on empleado.id_cargo = cargo.car_id";            
            
            ResultSet result = statement.executeQuery(sql);
            EmpleadoModelo empleado = null;
            while (result.next()) {
                
                empleado = new EmpleadoModelo();
                empleado.setEmp_cedula(result.getInt("emp_cedula"));
                empleado.setEmp_apellidos(result.getString("emp_apellidos"));
                empleado.setEmp_nombres(result.getString("emp_nombres"));
                empleado.setSed_nombre(result.getString("sed_nombre"));
                // empleado.setId_sede(result.getInt("id_sede"));
                // System.out.println(result.getString("emp_nombres")); 
                listadolEmpleados.add(empleado);                
            }
                   
        }catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    
       
       return listadolEmpleados;
    }  
        
    
    
    
/*--------------------------------------------------------------------
 *   
 *     Metodo para Borrar empleados a partir de la cedula
 * 
 ---------------------------------------------------------------------*/    

public void  deleteEmpleado(int cedula){
    try {
        Connection conn = null;
        
        try {
            conn = Fachada.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        
        String sql = "DELETE FROM empleado WHERE emp_cedula = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, cedula);
        int resultado = statement.executeUpdate();
        if (resultado>0)
            System.out.println("Eliminado con exito");
        else
            System.out.println("No se Elimino ningun registro");
                
    } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
    }    
}    

/*--------------------------------------------------------------------
 *   
 *     Metodo para agregar un empleado
 * 
 ---------------------------------------------------------------------*/ 


    
public void  saveEmpleado(EmpleadoModelo empleado){
    Connection conn = null;

    try {
        conn = Fachada.getConnection();

    } catch (ClassNotFoundException e) {
      
        e.printStackTrace();
    }
    
    
        try {
            String sql = "INSERT INTO empleado VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, empleado.getEmp_cedula());
            statement.setString(2, empleado.getEmp_apellidos());
            statement.setString(3, empleado.getEmp_nombres());
            statement.setInt(4, empleado.getId_cargo());
            statement.setInt(5,empleado.getId_sede());
            
            int Resultado = statement.executeUpdate();
            
            if (Resultado>0)
                System.out.println("Insertado con exito");
            else
                System.out.println("No se Inserto ningun registro");
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    

  }    


/*--------------------------------------------------------------------
 *   
 *     Metodo para Actualizar empleados a partir de la cedula
 * 
 ---------------------------------------------------------------------*/   

public void  updateEmpleado(EmpleadoModelo empleado){
    try {
        Connection conn = null;
        
        try {
            conn = Fachada.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        //emp_apellidos,emp_nombres,id_cargo,id_sede
        String sql = "UPDATE empleado SET emp_apellidos =?,emp_nombres=?,"
                + "id_cargo=?, id_sede =?  WHERE emp_cedula = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, empleado.getEmp_apellidos());
        statement.setString(2, empleado.getEmp_nombres());
        statement.setInt(3, empleado.getId_cargo());
        statement.setInt(4, empleado.getId_sede());
        statement.setInt(5, empleado.getEmp_cedula());
        int resultado = statement.executeUpdate();
        if (resultado>0)
            System.out.println("Actualizado con exito");
        else
            System.out.println("No se actualizo ningún registro");
                
    } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
    }    
}  

/*--------------------------------------------------------------------
 *   
 *     Metodo para el FullText
 * 
 ---------------------------------------------------------------------*/  

public ArrayList<EmpleadoModelo>  FullText(String valor){
    ArrayList<EmpleadoModelo> listadolEmpleados = new ArrayList<>();
    
    try {
            
            try {
                conn = Fachada.getConnection();
                System.out.println("Conectado");                
            }catch (ClassNotFoundException ex) {
                Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            Statement statement = conn.createStatement();
            String sql = "SELECT empleado.emp_cedula ,empleado.emp_apellidos ,"
                    + "empleado.emp_nombres ,cargo.sed_nombre "
                    + " FROM empleado JOIN cargo on empleado.id_cargo = cargo.car_id"
                    + " WHERE emp_nombres LIKE '%" +valor+ "%'";
            System.out.println(sql);
            ResultSet result = statement.executeQuery(sql);
            EmpleadoModelo empleado = null;
            while (result.next()) {
                
                empleado = new EmpleadoModelo();
                empleado.setEmp_cedula(result.getInt("emp_cedula"));
                empleado.setEmp_apellidos(result.getString("emp_apellidos"));
                empleado.setEmp_nombres(result.getString("emp_nombres"));
                empleado.setSed_nombre(result.getString("sed_nombre"));
                // empleado.setId_sede(result.getInt("id_sede"));
                // System.out.println(result.getString("emp_nombres")); 
                listadolEmpleados.add(empleado);                
            }
                   
        }catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    
       
       return listadolEmpleados;   
}


}