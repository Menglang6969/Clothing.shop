package com.menglang.Clothing.shop.services.cart.cart;

import com.menglang.Clothing.shop.dto.cart.ItemRequest;
import com.menglang.Clothing.shop.entity.CartEntity;
import com.menglang.Clothing.shop.entity.CartItemEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.UserEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.CartRepository;
import com.menglang.Clothing.shop.services.cart.cartItem.CartItemService;
import com.menglang.Clothing.shop.services.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductServiceImpl productService;

    @Override
    public CartEntity CreateCart(UserEntity user) throws Exception {
        CartEntity cart = CartEntity.builder()
                .user(user)
                .build();
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, ItemRequest request) throws Exception {
        CartEntity cart = cartRepository.findByUserId(userId);
        ProductEntity product = productService.findProductById(request.productId());
        CartItemEntity isPresent = cartItemService.isCartItemExist(cart, product, request.size());

        if (isPresent == null) {
            CartItemEntity cartItem = CartItemEntity.builder()
                    .product(product)
                    .cart(cart)
                    .quantity(request.quantity())
                    .price(Double.valueOf(request.price()))
                    .size(request.size())
                    .build();

            CartItemEntity createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);//add new item to previous cartItems of carts
        }
        return "Item Add to Cart";
    }

    @Override
    public CartEntity findUserCart(Long userId) throws Exception {
        try {
            CartEntity cart = cartRepository.findByUserId(userId);
            double totalPrice = (double) 0;
            int totalDiscountPrice = 0;
            int totalItem = 0;


            for (CartItemEntity cartItem : cart.getCartItems()) {
                totalPrice += cartItem.getPrice() * cartItem.getQuantity();
                totalDiscountPrice += cartItem.getDiscountedPrice();
                totalItem += cartItem.getQuantity();
            }

            cart.setDiscount(totalDiscountPrice);
            cart.setTotalDiscountedPrice(totalDiscountPrice);
            cart.setTotalItem(totalItem);
            cart.setTotalPrice(totalPrice);

            return cartRepository.save(cart);
        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "500");
        }

    }
}
