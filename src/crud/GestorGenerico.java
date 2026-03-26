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
     * Asigna los parámetros proporcionados al {@link PreparedStatement} en el orden
     * recibido.
     *
     * <p>
     * Cada elemento del arreglo {@code params} se vincula a su posición
     * correspondiente en la sentencia preparada, comenzando desde 1.
     * </p>
     *
     * @param stmt   la sentencia preparada a la que se asignarán los parámetros
     * @param params los valores que se asociarán a la sentencia
     * @throws SQLException si ocurre un error al establecer alguno de los
     *                      parámetros
     */
    private void bindParams(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }

    /**
     * Ejecuta una consulta SQL que devuelve múltiples filas y las transforma
     * en una lista de objetos del tipo {@code T} utilizando el {@link RowMapper}.
     *
     * <p>
     * La consulta puede incluir parámetros, los cuales serán vinculados
     * automáticamente antes de la ejecución.
     * </p>
     *
     * @param sql    la sentencia SQL a ejecutar
     * @param params los parámetros opcionales para la sentencia SQL
     * @return una lista de objetos mapeados desde el resultado de la consulta
     * @throws SQLException si ocurre un error al preparar o ejecutar la consulta
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
     * Ejecuta una consulta SQL que devuelve una única fila y la transforma
     * en un objeto del tipo {@code T} mediante el {@link RowMapper}.
     *
     * <p>
     * Si la consulta no produce resultados, se devuelve {@code null}.
     * </p>
     *
     * @param sql    la sentencia SQL a ejecutar
     * @param params los parámetros opcionales para la sentencia SQL
     * @return el objeto mapeado correspondiente a la primera fila del resultado,
     *         o {@code null} si no se encontró ninguna fila
     * @throws SQLException si ocurre un error al preparar o ejecutar la consulta
     */
    protected T queryOne(String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            bindParams(stmt, params);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapper.map(rs) : null;
            }
        }
    }

    protected boolean execute(String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            bindParams(stmt, params);
            return stmt.executeUpdate() == 1;
        }
    }

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
