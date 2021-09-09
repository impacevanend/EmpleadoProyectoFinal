/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import access.EmpleadoDAO;
import controller.controller;
import java.util.ArrayList;
import modelo.EmpleadoModelo;
import vista.vistaEmpleado;

/*
 * Modulo Principal
 * Permite la ejecucion del programa
 * 
 * Jose Bravo, John Contreras sep/2021
 */
public class MVCPrincipal {
    
    public static void main(String[] args) {
    
    vistaEmpleado view = new vistaEmpleado();
    EmpleadoModelo empleado = new EmpleadoModelo();
    controller ctr = new controller (view, empleado);
  
         
    }
    
}
