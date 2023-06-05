package com.example.driver.Repository;

import com.example.driver.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query(value="select * from product p where p.product_category=:category",nativeQuery=true)
    List<Product> findAllProductsByCategory(String category);

    @Query(value="select * from product p order by p.price asc",nativeQuery=true)
    List<Product> CheapestProducts();

    @Query(value="select * from product p order by p.price desc",nativeQuery=true)
    List<Product> CostliestProducts();

    @Query(value ="select * from product p where p.product_status=:status",nativeQuery=true)
    List<Product> productsByStatus(String status);

    @Query(value ="select * from product p where p.quantity<=:stock",nativeQuery=true)
    List<Product> productsByInventoryStatus(int stock);

    Product cheapestProductInCategory(String category);

    Product costliestProductInCategory(String category);
}
