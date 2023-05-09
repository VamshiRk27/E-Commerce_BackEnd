package com.example.driver.Service.Implementation;

import com.example.driver.DTO.Request.Item.AddItemRequest;
import com.example.driver.Entity.Customer;
import com.example.driver.Entity.Item;
import com.example.driver.Entity.Product;
import com.example.driver.Enum.ProductStatus;
import com.example.driver.Exception.CustomerException;
import com.example.driver.Exception.ProductException;
import com.example.driver.Repository.CustomerRepository;
import com.example.driver.Repository.ItemRepository;
import com.example.driver.Repository.ProductRepository;
import com.example.driver.Service.Interface.ItemService;
import com.example.driver.Tranformer.ItemTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CustomerRepository customerRepository;

    // 1.Add Item
    @Override
    public Item addItem(AddItemRequest addItemRequest) throws CustomerException, ProductException {
        //Retrieve the Customer using unique identifier key 'Customer EmailId'
        Customer customer=customerRepository.findByEmailId(addItemRequest.getCustomerEmail());
        if(customer==null){
            //If the customer doesn't exist then throw a Customer Exception
            throw new CustomerException("Customer doesn't exist");
        }
        //Retrieve the Product using 'Product ID'
        Product product=productRepository.findById(addItemRequest.getProductId()).get();
        if(product==null){
            //If the product doesn't exist then throw a Product Exception
            throw new ProductException("Product with given Id doesn't exist");
        }
        if(addItemRequest.getRequiredQuantity()>product.getQuantity()){
            //If the available quantity is short of required quantity then throw an Exception
            throw new ProductException("Required Quantity is more than available quantity\nCannot add to Cart");
        }
        else if(product.getProductStatus()!=ProductStatus.AVAILABLE){
            //If Product is Out of Stock then throw a Product Exception
            throw new ProductException("Product currently Out of Stock");
        }

        Item item= ItemTransformer.addItemRequestToItem(addItemRequest); //Preparing Item object using Item Transformer
        item.setName(product.getName()); //Setting the Item name using Product name
        item.setProduct(product); //Mapping the Product for Item object
        item.setCart(customer.getCart());

        product.getItemList().add(item); //Adding Item to the Item's list of Product
        Item savedItem=itemRepository.save(item); //Saving the Item to the Item Database
        return savedItem; //Returning the saved Item
    }
}
