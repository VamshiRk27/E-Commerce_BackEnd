package com.example.driver.Repository;

import com.example.driver.Entity.Product;
import com.example.driver.Enum.ProductCategory;
import com.example.driver.Enum.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query(value="select * from product p where p.product_category=:category",nativeQuery=true)
    List<Product> findAllProductsByCategory(ProductCategory category);

    @Query(value="select * from product p order by p.price asc",nativeQuery=true)
    List<Product> CheapestProducts();

    @Query(value="select * from product p order by p.price desc",nativeQuery=true)
    List<Product> CostliestProducts();

    @Query(value ="select * from product p where p.product_status=:status",nativeQuery=true)
    List<Product> productsByStatus(ProductStatus status);

    @Query(value ="select * from product p where p.quantity<=:stock",nativeQuery=true)
    List<Product> productsByInventoryStatus(int stock);

    @Query(value="select * from product p where p.product_category=:category order by p.price asc",nativeQuery=true)
    List<Product> cheapestProductInCategory(ProductCategory category);

    @Query(value="select * from product p where p.product_category=:category order by p.price desc",nativeQuery=true)
    List<Product> costliestProductInCategory(ProductCategory category);

    @Query(value ="select * from product p where p.quantity<=:stock and p.product_category=:category",nativeQuery=true)
    List<Product> productsByInventoryStatusAndCategory(Integer stock,ProductCategory category);
}
