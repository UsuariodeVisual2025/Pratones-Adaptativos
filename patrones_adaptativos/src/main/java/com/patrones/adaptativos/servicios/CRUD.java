package com.patrones.adaptativos.servicios;

import java.util.List;

/**
 * Interface CRUD: Define las operaciones básicas de persistencia.
 * Es un "contrato" que asegura que cualquier clase que la implemente
 * sabrá cómo guardar y leer información.
 * 
 * @param <T> Representa el tipo de objeto (ej: Score, Intento, etc.)
 */
public interface CRUD<T> {

    /**
     * create: Método para insertar un nuevo registro.
     * @param objeto El dato que queremos guardar (ej: un nuevo puntaje).
     * @return Un mensaje de confirmación o un código de error ("ERROR").
     */
    String create(T objeto);

    /**
     * readAll: Método para recuperar todos los registros existentes.
     * @return Una lista (List) que contiene todos los objetos guardados 
     * en la base de datos o en el sistema.
     */
    List<T> readAll();
}