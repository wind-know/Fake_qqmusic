package com.example.fakeqqmusic.ui.fragment.dashboard;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.fakeqqmusic.base.myview.MyViewPagerAdapter;
import com.example.fakeqqmusic.base.network.musicData;
import com.example.fakeqqmusic.databinding.FragmentDashboardBinding;
import com.example.fakeqqmusic.databinding.FragmentHomeBinding;
import com.example.fakeqqmusic.ui.fragment.home.HomeViewModel;
import com.example.fakeqqmusic.ui.fragment.myzone.MyZoneFragment;
import com.example.fakeqqmusic.ui.fragment.myzone.MyZoneModel;
import com.example.fakeqqmusic.ui.fragment.myzone.MyZonePresenter;
import com.example.fakeqqmusic.ui.fragment.star.starFragment;
import com.example.fakeqqmusic.ui.fragment.star.starModel;
import com.example.fakeqqmusic.ui.fragment.star.starPresenter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;


public class DashboardFragment extends Fragment implements DashboardContract.View {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private MyViewPagerAdapter adapter;
    private FragmentDashboardBinding binding;
    private DashboardContract.Presenter mPresenter;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tabLayout = binding.tabLayout;
        viewPager = binding.outerViewPager2;
        //        final TextView textView = binding.textHome;
        //        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new zhiboFragment());
        fragmentList.add(new zhiboFragment());
        fragmentList.add(new zhiboFragment());
        adapter = new MyViewPagerAdapter(getActivity(), fragmentList);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("听见");
                    break;
                case 1:
                    tab.setText("看见");
                    break;
                case 2:
                    tab.setText("交友");
                    break;
            }
        }).attach();
    }


    @Override
    public void setDashboardData(musicData musicData) {

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

    @Override
    public void setPresenter(DashboardContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
