package com.example.youtubedemo.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.youtubedemo.bean.STATUS;
import com.example.youtubedemo.bean.SceneBean;

import java.util.Arrays;
import java.util.List;

public class DetailViewModel extends ViewModel {
    private final MutableLiveData<List<SceneBean>> sceneList;

    public DetailViewModel() {
        sceneList = new MutableLiveData<>();
    }

    public LiveData<List<SceneBean>> getSceneList() {
        return sceneList;
    }

    public void setSceneList() {
        SceneBean[] sceneBeans = new SceneBean[]{
                new SceneBean("1", STATUS.STATUS_NORMAL)
                , new SceneBean("2", STATUS.STATUS_NORMAL)
                , new SceneBean("3", STATUS.STATUS_LIKE)
                , new SceneBean("4", STATUS.STATUS_NORMAL)
                , new SceneBean("5", STATUS.STATUS_NORMAL)
                , new SceneBean("6", STATUS.STATUS_DISLIKE)
                , new SceneBean("7", STATUS.STATUS_NORMAL)
                , new SceneBean("8", STATUS.STATUS_NORMAL)
                , new SceneBean("9", STATUS.STATUS_NORMAL)
                , new SceneBean("10", STATUS.STATUS_NORMAL)
        };
        sceneList.setValue(Arrays.asList(sceneBeans));
    }
}