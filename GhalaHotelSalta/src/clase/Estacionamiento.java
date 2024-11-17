package clase;

public class Estacionamiento {
    private int idEstacionamiento;
    private String numeroPlaza;
    private String estadoPlaza;
    private double precioHora;
    private double precioDia;

    // Constructor
    public Estacionamiento() {
        // Constructor vacío
    }
    public Estacionamiento(String numeroPlaza, String estadoPlaza, double precioHora, double precioDia) {
        this.numeroPlaza = numeroPlaza;
        this.estadoPlaza = estadoPlaza;
        this.precioHora = precioHora;
        this.precioDia = precioDia;
    }


    // Getters y Setters
    public String getNumeroPlaza() {
        return numeroPlaza;
    }

    public void setNumeroPlaza(String numeroPlaza) {
        this.numeroPlaza = numeroPlaza;
    }

    public String getEstadoPlaza() {
        return estadoPlaza;
    }

    public void setEstadoPlaza(String estadoPlaza) {
        this.estadoPlaza = estadoPlaza;
    }

    public double getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(double precioHora) {
        this.precioHora = precioHora;
    }

    public double getPrecioDia() {
        return precioDia;
    }

    public void setPrecioDia(double precioDia) {
        this.precioDia = precioDia;
    }
}
