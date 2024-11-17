package vista;

import java.util.Scanner;
//clases
import clase.*;
//dao
import dao.*;
//otros
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.InputMismatchException;
import java.time.format.DateTimeParseException;


public class MenuPrincipal {
    private Scanner scanner = new Scanner(System.in);

    public void mostrarMenu() {
        String opcion;
        do {
            System.out.println("------------------------------------");
            System.out.println("Menu Principal: Ghala Hotel Salta");
            System.out.println("------------------------------------");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Registrar personal");
            System.out.println("3. Realizar reserva");
            System.out.println("4. Gestionar reserva");
            System.out.println("5. Registrar estacionamiento");
            System.out.println("6. Gestionar estacionamiento");
            System.out.println("7. Salir");
            System.out.println("------------------------------------");
            System.out.print("Ingrese una opción: ");

            opcion = scanner.nextLine().trim();

            try {
                if (opcion.isEmpty()) {
                    System.out.println("Entrada vacía, por favor ingrese una opción.");
                    continue;
                }

                switch (opcion) {
                    case "1":
                        registrarCliente();
                        break;
                    case "2":
                        registrarPersonal();
                        break;
                    case "3":
                        realizarReserva();
                        break;
                    case "4":
                        mostrarSubmenuReserva();
                        break;
                    case "5":
                        registrarEstacionamiento();
                        break;
                    case "6":
                        mostrarSubmenuEstacionamiento();
                        break;
                    case "7":
                        System.out.println("Saliendo del sistema.");
                        break;
                    default:
                        System.out.println("Opción no válida, intente nuevamente.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida, por favor ingrese un número.");
                scanner.nextLine(); // Limpiar el buffer
            }
        } while (!opcion.equals("7")); // Verifica si la opción es diferente de 7
    }

    private void registrarCliente() {
        System.out.println("Ingrese los datos del cliente:");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();
        System.out.print("DNI: ");
        String dni = scanner.nextLine().trim();
        System.out.print("Número de contacto: ");
        String numeroContacto = scanner.nextLine().trim();
        System.out.print("Correo: ");
        String correo = scanner.nextLine().trim();

        Cliente cliente = new Cliente(nombre, apellido, dni, numeroContacto, correo);
        ClienteDAO clienteDAO = new ClienteDAO();

        boolean exito = clienteDAO.registrarCliente(cliente);
        if (exito) {
            System.out.println("Cliente registrado exitosamente.");
        } else {
            System.out.println("Hubo un problema al registrar el cliente.");
        }
    }

    // Método de registro de personal
    private void registrarPersonal() {
        System.out.println("Registro de nuevo personal");

        System.out.print("Número de empleado: ");
        String numeroEmpleado = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("DNI: ");
        String dni = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Turno: ");
        String turno = scanner.nextLine();

        System.out.print("ID del cargo: ");
        int idCargo = Integer.parseInt(scanner.nextLine());

        // Crear un objeto Personal con los datos ingresados
        Personal personal = new Personal(numeroEmpleado, nombre, apellido, dni, telefono, turno, idCargo);

        PersonalDAO personalDAO = new PersonalDAO();
        if (personalDAO.registrarPersonal(personal)) {
            System.out.println("Personal registrado exitosamente.");
        } else {
            System.out.println("Error al registrar personal.");
        }
    }
    //reserva
    private void realizarReserva() {
        LocalDate fechaIngreso = solicitarFecha("Ingrese fecha de ingreso (YYYY-MM-DD) ej: 2024-11-01: ");
        LocalDate fechaSalida = solicitarFecha("Ingrese fecha de salida (YYYY-MM-DD) ej: 2024-11-05: ");

        // Validar que la fecha de ingreso no sea posterior a la fecha de salida
        if (fechaIngreso.isAfter(fechaSalida)) {
            System.out.println("Error: La fecha de ingreso no puede ser posterior a la fecha de salida. Intente nuevamente.");
            return;
        }

        System.out.println("Ingrese cantidad de huéspedes: ");
        int cantidadHuesped = Integer.parseInt(scanner.nextLine());

        System.out.println("Ingrese DNI del cliente: ");
        int dni = Integer.parseInt(scanner.nextLine());

        System.out.println("Ingrese N° de la habitación: ");
        int idHabitacion = Integer.parseInt(scanner.nextLine());

        // Preguntar si quiere estacionamiento
        int idEstacionamiento = 0;
        System.out.println("¿Quiere reservar un estacionamiento? (S/N): ");
        String respuestaEstacionamiento = scanner.nextLine().trim().toUpperCase();
        if ("S".equals(respuestaEstacionamiento)) {
            idEstacionamiento = -1; // Marca temporal para asignar automáticamente
        }

        // Mostrar métodos de pago
        MetodoPagoDAO metodoPagoDAO = new MetodoPagoDAO();
        List<Pago> metodosPago = metodoPagoDAO.obtenerMetodosPago();
        System.out.println("Seleccione un método de pago:");

        for (int i = 0; i < metodosPago.size(); i++) {
            Pago pago = metodosPago.get(i);
            System.out.println((i + 1) + ". " + pago.getMetodoPago());
        }

        int seleccion;
        do {
            System.out.print("Ingrese el número del método de pago: ");
            seleccion = Integer.parseInt(scanner.nextLine());
        } while (seleccion < 1 || seleccion > metodosPago.size());

        int idPago = metodosPago.get(seleccion - 1).getIdPago();

        // Crear objeto reserva
        Reserva reserva = new Reserva(fechaIngreso, fechaSalida, "Confirmado", cantidadHuesped, dni, idHabitacion, idEstacionamiento, idPago);

        ReservaDAO reservaDAO = new ReservaDAO();
        if (reservaDAO.registrarReserva(reserva)) {
            System.out.println("Reserva registrada exitosamente.");
        } else {
            System.out.println("Error al registrar la reserva.");
        }
    }

    private LocalDate solicitarFecha(String mensaje) {
        LocalDate fecha = null;
        while (fecha == null) {
            try {
                System.out.println(mensaje);
                String entrada = scanner.nextLine();
                fecha = LocalDate.parse(entrada);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha no válida. Por favor, ingrese una fecha válida en el formato YYYY-MM-DD.");
            }
        }
        return fecha;
    }


    private void mostrarSubmenuReserva() {
        String opcionSubmenu;
        do {
            System.out.println("------------------------------");
            System.out.println("---- Gestión de Reservas ----");
            System.out.println("------------------------------");
            System.out.println("1. Ver lista de reservas");
            System.out.println("2. Modificar una reserva");
            System.out.println("3. Dar de baja una reserva");
            System.out.println("4. Volver");
            System.out.println("------------------------------");
            System.out.print("Ingrese una opción: ");

            opcionSubmenu = scanner.nextLine().trim();

            try {
                if (opcionSubmenu.isEmpty()) {
                    System.out.println("Entrada vacía, por favor ingrese una opción.");
                    continue;
                }

                switch (opcionSubmenu) {
                    case "1":
                        verListaReservas();
                        break;
                    case "2":
                        modificarReserva();
                        break;
                    case "3":
                        borrarReserva();
                        break;
                    case "4":
                        System.out.println("Volviendo al menú principal.");
                        break;
                    default:
                        System.out.println("Opción no válida, intente nuevamente.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida, por favor ingrese un número.");
                scanner.nextLine(); // Limpiar el buffer
            }
        } while (!opcionSubmenu.equals("4")); // Verifica si la opción es diferente de 4
    }


    private void verListaReservas() {
        ReservaDAO reservaDAO = new ReservaDAO();
        List<Reserva> reservas = reservaDAO.obtenerReserva();

        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
        } else {
            // Imprimir encabezados de la tabla
            System.out.println("== Mostrando reservas ==");
            System.out.println("--------------------------------------------------------------------------------------------------------------------");
            System.out.println("| ID   | Fecha Ingreso | Fecha Salida | Huéspedes | DNI Cliente | N° Habitación | N° Plaza | Forma de Pago         |");
            System.out.println("--------------------------------------------------------------------------------------------------------------------");

            // Imprimir cada reserva
            for (Reserva reserva : reservas) {
                System.out.printf("| %-4d | %-13s | %-12s | %-9d | %-11s | %-13d | %-7s | %-22s |%n",
                        reserva.getIdReserva(),
                        reserva.getFechaIngreso(),
                        reserva.getFechaSalida(),
                        reserva.getCantidadHuesped(),
                        reserva.getDniCliente(),
                        reserva.getIdHabitacion(),
                        reserva.getNumeroPlaza() != null ? reserva.getNumeroPlaza() : "N/A",
                        reserva.getMetodoPago() != null ? reserva.getMetodoPago() : "Sin definir");
            }
            System.out.println("--------------------------------------------------------------------------------------------------------------------");
        }
    }

//---------------------Modificacion Reserva-----------------------
private void modificarReserva() {
    ReservaDAO reservaDAO = new ReservaDAO();
    Scanner scanner = new Scanner(System.in);

    System.out.print("Ingrese el N° de la reserva que desea modificar: ");
    int idReserva = scanner.nextInt();
    scanner.nextLine(); // Limpiar el buffer

    // Buscar la reserva
    Reserva reserva = reservaDAO.obtenerReservaPorId(idReserva);
    if (reserva == null) {
        System.out.println("No se encontró una reserva con ese ID.");
        return;
    }

    boolean continuar = true;

    while (continuar) {
        // Mostrar datos actuales de la reserva
        System.out.println("Datos actuales de la reserva:");
        System.out.printf("1. ID: %d%n", reserva.getIdReserva());
        System.out.printf("2. Fecha Ingreso: %s%n", reserva.getFechaIngreso());
        System.out.printf("3. Fecha Salida: %s%n", reserva.getFechaSalida());
        System.out.printf("4. Estado: %s%n", reserva.getEstadoReserva());
        System.out.printf("5. Cantidad de Huéspedes: %d%n", reserva.getCantidadHuesped());
        System.out.printf("6. DNI Cliente: %d%n", reserva.getDniCliente());
        System.out.printf("7. N° Habitación: %d%n", reserva.getIdHabitacion());
        System.out.printf("8. N° Estacionamiento: %d%n", reserva.getIdEstacionamiento());
        System.out.printf("9. Método de Pago: %d%n", reserva.getIdPago());

        System.out.println("¿Qué desea modificar?");
        System.out.println("1. Fecha de Ingreso");
        System.out.println("2. Fecha de Salida");
        System.out.println("3. Estado");
        System.out.println("4. Cantidad de Huéspedes");
        System.out.println("5. Cambiar Habitación");
        System.out.println("6. Cambiar Método de Pago");
        System.out.println("7. Cancelar Reserva");
        System.out.println("8. Volver");
        System.out.print("Seleccione una opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        switch (opcion) {
            case 1:
                System.out.print("Ingrese la nueva fecha de ingreso (YYYY-MM-DD): ");
                String nuevaFechaIngresoStr = scanner.nextLine();
                LocalDate nuevaFechaIngreso = LocalDate.parse(nuevaFechaIngresoStr); // Convierte el String a LocalDate
                reserva.setFechaIngreso(nuevaFechaIngreso);
                break;
            case 2:
                System.out.print("Ingrese la nueva fecha de salida (YYYY-MM-DD): ");
                String nuevaFechaSalidaStr = scanner.nextLine();
                LocalDate nuevaFechaSalida = LocalDate.parse(nuevaFechaSalidaStr); // Convierte el String a LocalDate
                reserva.setFechaSalida(nuevaFechaSalida);
                break;
            case 3:
                System.out.print("Ingrese nuevo estado de la reserva (Confirmado/Cancelado): ");
                String nuevoEstado = scanner.nextLine();
                reserva.setEstadoReserva(nuevoEstado);
                break;
            case 4:
                System.out.print("Ingrese nueva cantidad de huéspedes: ");
                int nuevaCantidadHuesped = scanner.nextInt();
                reserva.setCantidadHuesped(nuevaCantidadHuesped);
                scanner.nextLine(); // Limpiar el buffer
                break;
            case 5:
                System.out.print("Ingrese el ID de la nueva habitación: ");
                int nuevaHabitacion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                // Llamar al método cambiarHabitacion a través de reservaDAO
                if (reservaDAO.cambiarHabitacion(reserva.getIdReserva(), nuevaHabitacion, reserva.getFechaIngreso(), reserva.getFechaSalida())) {
                    reserva.setIdHabitacion(nuevaHabitacion);  // Actualizamos la habitación en la reserva
                }
                break;
            case 6:
                System.out.print("Ingrese el nuevo método de pago (1.Tarjeta Credito, 2. Transferencia, 3. Efectivo): ");
                int nuevoMetodoPago = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                // Llamar al método cambiarMetodoPago a través de reservaDAO
                if (reservaDAO.cambiarMetodoPago(reserva.getIdReserva(), nuevoMetodoPago)) {
                    reserva.setIdPago(nuevoMetodoPago);  // Actualizamos el método de pago en la reserva
                }
                break;
            case 7:
                System.out.print("Ingrese el ID de la reserva a cancelar: ");
                int idReservaCancelar = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                // Llamar al método cancelarReserva
                if (reservaDAO.cancelarReserva(idReservaCancelar)) {
                    // Actualizamos el estado de la reserva en el objeto reserva (si se desea hacer)
                    reserva.setEstadoReserva("Cancelado");
                }
                break;
            case 8:
                continuar = false; // Sale del bucle y no hace cambios
                break;
            default:
                System.out.println("Opción no válida. Intente de nuevo.");
        }

        // Mostrar un mensaje de confirmación
        if (continuar && opcion != 7 && opcion != 8) {
            System.out.println("Modificación realizada con éxito.");
        } else if (opcion == 8) {
            // Al elegir "Volver", no se debe actualizar la reserva
            System.out.println("Volviendo al menú principal.");
        }
    }

    // Guardar los cambios si no se canceló la reserva
    if (reserva.getEstadoReserva().equals("Cancelado")) {
        // Si la reserva fue cancelada, no la guardamos de nuevo.
        System.out.println("Reserva cancelada, no se actualizan más datos.");
    } else {
        reservaDAO.actualizarReserva(reserva);
        System.out.println("Reserva modificada con éxito.");
    }
}

//---------------------------------Borrado de reserva------------------------------------------


    private void borrarReserva() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID de la reserva que desea cancelar: ");
        int idReserva = scanner.nextInt();  // Capturamos el ID de la reserva
        scanner.nextLine();  // Limpiar el buffer del scanner

        // Reutilizamos el método cancelarReserva() para cancelar la reserva
        ReservaDAO reservaDAO = new ReservaDAO();
        boolean exito = reservaDAO.cancelarReserva(idReserva);// Llamada al método cancelarReserva con el ID de la reserva

        // Verificamos si la operación fue exitosa
        if (exito) {
            System.out.println("Reserva cancelada con éxito.");
        } else {
            System.out.println("No se pudo cancelar la reserva.");
        }
    }


