package com.example.fakeqqmusic.ui.fragment.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fakeqqmusic.base.myview.MyViewPagerAdapter;
import com.example.fakeqqmusic.databinding.FragmentZhiboBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class zhiboFragment extends Fragment {
    private FragmentZhiboBinding binding;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private MyViewPagerAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentZhiboBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tabLayout = binding.innerTabLayout;
        viewPager = binding.innerViewPager2;
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new imgFragment());
        fragmentList.add(new imgFragment());
        fragmentList.add(new imgFragment());
        fragmentList.add(new imgFragment());
        fragmentList.add(new imgFragment());

        adapter = new MyViewPagerAdapter(getActivity(), fragmentList);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("热门");
                    break;
                case 1:
                    tab.setText("新人");
                    break;
                case 2:
                    tab.setText("直播");
                    break;
                case 3:
                    tab.setText("附近");
                    break;
                case 4:
                    tab.setText("音乐");
                    break;
            }
        }).attach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}