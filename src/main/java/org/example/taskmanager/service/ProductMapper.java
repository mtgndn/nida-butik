package org.example.taskmanager.service;

import org.example.taskmanager.dto.ProductResponse;
import org.example.taskmanager.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getModel(),
                product.getMaterial(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getImageUrl(),
                product.getCategory().getName(),
                product.getBrand().getName(),
                product.getSupplier().getName()
        );
    }
}
