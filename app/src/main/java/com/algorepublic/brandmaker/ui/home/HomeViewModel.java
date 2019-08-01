package com.algorepublic.brandmaker.ui.home;


import androidx.lifecycle.ViewModel;


public class HomeViewModel extends ViewModel {


//    private MutableLiveData<List<CategoryModel>> CategoryResponseObservable;
//    private MutableLiveData<List<CategoryModel>> SubCategoryResponseObservable;
//    private MutableLiveData<Boolean> IsCatUpdating;
//    private MutableLiveData<Boolean> IsSubUpdating;
//    private MutableLiveData<String> search;

//
//    public MutableLiveData<List<CategoryModel>> getCategoryListObservable() {
//        if (CategoryResponseObservable == null) {
//            CategoryResponseObservable = new MutableLiveData<>();
//        }
//        return CategoryResponseObservable;
//    }
//
//    public MutableLiveData<List<CategoryModel>> getSubCategoryListObservable() {
//        if (SubCategoryResponseObservable == null) {
//            SubCategoryResponseObservable = new MutableLiveData<>();
//        }
//        return SubCategoryResponseObservable;
//    }
//
//
//    public MutableLiveData<Boolean> getIsCatUpdating() {
//        if (IsCatUpdating == null) {
//            IsCatUpdating = new MutableLiveData<>();
//        }
//        return IsCatUpdating;
//    }
//
//    public MutableLiveData<Boolean> getIsSubCatUpdating() {
//        if (IsSubUpdating == null) {
//            IsSubUpdating = new MutableLiveData<>();
//        }
//        return IsSubUpdating;
//    }



//    public void CatergoryApi() {
//        IsCatUpdating.setValue(true);
//        HomeRepository.getInstance().GetCategory().observeForever(new Observer<List<CategoryModel>>() {
//            @Override
//            public void onChanged(@Nullable List<CategoryModel> Response) {
//                CategoryResponseObservable.setValue(Response);
//                IsCatUpdating.setValue(false);
//            }
//        });
//
//    }
//
//    public void SubCatergoryApi() {
//        IsSubUpdating.setValue(true);
//        HomeRepository.getInstance().GetSubCategory().observeForever(new Observer<List<CategoryModel>>() {
//            @Override
//            public void onChanged(@Nullable List<CategoryModel> Response) {
//                SubCategoryResponseObservable.setValue(Response);
//                IsSubUpdating.setValue(false);
//            }
//        });
//
//    }

//    public MutableLiveData<String> getSearch() {
//        if (search == null) {
//            search = new MutableLiveData<>();
//        }
//        return search;
//    }
}
