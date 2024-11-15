package com.example.fakeqqmusic.ui.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fakeqqmusic.base.myview.MyViewPagerAdapter;
import com.example.fakeqqmusic.base.network.musicData;
import com.example.fakeqqmusic.databinding.FragmentHomeBinding;
import com.example.fakeqqmusic.ui.fragment.myzone.MyZoneFragment;
import com.example.fakeqqmusic.ui.fragment.myzone.MyZoneModel;
import com.example.fakeqqmusic.ui.fragment.myzone.MyZonePresenter;
import com.example.fakeqqmusic.ui.fragment.star.starFragment;
import com.example.fakeqqmusic.ui.fragment.star.starModel;
import com.example.fakeqqmusic.ui.fragment.star.starPresenter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private MyViewPagerAdapter adapter;
    private FragmentHomeBinding binding;
    private HomeContract.Presenter mPresenter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tabLayout = binding.tabLayout;
        viewPager = binding.viewPager;
        //        final TextView textView = binding.textHome;
        //        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        List<Fragment> fragmentList = new ArrayList<>();
        starFragment newsFragment = new starFragment();
        starPresenter presenter = new starPresenter(newsFragment, new starModel());
        fragmentList.add(newsFragment);
        MyZoneFragment myZoneFragment = MyZoneFragment.newInstance();
        MyZonePresenter presenter1 = new MyZonePresenter(myZoneFragment, new MyZoneModel());
        fragmentList.add(myZoneFragment);

        adapter = new MyViewPagerAdapter(getActivity(), fragmentList);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("推荐");
                    break;
                case 1:
                    tab.setText("乐馆");
                    break;
            }
        }).attach();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setHomeData(musicData musicData) {

    }

    @Override
    public void showError() {

    }

    @Override
    public Boolean isACtive() {
        //判断是否加入到Activity
        return isAdded();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
//        binding = null;
        mPresenter.unSubscribe();
        mPresenter = null;
    }
}
