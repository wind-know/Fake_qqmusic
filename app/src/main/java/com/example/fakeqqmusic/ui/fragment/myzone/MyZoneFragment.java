package com.example.fakeqqmusic.ui.fragment.myzone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fakeqqmusic.R;
import com.example.fakeqqmusic.base.adapter.musiclistAdapter;
import com.example.fakeqqmusic.base.network.musicData;
import com.example.fakeqqmusic.databinding.FragmentMyzoneBinding;
import com.example.fakeqqmusic.base.adapter.starAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class MyZoneFragment extends Fragment implements MyZoneContract.View{

    private FragmentMyzoneBinding binding;
    private  MyZoneContract.Presenter mpresenter;
    public static MyZoneFragment newInstance() {
        return new MyZoneFragment();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyZoneViewModel myZoneViewModel =
                new ViewModelProvider(this).get(MyZoneViewModel.class);
        binding = FragmentMyzoneBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mpresenter.onstart();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
        mpresenter.unSubscribe();
        mpresenter = null;
    }
    @Override
    public void onResume(){
        super.onResume();
    }
    public void initView(List<musicData> musicDatalist) {
//        FragmentManager fm = requireActivity().getSupportFragmentManager();
//        MyMusicListFragment fragment = (MyMusicListFragment) fm.findFragmentById(R.id.fragment_tuijiangedan);
//        if (fragment == null) {
//            FragmentTransaction ft = fm.beginTransaction();
//            MyMusicListFragment newFragment = MyMusicListFragment.newInstance(musicDatalist);
//            ft.add(R.id.fragment_tuijiangedan, newFragment);
//            ft.commit();
//        }
        loadFragment(new MyMusicListFragment(musicDatalist));
        binding.recyclerviewZuijinbofang.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.recyclerviewZuijinbofang.setAdapter(new musiclistAdapter(musicDatalist));

        binding.recyclerviewTuijiangedan.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewTuijiangedan.setAdapter(new starAdapter(musicDatalist));

        binding.TabLayoutzijiangedan.addTab(binding.TabLayoutzijiangedan.newTab().setText("自建歌单"));
        binding.TabLayoutzijiangedan.addTab(binding.TabLayoutzijiangedan.newTab().setText("收藏歌单"));
        binding.TabLayoutzijiangedan.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        loadFragment(new MyMusicListFragment(musicDatalist));
                        break;
                    case 1:
                        loadFragment(new MyMusicListFragment(musicDatalist));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_tuijiangedan, fragment);
        ft.commit();
    }
    @Override
    public void setPresenter(MyZoneContract.Presenter presenter) {
        this.mpresenter = presenter;
    }

    @Override
    public void showError() {

    }

    @Override
    public void setMyZoneData(musicData musicData) {
        List<musicData> data = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            data.add(musicData);
        }
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initView(data);
            }
        });
    }

    @Override
    public Boolean isACtive() {
        return isAdded();
    }
}