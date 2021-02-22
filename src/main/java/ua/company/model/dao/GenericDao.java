package ua.company.model.dao;

public interface GenericDao<T> {
    void close(AutoCloseable autoCloseable);
}
