import usuario.Interactuador;

public class Concesionario implements Interactuador {

    public static final String RED_BRIGHT = "\033[0;91m";
    public static final String GREEN_BRIGHT = "\033[0;92m";
    public static final String YELLOW_BRIGHT = "\033[0;93m";
    public static final String RESET = "\033[0m";

    public static void main(String[] args) throws Exception {
        final int OPCION_MAX = 6;
        int eleccion;

        do {
            System.out.printf(
                    "\n%s¡Bienvenido a%s %sGESTISIMAL%s (%sGesti%són %ssim%splificada de %sal%smacén)!\n",
                    YELLOW_BRIGHT, RESET, GREEN_BRIGHT, RESET,
                    GREEN_BRIGHT, RESET, GREEN_BRIGHT, RESET, GREEN_BRIGHT, RESET);

            System.out.println("Por favor, seleccione una de estas opciones:");
            System.out.println("(1) Consulta ordenada de vehículos.");
            System.out.println("(2) Consulta de datos por número de bastidor.");
            System.out.println("(3) Alta de nuevo vehículo.");
            System.out.println("(4) Actualización de los datos de un vehículo.");
            System.out.println("(5) Baja de un vehículo.");
            System.out.println("(6) Salir del programa.");
            System.out.printf("%sOpción:%s ", GREEN_BRIGHT, RESET);

            eleccion = Interactuador.solicitarElección(OPCION_MAX);

            switch (eleccion) {
                case 1:
                    Interactuador.consultaOrdenada();
                    break;

                case 2:
                    Interactuador.consultaPorBastidor();
                    break;

                case 3:
                    Interactuador.altaVehiculo();
                    break;

                case 4:
                    Interactuador.modificaciónVehiculo();
                    break;

                case 5:
                    Interactuador.bajaVehiculo();
                    break;
            }

        } while (eleccion != OPCION_MAX);

        Interactuador.cerrarScanner();
    }
}
