package com.menglang.Clothing.shop.services.cart.cartItem;

import com.menglang.Clothing.shop.entity.CartEntity;
import com.menglang.Clothing.shop.entity.CartItemEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.CartItemRepository;
import com.menglang.Clothing.shop.repositories.CartRepository;
import com.menglang.Clothing.shop.repositories.UserRepository;
import com.menglang.Clothing.shop.services.user.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private CartRepository cartRepository;


    @Override
    public CartItemEntity createCartItem(CartItemEntity cartItem) throws Exception {
        try {
            cartItem.setQuantity(1);
            cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
            cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice());
            return cartItemRepository.save(cartItem);
        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "500");
        }

    }

    @Override
    public CartItemEntity updateCartItem(Long id, CartItemEntity cartItem) throws Exception {
        try {
            CartItemEntity item = findCartItemById(id);
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice());

            return cartItemRepository.save(item);
        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "500");
        }
    }

    @Override
    public CartItemEntity isCartItemExist(CartEntity cart, ProductEntity product, String size) throws Exception {
        return cartItemRepository.isCartItemExist(cart, product, size);

    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws Exception {
       try{
           CartItemEntity cartItem=findCartItemById(cartItemId);
           cartItemRepository.delete(cartItem);
       }catch (Exception e){
           throw new CustomMessageException(e.getMessage(),"500");
       }
    }

    @Override
    public CartItemEntity findCartItemById(Long cartItemId) throws Exception {
       try{
           Optional<CartItemEntity> cartItem=cartItemRepository.findById(cartItemId);
           if(cartItem.isPresent()){
               return cartItem.get();
           }else{
               throw new CustomMessageException("Item Not found","400");
           }
       }catch (Exception e){
           throw new CustomMessageException("Item Not found","400");
       }

    }
}
