package alten.shop.AltenShop.service;

import alten.shop.AltenShop.model.Product;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ProductService {

    private static final String FILE_PATH = "src/main/resources/products.json";
    private List<Product> products = new ArrayList<>(); 


    public List<Product> getAllProducts() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return Arrays.asList(objectMapper.readValue(file, Product[].class));
    }

    public void saveAllProducts(List<Product> products) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(FILE_PATH), products);
    }

    public Optional<Product> getProductById(long id) throws IOException {
        return getAllProducts().stream().filter(product -> product.getId() == id).findFirst();
    }

  
    public Optional<Product> addProduct(Product product) {
        if (product != null) {
            products.add(product);  
            return Optional.of(product); 
        } else {
            return Optional.empty();
        }
    }
    public Optional<Product> updateProduct(long id, Product product) throws IOException {
        List<Product> products = getAllProducts();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.set(i, product);
                saveAllProducts(products);
                return Optional.of(product); 
            }
        }
        return Optional.empty();
    }

    public Optional<Product> patchProduct(long id, Product product) throws IOException {
        List<Product> products = getAllProducts();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                Product existingProduct = products.get(i);
                if (product.getName() != null) existingProduct.setName(product.getName());
                if (product.getDescription() != null) existingProduct.setDescription(product.getDescription());
                if (product.getCategory() != null) existingProduct.setCategory(product.getCategory());
                if (product.getPrice() != 0) product.setPrice(product.getPrice()); // Vérifier la valeur par défaut

                if (product.getImage() != null) existingProduct.setImage(product.getImage());
                saveAllProducts(products);
                return Optional.of(existingProduct); 
            }
        }
        return Optional.empty(); 
    }

    public void deleteProduct(long id) throws IOException {
        List<Product> products = getAllProducts().stream()
            .filter(product -> product.getId() != id)
            .collect(Collectors.toList());
        saveAllProducts(products);
    }
}


