package com.example.administrator.lhylearndemo.day1.activity;

import android.os.Bundle;
import android.view.View;

import com.example.administrator.lhylearndemo.R;
import com.example.administrator.lhylearndemo.day1.fragment.TextFragment1;
import com.example.administrator.lhylearndemo.day1.fragment.TextFragment2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class TextFragmentActivity extends AppCompatActivity{
    boolean isTextFragment1=true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftagment);
        AppCompatButton button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextFragment1){
                    isTextFragment1=false;
                    replaceFragment(new TextFragment2());
                }else {
                    isTextFragment1=true;
                    replaceFragment(new TextFragment1());
                }
            }
        });
        replaceFragment(new TextFragment1());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction= fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout,fragment);
        transaction.addToBackStack("");
        transaction.commit();
    }
}
