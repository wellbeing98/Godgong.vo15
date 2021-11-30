package com.example.basicdemoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity1 extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle barDrawerToggle;

    TabLayout tabLayout;
    FrameLayout frameLayout;

    ViewPager pager;
    MyAdapter1 adapter;
    FirebaseAuth mFirebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();


        //Toolbar를 액션 바로 대체하기
        Toolbar  toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView=findViewById(R.id.nav);
        navigationView.setItemIconTintList(null);// 사이드 메뉴에 아이콘 색깔을 원래 아이콘 색으로


        drawerLayout=findViewById(R.id.layout_drawer);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);//누를때마다 아이콘이 팽그르 돈다.
        drawerToggle.syncState();// 삼선 메뉴 추가


        tabLayout=findViewById(R.id.layout_tab);
        //탭버튼(Tab객체) 생성
        TabLayout.Tab tab=null;
//        tab=tabLayout.newTab();
//        tab.setText("Home");
        //tab=tabLayout.newTab().setText("Home"); // 위 두줄을 한줄로
        // tab.setIcon(R.mipmap.ic_launcher); //요약된 한 줄에 아이콘 추가하여 한줄로
        tab=tabLayout.newTab().setText("Home").setIcon(R.mipmap.ic_launcher);
        tabLayout.addTab(tab);

        pager=findViewById(R.id.pager);
        adapter= new MyAdapter1(getSupportFragmentManager());
        pager.setAdapter(adapter);

        //TabLayout과 ViewPager를 연동
        tabLayout.setupWithViewPager(pager);
        //주의!  View페이저와 연동하게 되면
        //위에 직접 코드로 추가했던 Tab객체는 무시됨.
        //대신 ViewPager에서 탭버튼의 글씨를 설정

        //아이콘을 삽입하고 싶다면..
        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher);

        //제목줄에 서브제목 설정하기
        getSupportActionBar().setSubtitle("같이 공부하는 작은 공간");


        //탭 변경 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getSupportActionBar().setSubtitle(tab.getText());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        //네비게이션뷰에 아이템선택 리스너 추가
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);

                String getId = firebaseUser.getEmail();
                int id = item.getItemId();
                String title = item.getTitle().toString();

                Context context = null;

                if(id == R.id.menu_account) {
                    Toast.makeText(context, title + "회원님의 아이디는" + getId , Toast.LENGTH_SHORT).show();
                }
                else if (id==R.id.menu_look) {
                    Toast.makeText(context, title + ":내 교과목을 확인합니다.", Toast.LENGTH_SHORT).show();
                }
                else if (id == R.id.menu_logout) {
                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(i);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_search:
                Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}//MainActivity class..
