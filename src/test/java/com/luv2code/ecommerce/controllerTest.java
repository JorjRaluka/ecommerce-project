package com.luv2code.ecommerce;

import com.luv2code.ecommerce.controller.CheckoutController;
import com.luv2code.ecommerce.dto.PaymentInfo;
import com.luv2code.ecommerce.dto.Purchase;
import com.luv2code.ecommerce.dto.PurchaseResponse;
import com.luv2code.ecommerce.service.CheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class controllerTest {

    private CheckoutService checkoutService;
    private CheckoutController checkoutController;

    @BeforeEach
    void setUp() {
        // Mock the service
        checkoutService = mock(CheckoutService.class);

        // Inject mock into controller
        checkoutController = new CheckoutController(checkoutService);
    }

    @Test
    void testPlaceOrder() {
        Purchase purchase = new Purchase();
        PurchaseResponse mockResponse = new PurchaseResponse("ORDER123");

        when(checkoutService.placeOrder(purchase)).thenReturn(mockResponse);

        PurchaseResponse result = checkoutController.placeOrder(purchase);

        assertThat(result).isNotNull();
        assertThat(result.getOrderTrackingNumber()).isEqualTo("ORDER123");
    }

    @Test
    void testCreatePaymentIntent() throws StripeException {
        PaymentInfo info = new PaymentInfo();
        info.setAmount(1000); // assuming this is an int, or cast if long
        info.setCurrency("usd");
        info.setReceiptEmail("test@example.com");

        PaymentIntent mockIntent = mock(PaymentIntent.class);
        when(mockIntent.toJson()).thenReturn("{\"id\": \"pi_test_001\"}");

        // Mock the service call
        when(checkoutService.createPaymentIntent(info)).thenReturn(mockIntent);

        ResponseEntity<String> response = checkoutController.createPaymentIntent(info);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("pi_test_001");
    }
}
