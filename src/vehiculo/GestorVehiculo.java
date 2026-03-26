package vehiculo;

import crud.GestorGenerico;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * GestorVehiculo gestiona las operaciones CRUD sobre la tabla "vehiculos".
 *
 * <p>Esta clase extiende {@link GestorGenerico} y proporciona la lógica
 * específica para mapear objetos {@link Vehiculo} con los registros de la base
 * de datos.</p>
 *
 * <h2>IMPORTANTE SOBRE LOS NOMBRES DE COLUMNA</h2>
 * <p>
 * En PostgreSQL, cuando una columna se crea con mayúsculas (por ejemplo:
 * <code>numBastidor</code>), el motor la almacena internamente como un
 * identificador <strong>sensible a mayúsculas</strong>. Esto significa que
 * solo puede ser referenciada usando comillas dobles:
 * </p>
 *
 * <pre>
 * "numBastidor"
 * </pre>
 *
 * <p>
 * Si se escribe sin comillas, PostgreSQL convierte el nombre a minúsculas
 * (<code>numbastidor</code>) y provoca el error:
 * </p>
 *
 * <pre>
 * ERROR: column "numbastidor" does not exist
 * </pre>
 *
 * <p>
 * Por este motivo, todas las columnas con mayúsculas deben escribirse entre
 * comillas dobles en las sentencias SQL.
 * </p>
 */
public class GestorVehiculo extends GestorGenerico<Vehiculo> {

    /**
     * Constructor del gestor.
     *
     * @param conn conexión activa a la base de datos.
     *
     * Se indica el nombre de la tabla ("vehiculos") y la clave primaria,
     * que debe ir entre comillas porque contiene mayúsculas.
     */
    public GestorVehiculo(Connection conn) {
        super(conn, "vehiculos", "\"numBastidor\"",
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

    /**
     * Inserta un nuevo vehículo en la base de datos.
     *
     * <p>
     * Todas las columnas con mayúsculas deben ir entre comillas dobles
     * para evitar que PostgreSQL las convierta a minúsculas.
     * </p>
     *
     * @param v vehículo a insertar.
     * @return true si la operación fue exitosa.
     * @throws SQLException si ocurre un error SQL.
     */
    @Override
    public boolean insert(Vehiculo v) throws SQLException {
        return execute(
                "INSERT INTO vehiculos (\"matricula\", \"numBastidor\", \"cv\", \"descripcion\", \"precioCompra\", \"precioVenta\", \"marca\", \"modelo\") "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                v.getMatricula(), v.getNumBastidor(), v.getCv(),
                v.getDescripcion(), v.getPrecioCompra(), v.getPrecioVenta(),
                v.getMarca(), v.getModelo());
    }

    /**
     * Actualiza un vehículo existente.
     *
     * <p>
     * Igual que en el INSERT, todas las columnas con mayúsculas deben ir
     * entre comillas dobles.
     * </p>
     *
     * @param v vehículo con los datos actualizados.
     * @return true si la operación fue exitosa.
     * @throws SQLException si ocurre un error SQL.
     */
    @Override
    public boolean update(Vehiculo v) throws SQLException {
        return execute(
                "UPDATE vehiculos SET \"matricula\"=?, \"cv\"=?, \"descripcion\"=?, \"precioCompra\"=?, \"precioVenta\"=?, \"marca\"=?, \"modelo\"=? "
                        + "WHERE \"numBastidor\"=?",
                v.getMatricula(), v.getCv(), v.getDescripcion(),
                v.getPrecioCompra(), v.getPrecioVenta(),
                v.getMarca(), v.getModelo(), v.getNumBastidor());
    }

    /**
     * Obtiene todos los vehículos de la tabla.
     *
     * @return lista de vehículos.
     * @throws SQLException si ocurre un error SQL.
     */
    public List<Vehiculo> getAll() throws SQLException {
        return queryList("SELECT * FROM vehiculos");
    }
}
