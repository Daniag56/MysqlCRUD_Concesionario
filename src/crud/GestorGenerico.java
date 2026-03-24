package crud;

import java.sql.*;
import java.util.ArrayList;

public abstract class GestorGenerico<T> implements CRUD<T> {

    protected final Connection conn;
    protected final String tabla;
    protected final RowMapper<T> mapper;

    public GestorGenerico(Connection conn, String tabla, RowMapper<T> mapper) {
        this.conn = conn;
        this.tabla = tabla;
        this.mapper = mapper;
    }

    protected ArrayList<T> queryList(String sql, Object... params) throws SQLException {
        ArrayList<T> lista = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapper.map(rs));
            }
        }
        return lista;
    }

    protected T queryOne(String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            ResultSet rs = stmt.executeQuery();
            return rs.next() ? mapper.map(rs) : null;
        }
    }

    protected boolean executeUpdate(String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            return stmt.executeUpdate() == 1;
        }
    }

  

    @Override
    public ArrayList<T> requestAll(String sortedBy) throws SQLException {
        return queryList("SELECT * FROM " + tabla + " ORDER BY " + sortedBy);
    }

    @Override
    public T requestById(long id) throws SQLException {
        return queryOne("SELECT * FROM " + tabla + " WHERE matricula = ?", id);
    }

    @Override
    public boolean delete(long id) throws SQLException {
        return executeUpdate("DELETE FROM " + tabla + " WHERE matricula = ?", id);
    }

}
