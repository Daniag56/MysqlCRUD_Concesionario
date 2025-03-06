package productos;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;


import crud.CRUD;


public class GestorInventario implements CRUD<Producto>{
    Connection conn;
    public GestorInventario(Connection conn){
        this.conn = conn;
    }
   
    @Override
    /**
     * Method, that returns all the products order by the column the user select and the order the user select.
     *
     */
    public ArrayList<Producto> requestAll(String sortedBy) throws SQLException {
        ArrayList<Producto> resultado = new ArrayList<>();
        String query = "SELECT * FROM productos ORDER BY " + sortedBy;
        try(Statement stmt = this.conn.createStatement()){
            ResultSet result = stmt.executeQuery(query);
            while(result.next()){
                long productoID = result.getInt("codigo");
                String productoDescripcion= result.getString("descripcion");
                double producotPrecioCompra = result.getDouble("preciocompra");
                double productoPrecioVenta = result.getDouble("precioventa");
                int productoStock = result.getInt("stock");
                resultado.add(new Producto(productoID, productoDescripcion, producotPrecioCompra, productoPrecioVenta, productoStock));
            }
            return resultado;
        } catch (SQLException e) {
            throw e;
        }
    }  




    @Override
    /**
     * Method that return the product with the id that the user introduced.
     */
    public Producto requestById(long códigoProducto) throws SQLException{
        Producto result = new Producto(0, "", 0, 0, 0);
        String sqlQuery = "SELECT * FROM productos WHERE codigo = ?";


        try (PreparedStatement stmt = this.conn.prepareStatement(sqlQuery)) {
            stmt.setLong(1, códigoProducto);
            ResultSet resultado = stmt.executeQuery();
            if(resultado.next()){
                long codigoProducto = resultado.getLong("codigo");
                String descripcionProducto = resultado.getString("descripcion");
                double precioCompraProducto = resultado.getDouble("precioCompra");
                double precioVentaProducto = resultado.getDouble("precioventa");
                int stockProducto = resultado.getInt("preciocompra");
                result.setCodigo(codigoProducto);
                result.setDescripcion(descripcionProducto);
                result.setPrecioCompra(precioCompraProducto);
                result.setPrecioVenta(precioVentaProducto);
                result.setStock(stockProducto);
            }
            return result;
        } catch (SQLException e) {
            throw e;
        }
    }


    @Override
    /**
     * Method that takes the object `Producto` that the user introduced and create a new entry in the data base.
     */
    public boolean create(Producto producto) throws SQLException{
        long codigoProducto = producto.getCodigo();
        String descripcionProducto = producto.getDescripcion();
        double precioCompraProducto = producto.getPrecioCompra();
        double precioVentaProducto = producto.getPrecioVenta();
        int stockProducto = producto.getStock();
        int affectedRows = 0;
        String sqlQuery = "INSERT INTO productos (codigo, descripcion, preciocompra, precioventa, stock) VALUES(?, ?, ?, ?, ?)";


        try (PreparedStatement stmt = this.conn.prepareStatement(sqlQuery)) {
            stmt.setLong(1, codigoProducto);
            stmt.setString(2, descripcionProducto);
            stmt.setDouble(3, precioCompraProducto);
            stmt.setDouble(4, precioVentaProducto);
            stmt.setInt(5, stockProducto);
            affectedRows = stmt.executeUpdate();
            if(affectedRows != 0){
                System.out.println("Añadido con éxito");
            }


            if (affectedRows == 0) throw new SQLException("Falló la creación del nuevo producto.");
            return affectedRows == 1;
        } catch (SQLException e) {
            throw e;
        }
    }


    @Override
    /**
     * Method that takes the object `Producto` that the user introduced and update the entry of the prduct.  
     */
    public boolean update(Producto producto) throws SQLException{  
        long codigoProducto = producto.getCodigo();
        String descripcionProducto = producto.getDescripcion();
        double precioCompraProducto = producto.getPrecioCompra();
        double precioVentaProducto = producto.getPrecioVenta();
        int stockProducto = producto.getStock();
        int affectedRows = 0;
        String sqlQuery = "UPDATE productos SET descripcion = ?, preciocompra = ?, precioventa = ?, stock = ? WHERE codigo = ?";


        try (PreparedStatement stmt = this.conn.prepareStatement(sqlQuery)) {
            stmt.setString(1, descripcionProducto);
            stmt.setDouble(2, precioCompraProducto);
            stmt.setDouble(3, precioVentaProducto);
            stmt.setInt(4, stockProducto);
            stmt.setLong(5, codigoProducto);
            affectedRows = stmt.executeUpdate();
            if(affectedRows != 0){
                System.out.println("Actualizado con éxito.");
            }
            if (affectedRows == 0) throw new SQLException("Falló la actualización de los datos del producto.");
            return affectedRows == 1;
        } catch (SQLException e) {
            throw e;
        }
    }


    @Override
    /**
     * Method that takes the ID  that the user introduced and deletes the entry entry in the data base.
     */
    public boolean delete(long códigoProcucto) throws SQLException{
        String sqlQuery = "DELETE FROM productos WHERE codigo = ?";
        int affectedRows = 0;


        try (PreparedStatement stmt = this.conn.prepareStatement(sqlQuery)) {
            stmt.setLong(1, códigoProcucto);
            affectedRows = stmt.executeUpdate();
            if(affectedRows != 0){
                System.out.println("Eliminado con éxito.");
                affectedRows = 1;
            }


            if (affectedRows == 0) throw new SQLException("Falló el borrado de los datos del producto.");
            return affectedRows == 1;
        } catch (SQLException e) {
            throw e;
        }
    }


    public ArrayList<Producto> getAll() throws SQLException{
        String sqlQuery = "SELECT * FROM productos";
        ArrayList<Producto> productos = new ArrayList<>();
        try (Statement stmt = this.conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                Producto producto = new Producto(rs.getLong("codigo"), rs.getString("descripcion"), rs.getDouble("preciocompra"), rs.getDouble("precioventa"), rs.getInt("stock"));
                productos.add(producto);
            }
            return productos;
        }catch (SQLException e) {
            throw e;
        }
    }
}
