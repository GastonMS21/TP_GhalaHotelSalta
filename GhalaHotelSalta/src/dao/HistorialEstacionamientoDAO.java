package dao;

import bd.ConexionBD;
import clase.HistorialEstacionamiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class HistorialEstacionamientoDAO {
    private Connection conexion;

    public HistorialEstacionamientoDAO() {
        this.conexion = ConexionBD.obtenerConexion();
    }

    // Método para registrar estacionamiento en historial y actualizar estado de plaza
    public boolean registrarEstacionamiento(int dniCliente, String numeroPlaza) {
        String verificarEstadoQuery = "SELECT estado_plaza FROM Estacionamiento WHERE numero_plaza = ?";
        String insertarHistorialQuery = "INSERT INTO Historial_estacionamiento (dni_cliente, numero_plaza, hora_inicio) VALUES (?, ?, NOW())";
        String actualizarEstadoQuery = "UPDATE Estacionamiento SET estado_plaza = 'Ocupado' WHERE numero_plaza = ?";

        try (PreparedStatement verificarEstadoStmt = conexion.prepareStatement(verificarEstadoQuery);
             PreparedStatement insertarHistorialStmt = conexion.prepareStatement(insertarHistorialQuery);
             PreparedStatement actualizarEstadoStmt = conexion.prepareStatement(actualizarEstadoQuery)) {

            // Verificar si la plaza está disponible
            verificarEstadoStmt.setString(1, numeroPlaza);
            try (ResultSet rs = verificarEstadoStmt.executeQuery()) {
                if (rs.next()) {
                    String estadoPlaza = rs.getString("estado_plaza");
                    if (!estadoPlaza.equals("Disponible")) {
                        System.out.println("La plaza " + numeroPlaza + " está ocupada. No se puede registrar.");
                        return false;
                    }
                } else {
                    System.out.println("La plaza " + numeroPlaza + " no existe. Verifique el número ingresado.");
                    return false;
                }
            }

            // Registrar el estacionamiento en el historial
            insertarHistorialStmt.setInt(1, dniCliente);
            insertarHistorialStmt.setString(2, numeroPlaza);
            int filasInsertadas = insertarHistorialStmt.executeUpdate();

            // Actualizar el estado de la plaza
            actualizarEstadoStmt.setString(1, numeroPlaza);
            int filasActualizadas = actualizarEstadoStmt.executeUpdate();

            // Verificar que ambas operaciones fueron exitosas
            return filasInsertadas > 0 && filasActualizadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar el estacionamiento: " + e.getMessage());
            return false;
        }
    }

    //-----------------
    public boolean liberarPlaza(String numeroPlaza) {
        String queryHistorial = "UPDATE Historial_estacionamiento SET hora_fin = NOW() WHERE numero_plaza = ? AND hora_fin IS NULL";
        String queryPlaza = "UPDATE Estacionamiento SET estado_plaza = 'Disponible' WHERE numero_plaza = ?";

        try (PreparedStatement stmtHistorial = conexion.prepareStatement(queryHistorial);
             PreparedStatement stmtPlaza = conexion.prepareStatement(queryPlaza)) {

            // Establecer el número de plaza como String
            stmtHistorial.setString(1, numeroPlaza);
            stmtPlaza.setString(1, numeroPlaza);

            // Ejecutar la actualización en el historial (hora final)
            int filasActualizadasHistorial = stmtHistorial.executeUpdate();
            // Ejecutar la actualización en la plaza (estado disponible)
            int filasActualizadasPlaza = stmtPlaza.executeUpdate();

            // Si se actualizó el historial y la plaza, significa que la operación fue exitosa
            return filasActualizadasHistorial > 0 && filasActualizadasPlaza > 0;

        } catch (SQLException e) {
            System.err.println("Error al liberar la plaza: " + e.getMessage());
            return false; // Si ocurre algún error, devolver false
        }
    }


    //---------------
    // Método para obtener el último historial de estacionamiento por número de plaza
    public HistorialEstacionamiento obtenerUltimoHistorialPorPlaza(String numeroPlaza) {
        String query = "SELECT * FROM Historial_estacionamiento WHERE numero_plaza = ? AND hora_fin IS NULL ORDER BY hora_inicio DESC LIMIT 1";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, numeroPlaza);  // Usar el número de plaza como parámetro
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Crear el objeto HistorialEstacionamiento con los datos obtenidos
                int dniCliente = rs.getInt("dni_cliente");
                LocalDateTime horaInicio = rs.getTimestamp("hora_inicio").toLocalDateTime();

                return new HistorialEstacionamiento(dniCliente, numeroPlaza, horaInicio);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el historial de la plaza: " + e.getMessage());
        }
        return null;  // Si no se encuentra el historial, retornar null
    }

    //---------------------------------
    public List<HistorialEstacionamiento> obtenerHistorialConHoraFin() {
        String query = "SELECT id_historialest, dni_cliente, numero_plaza, hora_inicio, hora_fin " +
                "FROM Historial_estacionamiento " +
                "WHERE hora_fin IS NOT NULL";

        List<HistorialEstacionamiento> historialList = new ArrayList<>();

        try (PreparedStatement stmt = conexion.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idHistorialEst = rs.getInt("id_historialest");
                int dniCliente = rs.getInt("dni_cliente");
                String numeroPlaza = rs.getString("numero_plaza");
                LocalDateTime horaInicio = rs.getTimestamp("hora_inicio").toLocalDateTime();
                LocalDateTime horaFin = rs.getTimestamp("hora_fin").toLocalDateTime();

                HistorialEstacionamiento historial = new HistorialEstacionamiento(idHistorialEst, dniCliente, numeroPlaza, horaInicio, horaFin);
                historialList.add(historial);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el historial con hora final: " + e.getMessage());
        }

        return historialList;
    }
    //--------------------------------------------------
    public boolean actualizarPlazaEnHistorial(String plazaActual, String nuevaPlaza) {
        String query = "UPDATE Historial_estacionamiento SET numero_plaza = ? WHERE numero_plaza = ? AND hora_fin IS NULL";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, nuevaPlaza);
            stmt.setString(2, plazaActual);

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0; // Devuelve true si se actualizó correctamente
        } catch (SQLException e) {
            System.err.println("Error al actualizar la plaza en el historial: " + e.getMessage());
            return false;
        }
    }

    //------------------------------------------
    public boolean actualizarDniCliente(String numeroPlaza, int nuevoDni) {
        String query = "UPDATE Historial_estacionamiento SET dni_cliente = ? WHERE numero_plaza = ? AND hora_fin IS NULL";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, nuevoDni); // Nuevo DNI
            stmt.setString(2, numeroPlaza); // Número de plaza
            int filasActualizadas = stmt.executeUpdate();

            return filasActualizadas > 0; // Retornar true si se actualizó al menos una fila
        } catch (SQLException e) {
            System.err.println("Error al actualizar el cliente: " + e.getMessage());
            return false; // Retornar false si ocurre un error
        }
    }


}
