package com.example.fakeqqmusic.ui.fragment.myzone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fakeqqmusic.base.adapter.MyMusicListAdapter;
import com.example.fakeqqmusic.base.network.musicData;
import com.example.fakeqqmusic.databinding.FragmentMyMusicListBinding;

import java.util.ArrayList;
import java.util.List;


public class MyMusicListFragment extends Fragment {
    private FragmentMyMusicListBinding binding;
    private boolean isOpen = false;
    List<musicData> openList = new ArrayList<>();
    List<musicData> hideList= new ArrayList<>();
    private List<musicData> realList;
    private MyMusicListAdapter adapter;

    public MyMusicListFragment(List<musicData> list) {
        this.realList = list;

    }

    public static MyMusicListFragment newInstance(List<musicData> list) {
        return new MyMusicListFragment(list);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMyMusicListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        initlistener();
        adapter = new MyMusicListAdapter(realList);
        setData(realList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void initlistener() {
        // 处理展开收缩的逻辑
        binding.tvExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    adapter.setHideList(hideList);
                    binding.tvExpand.setText("展开全部歌单∨");
//                    binding.tvExpand.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.detail_open, 0);
                    isOpen = false;
                } else {
                    adapter.setOpenList(openList);
                    binding.tvExpand.setText("收起歌单∧");
//                    binding.tvExpand.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.detail_close, 0);
                    isOpen = true;
                }
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);


    }

    private void setData(List<musicData> list) {
        if (list.size() > 4) { // 设置大于多少条数据开始隐藏
            for (int i = 0, j = list.size(); i < j; i++) {
                openList.add(list.get(i));
            }
            for (int i = 0; i < 4; i++) {
                hideList.add(list.get(i));
            }
            adapter.setHideList(hideList);
        } else {
            adapter.setRealList(list);
        }
        binding.tvExpand.setVisibility(list.size() > 4 ? View.VISIBLE : View.GONE);
        binding.recyclerView.setAdapter(adapter);
    }

}