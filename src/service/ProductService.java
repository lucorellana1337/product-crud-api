package service;

import model.Product;
import repository.ProductRepository;

import java.util.List;

public class ProductService {
    private ProductRepository repo = new ProductRepository();

    public List<Product> getAll() {
        return repo.getAll();
    }

    public Product getById(int id) {
        return repo.getById(id);
    }

    public void add(Product p) {
        repo.add(p);
    }

    public void update(Product p) {
        repo.update(p);
    }

    public void delete(int id) {
        repo.delete(id);
    }
}
