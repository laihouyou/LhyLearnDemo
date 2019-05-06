package com.example.administrator.lhylearndemo.day12_28_view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.lhylearndemo.R;
import com.example.administrator.lhylearndemo.day19_4_28_viewpage2.ItemFragment;
import com.example.administrator.lhylearndemo.day19_4_28_viewpage2.MyItemRecyclerViewAdapter;
import com.example.administrator.lhylearndemo.day19_4_28_viewpage2.dummy.DummyContent;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MyViewActivity extends AppCompatActivity {
    private int mColumnCount=2;
    private Context context;

    private MyItemRecyclerViewAdapter itemRecyclerViewAdapter;
    private List<DummyContent.DummyItem> dummyItems=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view);
        context=this;

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
//            recyclerView.setLayoutManager(new GridLayoutManager(context,mColumnCount));
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        }
        itemRecyclerViewAdapter=new MyItemRecyclerViewAdapter(dummyItems,context);
        recyclerView.setAdapter(itemRecyclerViewAdapter);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(MyViewActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(5)
                        .minSelectNum(2)
                        .isZoomAnim(true)
                        .selectionMode(PictureConfig.MULTIPLE)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    dummyItems.clear();
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种 path
                    // 1.media.getPath(); 为原图 path
                    // 2.media.getCutPath();为裁剪后 path，需判断 media.isCut();是否为 true
                    // 3.media.getCompressPath();为压缩后 path，需判断 media.isCompressed();是否为 true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    for(int i=0;i<selectList.size();i++){
                        dummyItems.add(new DummyContent.DummyItem(i+"",selectList.get(i).getPath(),""));
                    }
                    itemRecyclerViewAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
