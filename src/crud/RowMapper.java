package crud;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interfaz funcional que define cómo convertir una fila de un {@link ResultSet}
 * en una instancia del tipo genérico {@code T}.
 *
 * <p>Las implementaciones de esta interfaz permiten desacoplar la lógica
 * de mapeo del acceso a datos, facilitando la reutilización y la limpieza
 * del código en los gestores genéricos.</p>
 *
 * @param <T> tipo de objeto al que se desea mapear cada fila del resultado SQL
 */
public interface RowMapper<T> {

    /**
     * Convierte la fila actual del {@link ResultSet} en un objeto del tipo {@code T}.
     *
     * @param rs resultado SQL posicionado en la fila que se desea mapear
     * @return una instancia del tipo {@code T} con los datos de la fila
     * @throws SQLException si ocurre un error al acceder a los datos del {@code ResultSet}
     */
    T map(ResultSet rs) throws SQLException;
}
