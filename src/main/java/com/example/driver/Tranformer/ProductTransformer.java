package com.example.driver.Tranformer;

import com.example.driver.DTO.Request.Product.AddProductRequest;
import com.example.driver.DTO.Response.Product.AddProductResponse;
import com.example.driver.DTO.Response.Product.ProductResponse;
import com.example.driver.DTO.Response.Seller.AddSellerResponse;
import com.example.driver.Entity.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductTransformer {
    public Product addProductRequestToProduct(AddProductRequest addProductRequest){
        return Product.builder()
                .name(addProductRequest.getName())
                .price(addProductRequest.getPrice())
                .quantity(addProductRequest.getQuantity())
                .productCategory(addProductRequest.getProductCategory())
                .productStatus(addProductRequest.getProductStatus())
                .build();
    }
    public AddProductResponse addProductResponseFromProduct(String sellerName,Product product){
        String outMessage="Product "+"'"+product.getName()+"'"+" by seller "+"'"+sellerName+"'"+" has been added";
        return AddProductResponse.builder()
                .name(product.getName())
                .sellerName(sellerName)
                .message(outMessage)
                .build();
    }
    public ProductResponse productResponseFromProduct(Product product){
        return ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .productCategory(product.getProductCategory())
                .productStatus(product.getProductStatus())
                .build();
    }
}
