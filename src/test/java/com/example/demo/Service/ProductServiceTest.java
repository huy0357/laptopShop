package com.example.demo.Service;

import com.example.demo.domain.Cart;
import com.example.demo.domain.CartDetail;
import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import com.example.demo.repository.CartDetailRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartDetailRepository cartDetailRepository;

    @Mock
    private UserService userService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private ProductService productService;

    private User user;
    private Product product;
    private Cart cart;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@example.com");

        product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(1000.0);
        product.setQuantity("10");

        cart = new Cart();
        cart.setUser(user);
        cart.setSum(0);
    }

    @Test
    void handleAppProductToCart_newCartAndNewProduct_shouldCreateCartAndCartDetail() {
        // Arrange
        when(userService.getUserByEmail("test@example.com")).thenReturn(user);
        when(cartRepository.findByUser(user)).thenReturn(null);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(cartDetailRepository.findByCartAndProduct(any(Cart.class), any(Product.class))).thenReturn(null);
        when(cartDetailRepository.save(any(CartDetail.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        productService.handleAppProductToCart("test@example.com", 1L, session);

        // Assert
        verify(cartRepository, times(1)).save(any(Cart.class));
        verify(cartDetailRepository, times(1)).save(any(CartDetail.class));
        verify(session, times(1)).setAttribute("sum", 1);
        verify(cartRepository, times(1)).save(argThat(c -> c.getSum() == 1));
    }

    @Test
    void handleAppProductToCart_existingCartNewProduct_shouldAddNewCartDetail() {
        // Arrange
        cart.setSum(2);
        when(userService.getUserByEmail("test@example.com")).thenReturn(user);
        when(cartRepository.findByUser(user)).thenReturn(cart);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(cartDetailRepository.findByCartAndProduct(cart, product)).thenReturn(null);
        when(cartDetailRepository.save(any(CartDetail.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        productService.handleAppProductToCart("test@example.com", 1L, session);

        // Assert
        verify(cartRepository, times(1)).save(argThat(c -> c.getSum() == 3));
        verify(cartDetailRepository, times(1)).save(any(CartDetail.class));
        verify(session, times(1)).setAttribute("sum", 3);
    }

    @Test
    void handleAppProductToCart_existingCartExistingProduct_shouldIncreaseQuantity() {
        // Arrange
        CartDetail existingCartDetail = new CartDetail();
        existingCartDetail.setCart(cart);
        existingCartDetail.setProduct(product);
        existingCartDetail.setPrice(product.getPrice());
        existingCartDetail.setQuantity(1);

        cart.setSum(2);
        when(userService.getUserByEmail("test@example.com")).thenReturn(user);
        when(cartRepository.findByUser(user)).thenReturn(cart);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(cartDetailRepository.findByCartAndProduct(cart, product)).thenReturn(existingCartDetail);

        // Act
        productService.handleAppProductToCart("test@example.com", 1L, session);

        // Assert
        verify(cartDetailRepository, times(1)).save(argThat(cd -> cd.getQuantity() == 2));
        verify(cartRepository, never()).save(any(Cart.class)); // Sum not updated in this case
        verify(session, never()).setAttribute(eq("sum"), anyInt());
    }
}