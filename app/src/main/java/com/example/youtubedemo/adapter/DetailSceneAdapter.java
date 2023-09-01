package com.example.youtubedemo.adapter;

import static com.example.youtubedemo.bean.STATUS.STATUS_NORMAL;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubedemo.bean.SceneBean;
import com.hao.usbproject.R;
import com.hao.usbproject.databinding.AdapterItemDetailSceneBinding;
import com.hao.usbproject.databinding.AdapterItemHomeSceneBinding;

public class DetailSceneAdapter extends BaseDataBindingAdapter<SceneBean, AdapterItemDetailSceneBinding> {
    private HomeSceneAdapterListener listener;

    public DetailSceneAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_item_detail_scene;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindItem(AdapterItemDetailSceneBinding binding, SceneBean sceneBean, RecyclerView.ViewHolder holder) {
        binding.title.setText(sceneBean.getTitle());
        if (sceneBean.getStatus() == STATUS_NORMAL) {
            binding.status.setText("status_normal");
        }
        switch (sceneBean.getStatus()) {
            case STATUS_LIKE:
                binding.status.setText("status_like");
                break;
            case STATUS_DISLIKE:
                binding.status.setText("status_dislike");
                break;
            default:
                binding.status.setText("status_normal");
                break;
        }
        binding.status.setOnClickListener(v -> {
            if (listener != null) {
                listener.status(sceneBean, holder.getLayoutPosition());
            }
        });
    }


    public void setHomeSceneAdapterListener(HomeSceneAdapterListener listener) {
        this.listener = listener;
    }

    public interface HomeSceneAdapterListener {
        void status(SceneBean sceneBean, int position);
    }

}
