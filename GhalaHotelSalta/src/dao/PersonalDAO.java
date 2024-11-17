package dao;

import bd.ConexionBD;
import clase.Personal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonalDAO {
    public boolean registrarPersonal(Personal personal) {
        String sql = "INSERT INTO Personal (numero_empleado, nombre, apellido, dni, telefono, turno, id_cargo) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, personal.getNumeroEmpleado());
            statement.setString(2, personal.getNombre());
            statement.setString(3, personal.getApellido());
            statement.setString(4, personal.getDni());
            statement.setString(5, personal.getTelefono());
            statement.setString(6, personal.getTurno());
            statement.setInt(7, personal.getIdCargo());

            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar personal: " + e.getMessage());
            return false;
        }
    }
}
