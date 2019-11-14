package vn.edu.poly.apppet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import vn.edu.poly.apppet.fragment.CareFragment;
import vn.edu.poly.apppet.fragment.ChatFragment;
import vn.edu.poly.apppet.fragment.HomeFragment;
import vn.edu.poly.apppet.fragment.MapFragment;
import vn.edu.poly.apppet.fragment.NewsFragment;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);


        BottomNavigationView bottomNavigationView = findViewById (R.id.btNavigation);

        BottomNavigationViewHelper.removeShiftMode (bottomNavigationView);


        bottomNavigationView.setSelectedItemId (R.id.navHome);
        Fragment fragment = null;
        fragment = new HomeFragment ();
        FragmentManager fragmentManager = getFragmentManager ();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        fragmentTransaction.replace (R.id.frameLayout, fragment);
        fragmentTransaction.commit ();


        bottomNavigationView.setOnNavigationItemSelectedListener (new BottomNavigationView.OnNavigationItemSelectedListener () {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                if (item.getItemId () == R.id.navCare) {

                    fragment = new CareFragment ();
                    FragmentManager fragmentManager = getFragmentManager ();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                    fragmentTransaction.replace (R.id.frameLayout, fragment);
                    fragmentTransaction.commit ();


                } else if (item.getItemId () == R.id.navNews) {

                    fragment = new NewsFragment ();
                    FragmentManager fragmentManager = getFragmentManager ();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                    fragmentTransaction.replace (R.id.frameLayout, fragment);
                    fragmentTransaction.commit ();


                } else if (item.getItemId () == R.id.navHome) {

                    fragment = new HomeFragment ();
                    FragmentManager fragmentManager = getFragmentManager ();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                    fragmentTransaction.replace (R.id.frameLayout, fragment);
                    fragmentTransaction.commit ();

                } else if (item.getItemId () == R.id.navChat) {

                    fragment = new ChatFragment ();
                    FragmentManager fragmentManager = getFragmentManager ();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                    fragmentTransaction.replace (R.id.frameLayout, fragment);
                    fragmentTransaction.commit ();

                }
                return true;
            }
        });


    }


}
