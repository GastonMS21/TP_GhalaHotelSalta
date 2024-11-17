package dao;

import bd.ConexionBD;
import clase.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAO {

    public boolean registrarCliente(Cliente cliente) {
        String sql = "INSERT INTO Cliente (nombre, apellido, dni, numero_contacto, correo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getApellido());
            statement.setString(3, cliente.getDni());
            statement.setString(4, cliente.getNumeroContacto());
            statement.setString(5, cliente.getCorreo());

            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas > 0; // True si se registró el cliente exitosamente

        } catch (SQLException e) {
            System.out.println("Error al registrar el cliente: " + e.getMessage());
            return false;
        }
    }
}
