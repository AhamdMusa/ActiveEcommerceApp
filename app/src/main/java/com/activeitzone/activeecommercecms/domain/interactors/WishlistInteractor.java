package com.activeitzone.activeecommercecms.domain.interactors;

import com.activeitzone.activeecommercecms.Models.WishlistModel;

import java.util.List;

public interface WishlistInteractor {
    interface CallBack {

        void onWishlistLodaded(List<WishlistModel> wishlistModels);

        void onWishlistError();
    }
}
