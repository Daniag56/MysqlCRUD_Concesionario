package crud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GestorGenerico<T> implements CRUD<T> {

    protected final Connection conn;
    protected final String tabla;
    protected final String columnaId;
    protected final RowMapper<T> mapper;

    public GestorGenerico(Connection conn, String tabla, String columnaId, RowMapper<T> mapper) {
        this.conn = conn;
        this.tabla = tabla;
        this.columnaId = columnaId;
        this.mapper = mapper;
    }

    /**
     * Asigna los parámetros proporcionados a un {@link PreparedStatement}.
     *
     * @param stmt   sentencia preparada a la que se asignarán los parámetros.
     * @param params valores que se enlazarán en la sentencia.
     * @throws SQLException si ocurre un error al asignar los parámetros.
     */

    private void bindParams(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }

    /**
     * Ejecuta una consulta SQL que devuelve múltiples registros.
     *
     * @param sql    sentencia SQL con parámetros.
     * @param params valores que se enlazarán en la sentencia.
     * @return una lista de objetos del tipo {@code T}.
     * @throws SQLException si ocurre un error durante la consulta.
     */
    protected List<T> queryList(String sql, Object... params) throws SQLException {
        List<T> lista = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            bindParams(stmt, params);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapper.map(rs));
                }
            }
        }
        return lista;
    }

    /**
     * Ejecuta una consulta SQL que devuelve un único registro.
     *
     * @param sql    sentencia SQL con parámetros.
     * @param params valores que se enlazarán en la sentencia.
     * @return una instancia de {@code T} o {@code null} si no se encuentra.
     * @throws SQLException si ocurre un error durante la consulta.
     */
    protected T queryOne(String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            bindParams(stmt, params);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapper.map(rs) : null;
            }
        }
    }
    /**
     * Ejecuta una sentencia SQL de actualización (INSERT, UPDATE o DELETE).
     *
     * @param sql    sentencia SQL con parámetros.
     * @param params valores que se enlazarán en la sentencia.
     * @return {@code true} si se modificó exactamente un registro.
     * @throws SQLException si ocurre un error durante la operación.
     */
    protected boolean execute(String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            bindParams(stmt, params);
            return stmt.executeUpdate() == 1;
        }
    }

    
    //Metodos traidos de la interfaz crud
    @Override
    public List<T> requestAll(String sortedBy) throws SQLException {
        return queryList("SELECT * FROM " + tabla + " ORDER BY " + sortedBy);
    }
    @Override
    public T requestById(long id) throws SQLException {
        return queryOne("SELECT * FROM " + tabla + " WHERE " + columnaId + " = ?", id);
    }
    @Override
    public boolean delete(long id) throws SQLException {
        return execute("DELETE FROM " + tabla + " WHERE " + columnaId + " = ?", id);
    }
    @Override
    public abstract boolean insert(T entity) throws SQLException;

    @Override
    public abstract boolean update(T entity) throws SQLException;

}
