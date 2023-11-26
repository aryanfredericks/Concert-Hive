package com.aryan.javaminiproject.TicketBooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.aryan.javaminiproject.TicketBooking.fragments.HomeFragment;
import com.aryan.javaminiproject.TicketBooking.fragments.ProfileFragment;
import com.aryan.javaminiproject.TicketBooking.fragments.TicketFragment;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class HomeActivity extends AppCompatActivity {
    MeowBottomNavigation bottomNav;
    FrameLayout containerF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        //Storing values from intent
        bottomNav = findViewById(R.id.bottomNav);
        containerF=findViewById(R.id.containerF);

        loadFragment(new HomeFragment(),1);

        bottomNav.show(2,true);
        bottomNav.add(new MeowBottomNavigation.Model(1,R.drawable.nav_ticket_icon));
        bottomNav.add(new MeowBottomNavigation.Model(2,R.drawable.baseline_home_24));
        bottomNav.add(new MeowBottomNavigation.Model(3,R.drawable.baseline_person_24));

        bottomNav.setOnClickMenuListener(model -> {
            if (intent != null && intent.getExtras() != null) {
                switch (model.getId()) {
                    case 1:
                        TicketFragment ticketFragment = new TicketFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("SENDUSERID",intent.getStringExtra("id"));
                        loadFragment(ticketFragment, 0);
                        break;
                    case 2:
                        HomeFragment homeFragment = new HomeFragment();
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("SENDUSERID",intent.getStringExtra("id"));
                        loadFragment(homeFragment, 0);
                        break;
                    case 3:
                        ProfileFragment profileFragment = new ProfileFragment();
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("SENDUSERID3",intent.getStringExtra("id"));
                        loadFragment(profileFragment, 0);
                        break;
                }
            }
            return null;
        });

    }

    private void loadFragment(Fragment fragment , int value){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("SENDUSERNAME", getIntent().getStringExtra("username"));
        bundle.putString("SENDEMAIL", getIntent().getStringExtra("email"));
        bundle.putString("SENDPASSWORD", getIntent().getStringExtra("password"));
        bundle.putInt("SENDUSERID", getIntent().getIntExtra("id", -3));
        fragment.setArguments(bundle);
        if (value==1){
            ft.add(R.id.containerF,fragment);
        }else{
            ft.replace(R.id.containerF,fragment);
        }
        ft.commit();
    }
}