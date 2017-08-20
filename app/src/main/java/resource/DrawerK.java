package resource;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.example.kala.movilkala.CitaActivity;
import com.example.kala.movilkala.DietaActivity;
import com.example.kala.movilkala.MensajeActivity;
import com.example.kala.movilkala.ProgresoActivity;
import com.example.kala.movilkala.R;
import com.example.kala.movilkala.RutinaActivity;
import com.example.kala.movilkala.SplashActivity;
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

/**
 * Created by HouSe on 16/08/2017.
 */

public class DrawerK {

    public static Drawer initDrawer(final Activity activity, final Toolbar toolbar) {

        SharedPreferences sesion = activity.getApplicationContext()
                .getSharedPreferences("user_sesion", Context.MODE_PRIVATE);

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
                .withName("Mis Progreso")
                .withIcon(R.drawable.ic_menu_gallery);

        SecondaryDrawerItem item5 = new SecondaryDrawerItem()
                .withIdentifier(5)
                .withName("Mis Mensajes")
                .withIcon(R.drawable.ic_menu_slideshow);

        SecondaryDrawerItem item6 = new SecondaryDrawerItem()
                .withIdentifier(6)
                .withName("Separar cita")
                .withIcon(R.drawable.material_drawer_badge);

        PrimaryDrawerItem item7 = new PrimaryDrawerItem()
                .withName("Cerrar sesión")
                .withIdentifier(7);
        PrimaryDrawerItem item8 = new PrimaryDrawerItem()
                .withName("Salir")
                .withIdentifier(8);

        //create the drawer and remember the `Drawer` result object
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withCompactStyle(false)
                //.withTextColor(R.color.colorAccent)
                .withHeaderBackground(R.color.colorAccent)
                //.withHeaderBackground(R.mipmap.ic_launcher)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(sesion.getString("nombre", "N") + " " +
                                        sesion.getString("apellido", "N"))
                                //.withEmail("mikepenz@gmail.com")
                        //.withIcon(getResources().getDrawable(R.drawable.material_drawer_badge))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        return new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withAccountHeader(headerResult)
                .addDrawerItems( item4, item2, item3, item5, item6 )
                .addStickyDrawerItems(item7, item8)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(drawerItem.getIdentifier() == 2L){

                            activity.startActivity(new Intent(activity, RutinaActivity.class));
                                return true;
                        }
                        else if(drawerItem.getIdentifier() == 3L){
                            activity.startActivity(new Intent(activity, DietaActivity.class));
                            return true;
                        }
                        else if(drawerItem.getIdentifier() == 4L){
                            activity.startActivity(new Intent(activity, ProgresoActivity.class));
                            return true;
                        }
                        else if(drawerItem.getIdentifier() == 5L){
                            activity.startActivity(new Intent(activity, MensajeActivity.class));
                            return true;
                        }
                        else if(drawerItem.getIdentifier() == 6L){
                            activity.startActivity(new Intent(activity, CitaActivity.class));
                            return true;
                        }
                        else if(drawerItem.getIdentifier() == 7L){
                            if(activity.getApplicationContext() != null) {
                                activity.getApplicationContext().getSharedPreferences("user_sesion",
                                        Context.MODE_PRIVATE).edit().clear().commit();
                                activity.finishAffinity();
                                activity.startActivity(new Intent(activity, SplashActivity.class));
                                return true;
                            }
                        }
                        else if(drawerItem.getIdentifier() == 8L){
                            activity.finishAffinity();
                            System.exit(0);
                            return true;
                        }
                        return false;
                    }
                })
                .build();
    }
}
