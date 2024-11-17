package clase;

public class Pago {
    private int idPago;
    private String metodoPago;

    public Pago(int idPago, String metodoPago) {
        this.idPago = idPago;
        this.metodoPago = metodoPago;
    }

    public int getIdPago() {
        return idPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }
}
