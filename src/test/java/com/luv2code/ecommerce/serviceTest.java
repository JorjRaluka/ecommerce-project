package com.luv2code.ecommerce;

import com.luv2code.ecommerce.dao.CustomerRepository;
import com.luv2code.ecommerce.dto.PaymentInfo;
import com.luv2code.ecommerce.dto.Purchase;
import com.luv2code.ecommerce.dto.PurchaseResponse;
import com.luv2code.ecommerce.entity.*;
import com.luv2code.ecommerce.service.CheckoutServiceImpl;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class serviceTest {

    private CheckoutServiceImpl checkoutService;

    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        checkoutService = new CheckoutServiceImpl(customerRepository, "sk_test_fakeKey");
        Stripe.apiKey = "sk_test_fakeKey"; // Safe for testing
    }


    @Test
    void testCreatePaymentIntentThrowsException() throws StripeException {
        PaymentInfo info = new PaymentInfo();
        info.setAmount(999);
        info.setCurrency("usd");
        info.setReceiptEmail("fail@test.com");

        when(customerRepository.findByEmail(anyString())).thenReturn(null);


        CheckoutServiceImpl service = new CheckoutServiceImpl(customerRepository, "invalid-key");

        assertThrows(StripeException.class, () -> {
            service.createPaymentIntent(info);
        });
    }
    @Test
    void testPlaceOrderWithNewCustomer() {
        Customer customer = new Customer();
        customer.setEmail("new@test.com");

        Purchase purchase = new Purchase();
        purchase.setCustomer(customer);
        purchase.setOrder(new Order());
        purchase.setBillingAddress(new Address());
        purchase.setShippingAddress(new Address());
        purchase.setOrderItems(new HashSet<>());

        when(customerRepository.findByEmail("new@test.com")).thenReturn(null);

        CheckoutServiceImpl service = new CheckoutServiceImpl(customerRepository, "test");
        PurchaseResponse response = service.placeOrder(purchase);

        assertThat(response.getOrderTrackingNumber()).isNotBlank();
        verify(customerRepository).save(any());
    }




    @Test
    void testPlaceOrder_newCustomer() {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("new@example.com");

        Order order = new Order();
        order.setTotalPrice(BigDecimal.valueOf(100));
        order.setTotalQuantity(1);

        Set<OrderItem> orderItems = new HashSet<>();
        OrderItem item = new OrderItem();
        item.setQuantity(1);
        item.setUnitPrice(BigDecimal.valueOf(100));
        orderItems.add(item);

        order.setOrderItems(orderItems);

        Address billing = new Address();
        Address shipping = new Address();

        Purchase purchase = new Purchase();
        purchase.setCustomer(customer);
        purchase.setOrder(order);
        purchase.setBillingAddress(billing);
        purchase.setShippingAddress(shipping);
        purchase.setOrderItems(orderItems);

        when(customerRepository.findByEmail("new@example.com")).thenReturn(null);


        // Act
        PurchaseResponse response = checkoutService.placeOrder(purchase);

        // Assert
        assertThat(response.getOrderTrackingNumber()).isNotEmpty();

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue().getOrders()).hasSize(1);
    }
}
