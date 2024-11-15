package com.example.fakeqqmusic.ui.fragment.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fakeqqmusic.R;
import com.example.fakeqqmusic.base.myview.MyViewPagerAdapter;
import com.example.fakeqqmusic.databinding.FragmentImgBinding;
import com.example.fakeqqmusic.databinding.FragmentZhiboBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class imgFragment extends Fragment{
    private FragmentImgBinding binding;
    ImageView img;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentImgBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        img = binding.image;
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        img.setImageResource(R.drawable.zhibo);
    }
    public void setImg(int res){
        img.setImageResource(res);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}