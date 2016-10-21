package com.karolskora.msorgranizer.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.java.DatabaseQueries;
import com.karolskora.msorgranizer.java.PdfGenerator;
import com.karolskora.msorgranizer.models.Injection;
import com.karolskora.msorgranizer.models.User;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReportFragment extends Fragment implements View.OnClickListener {

    public ReportFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Injection> injections = DatabaseQueries.getInjections(getActivity());

        for(Injection in:injections){
            CheckBox checkBox=new CheckBox(getActivity());

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(in.getTimeInMilis());

            String injectionDate = calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR);
            String injectionTime=calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
            String description="data: "+ injectionDate+"    godzina: "+injectionTime;
            checkBox.setText(description);
            checkBox.setTextSize(18f);

            LinearLayout layout=(LinearLayout)getActivity().findViewById(R.id.layout_report);

            layout.addView(checkBox);
        }
    }

    public void onButtonGenerateReportClick() {
        List<Injection> checkedInjections= getListOfCheckedInjections();

        Calendar calendar=Calendar.getInstance();
        final String date= getReportDate();
        final String fileName="report_"+ calendar.getTimeInMillis()+".pdf";
        PdfGenerator.generate(getActivity(), checkedInjections, fileName);

        AlertDialog ad =createAlertDialog(date, fileName);

        ad.show();
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        try {
            getView().findViewById(R.id.fragmentReportButton).setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        onButtonGenerateReportClick();
    }

    private List<Injection> getListOfCheckedInjections() {
        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.layout_report);
        List<Injection> injections = DatabaseQueries.getInjections(getActivity());
        List<Injection> checkedInjections = new ArrayList<>();
        int size = layout.getChildCount();

        for (int i = 0; i < size; i++) {
            CheckBox checkBox = (CheckBox) layout.getChildAt(i);
            if (checkBox.isChecked()) {
                checkedInjections.add(injections.get(i));
            }
        }

        return checkedInjections;
    }

    private String getReportDate() {
        Calendar calendar=Calendar.getInstance();
        String injectionDate = calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR);
        String injectionTime=calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
        final String date="data: "+ injectionDate+"    godzina: "+injectionTime;

        return date;
    }

    private AlertDialog createAlertDialog(final String date, final String fileName) {
        AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
        ad.setCancelable(false);
        ad.setMessage("Czy chcesz wysłać raport do lekarza?");

        ad.setButton(AlertDialog.BUTTON_POSITIVE, "wyślij", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                User user = DatabaseQueries.getUser(getActivity());
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

        return ad;
    }
}
