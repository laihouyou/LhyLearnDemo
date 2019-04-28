package com.example.administrator.lhylearndemo.day19_4_28_viewpage2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.administrator.lhylearndemo.R;

public class TextFragment extends Fragment {
    private String id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment1,container,false);
        AppCompatTextView appCompatTextView=view.findViewById(R.id.text_fragment);
        if (getArguments()!=null){
            id=getArguments().getString("id");
        }
        appCompatTextView.setText("这是第"+id+"个页面");
        return view;
    }

    public static TextFragment getFragment(String cityCid){
        TextFragment weatherFragment=new TextFragment();
        Bundle bundle=new Bundle();
        bundle.putString("id",cityCid);
        weatherFragment.setArguments(bundle);
        return weatherFragment;
    }
}
