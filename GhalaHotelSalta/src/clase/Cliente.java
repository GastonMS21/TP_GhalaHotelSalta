package clase;

public class Cliente {
    private int idCliente;
    private String nombre;
    private String apellido;
    private String dni;
    private String numeroContacto;
    private String correo;

    // Constructor, getters y setters
    public Cliente(String nombre, String apellido, String dni, String numeroContacto, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.numeroContacto = numeroContacto;
        this.correo = correo;
    }

    // Getters y setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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

    public String getNumeroContacto() {
        return numeroContacto;
    }

    public void setNumeroContacto(String numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
