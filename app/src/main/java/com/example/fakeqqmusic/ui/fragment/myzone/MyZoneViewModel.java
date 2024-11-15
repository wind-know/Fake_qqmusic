package com.example.fakeqqmusic.ui.fragment.myzone;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyZoneViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MyZoneViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}