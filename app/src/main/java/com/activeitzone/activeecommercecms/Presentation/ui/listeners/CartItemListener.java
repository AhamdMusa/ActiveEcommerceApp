package com.activeitzone.activeecommercecms.Presentation.ui.listeners;

import com.activeitzone.activeecommercecms.Models.CartModel;

public interface CartItemListener {
    void onCartRemove(CartModel cartModel);
    void onQuantityUpdate(int quantity, CartModel cartModel);
}
