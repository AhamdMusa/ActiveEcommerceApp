package com.activeitzone.activeecommercecms.domain.interactors;

import com.activeitzone.activeecommercecms.Models.Brand;

import java.util.List;

public interface BrandInteractor {
    interface CallBack {

        void onBrandsDownloaded(List<Brand> brands);

        void onBrandsDownloadError();
    }
}
