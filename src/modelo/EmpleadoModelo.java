package modelo;

/*
 * Modulo EmpleadoModelo
 * Es el modelo de la tabla empleado junto
 * con  sed_nombre y Sed_descripcion de la tabla cargo
 * Jose Bravo, John Contreras sep/2021
 */
public class EmpleadoModelo {
    
    int emp_cedula;
    String emp_apellidos;
    String emp_nombres;
    int id_cargo;
    int id_sede;
    
    // para JOIN con otra tabla
    
    String sed_nombre;
    String sed_descripcion;
    
    

    public EmpleadoModelo(int emp_cedula, String emp_apellidos, String emp_nombres, int id_cargo, int id_sede) {
        this.emp_cedula = emp_cedula;
        this.emp_apellidos = emp_apellidos;
        this.emp_nombres = emp_nombres;
        this.id_cargo = id_cargo;
        this.id_sede = id_sede;
    }

    public EmpleadoModelo(int emp_cedula, String emp_apellidos, String emp_nombres, int id_cargo, int id_sede, String sed_nombre, String sed_descripcion) {
        this.emp_cedula = emp_cedula;
        this.emp_apellidos = emp_apellidos;
        this.emp_nombres = emp_nombres;
        this.id_cargo = id_cargo;
        this.id_sede = id_sede;
        this.sed_nombre = sed_nombre;
        this.sed_descripcion = sed_descripcion;
    }
    
    public EmpleadoModelo(){}

    public String getSed_nombre() {
        return sed_nombre;
    }

    public void setSed_nombre(String sed_nombre) {
        this.sed_nombre = sed_nombre;
    }
    

    public int getEmp_cedula() {
        return emp_cedula;
    }

    public void setEmp_cedula(int emp_cedula) {
        this.emp_cedula = emp_cedula;
    }

    public String getEmp_apellidos() {
        return emp_apellidos;
    }

    public void setEmp_apellidos(String emp_apellidos) {
        this.emp_apellidos = emp_apellidos;
    }

    public String getEmp_nombres() {
        return emp_nombres;
    }

    public void setEmp_nombres(String emp_nombres) {
        this.emp_nombres = emp_nombres;
    }

    public int getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(int id_cargo) {
        this.id_cargo = id_cargo;
    }

    public int getId_sede() {
        return id_sede;
    }

    public void setId_sede(int id_sede) {
        this.id_sede = id_sede;
    }
    
    
    
    
    
    
}
