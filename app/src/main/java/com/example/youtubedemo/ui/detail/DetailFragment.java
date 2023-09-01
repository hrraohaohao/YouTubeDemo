package com.example.youtubedemo.ui.detail;

import static com.example.youtubedemo.bean.STATUS.STATUS_DISLIKE;
import static com.example.youtubedemo.bean.STATUS.STATUS_LIKE;
import static com.example.youtubedemo.bean.STATUS.STATUS_NORMAL;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.youtubedemo.adapter.DetailSceneAdapter;
import com.example.youtubedemo.adapter.HomeSceneAdapter;
import com.example.youtubedemo.ui.home.HomeViewModel;
import com.hao.usbproject.R;
import com.hao.usbproject.databinding.FragmentDetailBinding;
import com.hao.usbproject.databinding.FragmentHomeBinding;

public class DetailFragment extends Fragment {

    public static String TAG = DetailFragment.class.getName();
    private DetailViewModel mViewModel;
    private FragmentDetailBinding binding;
    private DetailSceneAdapter adapter;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.setViewModel(mViewModel);
        initView();
        initListener();
        initData();
        return root;
    }

    private void initView() {
        binding.viewContainer.transitionToStart();
    }

    private void initData() {
        mViewModel.setSceneList();
    }

    private void initListener() {
        adapter = new DetailSceneAdapter(getActivity());
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
        mViewModel.getSceneList().observe(getActivity(), sceneBeans -> {
            adapter.notifyDataSetChanged();
        });
    }


    public void closeFragment() {
        if (binding.viewContainer.getCurrentState() == R.id.end) {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .remove(this)
                    .commit();
        } else {
            binding.viewContainer.transitionToEnd();
        }
    }

}