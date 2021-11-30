package com.example.basicdemoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyAdapter1 extends FragmentPagerAdapter {

    Fragment[] fragments= new Fragment[1];
    String[] pageTitles= new String[]{"Home","", ""};
    public MyAdapter1(@NonNull FragmentManager fm) {
        super(fm);

        fragments[0]=  new Page1Fragment();


    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }
    @Override
    public int getCount() {
        return fragments.length;
    }

    //뷰페이저와 연동된 텝레이아웃의 탭버튼들의
    //글씨를 리턴해주는 메소드
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }
}