package com.example.fakeqqmusic.base.myview;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    java.util.List<Fragment> List;
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
        super(fragmentActivity);
        this.List = fragmentList;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return List.get(position);
    }
    @Override
    public int getItemCount() {
        return List != null ? List.size() : 0;
    }
}
