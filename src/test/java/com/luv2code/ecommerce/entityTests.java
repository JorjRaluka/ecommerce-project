package com.luv2code.ecommerce;

import com.luv2code.ecommerce.entity.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class entityTests {
    @Test
    void testCustomerAddOrder() {
        Customer customer = new Customer();
        Order order = new Order();

        customer.add(order);

        assertThat(customer.getOrders()).contains(order);
        assertThat(order.getCustomer()).isEqualTo(customer);
    }
    @Test
    void testOrderAddItem() {
        Order order = new Order();
        OrderItem item = new OrderItem();

        order.add(item);

        assertThat(order.getOrderItems()).contains(item);
        assertThat(item.getOrder()).isEqualTo(order);
    }
    @Test
    void testAddressGettersSetters() {
        Address address = new Address();
        address.setCity("Paris");
        address.setState("Île-de-France");

        assertThat(address.getCity()).isEqualTo("Paris");
        assertThat(address.getState()).isEqualTo("Île-de-France");
    }
    @Test
    void testAddressEntity() {
        Address address = new Address();
        address.setCity("Berlin");
        address.setState("Berlin");
        address.setCountry("Germany");
        address.setZipCode("10115");

        assertThat(address.getCity()).isEqualTo("Berlin");
        assertThat(address.getZipCode()).isEqualTo("10115");
    }
    @Test
    void testCountryEntity() {
        Country country = new Country();
        country.setCode("DE");
        country.setName("Germany");

        assertThat(country.getCode()).isEqualTo("DE");
        assertThat(country.getName()).isEqualTo("Germany");
    }
    @Test
    void testOrderItemEntity() {
        OrderItem item = new OrderItem();
        item.setImageUrl("image.jpg");
        item.setQuantity(3);
        item.setProductId(101L);

        assertThat(item.getQuantity()).isEqualTo(3);
    }
    @Test
    void testProductCategoryEntity() {
        ProductCategory category = new ProductCategory();
        category.setCategoryName("Electronics");

        assertThat(category.getCategoryName()).isEqualTo("Electronics");
    }




}
