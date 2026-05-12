package bts66.anthony.castlefightarena.database;

public interface ModelDAO<A, B> {
    boolean save(A data);
    boolean update(A data);
    boolean deleteById(int id);
    A getById(int id);
    B getAll();
}
