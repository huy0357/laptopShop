package com.example.demo.Service;


import com.example.demo.domain.*;
import com.example.demo.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private CartDetailRepository cartDetailRepository;

    private CartRepository cartRepository;

    private ProductRepository productRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository,
                          UserService userService,
                          CartRepository cartRepository,
                          CartDetailRepository cartDetailRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }


    public Product createProduct(Product pr) {
        return this.productRepository.save(pr);
    }

    public void deleteById(long id) {
        this.productRepository.deleteById(id);
    }


    public Optional<Product> getProductById(long id) {
        return this.productRepository.findById(id);
    }

    public void handleAppProductToCart(String email, Long productId, HttpSession session) {
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            //check user có card hay chua
            Cart cart = this.cartRepository.findByUser(user);
            if (cart == null) {
                ///tạo mới card
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);

                cart = this.cartRepository.save(otherCart);

            }

            //save
            //tìm product by id
            Optional<Product> productOptional = this.productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product realProduct = productOptional.get();

                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);

                if (oldDetail == null) {
                    CartDetail cd = new CartDetail();
                    cd.setCart(cart);
                    cd.setProduct(realProduct);
                    cd.setPrice(realProduct.getPrice());
                    cd.setQuantity(1);

                    this.cartDetailRepository.save(cd);

                    //update cart sum
                    int s = cart.getSum()+1;
                    cart.setSum(cart.getSum() + 1);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);
                }else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                    this.cartDetailRepository.save(oldDetail);
                }
        }
        }


    }

    public Cart fetchByUser(User user){
        return this.cartRepository.findByUser(user);
    }

    public void handleRemoveCartDetail(Long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);
        if(cartDetailOptional.isPresent()){
            CartDetail cartDetail = cartDetailOptional.get();

            Cart cart = cartDetail.getCart();
            //delete cart detail
            this.cartDetailRepository.deleteById(cartDetailId);

            if(cart.getSum() >1 ){
                int s = cart.getSum()-1;
                cart.setSum(s);
                session.setAttribute("sum", s);
                this.cartRepository.save(cart);

            }else {
                //delete cart (sum = 1)
                this.cartRepository.deleteById(cart.getId());
                session.setAttribute("sum", 0);

            }




        }
    }





}
