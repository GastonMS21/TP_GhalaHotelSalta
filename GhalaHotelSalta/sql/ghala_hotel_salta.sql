-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-11-2024 a las 23:40:14
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ghala_hotel_salta`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cargo`
--

CREATE TABLE `cargo` (
  `id_cargo` int(11) NOT NULL,
  `tipo_cargo` varchar(50) NOT NULL,
  `descripcion` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cargo`
--

INSERT INTO `cargo` (`id_cargo`, `tipo_cargo`, `descripcion`) VALUES
(1, 'Recepcionista', 'Encargado de la atención al cliente y gestión de reservas'),
(2, 'Conserje', 'Encargado de recibir a los huéspedes y gestionar el estacionamiento'),
(3, 'Gerente', 'Encargado de la administración del hotel');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id_cliente` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `dni` varchar(20) NOT NULL,
  `numero_contacto` varchar(20) DEFAULT NULL,
  `correo` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`id_cliente`, `nombre`, `apellido`, `dni`, `numero_contacto`, `correo`) VALUES
(1, 'Juan', 'Pérez', '12345678', '123456789', 'juan.perez@example.com'),
(2, 'María', 'González', '87654321', '987654321', 'maria.gonzalez@example.com'),
(3, 'Carlos', 'Ramírez', '11223344', '456789123', 'carlos.ramirez@example.com'),
(5, 'Agustina', 'Moreno', '12340987', '1156789123', 'agustina.moreno@gmail.com'),
(6, 'Lucas', 'Santana', '98761234', '1123445566', 'lucas.santana@gmail.com'),
(7, 'Martina', 'Vega', '65432109', '1177889900', 'martina.vega@gmail.com'),
(8, 'Mateo', 'Blanco', '34567891', '1165432109', 'mateo.blanco@gmail.com'),
(9, 'Camila', 'Ruiz', '76543210', '1144332211', 'camila.ruiz@gmail.com'),
(10, 'Santiago', 'Rojas', '56789023', '1198765431', 'santiago.rojas@gmail.com'),
(11, 'Victoria', 'Castro', '12345679', '1188996655', 'victoria.castro@gmail.com'),
(12, 'Facundo', 'Silva', '89012346', '1133224455', 'facundo.silva@gmail.com'),
(13, 'Elena', 'Méndez', '34567892', '1144112233', 'elena.mendez@gmail.com'),
(14, 'Nicolás', 'Ortiz', '56781235', '1155667788', 'nicolas.ortiz@gmail.com'),
(15, 'Julieta', 'Cabrera', '23456780', '1177998800', 'julieta.cabrera@gmail.com'),
(16, 'Benjamín', 'Ponce', '89023457', '1122113344', 'benjamin.ponce@gmail.com'),
(17, 'Florencia', 'Sosa', '34561235', '1166554433', 'florencia.sosa@gmail.com'),
(18, 'Thiago', 'Medina', '45678901', '1155443322', 'thiago.medina@gmail.com'),
(19, 'Isabella', 'Navarro', '23456789', '1199001122', 'isabella.navarro@gmail.com'),
(20, 'Franco', 'Figueroa', '56789024', '1122334466', 'franco.figueroa@gmail.com'),
(21, 'Renata', 'Peña', '67890123', '1133445566', 'renata.pena@gmail.com'),
(22, 'Tobías', 'Herrera', '78901234', '1177889901', 'tobias.herrera@gmail.com'),
(23, 'Milagros', 'Cortés', '89012348', '1144778899', 'milagros.cortes@gmail.com'),
(24, 'Joaquín', 'Vargas', '34567893', '1166442233', 'joaquin.vargas@gmail.com'),
(25, 'Santiago', 'Torres', '45548617', '3876554515', 'santiago@gmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estacionamiento`
--

CREATE TABLE `estacionamiento` (
  `id_estacionamiento` int(11) NOT NULL,
  `numero_plaza` varchar(11) NOT NULL,
  `estado_plaza` varchar(20) NOT NULL,
  `precio_hora` decimal(10,2) NOT NULL,
  `precio_dia` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `estacionamiento`
--

INSERT INTO `estacionamiento` (`id_estacionamiento`, `numero_plaza`, `estado_plaza`, `precio_hora`, `precio_dia`) VALUES
(1, 'A01', 'Disponible', '5.00', '20.00'),
(2, 'A02', 'Ocupado', '5.00', '20.00'),
(3, 'A03', 'Ocupado', '5.00', '20.00'),
(4, 'A04', 'Ocupado', '5.00', '20.00'),
(5, 'A05', 'Ocupado', '5.00', '20.00'),
(6, 'A06', 'Disponible', '5.00', '20.00'),
(7, 'A07', 'Disponible', '5.00', '20.00'),
(8, 'A08', 'Disponible', '5.00', '20.00'),
(9, 'A09', 'Disponible', '5.00', '20.00'),
(10, 'A10', 'Disponible', '5.00', '20.00'),
(11, 'B01', 'Disponible', '5.00', '20.00'),
(12, 'B02', 'Disponible', '5.00', '20.00'),
(13, 'B03', 'Disponible', '5.00', '20.00'),
(14, 'B04', 'Disponible', '5.00', '20.00'),
(15, 'B05', 'Disponible', '5.00', '20.00'),
(16, 'B06', 'Disponible', '5.00', '20.00'),
(17, 'B07', 'Disponible', '5.00', '20.00'),
(18, 'B08', 'Disponible', '5.00', '20.00'),
(19, 'B09', 'Disponible', '5.00', '20.00'),
(20, 'B10', 'Disponible', '5.00', '20.00'),
(21, 'C01', 'Disponible', '5.00', '20.00'),
(22, 'C02', 'Disponible', '5.00', '20.00'),
(23, 'C03', 'Disponible', '5.00', '20.00'),
(24, 'C04', 'Disponible', '5.00', '20.00'),
(25, 'C05', 'Disponible', '5.00', '20.00'),
(26, 'C06', 'Ocupado', '5.00', '20.00'),
(27, 'C07', 'Disponible', '5.00', '20.00'),
(28, 'C08', 'Ocupado', '5.00', '20.00'),
(29, 'C09', 'Disponible', '5.00', '20.00'),
(30, 'C10', 'Ocupado', '5.00', '20.00'),
(31, 'D01', 'Disponible', '5.00', '20.00'),
(32, 'D02', 'Ocupado', '5.00', '20.00'),
(33, 'D03', 'Disponible', '5.00', '20.00'),
(34, 'D04', 'Ocupado', '5.00', '20.00'),
(35, 'D05', 'Disponible', '5.00', '20.00'),
(36, 'D06', 'Ocupado', '5.00', '20.00'),
(37, 'D07', 'Disponible', '5.00', '20.00'),
(38, 'D08', 'Ocupado', '5.00', '20.00'),
(39, 'D09', 'Disponible', '5.00', '20.00'),
(40, 'D10', 'Ocupado', '5.00', '20.00'),
(41, 'E01', 'Disponible', '5.00', '20.00'),
(42, 'E02', 'Ocupado', '5.00', '20.00'),
(43, 'E03', 'Disponible', '5.00', '20.00'),
(44, 'E04', 'Ocupado', '5.00', '20.00'),
(45, 'E05', 'Disponible', '5.00', '20.00'),
(46, 'E06', 'Ocupado', '5.00', '20.00'),
(47, 'E07', 'Disponible', '5.00', '20.00'),
(48, 'E08', 'Ocupado', '5.00', '20.00'),
(49, 'E09', 'Disponible', '5.00', '20.00'),
(50, 'E10', 'Ocupado', '5.00', '20.00'),
(51, 'F01', 'Disponible', '5.00', '20.00'),
(52, 'F02', 'Ocupado', '5.00', '20.00'),
(53, 'F03', 'Disponible', '5.00', '20.00'),
(54, 'F04', 'Ocupado', '5.00', '20.00'),
(55, 'F05', 'Disponible', '5.00', '20.00'),
(56, 'F06', 'Ocupado', '5.00', '20.00'),
(57, 'F07', 'Disponible', '5.00', '20.00'),
(58, 'F08', 'Ocupado', '5.00', '20.00'),
(59, 'F09', 'Disponible', '5.00', '20.00'),
(60, 'F10', 'Ocupado', '5.00', '20.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `habitacion`
--

CREATE TABLE `habitacion` (
  `id_habitacion` int(11) NOT NULL,
  `numero_habitacion` int(11) NOT NULL,
  `tipo_habitacion` varchar(50) NOT NULL,
  `precio_noche` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `habitacion`
--

INSERT INTO `habitacion` (`id_habitacion`, `numero_habitacion`, `tipo_habitacion`, `precio_noche`) VALUES
(1, 1, 'Individual', '50.00'),
(2, 2, 'Doble', '80.00'),
(3, 3, 'Suite', '150.00'),
(4, 4, 'Individual', '50.00'),
(5, 5, 'Doble', '80.00'),
(6, 6, 'Suite', '150.00'),
(7, 7, 'Individual', '50.00'),
(8, 8, 'Doble', '80.00'),
(9, 9, 'Suite', '150.00'),
(10, 10, 'Individual', '50.00'),
(11, 11, 'Doble', '80.00'),
(12, 12, 'Suite', '150.00'),
(13, 13, 'Individual', '50.00'),
(14, 14, 'Doble', '80.00'),
(15, 15, 'Suite', '150.00'),
(16, 16, 'Individual', '50.00'),
(17, 17, 'Doble', '80.00'),
(18, 18, 'Suite', '150.00'),
(19, 19, 'Individual', '50.00'),
(20, 20, 'Doble', '80.00'),
(21, 21, 'Suite', '150.00'),
(22, 22, 'Individual', '50.00'),
(23, 23, 'Doble', '80.00'),
(24, 24, 'Suite', '150.00'),
(25, 25, 'Individual', '50.00'),
(26, 26, 'Doble', '80.00'),
(27, 27, 'Suite', '150.00'),
(28, 28, 'Individual', '50.00'),
(29, 29, 'Doble', '80.00'),
(30, 30, 'Suite', '150.00'),
(31, 31, 'Individual', '50.00'),
(32, 32, 'Doble', '80.00'),
(33, 33, 'Suite', '150.00'),
(34, 34, 'Individual', '50.00'),
(35, 35, 'Doble', '80.00'),
(36, 36, 'Suite', '150.00'),
(37, 37, 'Individual', '50.00'),
(38, 38, 'Doble', '80.00'),
(39, 39, 'Suite', '150.00'),
(40, 40, 'Individual', '50.00'),
(41, 41, 'Doble', '80.00'),
(42, 42, 'Suite', '150.00'),
(43, 43, 'Individual', '50.00'),
(44, 44, 'Doble', '80.00'),
(45, 45, 'Suite', '150.00'),
(46, 46, 'Individual', '50.00'),
(47, 47, 'Doble', '80.00'),
(48, 48, 'Suite', '150.00'),
(49, 49, 'Individual', '50.00'),
(50, 50, 'Doble', '80.00'),
(51, 51, 'Suite', '150.00'),
(52, 52, 'Individual', '50.00'),
(53, 53, 'Doble', '80.00'),
(54, 54, 'Suite', '150.00'),
(55, 55, 'Individual', '50.00'),
(56, 56, 'Doble', '80.00'),
(57, 57, 'Suite', '150.00'),
(58, 58, 'Individual', '50.00'),
(59, 59, 'Doble', '80.00'),
(60, 60, 'Suite', '150.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historial_estacionamiento`
--

CREATE TABLE `historial_estacionamiento` (
  `id_historialest` int(11) NOT NULL,
  `dni_cliente` int(11) DEFAULT NULL,
  `numero_plaza` varchar(50) DEFAULT NULL,
  `hora_inicio` datetime DEFAULT NULL,
  `hora_fin` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `historial_estacionamiento`
--

INSERT INTO `historial_estacionamiento` (`id_historialest`, `dni_cliente`, `numero_plaza`, `hora_inicio`, `hora_fin`) VALUES
(1, 42317059, 'A01', '2024-11-16 19:58:51', '2024-11-16 22:20:11'),
(2, 42315055, 'A01', '2024-11-16 22:21:20', '2024-11-16 22:23:21'),
(3, 45548618, 'A01', '2024-11-16 22:53:05', '2024-11-17 01:03:45'),
(4, 45555666, 'A01', '2024-11-17 10:41:17', '2024-11-17 17:02:28'),
(5, 45893413, 'A03', '2024-11-17 10:43:01', '2024-11-17 13:07:13'),
(6, 56874588, 'A05', '2024-11-17 10:53:48', '2024-11-17 12:33:03'),
(7, 12340987, 'A01', '2024-08-15 08:00:00', '2024-08-15 18:30:00'),
(8, 98761234, 'B02', '2024-08-16 09:15:00', '2024-08-16 17:45:00'),
(9, 65432109, 'C03', '2024-08-20 10:30:00', '2024-08-20 19:00:00'),
(10, 34567891, 'D04', '2024-08-22 12:00:00', '2024-08-22 20:30:00'),
(11, 76543210, 'E05', '2024-08-25 14:45:00', '2024-08-25 21:15:00'),
(12, 56789023, 'F06', '2024-08-30 07:00:00', '2024-08-30 15:30:00'),
(13, 12345679, 'A07', '2024-09-02 11:20:00', '2024-09-02 18:00:00'),
(14, 89012346, 'B08', '2024-09-05 09:40:00', '2024-09-05 17:10:00'),
(15, 34567892, 'C09', '2024-09-10 13:15:00', '2024-09-10 22:45:00'),
(16, 56781235, 'D10', '2024-09-15 16:00:00', '2024-09-15 23:59:00'),
(17, 23456780, 'E01', '2024-09-18 08:30:00', '2024-09-18 15:45:00'),
(18, 89023457, 'F02', '2024-09-20 07:15:00', '2024-09-20 13:30:00'),
(19, 34561235, 'A03', '2024-09-25 11:45:00', '2024-09-25 18:20:00'),
(20, 45678901, 'B04', '2024-09-27 12:10:00', '2024-09-27 19:55:00'),
(21, 23456789, 'C05', '2024-10-01 09:00:00', '2024-10-01 17:30:00'),
(22, 56789024, 'D06', '2024-10-03 10:20:00', '2024-10-03 18:15:00'),
(23, 67890123, 'E07', '2024-10-06 07:30:00', '2024-10-06 14:45:00'),
(24, 78901234, 'F08', '2024-10-08 08:00:00', '2024-10-08 16:15:00'),
(25, 89012348, 'A09', '2024-10-11 09:20:00', '2024-10-11 17:40:00'),
(26, 34567893, 'B10', '2024-10-14 10:50:00', '2024-10-14 19:10:00'),
(27, 12340987, 'C01', '2024-10-15 12:30:00', '2024-10-15 20:00:00'),
(28, 98761234, 'D02', '2024-10-17 13:45:00', '2024-10-17 22:30:00'),
(29, 65432109, 'E03', '2024-10-19 07:00:00', '2024-10-19 15:10:00'),
(30, 34567891, 'F04', '2024-10-20 08:20:00', '2024-10-20 17:00:00'),
(31, 76543210, 'A05', '2024-10-22 09:40:00', '2024-10-22 18:50:00'),
(32, 56789023, 'B06', '2024-10-25 10:10:00', '2024-10-25 19:30:00'),
(33, 12345679, 'C07', '2024-10-26 11:25:00', '2024-10-26 20:00:00'),
(34, 89012346, 'D08', '2024-10-28 12:15:00', '2024-10-28 21:40:00'),
(35, 34567892, 'E09', '2024-10-30 13:45:00', '2024-10-30 23:59:00'),
(36, 56781235, 'F10', '2024-11-01 07:30:00', '2024-11-01 16:00:00'),
(37, 23456780, 'A02', '2024-11-03 08:00:00', '2024-11-03 17:20:00'),
(38, 89023457, 'B03', '2024-11-05 09:30:00', '2024-11-05 18:45:00'),
(39, 34561235, 'C04', '2024-11-07 10:50:00', '2024-11-07 19:40:00'),
(40, 45678901, 'D05', '2024-11-09 11:20:00', '2024-11-09 20:50:00'),
(41, 23456789, 'E06', '2024-11-10 07:40:00', '2024-11-10 15:50:00'),
(42, 56789024, 'F07', '2024-11-12 08:30:00', '2024-11-12 16:40:00'),
(43, 67890123, 'A08', '2024-11-14 09:15:00', '2024-11-14 17:30:00'),
(44, 78901234, 'B09', '2024-11-15 10:10:00', '2024-11-15 19:00:00'),
(45, 89012348, 'C10', '2024-11-17 11:25:00', '2024-11-17 20:15:00'),
(46, 34567893, 'D01', '2024-11-18 07:30:00', '2024-11-18 15:45:00'),
(47, 12340987, 'E02', '2024-11-20 08:20:00', '2024-11-20 17:40:00'),
(48, 98761234, 'F03', '2024-11-22 09:40:00', '2024-11-22 18:30:00'),
(49, 65432109, 'A10', '2024-11-24 10:10:00', '2024-11-24 19:50:00'),
(50, 34567891, 'B04', '2024-11-26 11:30:00', '2024-11-26 20:45:00'),
(51, 76543210, 'C05', '2024-11-27 12:50:00', '2024-11-27 22:15:00'),
(52, 56789023, 'D06', '2024-11-28 13:30:00', '2024-11-28 23:59:00'),
(53, 12345679, 'E07', '2024-11-30 07:45:00', '2024-11-30 16:00:00'),
(54, 42317059, 'A01', '2024-11-17 17:17:03', '2024-11-17 17:17:46'),
(55, 25456541, 'A01', '2024-11-17 17:26:19', '2024-11-17 18:48:46'),
(56, 54554651, 'A02', '2024-11-17 18:21:39', NULL),
(57, 45654125, 'A03', '2024-11-17 18:27:01', NULL),
(58, 45548678, 'A04', '2024-11-17 18:40:21', '2024-11-17 18:41:40'),
(59, 42317059, 'A04', '2024-11-17 18:47:13', '2024-11-17 18:48:21'),
(60, 45645124, 'A01', '2024-11-17 18:51:06', '2024-11-17 19:00:41'),
(61, 45678945, 'F01', '2024-11-17 18:53:35', '2024-11-17 18:55:56'),
(62, 45548617, 'A04', '2024-11-17 18:59:40', NULL),
(63, 46231456, 'A01', '2024-11-17 19:05:32', '2024-11-17 19:09:20'),
(64, 12345645, 'A05', '2024-11-17 19:08:14', NULL),
(65, 45645875, 'A07', '2024-11-17 19:10:21', '2024-11-17 19:12:24');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historial_pago`
--

CREATE TABLE `historial_pago` (
  `id_historialpago` int(11) NOT NULL,
  `num_transaccion` varchar(50) NOT NULL,
  `fecha_pago` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `estado_pago` varchar(20) NOT NULL,
  `id_reserva` int(11) DEFAULT NULL,
  `id_pago` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `historial_pago`
--

INSERT INTO `historial_pago` (`id_historialpago`, `num_transaccion`, `fecha_pago`, `estado_pago`, `id_reserva`, `id_pago`) VALUES
(1, 'TX001', '2024-10-12 03:00:00', 'Pagado', 1, 1),
(2, 'TX002', '2024-10-18 03:00:00', 'Pendiente', 2, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historial_reserva`
--

CREATE TABLE `historial_reserva` (
  `id_hisreserva` int(11) NOT NULL,
  `id_reserva` int(11) DEFAULT NULL,
  `fecha_modificacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `estado_anterior` varchar(20) DEFAULT NULL,
  `estado_actual` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `historial_reserva`
--

INSERT INTO `historial_reserva` (`id_hisreserva`, `id_reserva`, `fecha_modificacion`, `estado_anterior`, `estado_actual`) VALUES
(1, 1, '2024-10-01 03:00:00', 'Pendiente', 'Confirmada'),
(2, 2, '2024-10-05 03:00:00', 'Reservada', 'Pendiente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago`
--

CREATE TABLE `pago` (
  `id_pago` int(11) NOT NULL,
  `metodo_pago` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pago`
--

INSERT INTO `pago` (`id_pago`, `metodo_pago`) VALUES
(1, 'Tarjeta de crédito'),
(2, 'Transferencia bancaria'),
(3, 'Efectivo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personal`
--

CREATE TABLE `personal` (
  `id_personal` int(11) NOT NULL,
  `numero_empleado` varchar(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `dni` varchar(20) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `turno` varchar(20) DEFAULT NULL,
  `id_cargo` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `personal`
--

INSERT INTO `personal` (`id_personal`, `numero_empleado`, `nombre`, `apellido`, `dni`, `telefono`, `turno`, `id_cargo`) VALUES
(1, 'E001', 'Ana', 'López', '34567890', '234567890', 'Mañana', 1),
(2, 'E002', 'Luis', 'Martínez', '56789012', '345678901', 'Tarde', 2),
(3, 'E003', 'Sandra', 'Gómez', '78901234', '456789012', 'Noche', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva`
--

CREATE TABLE `reserva` (
  `id_reserva` int(11) NOT NULL,
  `fecha_ingreso` date NOT NULL,
  `fecha_salida` date NOT NULL,
  `estado_reserva` varchar(20) NOT NULL,
  `cantidad_huesped` int(11) NOT NULL,
  `dni` int(11) NOT NULL,
  `id_habitacion` int(11) DEFAULT NULL,
  `id_estacionamiento` int(11) DEFAULT NULL,
  `id_pago` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reserva`
--

INSERT INTO `reserva` (`id_reserva`, `fecha_ingreso`, `fecha_salida`, `estado_reserva`, `cantidad_huesped`, `dni`, `id_habitacion`, `id_estacionamiento`, `id_pago`) VALUES
(1, '2024-10-10', '2024-10-12', 'Confirmada', 1, 42315546, 1, 1, 1),
(2, '2024-10-15', '2024-10-18', 'Pendiente', 2, 2, 2, NULL, 2),
(3, '2024-10-20', '2024-10-25', 'Confirmada', 3, 3, 3, 3, 3),
(6, '2024-11-17', '2024-11-25', 'Confirmado', 5, 1, 6, 1, 2),
(7, '2024-11-10', '2024-11-20', 'Confirmado', 4, 2, 3, 4, 2),
(9, '2024-11-17', '2024-11-25', 'Confirmado', 3, 42317059, 9, -1, NULL),
(10, '2024-11-17', '2024-11-25', 'Confirmado', 2, 42317059, 6, -1, NULL),
(11, '2024-11-19', '2024-12-01', 'Confirmado', 3, 45548617, 15, NULL, 2),
(12, '2024-11-20', '2024-12-02', 'Confirmado', 1, 56589845, 20, 1, 3),
(13, '2024-11-23', '2024-11-30', 'Confirmado', 1, 45555666, 30, 2, 1),
(14, '2024-11-20', '2024-12-05', 'Cancelado', 3, 45893413, 40, 3, 3),
(15, '2024-11-19', '2024-12-01', 'Cancelado', 4, 56874588, 55, 5, 2),
(16, '2024-11-16', '2024-11-18', 'Confirmado', 1, 25456541, 1, 1, 1),
(17, '2024-11-18', '2024-11-23', 'Confirmado', 3, 54554651, 64, 2, 1),
(18, '2024-11-18', '2024-11-20', 'Confirmado', 2, 45654125, 17, 3, 1),
(19, '2024-11-18', '2024-11-25', 'Cancelado', 3, 45548678, 33, 4, 1),
(20, '2024-11-18', '2024-11-20', 'Confirmado', 1, 42317059, 36, 4, 1),
(21, '2024-11-20', '2024-12-01', 'Cancelado', 1, 45645124, 27, 1, 1),
(22, '2024-11-20', '2024-12-01', 'Confirmado', 2, 45548617, 57, 4, 1),
(23, '2024-11-20', '2024-11-30', 'Cancelado', 1, 46231456, 52, 1, 1),
(24, '2024-11-20', '2024-11-25', 'Confirmado', 2, 12345645, 21, 5, 3);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cargo`
--
ALTER TABLE `cargo`
  ADD PRIMARY KEY (`id_cargo`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id_cliente`),
  ADD UNIQUE KEY `dni` (`dni`);

--
-- Indices de la tabla `estacionamiento`
--
ALTER TABLE `estacionamiento`
  ADD PRIMARY KEY (`id_estacionamiento`),
  ADD UNIQUE KEY `uc_numero_plaza` (`numero_plaza`);

--
-- Indices de la tabla `habitacion`
--
ALTER TABLE `habitacion`
  ADD PRIMARY KEY (`id_habitacion`);

--
-- Indices de la tabla `historial_estacionamiento`
--
ALTER TABLE `historial_estacionamiento`
  ADD PRIMARY KEY (`id_historialest`),
  ADD KEY `fk_numero_plaza` (`numero_plaza`);

--
-- Indices de la tabla `historial_pago`
--
ALTER TABLE `historial_pago`
  ADD PRIMARY KEY (`id_historialpago`),
  ADD KEY `id_reserva` (`id_reserva`),
  ADD KEY `id_pago` (`id_pago`);

--
-- Indices de la tabla `historial_reserva`
--
ALTER TABLE `historial_reserva`
  ADD PRIMARY KEY (`id_hisreserva`),
  ADD KEY `id_reserva` (`id_reserva`);

--
-- Indices de la tabla `pago`
--
ALTER TABLE `pago`
  ADD PRIMARY KEY (`id_pago`);

--
-- Indices de la tabla `personal`
--
ALTER TABLE `personal`
  ADD PRIMARY KEY (`id_personal`),
  ADD UNIQUE KEY `numero_empleado` (`numero_empleado`),
  ADD UNIQUE KEY `dni` (`dni`),
  ADD KEY `id_cargo` (`id_cargo`);

--
-- Indices de la tabla `reserva`
--
ALTER TABLE `reserva`
  ADD PRIMARY KEY (`id_reserva`),
  ADD KEY `id_habitacion` (`id_habitacion`),
  ADD KEY `id_estacionamiento` (`id_estacionamiento`),
  ADD KEY `id_pago` (`id_pago`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cargo`
--
ALTER TABLE `cargo`
  MODIFY `id_cargo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT de la tabla `estacionamiento`
--
ALTER TABLE `estacionamiento`
  MODIFY `id_estacionamiento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- AUTO_INCREMENT de la tabla `habitacion`
--
ALTER TABLE `habitacion`
  MODIFY `id_habitacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- AUTO_INCREMENT de la tabla `historial_estacionamiento`
--
ALTER TABLE `historial_estacionamiento`
  MODIFY `id_historialest` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=66;

--
-- AUTO_INCREMENT de la tabla `historial_pago`
--
ALTER TABLE `historial_pago`
  MODIFY `id_historialpago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `historial_reserva`
--
ALTER TABLE `historial_reserva`
  MODIFY `id_hisreserva` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `pago`
--
ALTER TABLE `pago`
  MODIFY `id_pago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `personal`
--
ALTER TABLE `personal`
  MODIFY `id_personal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `reserva`
--
ALTER TABLE `reserva`
  MODIFY `id_reserva` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `historial_estacionamiento`
--
ALTER TABLE `historial_estacionamiento`
  ADD CONSTRAINT `fk_numero_plaza` FOREIGN KEY (`numero_plaza`) REFERENCES `estacionamiento` (`numero_plaza`);

--
-- Filtros para la tabla `historial_pago`
--
ALTER TABLE `historial_pago`
  ADD CONSTRAINT `historial_pago_ibfk_1` FOREIGN KEY (`id_reserva`) REFERENCES `reserva` (`id_reserva`),
  ADD CONSTRAINT `historial_pago_ibfk_2` FOREIGN KEY (`id_pago`) REFERENCES `pago` (`id_pago`);

--
-- Filtros para la tabla `historial_reserva`
--
ALTER TABLE `historial_reserva`
  ADD CONSTRAINT `historial_reserva_ibfk_1` FOREIGN KEY (`id_reserva`) REFERENCES `reserva` (`id_reserva`);

--
-- Filtros para la tabla `personal`
--
ALTER TABLE `personal`
  ADD CONSTRAINT `personal_ibfk_1` FOREIGN KEY (`id_cargo`) REFERENCES `cargo` (`id_cargo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
