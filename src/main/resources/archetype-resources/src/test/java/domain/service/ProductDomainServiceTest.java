package ${package}.domain.service;

import ${package}.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductDomainServiceTest {

    private ProductDomainService service;

    @BeforeEach
    void setUp() {
        service = new ProductDomainService();
    }

    @Test
    void shouldValidateValidPrice() {
        assertTrue(service.isValidPrice(new BigDecimal("10.00")));
    }

    @Test
    void shouldInvalidateNullPrice() {
        assertFalse(service.isValidPrice(null));
    }

    @Test
    void shouldInvalidateZeroPrice() {
        assertFalse(service.isValidPrice(BigDecimal.ZERO));
    }

    @Test
    void shouldValidateValidName() {
        assertTrue(service.isValidName("Product Name"));
    }

    @Test
    void shouldInvalidateNullName() {
        assertFalse(service.isValidName(null));
    }

    @Test
    void shouldInvalidateEmptyName() {
        assertFalse(service.isValidName(""));
        assertFalse(service.isValidName("   "));
    }

    @Test
    void shouldValidateValidProduct() {
        Product product = new Product("Valid Product", new BigDecimal("29.99"));
        assertDoesNotThrow(() -> service.validateProduct(product));
    }

    @Test
    void shouldThrowExceptionForInvalidName() {
        Product product = new Product("", new BigDecimal("29.99"));
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.validateProduct(product)
        );
        assertEquals("Product name cannot be empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidPrice() {
        Product product = new Product("Valid Name", BigDecimal.ZERO);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.validateProduct(product)
        );
        assertEquals("Product price must be positive", exception.getMessage());
    }
}
