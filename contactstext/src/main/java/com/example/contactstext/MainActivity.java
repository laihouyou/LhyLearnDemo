package com.example.contactstext;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private List<String> mDataList=new ArrayList<>();
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mListView=findViewById(R.id.mListview);
        mAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mDataList);
        mListView.setAdapter(mAdapter);
        AppCompatButton button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(
                        MainActivity.this,
                        Manifest.permission.READ_CONTACTS)
                        ==PackageManager.PERMISSION_GRANTED){
                    addDataList();
                }else {
                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            1);
                }
            }
        });
    }

    public void  addDataList(){
        mDataList.clear();
        Cursor cursor=null;
        try {
            cursor=getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
            while (cursor.moveToNext()){
                //获取联系人名字
                String name=cursor.getString(
                        cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String tel=cursor.getString(
                        cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                //获取联系人电话号码
                mDataList.add(name+"\n"+tel);
            }
            cursor.close();
            mAdapter.notifyDataSetChanged();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor!=null){
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1){
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                addDataList();
            }else {
                Toast.makeText(this,getString(R.string.app_name3),Toast.LENGTH_LONG).show();
            }
        }
    }
}
