package usuario;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import pool.ConnectionPool;
import vehiculo.GestorVehiculo;
import vehiculo.Vehiculo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;

public interface Interactuador {

    final String URL = "jdbc:postgresql://db.gkwtbmbpjtnhhimkuwnu.supabase.co:5432/postgres";
    final String USUARIO = "Concesionario";
    final String CLAVE = "Alozaina.12";

    ConnectionPool pool = new ConnectionPool(URL, USUARIO, CLAVE);
    GestorVehiculo miGestor = new GestorVehiculo(pool.getConnection());
    Scanner sc = new Scanner(System.in);

    public static void consultaOrdenada() {
        List<Vehiculo> vehiculos = new ArrayList<>();

        System.out.println("Opciones ordenación");
        System.out.println("1. Matrícula");
        System.out.println("2. Número de Bastidor");
        System.out.println("3. Caballos (CV)");
        System.out.println("4. Descripción");
        System.out.println("5. Precio de Compra");
        System.out.println("6. Precio de Venta");
        System.out.println("7. Marca");
        System.out.println("8. Modelo");
        System.out.print("Escoja una elección: ");

        int eleccion = solicitarElección(8);

        try {
            String query = "";

            switch (eleccion) {
                case 1:
                    query = "matricula";
                    break;
                case 2:
                    query = "numBastidor";
                    break;
                case 3:
                    query = "cv";
                    break;
                case 4:
                    query = "descripcion";
                    break;
                case 5:
                    query = "precioCompra";
                    break;
                case 6:
                    query = "precioVenta";
                    break;
                case 7:
                    query = "marca";
                    break;
                case 8:
                    query = "modelo";
                    break;
            }

            query += solicitarOrden();
            vehiculos = miGestor.requestAll(query);

            if (vehiculos.isEmpty()) {
                System.out.println("Error: No se encontraron vehículos.");
            } else {
                for (Vehiculo v : vehiculos) {
                    System.out.println(v);
                }
                exportar(new ArrayList<>(vehiculos));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void consultaPorBastidor() {
        System.out.print("Introduzca el número de bastidor del vehículo: ");
        long numBastidor = (long) solicitarValorNumérico(Long.class);

        try {
            Vehiculo vehiculo = miGestor.requestById(numBastidor);
            if (vehiculo != null) {
                System.out.println(vehiculo);
            } else {
                System.out.println("Error: No se encontró ningún vehículo con ese número de bastidor.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void altaVehiculo() {
        System.out.print("Introduzca la matrícula: ");
        long matricula = (long) solicitarValorNumérico(Long.class);

        System.out.print("Introduzca el número de bastidor: ");
        long numBastidor = (long) solicitarValorNumérico(Long.class);

        System.out.print("Introduzca los caballos (CV): ");
        int cv = (int) solicitarValorNumérico(Integer.class);

        System.out.print("Introduzca la descripción: ");
        String descripcion = sc.nextLine();

        System.out.print("Introduzca el precio de compra: ");
        double precioCompra = (double) solicitarValorNumérico(Double.class);

        System.out.print("Introduzca el precio de venta: ");
        double precioVenta = (double) solicitarValorNumérico(Double.class);

        System.out.print("Introduzca la marca: ");
        String marca = sc.nextLine();

        System.out.print("Introduzca el modelo: ");
        String modelo = sc.nextLine();

        Vehiculo vehiculo = new Vehiculo(matricula, numBastidor, cv, descripcion, precioCompra, precioVenta, marca,
                modelo);

        try {
            if (!miGestor.insert(vehiculo)) {
                System.out.println("Error: no se pudo crear el vehículo.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void modificaciónVehiculo() {
        System.out.print("Introduzca el número de bastidor del vehículo que desea modificar: ");
        long numBastidor = (long) solicitarValorNumérico(Long.class);

        System.out.print("Introduzca la nueva matrícula: ");
        long matricula = (long) solicitarValorNumérico(Long.class);

        System.out.print("Introduzca los nuevos caballos (CV): ");
        int cv = (int) solicitarValorNumérico(Integer.class);

        System.out.print("Introduzca la nueva descripción: ");
        String descripcion = sc.nextLine();

        System.out.print("Introduzca el nuevo precio de compra: ");
        double precioCompra = (double) solicitarValorNumérico(Double.class);

        System.out.print("Introduzca el nuevo precio de venta: ");
        double precioVenta = (double) solicitarValorNumérico(Double.class);

        System.out.print("Introduzca la nueva marca: ");
        String marca = sc.nextLine();

        System.out.print("Introduzca el nuevo modelo: ");
        String modelo = sc.nextLine();

        Vehiculo vehiculo = new Vehiculo(matricula, numBastidor, cv, descripcion, precioCompra, precioVenta, marca,
                modelo);

        try {
            if (!miGestor.update(vehiculo)) {
                System.out.println("Error: no se pudo modificar el vehículo.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void bajaVehiculo() {
        System.out.print("Introduzca el número de bastidor del vehículo que desea dar de baja: ");
        long numBastidor = (long) solicitarValorNumérico(Long.class);

        try {
            if (!miGestor.delete(numBastidor)) {
                System.out.println("Error: no se pudo borrar el vehículo.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static int solicitarElección(int OPCIÓN_MAX) {
        int elección = 0;
        try {
            elección = sc.nextInt();
            if (elección < 1 || elección > OPCIÓN_MAX)
                throw new IllegalArgumentException();
        } catch (Exception e) {
            System.out.println("Opción inválida.\n");
        } finally {
            sc.nextLine();
        }
        return elección;
    }

    private static Object solicitarValorNumérico(Class<?> tipo) {
        Object resultado = null;
        while (resultado == null) {
            try {
                if (tipo == Short.class)
                    resultado = sc.nextShort();
                else if (tipo == Integer.class)
                    resultado = sc.nextInt();
                else if (tipo == Long.class)
                    resultado = sc.nextLong();
                else if (tipo == Float.class)
                    resultado = sc.nextFloat();
                else if (tipo == Double.class)
                    resultado = sc.nextDouble();
                else
                    System.out.println("Tipo no soportado.");
            } catch (Exception e) {
                System.out.print("Valor inválido.\nPruebe de nuevo: ");
            } finally {
                sc.nextLine();
            }
        }
        return resultado;
    }

    private static String solicitarOrden() {
        System.out.println("1. Ascendente");
        System.out.println("2. Descendente");
        System.out.print("Seleccione el orden: ");

        switch (solicitarElección(2)) {
            case 1:
                return " ASC";
            case 2:
                return " DESC";
            default:
                return "";
        }
    }

    public static void exportar(ArrayList<Vehiculo> vehiculos) {
        System.out.println("¿Desea exportar los datos a un archivo? (Y/N)");
        String eleccion = sc.nextLine();

        if (eleccion.equalsIgnoreCase("n"))
            return;

        System.out.print("Introduzca el nombre del archivo: ");
        String nombreArchivo = sc.nextLine();

        try (BufferedWriter escribir = new BufferedWriter(new FileWriter(nombreArchivo + ".csv"))) {
            for (Vehiculo v : vehiculos) {
                escribir.write(v.serializar() + "\n");
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public static void importar() {
        System.out.print("Escriba el nombre del archivo a importar: ");
        String nombreArchivo = sc.nextLine();

        try (BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo + ".csv"))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");

                Vehiculo vehiculo = new Vehiculo(
                        Long.parseLong(datos[0]),
                        Long.parseLong(datos[1]),
                        Integer.parseInt(datos[2]),
                        datos[3],
                        Double.parseDouble(datos[4]),
                        Double.parseDouble(datos[5]),
                        datos[6],
                        datos[7]);

                try {
                    if (!miGestor.insert(vehiculo)) {
                        System.out.println("Error al importar vehículo.");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public static void exportarAll() {
        System.out.print("Escriba el nombre del archivo: ");
        String nombreArchivo = sc.nextLine();

        try (BufferedWriter escribir = new BufferedWriter(new FileWriter(nombreArchivo + ".csv"))) {
            for (Vehiculo v : miGestor.getAll()) {
                escribir.write(v.serializar() + "\n");
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void cerrarScanner() {
        sc.close();
    }
}