    //---------------------------registrar estacionamiento------------------------
private static void registrarEstacionamiento() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("=== Registro de Estacionamiento ===");
    System.out.print("Ingrese el DNI del cliente: ");
    int dniCliente = scanner.nextInt();
    scanner.nextLine(); // Limpiar el buffer

    System.out.print("Ingrese el número de plaza: ");
    String numeroPlaza = scanner.nextLine();

    // Confirmación de registro
    System.out.println("¿Está seguro de registrar el estacionamiento?");
    System.out.print("Ingrese 'S' para confirmar o 'N' para cancelar: ");
    String confirmacion = scanner.nextLine().toUpperCase();

    if (confirmacion.equals("N")) {
        System.out.println("El registro de estacionamiento ha sido cancelado.");
        return; // Cancelar el proceso y salir del método
    }

    // Si el usuario confirma, se llama al DAO para registrar el estacionamiento
    HistorialEstacionamientoDAO historialDAO = new HistorialEstacionamientoDAO();
    boolean registrado = historialDAO.registrarEstacionamiento(dniCliente, numeroPlaza);

    if (registrado) {
        System.out.println("Estacionamiento registrado exitosamente.");
    } else {
        System.out.println("Error al registrar el estacionamiento. Verifique los datos ingresados.");
    }
}
//-----------------------SUBMENU ESTACIONAMIENTO--------------------------------
    public void mostrarSubmenuEstacionamiento() {
        String opcionSubmenu;
        do {
            System.out.println("------------------------------------");
            System.out.println("---- Gestión de Estacionamiento ----");
            System.out.println("------------------------------------");
            System.out.println("1. Ver lugares disponibles");
            System.out.println("2. Ver lugares ocupados");
            System.out.println("3. Listar todos los lugares");
            System.out.println("4. Consultar lugar");
            System.out.println("5. Modificar lugar");
            System.out.println("6. Liberar lugar");
            System.out.println("7. Historial de estacionamientos");
            System.out.println("8. Volver");
            System.out.println("------------------------------------");
            System.out.print("Ingrese una opción: ");

            opcionSubmenu = scanner.nextLine().trim();

            try {
                if (opcionSubmenu.isEmpty()) {
                    System.out.println("Entrada vacía, por favor ingrese una opción.");
                    continue;
                }

                switch (opcionSubmenu) {
                    case "1":
                        verLugaresDisponibles();
                        break;
                    case "2":
                        verLugaresOcupados();
                        break;
                    case "3":
                        listarEstacionamiento();
                        break;
                    case "4":
                        consultarEstacionamiento();
                        break;
                    case "5":
                        modificarLugar();
                        break;
                    case "6":
                        borrarLugar();
                        break;
                    case "7":
                        historialLugar();
                        break;
                    case "8":
                        System.out.println("Volviendo al menú principal.");
                        break;
                    default:
                        System.out.println("Opción no válida, intente nuevamente.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida, por favor ingrese un número.");
                scanner.nextLine(); // Limpiar el buffer
            }
        } while (!opcionSubmenu.equals("8")); // Verifica si la opción es diferente de 6
    }


    private void verLugaresDisponibles() {
    EstacionamientoDAO estacionamientoDAO = new EstacionamientoDAO();
    List<Estacionamiento> lugaresDisponibles = estacionamientoDAO.obtenerLugaresDisponibles();
    System.out.println("== Mostrando lugares disponibles ==");
    System.out.println("------------------------------------------------------");
    System.out.println("| Lugar  | Disponibilidad | Lugar | Disponibilidad |");
    System.out.println("------------------------------------------------------");

    if (lugaresDisponibles.isEmpty()) {
        System.out.println("No hay lugares disponibles.");
    } else {
        // Mostrar los lugares de dos en dos por línea
        for (int i = 0; i < lugaresDisponibles.size(); i++) {
            Estacionamiento lugar1 = lugaresDisponibles.get(i);

            // Si hay un siguiente lugar, mostramos los dos en la misma línea
            if (i + 1 < lugaresDisponibles.size()) {
                Estacionamiento lugar2 = lugaresDisponibles.get(i + 1);
                System.out.printf("| %-6s | %-15s | %-6s | %-15s |\n",
                        lugar1.getNumeroPlaza(), lugar1.getEstadoPlaza(),
                        lugar2.getNumeroPlaza(), lugar2.getEstadoPlaza());
                i++; // Incrementar el índice ya que mostramos dos lugares
            } else {
                // Si solo queda uno, lo mostramos en una línea
                System.out.printf("| %-6s | %-15s | %-6s | %-15s |\n",
                        lugar1.getNumeroPlaza(), lugar1.getEstadoPlaza(),
                        "", "");
            }
        }
    }

    System.out.println("------------------------------------------------------");
}
    private void verLugaresOcupados() {
        EstacionamientoDAO estacionamientoDAO = new EstacionamientoDAO();
        List<Estacionamiento> lugaresOcupados = estacionamientoDAO.obtenerLugaresOcupados();
        System.out.println("== Mostrando lugares Ocupados ==");
        System.out.println("------------------------------------------------------");
        System.out.println("| Lugar  | Disponibilidad | Lugar | Disponibilidad |");
        System.out.println("------------------------------------------------------");

        if (lugaresOcupados.isEmpty()) {
            System.out.println("No hay lugares ocupados.");
        } else {
            // Mostrar los lugares de dos en dos por línea
            for (int i = 0; i < lugaresOcupados.size(); i++) {
                Estacionamiento lugar1 = lugaresOcupados.get(i);

                // Si hay un siguiente lugar, mostramos los dos en la misma línea
                if (i + 1 < lugaresOcupados.size()) {
                    Estacionamiento lugar2 = lugaresOcupados.get(i + 1);
                    System.out.printf("| %-6s | %-15s | %-6s | %-15s |\n",
                            lugar1.getNumeroPlaza(), lugar1.getEstadoPlaza(),
                            lugar2.getNumeroPlaza(), lugar2.getEstadoPlaza());
                    i++; // Incrementar el índice ya que mostramos dos lugares
                } else {
                    // Si solo queda uno, lo mostramos en una línea
                    System.out.printf("| %-6s | %-15s | %-6s | %-15s |\n",
                            lugar1.getNumeroPlaza(), lugar1.getEstadoPlaza(),
                            "", "");
                }
            }
        }

        System.out.println("------------------------------------------------------");
    }
    //Listar estacionamiento
    private void listarEstacionamiento() {
        System.out.println("== listado de estacionamiento ==");
        EstacionamientoDAO estacionamientoDAO = new EstacionamientoDAO();

        // Obtener todos los lugares del estacionamiento
        List<Estacionamiento> lugares = estacionamientoDAO.obtenerTodosLosLugares();

        if (lugares.isEmpty()) {
            System.out.println("No hay lugares registrados en el estacionamiento.");
        } else {
            // Encabezado de la tabla
            System.out.println("------------------------------------------------------");
            System.out.println("| Lugar  | Disponibilidad | Lugar  | Disponibilidad |");
            System.out.println("------------------------------------------------------");

            // Mostrar los lugares de dos en dos
            for (int i = 0; i < lugares.size(); i++) {
                Estacionamiento lugar1 = lugares.get(i);

                if (i + 1 < lugares.size()) {
                    Estacionamiento lugar2 = lugares.get(i + 1);
                    System.out.printf("| %-6s | %-15s | %-6s | %-15s |\n",
                            lugar1.getNumeroPlaza(), lugar1.getEstadoPlaza(),
                            lugar2.getNumeroPlaza(), lugar2.getEstadoPlaza());
                    i++; // Incrementar el índice ya que mostramos dos lugares por línea
                } else {
                    System.out.printf("| %-6s | %-15s | %-6s | %-15s |\n",
                            lugar1.getNumeroPlaza(), lugar1.getEstadoPlaza(),
                            "", ""); // Mostrar un espacio vacío si no hay un segundo lugar
                }
            }
            System.out.println("------------------------------------------------------");
        }
    }

    private void consultarEstacionamiento() {
        Scanner scanner = new Scanner(System.in);

        // Pedir el número de plaza al usuario
        System.out.print("Ingrese el número de plaza que desea consultar: ");
        String numeroPlaza = scanner.nextLine();  // Usamos nextLine para capturar el número de plaza como String

        // Verificar si la plaza está ocupada o libre
        EstacionamientoDAO estacionamientoDAO = new EstacionamientoDAO();
        boolean estaOcupada = estacionamientoDAO.esPlazaOcupada(numeroPlaza);

        if (estaOcupada) {
            // Si está ocupada, buscamos el último historial de estacionamiento
            HistorialEstacionamientoDAO historialEstacionamientoDAO = new HistorialEstacionamientoDAO();
            HistorialEstacionamiento historial = historialEstacionamientoDAO.obtenerUltimoHistorialPorPlaza(numeroPlaza);

            if (historial != null) {
                // Mostrar los datos del historial
                System.out.println("Plaza: " + historial.getNumeroPlaza());
                System.out.println("DNI del cliente: " + historial.getDniCliente());
                System.out.println("Hora de inicio: " + historial.getHoraInicio());
            } else {
                System.out.println("No se encontró historial para la plaza " + numeroPlaza);
            }
        } else {
            // Si está libre, se muestra el estado
            System.out.println("La plaza " + numeroPlaza + " está libre.");
        }
    }
    //-----------------------modificarLugar------------------------------
    // Método para modificar lugar de estacionamiento
    private void modificarLugar() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Modificación de Lugar de Estacionamiento ===");

        System.out.print("Ingrese el número de la plaza actual: ");
        String plazaActual = scanner.nextLine();

        // Verificar si la plaza actual está ocupada
        EstacionamientoDAO estacionamientoDAO = new EstacionamientoDAO();
        boolean estaOcupada = estacionamientoDAO.esPlazaOcupada(plazaActual);

        if (!estaOcupada) {
            System.out.println("La plaza " + plazaActual + " está disponible. No se puede modificar.");
            return;
        }

        int opcion;
        do {
            System.out.println("\nSeleccione una opción para modificar:");
            System.out.println("1. Modificar Plaza de Estacionamiento");
            System.out.println("2. Modificar Cliente");
            System.out.println("3. Modificar Estado");
            System.out.println("4. Volver");
            System.out.print("Opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    modificarPlaza(plazaActual, scanner, estacionamientoDAO);
                    break;
                case 2:
                    modificarCliente(plazaActual, scanner);
                    break;
                case 3:
                    modificarEstadoPlaza(plazaActual, scanner, estacionamientoDAO);
                    break;
                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 4);
    }

    private void modificarPlaza(String plazaActual, Scanner scanner, EstacionamientoDAO estacionamientoDAO) {
        System.out.print("Ingrese el número de la nueva plaza deseada: ");
        String nuevaPlaza = scanner.nextLine();

        // Verificar si la nueva plaza está ocupada
        boolean nuevaEstaOcupada = estacionamientoDAO.esPlazaOcupada(nuevaPlaza);
        if (nuevaEstaOcupada) {
            System.out.println("La nueva plaza " + nuevaPlaza + " ya está ocupada. Intente con otra plaza.");
            return;
        }

        // Actualizar los registros en la base de datos
        HistorialEstacionamientoDAO historialDAO = new HistorialEstacionamientoDAO();
        boolean exito = historialDAO.actualizarPlazaEnHistorial(plazaActual, nuevaPlaza);

        if (exito) {
            estacionamientoDAO.actualizarEstadoPlaza(plazaActual, "Disponible");
            estacionamientoDAO.actualizarEstadoPlaza(nuevaPlaza, "Ocupado");
            System.out.println("La plaza ha sido actualizada exitosamente.");
        } else {
            System.out.println("Error al actualizar la plaza. Verifique los datos e intente nuevamente.");
        }
    }
    private void modificarCliente(String plazaActual, Scanner scanner) {
        HistorialEstacionamientoDAO historialDAO = new HistorialEstacionamientoDAO();

        // Pedir el nuevo DNI al usuario
        System.out.print("Ingrese el nuevo DNI del cliente: ");
        int nuevoDni = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        // Actualizar el DNI en la tabla Historial_estacionamiento
        boolean exito = historialDAO.actualizarDniCliente(plazaActual, nuevoDni);

        if (exito) {
            System.out.println("El cliente ha sido actualizado exitosamente.");
        } else {
            System.out.println("Error al actualizar el cliente. Verifique los datos e intente nuevamente.");
        }
    }

    private void modificarEstadoPlaza(String plazaActual, Scanner scanner, EstacionamientoDAO estacionamientoDAO) {
        System.out.println("Estado actual de la plaza: Ocupado");
        System.out.println("¿A qué estado desea cambiar?");
        System.out.println("Presione 'D' para cambiar a Disponible.");
        System.out.println("Presione 'O' para cambiar a Ocupado.");
        System.out.print("Opción: ");
        String opcion = scanner.nextLine().toUpperCase();

        if (!opcion.equals("D") && !opcion.equals("O")) {
            System.out.println("Opción inválida. Intente nuevamente.");
            return;
        }

        String nuevoEstado = opcion.equals("D") ? "Disponible" : "Ocupado";

        // Actualizar el estado en la base de datos
        boolean exito = estacionamientoDAO.actualizarEstadoPlaza(plazaActual, nuevoEstado);

        if (exito) {
            System.out.println("El estado de la plaza ha sido actualizado a " + nuevoEstado + " exitosamente.");
        } else {
            System.out.println("Error al actualizar el estado de la plaza. Intente nuevamente.");
        }
    }


    private void historialLugar() {
        HistorialEstacionamientoDAO historialDAO = new HistorialEstacionamientoDAO();
        List<HistorialEstacionamiento> historialList = historialDAO.obtenerHistorialConHoraFin();
        System.out.println(" ==== Historial de registros ====");
        if (historialList.isEmpty()) {
            System.out.println("No hay registros disponibles en el historial.");
        } else {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.printf("| %-11s | %-11s | %-13s | %-20s | %-20s |\n",
                    "N°Historial", "DNI Cliente", "Numero Plaza", "Hora de entrada", "Hora de salida");
            System.out.println("------------------------------------------------------------------------------------------------");

            for (HistorialEstacionamiento historial : historialList) {
                System.out.printf("| %-11d | %-11d | %-13s | %-20s | %-20s |\n",
                        historial.getIdHistorialEst(),
                        historial.getDniCliente(),
                        historial.getNumeroPlaza(),
                        historial.getHoraInicio(),
                        historial.getHoraFin());
            }
            System.out.println("------------------------------------------------------------------------------------------------");
        }
    }

    //---------Finalizar un Puesto de estacionamiento (liberar)---------------
    private void borrarLugar() {
        System.out.println("Función para liberar un lugar de estacionamiento.");
        HistorialEstacionamientoDAO historialEstacionamientoDAO = new HistorialEstacionamientoDAO();

        // Pedir el número de plaza al usuario (como String)
        System.out.print("Ingrese el número de plaza a liberar: ");
        Scanner scanner = new Scanner(System.in);
        String numeroPlaza = scanner.nextLine();  // Leer como String

        // Llamar al DAO para liberar el lugar
        boolean exito = historialEstacionamientoDAO.liberarPlaza(numeroPlaza);

        if (exito) {
            System.out.println("El lugar de estacionamiento " + numeroPlaza + " ha sido liberado exitosamente.");
        } else {
            System.out.println("No se pudo liberar el lugar de estacionamiento " + numeroPlaza + ". Asegúrese de que el número de plaza esté ocupado.");
        }
    }


}
