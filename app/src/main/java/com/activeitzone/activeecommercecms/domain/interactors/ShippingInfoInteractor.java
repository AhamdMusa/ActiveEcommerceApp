package com.activeitzone.activeecommercecms.domain.interactors;

import com.activeitzone.activeecommercecms.Models.ShippingAddress;
import java.util.List;

public interface ShippingInfoInteractor {
    interface CallBack {

        void onShippingInfoRetrived(List<ShippingAddress> shippingAddresses);

        void onShippingInfoRetrivedError();
    }
}
