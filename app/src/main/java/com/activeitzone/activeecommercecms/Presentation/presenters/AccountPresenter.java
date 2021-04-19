package com.activeitzone.activeecommercecms.Presentation.presenters;

import com.activeitzone.activeecommercecms.Network.response.LogoutResponse;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.AccountView;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.LogoutInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.impl.LogoutInteractorImpl;

public class AccountPresenter extends AbstractPresenter implements LogoutInteractor.CallBack {

    private AccountView accountView;

    public AccountPresenter(Executor executor, MainThread mainThread, AccountView accountView) {
        super(executor, mainThread);
        this.accountView = accountView;
    }

    public void performLogout(String token){
        new LogoutInteractorImpl(mExecutor, mMainThread, this, token).execute();
    }

    @Override
    public void onLoggedOut(LogoutResponse logoutResponse) {
        if(accountView != null){
            accountView.showLogoutMessage(logoutResponse);
        }
    }

    @Override
    public void onLoggedOutError() {

    }
}
