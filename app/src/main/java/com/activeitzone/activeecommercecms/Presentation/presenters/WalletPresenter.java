package com.activeitzone.activeecommercecms.Presentation.presenters;

import com.activeitzone.activeecommercecms.Models.Wallet;
import com.activeitzone.activeecommercecms.Models.WishlistModel;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.WalletView;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.WishlistView;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.WalletBalanceInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.WalletHistoryInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.WishlistInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.impl.WalletBalanceInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.WalletHistoryInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.WishlistInteractorImpl;

import java.util.List;

public class WalletPresenter extends AbstractPresenter implements WalletBalanceInteractor.CallBack, WalletHistoryInteractor.CallBack {
    private WalletView walletView;

    public WalletPresenter(Executor executor, MainThread mainThread, WalletView walletView) {
        super(executor, mainThread);
        this.walletView = walletView;
    }

    public void getWalletBalance(int id, String token) {
        new WalletBalanceInteractorImpl(mExecutor, mMainThread, this, id, token).execute();
    }

    public void getWalletHistory(int id, String token) {
        new WalletHistoryInteractorImpl(mExecutor, mMainThread, this, id, token).execute();
    }

    @Override
    public void onWalletBalanceLodaded(Double balance) {
        if (walletView != null){
            walletView.setWalletBalance(balance);
        }
    }

    @Override
    public void onWalletBalanceLoadError() {

    }

    @Override
    public void onWalletHistoryLodaded(List<Wallet> walletList) {
        if(walletView != null){
            walletView.setWalletHistory(walletList);
        }
    }

    @Override
    public void onWalletHistoryLoadError() {

    }
}
