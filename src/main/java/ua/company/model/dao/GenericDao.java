package ua.company.model.dao;

public interface GenericDao<T> {
    T findById(int id);
    void close(AutoCloseable autoCloseable);
}
