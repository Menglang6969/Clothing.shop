package com.menglang.Clothing.shop.services.purchase.purchaseItems;

import com.menglang.Clothing.shop.entity.PurchaseOrderEntity;
import com.menglang.Clothing.shop.entity.PurchaseItemEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.PurchaseItemsRepository;
import com.menglang.Clothing.shop.repositories.PurchaseOrderRepository;
import com.menglang.Clothing.shop.repositories.UserRepository;
import com.menglang.Clothing.shop.services.user.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseItemsServiceImpl implements PurchaseItemsService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private PurchaseItemsRepository purchaseItemsRepository;
    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;


    @Override
    public PurchaseItemEntity addItem(PurchaseItemEntity cartItem) throws Exception {
        try {
            cartItem.setQuantity(1);
            cartItem.setPrice(11.1);
            //cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
            cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice());
            return purchaseItemsRepository.save(cartItem);
        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "500");
        }

    }

    @Override
    public PurchaseItemEntity updateCartItem(Long id, PurchaseItemEntity cartItem) throws Exception {
        try {
            PurchaseItemEntity item = findCartItemById(id);
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice());

            return purchaseItemsRepository.save(item);
        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "500");
        }
    }

    @Override
    public PurchaseItemEntity isCartItemExist(PurchaseOrderEntity cart, ProductEntity product, String size) throws Exception {
        //return purchaseItemsRepository.isCartItemExist(cart, product, size);
return null;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws Exception {
       try{
           PurchaseItemEntity cartItem=findCartItemById(cartItemId);
           purchaseItemsRepository.delete(cartItem);
       }catch (Exception e){
           throw new CustomMessageException(e.getMessage(),"500");
       }
    }

    @Override
    public PurchaseItemEntity findCartItemById(Long cartItemId) throws Exception {
       try{
           Optional<PurchaseItemEntity> cartItem= purchaseItemsRepository.findById(cartItemId);
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
