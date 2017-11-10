package com.lxp.widgetview.photoview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxp.widgetview.R;

/**
 * 图片预览
 * Created by Li Xiaopeng on 17/11/7.
 */

public class PicViewFragment2 extends Fragment implements View.OnClickListener {

    PhotoView picImageview;
    private Context mContext;
    /**
     *  获取fragment
     *
     */
    public static PicViewFragment2 getFragment() {
        PicViewFragment2 picViewFragment2 = new PicViewFragment2();
        Bundle bundle = new Bundle();
        picViewFragment2.setArguments(bundle);


        return picViewFragment2;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        Bundle arguments = getArguments();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pic_view2, container, false);

        initView(view);
        return view;
    }

    private Bitmap mBitmap;
    private boolean canSave = false;

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    private void initView( View view) {
        picImageview = (PhotoView) view.findViewById(R.id.pic_imageview);
        picImageview.enable();
        picImageview.setDragFinish(true, new PhotoView.FinishCallback() {
            @Override
            public void doFinish() {
                getActivity().finish();
            }
        });
        picImageview.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pic_imageview:
                Activity activity = getActivity();
                if (activity != null && !activity.isFinishing()) {
                    getActivity().finish();
                }
                break;
            default:
                break;
        }
    }
}
