package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {

    private static Warehouse instance;
    private final Map<UUID, ProductRecord> products;
    private final List<ProductRecord> changedProducts;
    private final String name;

    private Warehouse() {
        this.name = "MyStore";
        this.products = new LinkedHashMap<>();
        this.changedProducts = new ArrayList<>();
    }

    private Warehouse(String name) {
        this.name = name;
        this.products = new LinkedHashMap<>();
        this.changedProducts = new ArrayList<>();
    }

    public static Warehouse getInstance() {
        return new Warehouse();
    }

    public static Warehouse getInstance(String name) {
        if (instance == null || !instance.isEmpty()) {
            instance = new Warehouse(name);
        }
        return instance;
    }

    public void clearProducts() {
        products.clear();
        changedProducts.clear();
    }

    public boolean isEmpty() {
        return products.isEmpty() && changedProducts.isEmpty();
    }

    public List<ProductRecord> getProducts() {
        return List.copyOf(products.values());
    }

    public ProductRecord addProduct(UUID uuid, String name, Category category, BigDecimal price) {

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        if (price == null) {
            price = BigDecimal.ZERO;
        }
        if (products.containsKey(uuid)) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }
        ProductRecord product = new ProductRecord(uuid, name, price, category);
        products.put(uuid, product);
        return product;
    }

    public Optional<ProductRecord> getProductById(UUID uuid) {
        return Optional.ofNullable(products.get(uuid)); }

    public void updateProductPrice(UUID uuid, BigDecimal newPrice) {
        if (!products.containsKey(uuid)) {
            throw new IllegalArgumentException("Product with that id doesn't exist.");
        }
        ProductRecord product = products.get(uuid);
        product.setPrice(newPrice);

        if (!changedProducts.contains(product))
            changedProducts.add(product);
    }

    public List<ProductRecord> getChangedProducts() {
        return new ArrayList<>(changedProducts);
    }

    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        return products.values().stream()
                .collect(Collectors.groupingBy(ProductRecord::category));
    }

    public List<ProductRecord> getProductsBy(Category category) {
        return products.values().stream()
                .filter(p -> p.category().equals(category))
                .collect(Collectors.toList());
    }

}



