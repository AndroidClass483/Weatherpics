package edu.rosehulman.defaritl.weatherpics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by defaritl on 1/24/2016.
 */
public class WeatherpicDetailFragment extends Fragment {

    private Weatherpic mWeatherpic;
    private ImageView mImageView;
    private TextView mTextView;
    private View mView;

    public WeatherpicDetailFragment(){}

    public WeatherpicDetailFragment(Weatherpic weatherpic){
        mWeatherpic = weatherpic;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View lindaView = inflater.inflate(R.layout.fragment_detail_weatherpic_pic, container, false);

        mImageView = (ImageView) lindaView.findViewById(R.id.imageView);
        mTextView = (TextView) lindaView.findViewById(R.id.textView);

        loadBitmap(mWeatherpic.getUrl(), mImageView);
        mTextView.setText(mWeatherpic.getCaption());

        return lindaView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void loadBitmap(String url, ImageView imageView){
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(url);
    }
}
