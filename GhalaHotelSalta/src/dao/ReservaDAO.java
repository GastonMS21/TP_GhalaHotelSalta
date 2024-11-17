package dao;

import clase.Reserva;
import bd.ConexionBD;
import java.util.Scanner;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
public class ReservaDAO {
    private Connection conexion;

    public ReservaDAO() {
        this.conexion = ConexionBD.obtenerConexion();
    }

    public boolean registrarReserva(Reserva reserva) {
        // Consultas SQL
        String sqlBuscarEstacionamiento = "SELECT id_estacionamiento FROM Estacionamiento WHERE estado_plaza = 'Disponible' LIMIT 1";
        String sqlActualizarEstacionamiento = "UPDATE Estacionamiento SET estado_plaza = 'Ocupado' WHERE id_estacionamiento = ?";
        String sqlInsertReserva = "INSERT INTO Reserva (fecha_ingreso, fecha_salida, estado_reserva, cantidad_huesped, dni, id_habitacion, id_estacionamiento, id_pago) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlInsertHistorial = "INSERT INTO Historial_Estacionamiento (dni_cliente, numero_plaza, hora_inicio, hora_fin) VALUES (?, ?, ?, ?)";

        try (PreparedStatement buscarEstacionamientoStmt = conexion.prepareStatement(sqlBuscarEstacionamiento);
             PreparedStatement actualizarEstacionamientoStmt = conexion.prepareStatement(sqlActualizarEstacionamiento);
             PreparedStatement insertReservaStmt = conexion.prepareStatement(sqlInsertReserva, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertHistorialStmt = conexion.prepareStatement(sqlInsertHistorial)) {

            // Validar que las fechas ingresadas sean correctas
            if (!esFechaValida(reserva.getFechaIngreso()) || !esFechaValida(reserva.getFechaSalida())) {
                System.out.println("Error: Una o ambas fechas ingresadas no son válidas. Por favor, ingrese fechas correctas.");
                return false;
            }

            // Verificar que la fecha de ingreso no sea posterior a la de salida
            if (reserva.getFechaIngreso().isAfter(reserva.getFechaSalida())) {
                System.out.println("Error: La fecha de ingreso no puede ser posterior a la fecha de salida.");
                return false;
            }

            // Validar datos de entrada
            if (reserva.getDniCliente() == 0) {
                System.out.println("Error: DNI no puede ser 0.");
                return false;
            }

            // Buscar un estacionamiento disponible
            int idEstacionamiento = -1;
            try (ResultSet rs = buscarEstacionamientoStmt.executeQuery()) {
                if (rs.next()) {
                    idEstacionamiento = rs.getInt("id_estacionamiento");
                }
            }

            if (idEstacionamiento == -1) {
                System.out.println("Error: No hay estacionamientos disponibles.");
                return false;
            }

            // Actualizar el estado del estacionamiento a 'Ocupado'
            actualizarEstacionamientoStmt.setInt(1, idEstacionamiento);
            actualizarEstacionamientoStmt.executeUpdate();

            // Preparar la consulta para insertar la nueva reserva
            insertReservaStmt.setDate(1, java.sql.Date.valueOf(reserva.getFechaIngreso()));
            insertReservaStmt.setDate(2, java.sql.Date.valueOf(reserva.getFechaSalida()));
            insertReservaStmt.setString(3, "Confirmado");
            insertReservaStmt.setInt(4, reserva.getCantidadHuesped());
            insertReservaStmt.setInt(5, reserva.getDniCliente());
            insertReservaStmt.setInt(6, reserva.getIdHabitacion());
            insertReservaStmt.setInt(7, idEstacionamiento); // Usar el estacionamiento encontrado
            insertReservaStmt.setObject(8, reserva.getIdPago() > 0 ? reserva.getIdPago() : null, java.sql.Types.INTEGER);

            int rowsAffected = insertReservaStmt.executeUpdate();

            if (rowsAffected > 0) {
                // Obtener el ID generado para la reserva
                try (ResultSet generatedKeys = insertReservaStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idReserva = generatedKeys.getInt(1);
                        System.out.println("Reserva registrada con Numero: " + idReserva);

                        // Insertar el registro en historial_estacionamiento
                        String numeroPlaza = obtenerNumeroPlaza(idEstacionamiento);
                        insertHistorialStmt.setInt(1, reserva.getDniCliente());
                        insertHistorialStmt.setString(2, numeroPlaza);
                        insertHistorialStmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now())); // Fecha y hora de inicio
                        insertHistorialStmt.setTimestamp(4, null); // Fecha y hora de fin como NULL
                        insertHistorialStmt.executeUpdate();
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar reserva: " + e.getMessage());
        }

        return false; // Retorna falso en caso de error
    }

    // Método auxiliar para validar una fecha
    private boolean esFechaValida(LocalDate fecha) {
        try {
            // Intentar crear la fecha. Si no es válida, lanzará una excepción.
            LocalDate.of(fecha.getYear(), fecha.getMonthValue(), fecha.getDayOfMonth());
            return true;
        } catch (DateTimeParseException | IllegalArgumentException e) {
            return false;
        }
    }

    // Método auxiliar para obtener el número de plaza
    private String obtenerNumeroPlaza(int idEstacionamiento) {
        String sql = "SELECT numero_plaza FROM Estacionamiento WHERE id_estacionamiento = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idEstacionamiento);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("numero_plaza");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener número de plaza: " + e.getMessage());
        }
        return null; // Retorna null si no se encuentra
    }


    // Método para obtener todas las habitaciones
    public List<Integer> obtenerTodasLasHabitaciones() {
        List<Integer> habitaciones = new ArrayList<>();
        String query = "SELECT id_habitacion FROM habitaciones";

        try (Connection connection = ConexionBD.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int idHabitacion = resultSet.getInt("id_habitacion");
                habitaciones.add(idHabitacion);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener habitaciones: " + e.getMessage());
        }

        return habitaciones;
    }

    // Método para obtener habitaciones disponibles
    public List<Integer> obtenerHabitacionesDisponibles(LocalDate fechaIngreso, LocalDate fechaSalida) {
        List<Integer> habitacionesDisponibles = new ArrayList<>();
        List<Integer> todasLasHabitaciones = obtenerTodasLasHabitaciones();

        // Consulta SQL para encontrar habitaciones ocupadas en las fechas solicitadas
        String query = "SELECT id_habitacion FROM reservas WHERE (fecha_ingreso <= ? AND fecha_salida >= ?)";

        try (Connection connection = ConexionBD.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDate(1, java.sql.Date.valueOf(fechaSalida));
            statement.setDate(2, java.sql.Date.valueOf(fechaIngreso));
            ResultSet resultSet = statement.executeQuery();

            // Almacena las habitaciones ocupadas
            List<Integer> habitacionesOcupadas = new ArrayList<>();
            while (resultSet.next()) {
                habitacionesOcupadas.add(resultSet.getInt("id_habitacion"));
            }

            // Verifica las habitaciones disponibles
            for (int idHabitacion : todasLasHabitaciones) {
                if (!habitacionesOcupadas.contains(idHabitacion)) {
                    habitacionesDisponibles.add(idHabitacion);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener habitaciones disponibles: " + e.getMessage());
        }

        return habitacionesDisponibles;
    }


    // Método para obtener todas las reservas
    public List<Reserva> obtenerReserva() {
        List<Reserva> reservas = new ArrayList<>();
        String query = "SELECT r.id_reserva, r.fecha_ingreso, r.fecha_salida, r.estado_reserva, r.cantidad_huesped, r.dni, r.id_habitacion, e.numero_plaza, p.metodo_pago FROM reserva r JOIN estacionamiento e ON r.id_estacionamiento = e.id_estacionamiento LEFT JOIN pago p ON r.id_pago = p.id_pago;";

        try (PreparedStatement ps = conexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setIdReserva(rs.getInt("id_reserva"));
                reserva.setFechaIngreso(rs.getDate("fecha_ingreso").toLocalDate());
                reserva.setFechaSalida(rs.getDate("fecha_salida").toLocalDate());
                reserva.setEstadoReserva(rs.getString("estado_reserva"));
                reserva.setCantidadHuesped(rs.getInt("cantidad_huesped"));
                reserva.setDniCliente(rs.getInt("dni")); // Verifica el nombre correcto
                reserva.setIdHabitacion(rs.getInt("id_habitacion"));
                reserva.setNumeroPlaza(rs.getString("numero_plaza")); // Puede ser null si no hay estacionamiento
                reserva.setMetodoPago(rs.getString("metodo_pago"));   // Puede ser null si no hay pago registrado
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener reservas: " + e.getMessage());
        }

        return reservas;
    }


    // Método para obtener una reserva por su ID

    public Reserva obtenerReservaPorId(int idReserva) {
        Reserva reserva = null;
        String sql = "SELECT * FROM reserva WHERE id_reserva = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, idReserva);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                reserva = new Reserva();
                reserva.setIdReserva(resultSet.getInt("id_reserva"));
                reserva.setFechaIngreso(resultSet.getDate("fecha_ingreso").toLocalDate());
                reserva.setFechaSalida(resultSet.getDate("fecha_salida").toLocalDate());
                reserva.setEstadoReserva(resultSet.getString("estado_reserva"));
                reserva.setCantidadHuesped(resultSet.getInt("cantidad_huesped"));
                //reserva.setIdCliente(resultSet.getInt("id_cliente"));
                reserva.setIdHabitacion(resultSet.getInt("id_habitacion"));
                reserva.setIdEstacionamiento(resultSet.getInt("id_estacionamiento"));
                reserva.setIdPago(resultSet.getInt("id_pago"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reserva;
    }



    // Método para actualizar una reserva existente

    public boolean actualizarReserva(Reserva reserva) {
        String sql = "UPDATE reserva SET fecha_ingreso = ?, fecha_salida = ?, estado_reserva = ?, cantidad_huesped = ?, id_habitacion = ? WHERE id_reserva = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(reserva.getFechaIngreso()));
            statement.setDate(2, java.sql.Date.valueOf(reserva.getFechaSalida()));
            statement.setString(3, reserva.getEstadoReserva());
            statement.setInt(4, reserva.getCantidadHuesped());
            statement.setInt(5, reserva.getIdHabitacion());
            statement.setInt(6, reserva.getIdReserva());

            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean borrarReserva(int idReserva) {
        String sql = "DELETE FROM reserva WHERE id_reserva = ?";
        try (Connection connection = ConexionBD.obtenerConexion(); //
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idReserva);
            int filasAfectadas = statement.executeUpdate();

            return filasAfectadas > 0; // Devuelve true si se eliminó al menos una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Devuelve false si hubo un error
        }
    }

    //-------------------------------------------------
    public boolean cambiarHabitacion(int idReserva, int nuevaHabitacion, LocalDate fechaIngreso, LocalDate fechaSalida) {
        // Verificar si la habitación está disponible para las fechas solicitadas
        if (isHabitacionDisponible(nuevaHabitacion, fechaIngreso, fechaSalida)) {
            String query = "UPDATE Reserva SET id_habitacion = ? WHERE id_reserva = ?";

            try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                stmt.setInt(1, nuevaHabitacion);  // Establecer el nuevo ID de habitación
                stmt.setInt(2, idReserva);  // Establecer el ID de la reserva que se va a modificar

                int filasActualizadas = stmt.executeUpdate();
                if (filasActualizadas > 0) {
                    System.out.println("Habitación modificada con éxito.");
                    return true;
                } else {
                    System.out.println("No se encontró la reserva con el ID proporcionado.");
                }
            } catch (SQLException e) {
                System.err.println("Error al modificar la habitación: " + e.getMessage());
            }
        } else {
            System.out.println("La habitación no está disponible en las fechas seleccionadas.");
        }
        return false;
    }

    private boolean isHabitacionDisponible(int idHabitacion, LocalDate fechaIngreso, LocalDate fechaSalida) {
        String query = "SELECT COUNT(*) FROM Reserva WHERE id_habitacion = ? AND (" +
                "(fecha_ingreso BETWEEN ? AND ?) OR " +
                "(fecha_salida BETWEEN ? AND ?))";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, idHabitacion);  // Usar el id de la habitación
            stmt.setDate(2, Date.valueOf(fechaIngreso));  // Establecer fecha de ingreso
            stmt.setDate(3, Date.valueOf(fechaSalida));  // Establecer fecha de salida
            stmt.setDate(4, Date.valueOf(fechaIngreso));  // Establecer fecha de ingreso para la comparación
            stmt.setDate(5, Date.valueOf(fechaSalida));  // Establecer fecha de salida para la comparación

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0;  // Si no hay reservas que se solapen, la habitación está disponible
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar disponibilidad de la habitación: " + e.getMessage());
        }
        return false;
    }


    public boolean cambiarMetodoPago(int idReserva, int nuevoMetodoPago) {
        // Verificar si el nuevo método de pago es válido (esto depende de tu lógica de negocio)
        if (isMetodoPagoValido(nuevoMetodoPago)) {
            String query = "UPDATE Reserva SET id_pago = ? WHERE id_reserva = ?";

            try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                stmt.setInt(1, nuevoMetodoPago);  // Establecer el nuevo ID de método de pago
                stmt.setInt(2, idReserva);  // Establecer el ID de la reserva que se va a modificar

                int filasActualizadas = stmt.executeUpdate();
                if (filasActualizadas > 0) {
                    System.out.println("Método de pago modificado con éxito.");
                    return true;
                } else {
                    System.out.println("No se encontró la reserva con el ID proporcionado.");
                }
            } catch (SQLException e) {
                System.err.println("Error al modificar el método de pago: " + e.getMessage());
            }
        } else {
            System.out.println("El método de pago seleccionado no es válido.");
        }
        return false;
    }

    private boolean isMetodoPagoValido(int metodoPago) {

        String query = "SELECT COUNT(*) FROM Pago WHERE id_pago = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, metodoPago);  // Establecer el ID del método de pago
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;  // Si el método de pago existe, es válido
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar la validez del método de pago: " + e.getMessage());
        }
        return false;
    }
