package com.example.administrator.lhylearndemo.day_19_7_1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.lhylearndemo.R;
import com.example.administrator.mvp.model.Token;
import com.example.administrator.mvp.presenter.TestPicPresenter;
import com.example.administrator.mvp.view.TestPicView;

public class LoadingPicActivity extends BaseActivity implements TestPicView {
    private Button buttonshow;
    private Button buttonhide;
    private ImageView imageButton;

    private TestPicPresenter testPicPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_pic);


        testPicPresenter =new TestPicPresenter();
        testPicPresenter.attachView(this);

        buttonshow =findViewById(R.id.button5);
        buttonhide =findViewById(R.id.button6);
        imageButton=findViewById(R.id.imageButton);

        buttonshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testPicPresenter.showPic(Token.API_TEST_DATA,"http://guolin.tech/api/bing_pic");
            }
        });

        buttonhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePic();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        testPicPresenter.detachView();
    }

    @Override
    public void showPic(String url) {
        imageButton.setVisibility(View.VISIBLE);
        Glide.with(this).load(url).into(imageButton);
    }

    @Override
    public void hidePic() {
        imageButton.setVisibility(View.GONE);
    }
}
