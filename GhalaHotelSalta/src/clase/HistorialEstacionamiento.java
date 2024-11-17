package clase;

import dao.HistorialEstacionamientoDAO;

import java.time.LocalDateTime;

public class HistorialEstacionamiento {
    private int idHistorialEst; // Identificador único para cada historial
    private int dniCliente; // DNI del cliente
    private String numeroPlaza; // Número de la plaza
    private LocalDateTime horaInicio; // Hora de ingreso al estacionamiento
    private LocalDateTime horaFin; // Hora de salida del estacionamiento

    // Constructor
    public HistorialEstacionamiento(int idHistorialEst, int dniCliente, String numeroPlaza, LocalDateTime horaInicio, LocalDateTime horaFin) {
        this.idHistorialEst = idHistorialEst;
        this.dniCliente = dniCliente;
        this.numeroPlaza = numeroPlaza;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Constructor
    public HistorialEstacionamiento(int dniCliente, String numeroPlaza, LocalDateTime horaInicio) {
        this.dniCliente = dniCliente;
        this.numeroPlaza = numeroPlaza;
        this.horaInicio = horaInicio;
    }

    // Getters y Setters
    public int getIdHistorialEst() {
        return idHistorialEst;
    }

    public void setIdHistorialEst(int idHistorialEst) {
        this.idHistorialEst = idHistorialEst;
    }

    public int getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(int dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getNumeroPlaza() {
        return numeroPlaza;
    }

    public void setNumeroPlaza(String numeroPlaza) {
        this.numeroPlaza = numeroPlaza;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDateTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalDateTime horaFin) {
        this.horaFin = horaFin;
    }



    // Método para mostrar la información del historial de estacionamiento
    @Override
    public String toString() {
        return "HistorialEstacionamiento{" +
                "idHistorialEst=" + idHistorialEst +
                ", dniCliente=" + dniCliente +
                ", numeroPlaza='" + numeroPlaza + '\'' +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                '}';
    }
}
