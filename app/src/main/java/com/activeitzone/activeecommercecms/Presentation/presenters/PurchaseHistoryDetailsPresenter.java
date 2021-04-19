package com.activeitzone.activeecommercecms.Presentation.presenters;

import com.activeitzone.activeecommercecms.Models.OrderDetail;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.PurchaseHistoryDetailsView;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.PurchaseHistoryDetailsInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.impl.PurchaseHistoryDetailsInteractorImpl;

import java.util.List;

public class PurchaseHistoryDetailsPresenter extends AbstractPresenter implements PurchaseHistoryDetailsInteractor.CallBack {

    private PurchaseHistoryDetailsView purchaseHistoryDetailsView;

    public PurchaseHistoryDetailsPresenter(Executor executor, MainThread mainThread, PurchaseHistoryDetailsView purchaseHistoryDetailsView) {
        super(executor, mainThread);
        this.purchaseHistoryDetailsView = purchaseHistoryDetailsView;
    }

    public void getOrderDetails(String token, String url){
        new PurchaseHistoryDetailsInteractorImpl(mExecutor, mMainThread, this, url, token).execute();
    }

    @Override
    public void onOrderDetailsLoaded(List<OrderDetail> orderDetails) {
        if (purchaseHistoryDetailsView != null){
            purchaseHistoryDetailsView.onOrderDetailsLoaded(orderDetails);
        }
    }

    @Override
    public void onOrderDetailsLoadError() {

    }
}
