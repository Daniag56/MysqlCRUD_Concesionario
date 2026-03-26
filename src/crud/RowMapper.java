package crud;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interfaz funcional que define un mapeador de filas para convertir
 * un {@link ResultSet} en una instancia del tipo deseado.
 *
 * <p>Su propósito es abstraer la lógica de transformación de cada fila
 * obtenida desde la base de datos, permitiendo desacoplar el acceso a datos
 * de la construcción de objetos de dominio.</p>
 *
 * @param <T> el tipo de objeto al que se desea mapear cada fila del ResultSet
 */
public interface RowMapper<T> {

    /**
     * Convierte la fila actual del {@link ResultSet} en una instancia del tipo {@code T}.
     *
     * <p>Este método se invoca por cada fila del resultado. La implementación
     * debe leer las columnas necesarias del {@code ResultSet} y construir el
     * objeto correspondiente.</p>
     *
     * @param rs el {@code ResultSet} posicionado en la fila actual
     * @return una instancia de {@code T} que representa la fila mapeada
     * @throws SQLException si ocurre un error al acceder a los datos del {@code ResultSet}
     */
    T map(ResultSet rs) throws SQLException;
}
