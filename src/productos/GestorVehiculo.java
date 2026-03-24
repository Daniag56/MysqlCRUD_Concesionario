package productos;

import crud.GestorGenerico;
import java.sql.Connection;
import java.sql.SQLException;

public class GestorVehiculo extends GestorGenerico<Vehiculo> {

    public GestorVehiculo(Connection conn) {
        super(conn, "vehiculos", (rs) -> new Vehiculo(
                rs.getLong("matricula"),
                rs.getLong("numBastidor"),
                rs.getInt("cv"),
                rs.getString("descripcion"),
                rs.getDouble("precioCompra"),
                rs.getDouble("precioVenta"),
                rs.getString("marca"),
                rs.getString("modelo")
        ));
    }

    @Override
    public boolean create(Vehiculo v) throws SQLException {
        return executeUpdate(
                "INSERT INTO vehiculos VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                v.getMatricula(), v.getNumBastidor(), v.getCv(),
                v.getDescripcion(), v.getPrecioCompra(), v.getPrecioVenta(),
                v.getMarca(), v.getModelo()
        );
    }

    @Override
    public boolean update(Vehiculo v) throws SQLException {
        return executeUpdate(
                "UPDATE vehiculos SET numBastidor=?, cv=?, descripcion=?, precioCompra=?, precioVenta=?, marca=?, modelo=? WHERE matricula=?",
                v.getNumBastidor(), v.getCv(), v.getDescripcion(),
                v.getPrecioCompra(), v.getPrecioVenta(),
                v.getMarca(), v.getModelo(), v.getMatricula()
        );
    }

    public java.util.ArrayList<Vehiculo> getAll() throws SQLException {
        return queryList("SELECT * FROM vehiculos");
    }
}
