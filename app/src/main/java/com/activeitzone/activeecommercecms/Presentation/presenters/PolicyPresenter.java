package com.activeitzone.activeecommercecms.Presentation.presenters;

import com.activeitzone.activeecommercecms.Models.PolicyContent;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.PolicyView;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.PolicyInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.impl.PolicyInteractorImpl;

public class PolicyPresenter extends AbstractPresenter implements PolicyInteractor.CallBack {
    private PolicyView policyView;
    private int type = 0;

    public PolicyPresenter(Executor executor, MainThread mainThread, PolicyView policyView) {
        super(executor, mainThread);
        this.policyView = policyView;
    }

    public void getPolicy(String url){
        new PolicyInteractorImpl(mExecutor, mMainThread, this, url).execute();
    }

    @Override
    public void onPolicyLoaded(PolicyContent policyContent) {
        if (policyView != null){
            policyView.onPolicyContentLoaded(policyContent);
        }
    }

    @Override
    public void onPolicyLoadError() {

    }
}
