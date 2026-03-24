package crud;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz genérica que define las operaciones CRUD básicas
 * (Create, Read, Update, Delete) para gestionar entidades de tipo {@code T}
 * en una base de datos.
 *
 * @param <T> tipo de objeto que gestionará la implementación del CRUD
 */
public interface CRUD<T> {

    /**
     * Obtiene todos los registros de la tabla asociada al modelo,
     * ordenados por la columna especificada.
     *
     * @param sortedBy nombre de la columna por la que se ordenarán los resultados.
     *                 Puede incluir dirección de ordenación (ASC o DESC).
     * @return una lista con todos los registros encontrados.
     * @throws SQLException si ocurre un error al ejecutar la consulta.
     */
    List<T> requestAll(String sortedBy) throws SQLException;

    /**
     * Obtiene un registro concreto a partir de su identificador.
     *
     * @param id valor de la clave primaria del registro buscado.
     * @return una instancia del tipo {@code T} si existe, o {@code null} si no se encuentra.
     * @throws SQLException si ocurre un error al ejecutar la consulta.
     */
    T requestById(long id) throws SQLException;

    /**
     * Inserta un nuevo registro en la base de datos.
     *
     * @param model objeto del tipo {@code T} que se desea almacenar.
     * @return {@code true} si la operación se realizó correctamente,
     *         {@code false} en caso contrario.
     * @throws SQLException si ocurre un error durante la inserción.
     */
    boolean insert(T model) throws SQLException;

    /**
     * Actualiza un registro existente en la base de datos.
     *
     * @param model objeto del tipo {@code T} con los datos actualizados.
     * @return {@code true} si la actualización fue exitosa,
     *         {@code false} si no se modificó ningún registro.
     * @throws SQLException si ocurre un error durante la actualización.
     */
    boolean update(T model) throws SQLException;

    /**
     * Elimina un registro de la base de datos a partir de su identificador.
     *
     * @param id valor de la clave primaria del registro a eliminar.
     * @return {@code true} si el registro fue eliminado correctamente,
     *         {@code false} si no existía o no se eliminó.
     * @throws SQLException si ocurre un error durante la eliminación.
     */
    boolean delete(long id) throws SQLException;
}
