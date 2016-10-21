package com.karolskora.msorgranizer.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.fragments.HistoryFragment;
import com.karolskora.msorgranizer.fragments.LastInjectionDetailsFragment;
import com.karolskora.msorgranizer.fragments.MainFragment;
import com.karolskora.msorgranizer.fragments.ReportFragment;
import com.karolskora.msorgranizer.fragments.ReserveFragment;
import com.karolskora.msorgranizer.fragments.SettingsFragment;
import com.karolskora.msorgranizer.fragments.StatsFragment;
import com.karolskora.msorgranizer.java.DatabaseHelper;
import com.karolskora.msorgranizer.java.DatabaseQueries;
import com.karolskora.msorgranizer.java.PdfGenerator;
import com.karolskora.msorgranizer.models.Injection;
import com.karolskora.msorgranizer.models.Notification;
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
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private String[] titles;
    private User user;
    private CrossfadeDrawerLayout crossfadeDrawerLayout;
    private Drawer result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_main);

        user = DatabaseQueries.getUser(this);
        if (user == null) {
            startUserInformationActivity();
        }
        else {
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

    public void onButtonSaveSymptomsClick(View view) {

        CheckBox temperatureCheckBox=(CheckBox)findViewById(R.id.temperatureCheckBox);
        CheckBox tremblesCheckBox=(CheckBox)findViewById(R.id.tremblesCheckBox);
        CheckBox acheCheckBox=(CheckBox)findViewById(R.id.acheCheckBox);


        boolean temperature=temperatureCheckBox.isChecked();
        boolean trembles=tremblesCheckBox.isChecked();
        boolean ache=acheCheckBox.isChecked();

        LastInjectionDetailsFragment fragment = (LastInjectionDetailsFragment)getFragmentManager().findFragmentByTag("LAST_INJECTION_FRAGMENT");

        Injection injection=fragment.getInjection();
        DatabaseQueries.updateInjection(this, injection, temperature, trembles, ache);

        Toast toast = Toast.makeText(this, "Zaktualizowano objawy", Toast.LENGTH_LONG);
        toast.show();

    }

    public void onButtonGenerateReportClick(View view) {

        LinearLayout layout=(LinearLayout)findViewById(R.id.layout_report);

        List<Injection> injections=DatabaseQueries.getInjections(this);
        List<Injection> checkedInjections= new ArrayList<>();

        int size=layout.getChildCount();

        for(int i=0; i<size; i++){
            CheckBox checkBox =(CheckBox)layout.getChildAt(i);

            if(checkBox.isChecked())
            {
                checkedInjections.add(injections.get(i));
            }
        }

        Calendar calendar=Calendar.getInstance();
        String injectionDate = calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR);
        String injectionTime=calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
        final String date="data: "+ injectionDate+"    godzina: "+injectionTime;

        final String fileName="report_"+ calendar.getTimeInMillis()+".pdf";
        PdfGenerator.generate(this, checkedInjections, fileName);

        AlertDialog ad = new AlertDialog.Builder(this).create();
        ad.setCancelable(false);
        ad.setMessage("Czy chcesz wysłać raport do lekarza?");

        ad.setButton(AlertDialog.BUTTON_POSITIVE, "wyślij", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"email@example.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, user.getName() + "-raport");
                intent.putExtra(Intent.EXTRA_TEXT, "Raport wysłany w dniu " + date);
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName);
                Uri uri = Uri.fromFile(file);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(intent, "Wyślij email..."));
            }

        });

        ad.setButton(AlertDialog.BUTTON_NEGATIVE, "wyświetl", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }

        });

        ad.show();

    }

    public void onButtonInjectClick(View view) {

        Intent intent=new Intent(this, InjectionActivity.class);

        startActivity(intent);
    }

    public Notification getInjectionsSchedule() {
        dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);

        RuntimeExceptionDao<Notification, Integer> injectionSchedulesDao = dbHelper.getInjectionsScheduleDao();

        return injectionSchedulesDao.queryForAll().iterator().next();
    }

    private void startUserInformationActivity() {
        Intent intent = new Intent(this, UserInformationsActivity.class);
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
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withGenerateMiniDrawer(true)
                .withHeader(R.layout.material_drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(titles[0]).withIcon(R.drawable.ic_home_black_48dp),
                        new PrimaryDrawerItem().withName(titles[1]).withIcon(R.drawable.ic_history_black_48dp),
                        new PrimaryDrawerItem().withName(titles[2]).withIcon(R.drawable.ic_settings_black_48dp),
                        new PrimaryDrawerItem().withName(titles[3]).withIcon(R.drawable.ic_receipt_black_48dp),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(titles[5]).withIcon(R.drawable.ic_trending_up_black_48dp),
                        new PrimaryDrawerItem().withName(titles[6]).withIcon(R.drawable.ic_settings_applications_black_48dp),
                        new PrimaryDrawerItem().withName(titles[7]).withIcon(R.drawable.ic_live_help_black_48dp)
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
}

