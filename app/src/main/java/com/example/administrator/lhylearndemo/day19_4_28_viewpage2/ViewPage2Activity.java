package com.example.administrator.lhylearndemo.day19_4_28_viewpage2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.administrator.lhylearndemo.R;
import com.example.administrator.lhylearndemo.day1.fragment.TextFragment1;

import java.util.ArrayList;
import java.util.List;

public class ViewPage2Activity extends AppCompatActivity  implements View.OnClickListener {
    private ViewPager2 viewPager2;
    private Spinner itemSpinner;
    private Button buttonGoTo;
    private Button buttonRemove;
    private Button buttonAddBefore;
    private Button buttonAddAfter;

    private List<Fragment> fragmentList=new ArrayList<>();
    private List<String> itemList=new ArrayList<>();
    private FragmentStateAdapter fragmentStateAdapter;

    private int pos;
    private int conste=1;

    private Fragment fragment;
    private String item;

    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutable_collection);
        viewPager2=findViewById(R.id.viewPager);
        itemSpinner=findViewById(R.id.itemSpinner);
        buttonGoTo=findViewById(R.id.buttonGoTo);
        buttonRemove=findViewById(R.id.buttonRemove);
        buttonAddBefore=findViewById(R.id.buttonAddBefore);
        buttonAddAfter=findViewById(R.id.buttonAddAfter);

        buttonGoTo.setOnClickListener(this);
        buttonRemove.setOnClickListener(this);
        buttonAddBefore.setOnClickListener(this);
        buttonAddAfter.setOnClickListener(this);


        fragmentStateAdapter=new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
            }
        };
        viewPager2.setAdapter(fragmentStateAdapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position<=itemList.size()){
                    pos=position;

                    fragment=fragmentList.get(position);
                    item=itemList.get(position);
                }
            }
        });

        initData();

        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,itemList);
        itemSpinner.setAdapter(arrayAdapter);
        itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fragment=fragmentList.get(position);
                item=itemList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initData() {
        for (int i=0;i<12;i++){
            fragmentList.add(TextFragment.getFragment(i+""));
            itemList.add("第"+i+"个");
            conste++;
        }

        fragmentStateAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.buttonGoTo){
            viewPager2.setCurrentItem(fragmentList.indexOf(fragment));
            fragmentStateAdapter.notifyDataSetChanged();
        }
        else if (v.getId()==R.id.buttonRemove){
           remove();
        }
        else if (v.getId()==R.id.buttonAddBefore){
            addBefore();
        }
        else if (v.getId()==R.id.buttonAddAfter){

        }
    }

    private void addBefore() {
        fragmentList.add(TextFragment.getFragment(conste+""));
        fragmentStateAdapter.notifyDataSetChanged();

        itemList.add("第"+conste+"个");
        arrayAdapter.notifyDataSetChanged();
    }

    private void remove() {
        fragmentList.remove(fragment);
        fragmentStateAdapter.notifyDataSetChanged();

        itemList.remove(item);
        arrayAdapter.notifyDataSetChanged();

    }
}
