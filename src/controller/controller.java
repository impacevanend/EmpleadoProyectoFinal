/*
 * Modulo Controller
 * Permie la conexion entre la vista y el DAO
 * 
 * Jose Bravo, John Contreras sep/2021
 */
package controller;

import access.EmpleadoDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import modelo.EmpleadoModelo;
import vista.vistaEmpleado;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JJ
 */
public class controller implements ActionListener{
    
    
    vistaEmpleado vista = null;
    EmpleadoModelo empleado = null;
    EmpleadoDAO dao = new EmpleadoDAO();
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel modelo1 = new DefaultTableModel();
    
    public controller(vistaEmpleado vista, EmpleadoModelo empleado){
        
       this.vista = vista;
       this.empleado= empleado;
       this.vista.btnActualizar.addActionListener(this);
       this.vista.btnNuevo.addActionListener(this);
       this.vista.btnGuardar.addActionListener(this);
       this.vista.btnEliminar.addActionListener(this);
       this.vista.txtBuscar.addActionListener(this);
       listar(vista.tablaEmpleado,1);
       limpiarCajas();
        
        
    }
    
 /*--------------------------------------------------------------------
 *   
 *     Action performed para escuchar la vista
 * 
 ---------------------------------------------------------------------*/    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == vista.btnActualizar){
            actualizar();
            System.out.println(vista.txtApellidos.getText());
        }else if (e.getSource() == vista.btnNuevo){
            limpiarCajas();
        }else if (e.getSource() == vista.btnEliminar){
            eliminar();
        }else if (e.getSource() == vista.btnGuardar){
            guardar();
        }else if (e.getSource() == vista.txtBuscar){
            System.out.println("PROBANDOO");
            busqueda(); 
        }else if (e.getSource() == vista.btnNuevo){
            limpiarCajas();
        }
        
    }
    
 /*--------------------------------------------------------------------
 *   
 *    Obtiene la lista de empleados de la tabla empleado
 * 
 ---------------------------------------------------------------------*/    
    
    
    public void listar(JTable tabla, int type){
     ArrayList<EmpleadoModelo> empleado =null;
     String texto ="";
     modelo=(DefaultTableModel)tabla.getModel();
     
     if (type == 1)
         empleado = dao.obtenerEmpleados();
     else{
         texto = vista.txtBuscar.getText();
         empleado = dao.FullText(texto);
     }  
      
     System.out.println(empleado.size());
     Object[]object = new Object[4];
     
    for (int i =0; i <empleado.size(); i++){       
       object[0]=empleado.get(i).getEmp_cedula();
       object[1]=empleado.get(i).getEmp_apellidos();
       object[2]=empleado.get(i).getEmp_nombres();
       object[3]=empleado.get(i).getSed_nombre();
       modelo.addRow(object);   
     }       
       vista.tablaEmpleado.setModel(modelo);                
    }   
    
    public void limpiarListadoTabla(JTable tabla){
        System.out.println("BORRANDOOO");
        modelo=(DefaultTableModel)tabla.getModel();
        for(int i=modelo.getRowCount()-1; i>=0 ; i--){
            modelo.removeRow(i);
        }
    }
    
 /*--------------------------------------------------------------------
 *   
 *     Limpia los campos en la interfaz grafica
 *     No recibe parametros
 ---------------------------------------------------------------------*/    
    
    
     public void limpiarCajas(){
        
        vista.txtApellidos.setText("");
        vista.txtCedula.setText("");
        vista.txtApellidos.setText("");
        vista.txtNombre.setText("");
        vista.cbCargo.setSelectedItem(null);
        vista.cbSede.setSelectedItem(null);
        
        
    }
    
     /*--------------------------------------------------------------------
    *   
    *     Actualiza los campos seleccionados o ingresados en la interfaz grafica
    *     no recibe parametros
    ---------------------------------------------------------------------*/   
     
     
    
    public void actualizar(){
        //updateEmpleado(EmpleadoModelo empleado)
    if (!vista.txtCedula.getText().isEmpty() && !Character.isDigit(vista.txtCedula.getText().charAt(0)))
            JOptionPane.showMessageDialog(vista, "El Campo cedula debe ser numerico"); 
    else{    
    if (vista.txtApellidos.getText().isEmpty() ||  vista.txtNombre.getText().isEmpty() || vista.txtCedula.getText().isEmpty() || vista.cbSede.getSelectedItem() == null || vista.cbCargo.getSelectedItem() == null  )        
                JOptionPane.showMessageDialog(vista, "Debe Llenar Todos los campos");           
    else{    
        String apellidos = vista.txtApellidos.getText();
        String nombre = vista.txtNombre.getText();
        int cedula =  Integer.parseInt(vista.txtCedula.getText());        
        int seleccionado = vista.cbCargo.getSelectedIndex();
        String cargo = vista.cbCargo.getItemAt(seleccionado);        
        int id_cargo= 0;
        switch(cargo){
            case "Médico":
                 id_cargo = 1;
                 break;
            case "Residente":
                id_cargo = 2;
                 break;
                 
            case "Enfermera titulada":
                id_cargo = 3;
                break;
            case "Enfermera en práctica":
                id_cargo = 4;
                break;
            case "Defensor del paciente":
                id_cargo = 5;
                break;
            case "Auxiliares de enfermería":
                id_cargo = 6;
                break;
            case "Fisioterapeuta":
                id_cargo = 7;
                break;
            case "Asesor":
                id_cargo = 3;
                break;            
        }
        
        
        int seleccionado1 = vista.cbSede.getSelectedIndex();
        String sede = vista.cbSede.getItemAt(seleccionado1);
        System.out.println("OJOOOOOOOOOOO");
        
        if (vista.cbSede.getSelectedItem() != null ){            
            int id_sede= 0;
            switch(sede){
                case "Norte":
                     id_sede = 101;
                    break;
                case "Sur":
                    id_sede = 102;
                    break;                 
                case "Libertadores":
                    id_sede = 103;
                    break;
                case "Champagna":
                    id_sede = 104;
                    break;
                case "Cambulos":
                    id_sede = 105;
                    break;           
            }
 
            EmpleadoModelo objEmpleado = new EmpleadoModelo(cedula,apellidos,nombre,id_cargo,id_sede);
            dao.updateEmpleado(objEmpleado);
            limpiarCajas();
            limpiarListadoTabla(vista.tablaEmpleado);
            JOptionPane.showMessageDialog(vista, "Registro Actualizado");
            listar(vista.tablaEmpleado,1);
        }else{
            JOptionPane.showMessageDialog(vista, "Debe selccionar una sede");
        }
    }  
   }
 }  
    
    
     /*--------------------------------------------------------------------
    *   
    *     Elimina un registro de la base de datos partiendo de la cedula
    *     el campo cedula es obligatorio y debe ser numerico
    ---------------------------------------------------------------------*/  
    
      public void eliminar(){
        //deleteEmpleado(int cedula)
        if (!vista.txtCedula.getText().isEmpty() && !Character.isDigit(vista.txtCedula.getText().charAt(0)))
            JOptionPane.showMessageDialog(vista, "El Campo cedula debe ser numerico"); 
        else{
        
        if (vista.txtCedula.getText().isEmpty())
            JOptionPane.showMessageDialog(vista, "Debe Seleccionar almenos una cedula");
        else{    
            int cedula =  Integer.parseInt(vista.txtCedula.getText());
            dao.deleteEmpleado(cedula);
            limpiarListadoTabla(vista.tablaEmpleado);
            limpiarCajas();
            listar(vista.tablaEmpleado,1);
            JOptionPane.showMessageDialog(vista, "Registro Eliminado con Exito");
            }
        }
    }
     
 /*--------------------------------------------------------------------
 *   
 *     Guarda un registro en la base de datos
 * 
 ---------------------------------------------------------------------*/    
      
      
        public void guardar(){
        //saveEmpleado(EmpleadoModelo empleado)
        
        if (!vista.txtCedula.getText().isEmpty() && !Character.isDigit(vista.txtCedula.getText().charAt(0)))
            JOptionPane.showMessageDialog(vista, "El Campo cedula debe ser numerico"); 
        else{
        
        if (vista.txtApellidos.getText().isEmpty() ||  vista.txtNombre.getText().isEmpty() || vista.txtCedula.getText().isEmpty() || vista.cbSede.getSelectedItem() == null || vista.cbCargo.getSelectedItem() == null  )
              JOptionPane.showMessageDialog(vista, "Debe Llenar Todos los campos");
        else {
            
                
            String apellidos = vista.txtApellidos.getText();
            System.out.println(apellidos);
            String nombre = vista.txtNombre.getText();
            int cedula =  Integer.parseInt(vista.txtCedula.getText());
            
          if (dao.checkIfExists(cedula)){
            JOptionPane.showMessageDialog(vista, "El empleado ya Existe"); 
          }else{
            int seleccionado = vista.cbCargo.getSelectedIndex();
            String cargo = vista.cbCargo.getItemAt(seleccionado);
            int id_cargo= 0;
            switch(cargo){
                case "Médico":
                     id_cargo = 1;
                     break;
                case "Residente":
                    id_cargo = 2;
                     break;
                 
                case "Enfermera titulada":
                   id_cargo = 3;
                    break;
                case "Enfermera en práctica":
                   id_cargo = 4;
                    break;
                case "Defensor del paciente":
                    id_cargo = 5;
                    break;
                case "Auxiliares de enfermería":
                    id_cargo = 6;
                    break;
                case "Fisioterapeuta":
                    id_cargo = 7;
                    break;
                case "Asesor":
                    id_cargo = 3;
                    break;            
            }
        
        
            int seleccionado1 = vista.cbSede.getSelectedIndex();
            String sede = vista.cbSede.getItemAt(seleccionado1);
            int id_sede= 0;
            switch(sede){
                case "Norte":
                     id_sede = 101;
                     break;
                case "Sur":
                    id_sede = 102;
                     break;                 
                case "Libertadores":
                    id_sede = 103;
                    break;
                case "Champagna":
                    id_sede = 104;
                    break;
                case "Cambulos":
                    id_sede = 105;
                    break;           
            }
                
            EmpleadoModelo objEmpleado = new EmpleadoModelo(cedula,apellidos,nombre,id_cargo,id_sede);
            dao.saveEmpleado(objEmpleado);
            limpiarListadoTabla(vista.tablaEmpleado);
            limpiarCajas();
            listar(vista.tablaEmpleado,1);
          }          
        }
        
      }
    }  
    
    
 /*--------------------------------------------------------------------
 *   
 *     Realiza la busqueda como fulltext, recibe el valor del campo busqueda
 *     llama listar vista para traer los resultados con el parametro 2 
 ---------------------------------------------------------------------*/      
        
        
    public void busqueda(){
        

        limpiarListadoTabla(vista.tablaEmpleado);
        listar(vista.tablaEmpleado,2);
               
        
    } 
    
    
}
