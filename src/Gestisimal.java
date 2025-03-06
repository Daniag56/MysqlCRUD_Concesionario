import usuario.Interactuador;

public class Gestisimal implements Interactuador {
    
    public static final String RED_BRIGHT = "\033[0;91m"; // RED
    public static final String GREEN_BRIGHT = "\033[0;92m"; // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String RESET = "\033[0m"; // Text Reset

    public static void main(String[] args) throws Exception {
        // Variables y constantes
        final int OPCIÓN_MAX = 6;
        int elección;

        // Menú
        do {
            System.out.printf("\n%s¡Bienvenido a%s %sGESTISIMAL%s (%sGesti%són %ssim%splificada de %sal%smacén)!\n", 
            YELLOW_BRIGHT, RESET, GREEN_BRIGHT, RESET, GREEN_BRIGHT, RESET, GREEN_BRIGHT, RESET, GREEN_BRIGHT, RESET);
            System.out.println("Por favor, seleccione una de estas opciones:");
            System.out.println("(1) Consulta ordenada de productos.");
            System.out.println("(2) Consulta de datos por código.");
            System.out.println("(3) Alta de nuevo producto.");
            System.out.println("(4) Actualización de los datos de un producto.");
            System.out.println("(5) Baja de un producto.");
            System.out.println("(6) Salir del programa.");
            System.out.printf("%sOpción:%s ", GREEN_BRIGHT, RESET);
            elección = Interactuador.solicitarElección(OPCIÓN_MAX);

            switch (elección) {
            case 1: // Consulta ordenada de productos
                Interactuador.consultaOrdenada();
                break;

            case 2: // Consulta de datos por código
                Interactuador.consultaPorCódigo();
                break;

            case 3: // Alta de nuevo producto
                Interactuador.altaProducto();
                break;

            case 4: // Actualización de los datos de un producto
                Interactuador.modificaciónProducto();
                break;

            case 5: // Baja de un producto
                Interactuador.bajaProducto();
                break;     
            }
        } while (elección != OPCIÓN_MAX);
        Interactuador.cerrarScanner();
    }
}
