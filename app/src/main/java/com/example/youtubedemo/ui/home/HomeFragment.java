package com.example.youtubedemo.ui.home;

import static com.example.youtubedemo.bean.STATUS.STATUS_DISLIKE;
import static com.example.youtubedemo.bean.STATUS.STATUS_LIKE;
import static com.example.youtubedemo.bean.STATUS.STATUS_NORMAL;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.youtubedemo.listener.OnNextListener;
import com.example.youtubedemo.adapter.HomeSceneAdapter;
import com.hao.usbproject.databinding.FragmentHomeBinding;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeSceneAdapter adapter;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.setViewModel(homeViewModel);
        initListener();
        initData();
        return root;
    }

    private void initData() {
        homeViewModel.setSceneList();
    }

    private void initListener() {
        adapter = new HomeSceneAdapter(getActivity());
        binding.setAdapter(adapter);

        adapter.setHomeSceneAdapterListener((sceneBean, position) -> {
            switch (sceneBean.getStatus()) {
                case STATUS_NORMAL:
                    sceneBean.setStatus(STATUS_LIKE);
                    break;
                case STATUS_LIKE:
                    sceneBean.setStatus(STATUS_DISLIKE);
                    break;
                case STATUS_DISLIKE:
                    sceneBean.setStatus(STATUS_NORMAL);
                    break;
                default:
                    break;
            }
            adapter.notifyDataSetChanged();
        });
        homeViewModel.getSceneList().observe(getActivity(), sceneBeans -> {
            adapter.notifyDataSetChanged();
        });
        binding.SmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Toast.makeText(getActivity(), "loading more", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(() -> {
                    binding.SmartRefreshLayout.finishLoadMoreWithNoMoreData();
                }, 1000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                new Handler().postDelayed(() -> {
                    homeViewModel.setSceneList();
                    binding.SmartRefreshLayout.finishRefresh();
                }, 1000);
            }
        });

        adapter.setOnItemClickListener((item, position) -> {
            if (listener != null) {
                listener.onNext();
            }
        });
    }

    private OnNextListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (OnNextListener) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}