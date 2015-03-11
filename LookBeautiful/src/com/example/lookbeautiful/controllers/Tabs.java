package com.example.lookbeautiful.controllers;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.example.lookbeautiful.R;
import com.example.lookbeautiful.controllers.CartActivity;
import com.example.lookbeautiful.controllers.GroupsActivity;
import com.example.lookbeautiful.controllers.SavedActivity;
import com.example.lookbeautiful.controllers.TryOnActivity;

public class Tabs extends TabActivity 
{
            /** Called when the activity is first created. */
            @Override
            public void onCreate(Bundle savedInstanceState)
            {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.tabs);

                    // create the TabHost that will contain the Tabs
                    TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);


//                    TabSpec tab1 = tabHost.newTabSpec("First Tab");
                    TabSpec tab2 = tabHost.newTabSpec("Second Tab");
//                    TabSpec tab3 = tabHost.newTabSpec("Third tab");
                    TabSpec tab4 = tabHost.newTabSpec("Fourth tab");

                   // Set the Tab name and Activity
                   // that will be opened when particular Tab will be selected
//                    tab1.setIndicator("Groups");
//                    tab1.setContent(new Intent(this,GroupsActivity.class));
////                    
//                    tab2.setIndicator("Cart");
//                    tab2.setContent(new Intent(this,CartActivity.class));
//
//                    tab3.setIndicator("Saved");
//                    tab3.setContent(new Intent(this,SavedActivity.class));
//                    
                    tab4.setIndicator("Try On");
                    tab4.setContent(new Intent(this,TryOnActivity.class));
                    
                    /** Add the tabs  to the TabHost to display. */
                   // tabHost.addTab(tab1);
                    //tabHost.addTab(tab2);
//                    tabHost.addTab(tab3);
                    tabHost.addTab(tab4);

            }
            
} 