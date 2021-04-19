package com.activeitzone.activeecommercecms.Presentation.presenters;

import com.activeitzone.activeecommercecms.Network.response.AddToCartResponse;
import com.activeitzone.activeecommercecms.Network.response.VariantResponse;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.BuyingOptionView;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.AddToCartInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.BuyingOptionInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.impl.AddToCartInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.BuyingOptionInteractorImpl;
import com.google.gson.JsonArray;


public class BuyingOptionPresenter extends AbstractPresenter implements BuyingOptionInteractor.CallBack, AddToCartInteractor.CallBack {

    private BuyingOptionView buyingOptionView;

    public BuyingOptionPresenter(Executor executor, MainThread mainThread, BuyingOptionView buyingOptionView) {
        super(executor, mainThread);
        this.buyingOptionView = buyingOptionView;
    }

    public void getVariantPrice(int id, String color, JsonArray choicesArray) {
        new BuyingOptionInteractorImpl(mExecutor, mMainThread, this, id, color, choicesArray).execute();
    }

    public void addToCart(String token, int user_id, int product_id, String variant){
        new AddToCartInteractorImpl(mExecutor, mMainThread, this, token, user_id, product_id, variant).execute();
    }

    @Override
    public void onGetVariantPrice(VariantResponse variantResponse) {
        if(buyingOptionView != null){
            buyingOptionView.setVariantprice(variantResponse);
        }
    }

    @Override
    public void onGetVariantPriceError() {

    }

    @Override
    public void onCartItemAdded(AddToCartResponse addToCartResponse) {
        if(buyingOptionView != null){
            buyingOptionView.setAddToCartMessage(addToCartResponse);
        }
    }

    @Override
    public void onCartItemAddedError() {

    }
}
