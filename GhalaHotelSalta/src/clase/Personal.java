package clase;

public class Personal {
    private int idPersonal;
    private String numeroEmpleado;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String turno;
    private int idCargo;

    // Constructor vacío
    public Personal() {}

    // Constructor con parámetros
    public Personal(String numeroEmpleado, String nombre, String apellido, String dni, String telefono, String turno, int idCargo) {
        this.numeroEmpleado = numeroEmpleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.turno = turno;
        this.idCargo = idCargo;
    }

    // Getters y Setters
    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }
}
