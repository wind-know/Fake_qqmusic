package com.example.fakeqqmusic.ui.fragment.star;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fakeqqmusic.base.adapter.musiclistAdapter;
import com.example.fakeqqmusic.base.adapter.starthreeAdapter;
import com.example.fakeqqmusic.base.network.musicData;
import com.example.fakeqqmusic.databinding.FragmentStarBinding;

import java.util.ArrayList;
import java.util.List;


public class starFragment extends Fragment implements starContract.View{
    private FragmentStarBinding binding;
    private starContract.Presenter mPresenter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentStarBinding.inflate(inflater, container, false);
        mPresenter.onstart();
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        binding = null;
        mPresenter.unSubscribe();
        mPresenter = null;
    }

    @Override
    public void showError() {

    }

    @Override
    public void setStarData(musicData starData) {
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initView(starData);
            }
        });

    }

    @Override
    public Boolean isACtive() {
        //判断是否加入到Activity
        return isAdded();
    }

    public void initView(musicData starData){
        List<List<musicData>> dataList = new ArrayList<>();
        List<musicData> data = new ArrayList<>();
        data.add(starData);
        data.add(starData);
        data.add(starData);
        for(int i = 0; i < 3; i++){
            dataList.add(data);
        }

        binding.starRecyclerViewFirst.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        musiclistAdapter musiclistAdapter = new musiclistAdapter(data);
        binding.starRecyclerViewFirst.setAdapter(musiclistAdapter);

        binding.starRecyclerViewSecond.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        starthreeAdapter starAdapter2 = new starthreeAdapter();
        starAdapter2.starthreeAdapter(dataList);
        binding.starRecyclerViewSecond.setAdapter(starAdapter2);

        binding.starRecyclerViewThird.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        starthreeAdapter starAdapter3 = new starthreeAdapter();
        starAdapter3.starthreeAdapter(dataList);
        binding.starRecyclerViewThird.setAdapter(starAdapter3);
    }
    @Override
    public void setPresenter(starContract.Presenter presenter) {
        mPresenter = presenter;
    }
}