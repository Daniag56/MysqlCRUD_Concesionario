package vehiculo;

import crud.GestorGenerico;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GestorVehiculo extends GestorGenerico<Vehiculo> {

    public GestorVehiculo(Connection conn) {
        super(conn, "vehiculos", "numBastidor",
                rs -> new Vehiculo(
                        rs.getString("matricula"),     
                        rs.getLong("numBastidor"),
                        rs.getInt("cv"),
                        rs.getString("descripcion"),
                        rs.getDouble("precioCompra"),
                        rs.getDouble("precioVenta"),
                        rs.getString("marca"),
                        rs.getString("modelo")));
    }

    @Override
    public boolean insert(Vehiculo v) throws SQLException {
        return execute(
                "INSERT INTO vehiculos (matricula, numBastidor, cv, descripcion, precioCompra, precioVenta, marca, modelo) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                v.getMatricula(), v.getNumBastidor(), v.getCv(),
                v.getDescripcion(), v.getPrecioCompra(), v.getPrecioVenta(),
                v.getMarca(), v.getModelo());
    }

    @Override
    public boolean update(Vehiculo v) throws SQLException {
        return execute(
                "UPDATE vehiculos SET matricula=?, cv=?, descripcion=?, precioCompra=?, precioVenta=?, marca=?, modelo=? "
                        + "WHERE numBastidor=?",
                v.getMatricula(), v.getCv(), v.getDescripcion(),
                v.getPrecioCompra(), v.getPrecioVenta(),
                v.getMarca(), v.getModelo(), v.getNumBastidor());
    }

    public List<Vehiculo> getAll() throws SQLException {
        return queryList("SELECT * FROM vehiculos");
    }
}
