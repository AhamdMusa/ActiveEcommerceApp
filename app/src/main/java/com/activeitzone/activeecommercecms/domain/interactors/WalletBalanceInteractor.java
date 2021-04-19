package com.activeitzone.activeecommercecms.domain.interactors;

import com.activeitzone.activeecommercecms.Models.User;

public interface WalletBalanceInteractor {
    interface CallBack {

        void onWalletBalanceLodaded(Double balance);

        void onWalletBalanceLoadError();
    }
}
