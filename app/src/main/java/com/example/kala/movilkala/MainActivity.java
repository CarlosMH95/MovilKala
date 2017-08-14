package com.example.kala.movilkala;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        if(getSupportActionBar() != null ) {
            getSupportActionBar().hide(); //.setDisplayShowTitleEnabled(false);
        }
        //setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName("Principal")
                .withIcon(R.drawable.ic_menu_send);

        SecondaryDrawerItem item2 = new SecondaryDrawerItem()
                .withIdentifier(2)
                .withName("Mis Rutinas")
                .withIcon(R.drawable.ic_menu_camera);

        SecondaryDrawerItem item3 = new SecondaryDrawerItem()
                .withIdentifier(3)
                .withName("Mis Dietas")
                .withIcon(R.drawable.ic_menu_manage);

        SecondaryDrawerItem item4 = new SecondaryDrawerItem()
                .withIdentifier(4)
                .withName("Mis Mensajes")
                .withIcon(R.drawable.ic_menu_slideshow);

        SecondaryDrawerItem item5 = new SecondaryDrawerItem()
                .withIdentifier(7)
                .withName("Separar cita")
                .withIcon(R.drawable.material_drawer_badge);

        PrimaryDrawerItem item6 = new PrimaryDrawerItem()
                .withName("Salir");
        //create the drawer and remember the `Drawer` result object
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
            .withActivity(this)
            .withCompactStyle(true)
            //.withTextColor(R.color.colorAccent)
             .withHeaderBackground(R.color.colorAccent)
            //.withHeaderBackground(R.mipmap.ic_launcher)
            .addProfiles(
                    new ProfileDrawerItem().withName("Mike Penz")
                            .withEmail("mikepenz@gmail.com")
                            .withIcon(getResources().getDrawable(R.drawable.material_drawer_badge))
            )
            .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                @Override
                public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                    return false;
                }
            })
            .build();

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                    item1,
                    new DividerDrawerItem(),
                    item2,
                    item3,
                    item4,
                    item5
                )
                .addStickyDrawerItems(item6)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(drawerItem.getIdentifier() == 7L){

                            startActivity(new Intent(MainActivity.this, CitaActivity.class));
                            //finish();
                            return true;
                        }
                        return false;
                    }
                })
                .build();
    }

}
