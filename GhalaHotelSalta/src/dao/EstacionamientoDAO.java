package dao;
import clase.Estacionamiento;
import bd.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public class EstacionamientoDAO {
    private Connection conexion;

    public EstacionamientoDAO() {
        this.conexion = ConexionBD.obtenerConexion();
    }

    // Método para obtener todos los lugares disponibles
    public List<Estacionamiento> obtenerLugaresDisponibles() {
        List<Estacionamiento> lugares = new ArrayList<>();
        String query = "SELECT numero_plaza, estado_plaza FROM estacionamiento WHERE estado_plaza = 'Disponible'";  // Suponiendo que "estado_plaza" es la columna que indica si un lugar está disponible

        try (PreparedStatement ps = conexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Estacionamiento estacionamiento = new Estacionamiento();
                estacionamiento.setNumeroPlaza(rs.getString("numero_plaza"));
                estacionamiento.setEstadoPlaza(rs.getString("estado_plaza"));
                lugares.add(estacionamiento);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener lugares disponibles: " + e.getMessage());
        }
        return lugares;
    }

    // Método para obtener todos los lugares ocupados
    public List<Estacionamiento> obtenerLugaresOcupados() {
        List<Estacionamiento> lugares = new ArrayList<>();
        String query = "SELECT numero_plaza, estado_plaza FROM estacionamiento WHERE estado_plaza = 'Ocupado'";  // Suponiendo que "estado_plaza" es la columna que indica si un lugar está disponible

        try (PreparedStatement ps = conexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Estacionamiento estacionamiento = new Estacionamiento();
                estacionamiento.setNumeroPlaza(rs.getString("numero_plaza"));
                estacionamiento.setEstadoPlaza(rs.getString("estado_plaza"));
                lugares.add(estacionamiento);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener lugares disponibles: " + e.getMessage());
        }
        return lugares;
    }

    //Metodo listar
    public List<Estacionamiento> obtenerTodosLosLugares() {
        List<Estacionamiento> lugares = new ArrayList<>();
        String query = "SELECT numero_plaza, estado_plaza FROM estacionamiento"; // Sin filtro para traer todos los lugares

        try (PreparedStatement ps = conexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Estacionamiento estacionamiento = new Estacionamiento();
                estacionamiento.setNumeroPlaza(rs.getString("numero_plaza"));
                estacionamiento.setEstadoPlaza(rs.getString("estado_plaza"));
                lugares.add(estacionamiento);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los lugares del estacionamiento: " + e.getMessage());
        }
        return lugares;
    }


    // Método para cambiar el estado de la plaza en la tabla Estacionamiento
    public boolean cambiarEstadoPlaza(String numeroPlaza, String nuevoEstado) {
        String query = "UPDATE Estacionamiento SET estado_plaza = ? WHERE numero_plaza = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, nuevoEstado); // Establecer el nuevo estado (disponible)
            stmt.setString(2, numeroPlaza); // Establecer el número de plaza
            int filasActualizadas = stmt.executeUpdate();

            return filasActualizadas > 0; // Si se actualizó alguna fila, retorna true
        } catch (SQLException e) {
            System.err.println("Error al cambiar el estado de la plaza: " + e.getMessage());
            return false;
        }
    }

    // Método para verificar si la plaza está ocupada

    public boolean esPlazaOcupada(String numeroPlaza) {
        String query = "SELECT estado_plaza FROM Estacionamiento WHERE numero_plaza = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, numeroPlaza);  // Usar el número de plaza que ingresó el usuario
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String estadoPlaza = rs.getString("estado_plaza");  // Obtener el estado de la plaza
                //System.out.println("Estado de la plaza: " + estadoPlaza);  // Depuración adicional
                return "ocupado".equalsIgnoreCase(estadoPlaza);  // Verificar si está ocupado
            } else {
                System.out.println("No se encontró la plaza con el número: " + numeroPlaza);  // Depuración en caso de que no se encuentre la plaza
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar la plaza: " + e.getMessage());
        }
        return false;  // Si no se encuentra la plaza o el estado es otro, se considera libre
    }

    //----------------------------------
    public boolean actualizarEstadoPlaza(String numeroPlaza, String nuevoEstado) {
        String query = "UPDATE Estacionamiento SET estado_plaza = ? WHERE numero_plaza = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, nuevoEstado);
            stmt.setString(2, numeroPlaza);

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0; // Devuelve true si se actualizó correctamente
        } catch (SQLException e) {
            System.err.println("Error al actualizar el estado de la plaza: " + e.getMessage());
            return false;
        }
    }

//--------------------------------------------



}

