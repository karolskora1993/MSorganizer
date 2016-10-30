package com.karolskora.msorgranizer.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.fragments.HistoryFragment;
import com.karolskora.msorgranizer.fragments.MainFragment;
import com.karolskora.msorgranizer.fragments.ReportFragment;
import com.karolskora.msorgranizer.fragments.ReserveFragment;
import com.karolskora.msorgranizer.fragments.SettingsFragment;
import com.karolskora.msorgranizer.fragments.StatsFragment;
import com.karolskora.msorgranizer.java.DatabaseQueries;
import com.karolskora.msorgranizer.models.User;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;

public class MainActivity extends AppCompatActivity {

    private String[] titles;
    private User user;
    private CrossfadeDrawerLayout crossfadeDrawerLayout;
    private Drawer result;
    private String menuBgColor = "#FFFFFF";
    private String menuItemColor = "#000000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_main);

        user = DatabaseQueries.getUser(this);
        if (user == null) {
            startFirstUseSettings();
        }
        else {
            int appStyle = DatabaseQueries.getApplicationStyle(this);

            if(appStyle == 2) {
                setTheme(R.style.darkAppTheme);
                Toolbar bar = getToolbar();
                menuBgColor = "#282828";
                menuItemColor = "#f5f5f5";
                bar.setBackground(new ColorDrawable(Color.parseColor(menuBgColor)));
            }
            Toolbar toolbar = getToolbar();
            titles = getResources().getStringArray(R.array.titles);

            if (savedInstanceState == null)
                selectItem(1);

            AccountHeader headerResult = buildAccountHeader();

            buildDrawer(savedInstanceState, toolbar, headerResult);
            buildCrossfadeDrawer();
        }
    }

    private void selectItem(int position) {
        Fragment fragment = new MainFragment();
        switch (position) {
            case 2:
                fragment = new HistoryFragment();
                break;
            case 3:
                fragment = new ReserveFragment();
                break;
            case 4:
                fragment = new ReportFragment();
                break;
            case 6:
                fragment = new StatsFragment();
                break;
            case 7:
                fragment = new SettingsFragment();
                break;
            case 8:
                Intent intent=new Intent(this, HelpActivity.class);
                startActivity(intent);
                break;
            default:
                fragment = new MainFragment();
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.drawer_layout, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        setActionBarTitle(position-1);
    }

    public void setActionBarTitle(int position) {
        String title;
        if (position == 0) {
            title = getResources().getString(R.string.app_name);
        } else
            title = this.titles[position];

        getSupportActionBar().setTitle(title);

    }

    private void startFirstUseSettings() {
        Intent intent = new Intent(this, AppStyleActivity.class);
        startActivity(intent);
    }

    private Toolbar getToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        return toolbar;
    }

    private AccountHeader buildAccountHeader() {
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header_bg)
                .addProfiles(
                        new ProfileDrawerItem().withName(user.getName()).withEmail(user.getEmail()).withIcon(getResources().getDrawable(R.drawable.ic_face_black_48dp))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        return headerResult;
    }

    private void buildDrawer(Bundle savedInstanceState, Toolbar toolbar, AccountHeader headerResult) {
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withDisplayBelowStatusBar(true)
                .withHasStableIds(true)
                .withDrawerLayout(R.layout.crossfade_drawer)
                .withDrawerWidthDp(72)
                .withSliderBackgroundDrawable(new ColorDrawable(Color.parseColor(menuBgColor)))
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withGenerateMiniDrawer(true)
                .withHeader(R.layout.material_drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(titles[0]).withIcon(R.drawable.ic_home_black_48dp).withTextColor(Color.parseColor(menuItemColor)),
                        new PrimaryDrawerItem().withName(titles[1]).withIcon(R.drawable.ic_history_black_48dp).withTextColor(Color.parseColor(menuItemColor)),
                        new PrimaryDrawerItem().withName(titles[2]).withIcon(R.drawable.ic_settings_black_48dp).withTextColor(Color.parseColor(menuItemColor)),
                        new PrimaryDrawerItem().withName(titles[3]).withIcon(R.drawable.ic_receipt_black_48dp).withTextColor(Color.parseColor(menuItemColor)),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(titles[5]).withIcon(R.drawable.ic_trending_up_black_48dp).withTextColor(Color.parseColor(menuItemColor)),
                        new PrimaryDrawerItem().withName(titles[6]).withIcon(R.drawable.ic_settings_applications_black_48dp).withTextColor(Color.parseColor(menuItemColor)),
                        new PrimaryDrawerItem().withName(titles[7]).withIcon(R.drawable.ic_live_help_black_48dp).withTextColor(Color.parseColor(menuItemColor))
                )
                .withSavedInstance(savedInstanceState)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if (drawerItem != null) {
                            selectItem(position);
                        }

                        return false;
                    }
                })
                .build();
    }

    private void buildCrossfadeDrawer() {
        crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();

        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
        final MiniDrawer miniResult = result.getMiniDrawer();
        View view = miniResult.build(this);
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);

                if (isFaded) {
                    result.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });
    }

    public void updateTheme() {
        int appStyle = DatabaseQueries.getApplicationStyle(this);
        if(appStyle == 1) {
            setTheme(R.style.lightAppTheme);
            Toolbar bar = getToolbar();
            menuBgColor = "#FFFFFF";
            menuItemColor = "#000000";
            bar.setBackground(new ColorDrawable(Color.parseColor(menuBgColor)));

            AccountHeader headerResult = buildAccountHeader();

            buildDrawer(null, bar, headerResult);
            buildCrossfadeDrawer();
        }
        else if(appStyle == 2) {
            setTheme(R.style.darkAppTheme);
            Toolbar bar = getToolbar();
            menuBgColor = "#282828";
            menuItemColor = "#f5f5f5";
            bar.setBackground(new ColorDrawable(Color.parseColor(menuBgColor)));

            AccountHeader headerResult = buildAccountHeader();

            buildDrawer(null, bar, headerResult);
            buildCrossfadeDrawer();
        }
    }
}

