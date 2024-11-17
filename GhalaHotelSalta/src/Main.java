import bd.ConexionBD;
import vista.MenuPrincipal;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conexion = ConexionBD.obtenerConexion();

        if (conexion != null) {
            MenuPrincipal menu = new MenuPrincipal();
            menu.mostrarMenu();
        } else {
            System.out.println("No se pudo conectar a la base de datos. Verifique la configuración e intente nuevamente.");
        }

        ConexionBD.cerrarConexion();
    }
}