//-----------------------------------------

    public boolean cancelarReserva(int idReserva) {
        // Obtener la reserva por su ID
        Reserva reserva = obtenerReservaPorId(idReserva);
        if (reserva == null) {
            System.out.println("No se encontró la reserva con el ID proporcionado.");
            return false;
        }

        // Iniciar la transacción
        try {
            conexion.setAutoCommit(false);  // Desactivar autocommit para asegurar transacciones

            // 1. Cambiar el estado de la reserva a "Cancelado"
            String queryReserva = "UPDATE Reserva SET estado_reserva = 'Cancelado' WHERE id_reserva = ?";
            try (PreparedStatement stmt = conexion.prepareStatement(queryReserva)) {
                stmt.setInt(1, idReserva);
                stmt.executeUpdate();
            }

            // 2. Si la reserva tiene estacionamiento vinculado, cambiar el estado de la plaza y finalizar el historial
            if (reserva.getIdEstacionamiento() != 0) {
                // Cambiar el estado de la plaza a "Disponible"
                String queryPlaza = "UPDATE Estacionamiento SET estado_plaza = 'Disponible' WHERE id_estacionamiento = ?";
                try (PreparedStatement stmt = conexion.prepareStatement(queryPlaza)) {
                    stmt.setInt(1, reserva.getIdEstacionamiento());
                    stmt.executeUpdate();
                }

                // Finalizar la hora de fin en el historial de estacionamiento
                String queryHistorial = "UPDATE Historial_estacionamiento SET hora_fin = ? WHERE numero_plaza = (SELECT numero_plaza FROM Estacionamiento  WHERE id_estacionamiento = ?) AND hora_fin IS NULL ORDER BY hora_inicio DESC LIMIT 1;";
                try (PreparedStatement stmt = conexion.prepareStatement(queryHistorial)) {
                    stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));  // Hora actual
                    stmt.setInt(2, reserva.getIdEstacionamiento());
                    stmt.executeUpdate();
                }
            }

            // Commit de la transacción
            conexion.commit();
            System.out.println("Reserva cancelada exitosamente.");
            return true;
        } catch (SQLException e) {
            try {
                conexion.rollback();  // Si algo falla, revertir los cambios
            } catch (SQLException rollbackEx) {
                System.err.println("Error al realizar el rollback: " + rollbackEx.getMessage());
            }
            System.err.println("Error al cancelar la reserva: " + e.getMessage());
        } finally {
            try {
                conexion.setAutoCommit(true);  // Restaurar el autocommit
            } catch (SQLException e) {
                System.err.println("Error al restaurar el autocommit: " + e.getMessage());
            }
        }

        return false;
    }

}
