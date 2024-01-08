package ch.application.readModel;

public interface ProductRepository {
    void add(Product product);

    Product find_by(int i);
}
