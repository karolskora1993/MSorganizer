package com.karolskora.msorgranizer.java;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.pdf.PrintedPdfDocument;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.karolskora.msorgranizer.models.Injection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by apple on 14.04.2016.
 */
public class PdfGenerator {

    public static void generate(Context context, List<Injection> list){
        // create a new document

        PrintAttributes printAttrs = new PrintAttributes.Builder().
                setColorMode(PrintAttributes.COLOR_MODE_COLOR).
                setMediaSize(PrintAttributes.MediaSize.NA_LETTER).
                setResolution(new PrintAttributes.Resolution("res", "res", 500, 500)).
                setMinMargins(PrintAttributes.Margins.NO_MARGINS).
                build();

        PdfDocument document = new PrintedPdfDocument(context,printAttrs);

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(500, 500, 1).create();

        PdfDocument.Page page = document.startPage(pageInfo);

        View content = getContentView(context, list);

        content.draw(page.getCanvas());

        document.finishPage(page);

        try {
            Calendar calendar=Calendar.getInstance();
            String fileName="report_"+ calendar.getTimeInMillis()+"pdf";
            File f = new File(Environment.getExternalStorageDirectory().getPath() + "/"+fileName);
            FileOutputStream fos = new FileOutputStream(f);
            document.writeTo(fos);
            document.close();
            fos.close();
        } catch (IOException e) {
            Log.e(PdfGenerator.class.toString(), e.getMessage());
        }

        Log.d(PdfGenerator.class.toString(), "dokument zapisany");
    }

    public static View getContentView(Context context, List<Injection> injections){

        LinearLayout layout=new LinearLayout(context);

        layout.setOrientation(LinearLayout.VERTICAL);

        for(Injection in: injections){

            LinearLayout content = new LinearLayout(context);
            content.setOrientation(LinearLayout.VERTICAL);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(in.getTimeInMilis());
            String injectionDate = calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR) +
                    " " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);

            TextView dateTextView=new TextView(context);
            dateTextView.setText("Data: " + injectionDate);
            content.addView(dateTextView);

            TextView symptomsTextView=new TextView(context);
            symptomsTextView.setText("Objawy:");
            content.addView(symptomsTextView);

            CheckBox temperature=new CheckBox(context);
            temperature.setChecked(in.isTemperature());

            CheckBox trembles=new CheckBox(context);
            trembles.setChecked(in.isTrembles());

            CheckBox ache=new CheckBox(context);
            ache.setChecked(in.isAche());

            content.addView(temperature);
            content.addView(trembles);
            content.addView(ache);

        }

        return layout;
    }
}
