package usuario;


import java.util.ArrayList;
import java.util.Scanner;


import pool.ConnectionPool;
import productos.Producto;
import productos.GestorInventario;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;


public interface Interactuador {    
    // Configuración de la conexión a la base de datos
    final String URL = "jdbc:mariadb://localhost:3306/gestisimal";
    final String USUARIO = "root";
    final String CLAVE = "";


    ConnectionPool pool = new ConnectionPool(URL, USUARIO, CLAVE);
    GestorInventario miGestor = new GestorInventario(pool.getConnection());    
    Scanner sc = new Scanner(System.in);


    // Consulta ordenada de productos
    /**
     * Method that let the user select the option of how he wanna order the products on the output
     */
    public static void consultaOrdenada() {
        ArrayList<Producto> productos = new ArrayList<Producto>();
        System.out.println("Opciones ordenación");
        System.out.println("1. Código");
        System.out.println("2. Descripción");
        System.out.println("3. Precio de Compra");
        System.out.println("4. Precio de Venta");
        System.out.println("5. Stock");
        System.out.print("Escoja una elección: ");
        int eleccion = (int) solicitarElección(5);




        try{
            String query = "";
            switch (eleccion) {
                case 1:
                    query = "codigo";
                    query += solicitarOrden();
                    productos = miGestor.requestAll(query);
                    break;
                case 2:
                    query = "descripcion";
                    query += solicitarOrden();
                    productos = miGestor.requestAll(query);
                    break;
                case 3:
                    query = "preciocompra";
                    query += solicitarOrden();
                    productos = miGestor.requestAll(query);
                    break;
                case 4:
                    query = "precioventa";
                    query += solicitarOrden();
                    productos = miGestor.requestAll(query);
                    break;
                case 5:
                    query = "stock";
                    query += solicitarOrden();
                    productos = miGestor.requestAll(query);
                    break;
            }
            if(productos.size() == 0){
                System.out.println("Error: No se encontraron productos.");
            }else{
                for(Producto producto: productos){
                    System.out.println(producto);
                }
                exportar(productos);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    // Consulta de datos por ID
    /**
     * Method that let's the user search a product by ID
     */
    public static void consultaPorCódigo() {
        System.out.print("Introduzca el código del producto que desea imprimir: ");
        long codigo = (long) solicitarValorNumérico(Long.class);


        try{  
            Producto producto = miGestor.requestById(codigo);
            if(!(producto == null)){
                System.out.println(producto);
            }else{
                System.out.println("Error: No se encontro nigún producto por ese ID.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    // Alta de nuevo producto
    /**
     * Method that let's the user create an entry of the data base
     */
    public static void altaProducto() {    
        System.out.print("Introduzca el código del producto: ");
        long codigo = (long)solicitarValorNumérico(Long.class);
        System.out.print("Introduzca la descripción del producto: ");
        String descripcion = sc.nextLine();
        System.out.print("Introduzca el precio de compra: ");
        double precioCompra = (double)solicitarValorNumérico(Double.class);
        System.out.print("Introduzca el precio de venta: ");
        double precioVenta = (double)solicitarValorNumérico(Double.class);
        System.out.print("Introduzca el stock del producto: ");
        int stock = (int)solicitarValorNumérico(Integer.class);
        Producto producto = new Producto(codigo, descripcion, precioCompra, precioVenta, stock);


        try{  
            if(!miGestor.create(producto)){
                System.out.println("Error: error al crear producto.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    // Actualización de los datos de un producto
    /**
     * Method that let's the user update an entry of the data base
     */
    public static void modificaciónProducto() {      
        System.out.print("Introduzca el código del producto que desea modificar: ");
        long codigo = (long)solicitarValorNumérico(Long.class);
        System.out.print("Intoduzca la nueva descripción: ");
        String descipcion = sc.nextLine();
        System.out.print("Introduzca el nuevo precio de venta: ");
        double precioCompra = (double)solicitarValorNumérico(Double.class);
        System.out.print("Introduzca el nuevo precio de venta: ");
        double precioVenta = (double)solicitarValorNumérico(Double.class);
        System.out.print("Introduzca el nuevo número de stock del producto: ");
        int stock = (int)solicitarValorNumérico(Integer.class);
        Producto producto = new Producto(codigo, descipcion, precioCompra, precioVenta, stock);


        try{
            if(!miGestor.update(producto)){
                System.out.println("Error: error al modificar producto.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    // Baja de un producto
    /**
     * Method that let's the user delete an entry on the data base
     */
    public static void bajaProducto() {  
        System.out.print("Introduzca el código del producto que desea dar de baja: ");
        long codigo = (long)solicitarValorNumérico(Long.class);


        try{
            if(!miGestor.delete(codigo)){
                System.out.println("Error: error al borrar producto.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    // Solicitar opción al usuario
    public static int solicitarElección(int OPCIÓN_MAX) {
        int elección = 0;
        try {
            elección = sc.nextInt();
        if (elección < 1 || elección > OPCIÓN_MAX) throw new IllegalArgumentException();
        } catch (Exception e) {
            System.out.println("Opción inválida.\n");
        } finally {
            sc.nextLine(); // Limpiamos buffer de entrada
        }
        return elección;
    }


     // Solicitar valor numérico al usuario    
     // Ejemplo de uso 1: int existencias = (Integer) solicitarValorNumérico(Integer.class);
     // Ejemplo de uso 2: long código = (Long) solicitarValorNumérico(Long.class);
     // Ejemplo de uso 3: double precioCompra = (Double) solicitarValorNumérico(Double.class);
    private static Object solicitarValorNumérico(Class<?> tipo) {
        Object resultado = null;
        while (resultado == null) {
            try {
                if (tipo == Short.class) resultado = sc.nextShort();
                else if (tipo == Integer.class) resultado = sc.nextInt();
                else if (tipo == Long.class) resultado = sc.nextLong();
                else if (tipo == Float.class) resultado = sc.nextFloat();
                else if (tipo == Double.class) resultado = sc.nextDouble();
                else System.out.println("Tipo de dato no soportado.");
            } catch (Exception e) {
                System.out.print("Valor inválido.\nPruebe de nuevo: ");
            } finally {
                sc.nextLine(); // Limpiamos buffer de entrada
            }
        }        
        return resultado;
    }


    /**
     * A method that asks the user the order of how he wanna see the outputs of the productos.
     * @return The order taht the product are going to be outputed
     */
    private static String solicitarOrden(){
        System.out.println("1. Ascendente");
        System.out.println("2. Descendente");
        System.out.print("Seleccione el orden en el que desea imprimir los resultados:");
        switch (solicitarElección(2)) {
            case 1:
                return " ASC";
            case 2:
                return " DESC";
            default:
                return "null";
        }
    }


    public static void exportar(ArrayList<Producto> arrayProductos){
        System.out.println("¿Desea exportar los datos de un archivo?(Y/N)");
        String elección = sc.nextLine();
        if(elección.toLowerCase().equals("n")){
           
        }else{
            System.out.print("Introduzca el nombre del archivo con el que quiere guardar los datos: ");
            String nombreArchivo = sc.nextLine();
            try(BufferedWriter escribir = new BufferedWriter(new FileWriter(nombreArchivo + ".csv"))){
            for(Producto productos : arrayProductos){
                String linea = productos.seralizar();
                escribir.write(linea + "\n");
            }
            }catch (IOException ioe){
                System.out.println(ioe.getMessage());
            }
        }
    }


    public static void importar(){
        System.out.print("Escriba el nombre del archivo que quiere importar: ");
        String nombreArchivo = sc.nextLine();
        String[] datos = new String[5];
        try(BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo + ".csv"))){
            String linea= "";
            while((linea = lector.readLine()) != null){
                datos = linea.split(",");
                Producto producto = new Producto(Long.parseLong(datos[0]), datos[1], Double.parseDouble(datos[2]), Double.parseDouble(datos[3]), Integer.valueOf(datos[4]));
                try{
                    if(!miGestor.create(producto)){
                        System.out.println("Error: Algo salio mal en la importación");
                    }
                }catch (Exception e){
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }


    public static void exportarAll(){
        System.out.print("Escriba el nombre del archivo con el que quiere guardar los datos: ");
        String nombreArchivo = sc.nextLine();
        try(BufferedWriter escribir = new BufferedWriter(new FileWriter(nombreArchivo + ".csv"))){
            for(Producto producto : miGestor.getAll()){
                String linea = producto.seralizar();
                escribir.write(linea + "\n");
            }
        }catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }


    // Cerrar scanner
    public static void cerrarScanner() {
        sc.close();
    }


}
