package clase;

import java.time.LocalDate;

public class Reserva {
    private int idReserva;
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;
    private String estadoReserva;
    private int cantidadHuesped;
    private int dniCliente;  // Cambié idCliente por dniCliente
    private int idHabitacion;
    private int idEstacionamiento;
    private int idPago;
    private String numeroPlaza;  // Se mantiene el campo para la plaza de estacionamiento
    private String metodoPago;

    // Constructor vacío
    public Reserva() {}

    // Constructor con parámetros
    public Reserva(LocalDate fechaIngreso, LocalDate fechaSalida, String estadoReserva, int cantidadHuesped, int dniCliente, int idHabitacion, int idEstacionamiento, int idPago) {
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.estadoReserva = estadoReserva;
        this.cantidadHuesped = cantidadHuesped;
        this.dniCliente = dniCliente;
        this.idHabitacion = idHabitacion;
        this.idEstacionamiento = idEstacionamiento;
        this.idPago = idPago;
    }

    // Getters y Setters
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public int getCantidadHuesped() {
        return cantidadHuesped;
    }

    public void setCantidadHuesped(int cantidadHuesped) {
        this.cantidadHuesped = cantidadHuesped;
    }

    public int getDniCliente() {
        return dniCliente;  // Ajusté para que devuelva el dniCliente
    }

    public void setDniCliente(int dniCliente) {  // Cambié el tipo de setter a int
        this.dniCliente = dniCliente;
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public int getIdEstacionamiento() {
        return idEstacionamiento;
    }

    public void setIdEstacionamiento(int idEstacionamiento) {
        this.idEstacionamiento = idEstacionamiento;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public String getNumeroPlaza() {
        return numeroPlaza;
    }

    public void setNumeroPlaza(String numeroPlaza) {
        this.numeroPlaza = numeroPlaza;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}
