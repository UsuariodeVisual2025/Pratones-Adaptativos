package com.patrones.adaptativos.servicios;
import java.util.List;

public interface CRUD<T> {
    String create(T objeto);
    List<T> readAll();
}