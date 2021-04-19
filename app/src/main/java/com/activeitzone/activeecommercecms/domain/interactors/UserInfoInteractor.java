package com.activeitzone.activeecommercecms.domain.interactors;

import com.activeitzone.activeecommercecms.Models.User;

public interface UserInfoInteractor {
    interface CallBack {

        void onUserInfoLodaded(User user);

        void onUserInfoError();
    }
}
