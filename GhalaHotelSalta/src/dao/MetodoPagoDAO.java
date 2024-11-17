package dao;

import bd.ConexionBD;
import clase.Pago;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MetodoPagoDAO {
    private Connection conexion;

    public MetodoPagoDAO() {
        this.conexion = ConexionBD.obtenerConexion();
    }

    public List<Pago> obtenerMetodosPago() {
        List<Pago> metodosPago = new ArrayList<>();
        String query = "SELECT id_pago, metodo_pago FROM Pago"; // Ajusta según tu tabla

        try (PreparedStatement ps = conexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idPago = rs.getInt("id_pago");
                String metodoPago = rs.getString("metodo_pago");
                metodosPago.add(new Pago(idPago, metodoPago));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener métodos de pago: " + e.getMessage());
        }

        return metodosPago;
    }
}
