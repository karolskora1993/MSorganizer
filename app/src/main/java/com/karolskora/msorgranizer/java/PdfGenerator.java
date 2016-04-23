package com.karolskora.msorgranizer.java;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAWriter;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.models.Injection;
import com.karolskora.msorgranizer.models.User;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Calendar;
import java.util.List;


/**
 * Created by apple on 14.04.2016.
 */
public class PdfGenerator{

    public static void generate(Context context, List<Injection> list, String fileName){

        try {


            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName);
            FileOutputStream output = new FileOutputStream(f);

            Document document = new Document(PageSize.A4);



            PdfAWriter.getInstance(document, output);

            document.open();

            User user = DatabaseQueries.getUser(context);

            String text = user.getName() + "- Raport z zastrzyków: \n \n ";

            document.addHeader("nagłówek", text);
            document.addTitle(text);
            document.addAuthor(user.getName());
            document.add(new Paragraph(text));

            for (Injection currentInj : list) {
                PdfPTable nextTable = addTable(currentInj, context);
                document.add(nextTable);
            }


            document.close();
            Log.d(PdfGenerator.class.toString(), "dokument zapisany");
        }
        catch (FileNotFoundException| DocumentException e){

            Log.e(PdfGenerator.class.toString(), "blad przy generowaniu pdf");

        }
    }

    private static PdfPTable addTable(Injection currentInj, Context context){

        PdfPTable table = new PdfPTable(2);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentInj.getTimeInMilis());
        String injectionDate = calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR) +
                " " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);

        PdfPCell cell=new PdfPCell(new Phrase("Data zastrzyku: "+injectionDate));
        table.addCell(cell);

        cell=new PdfPCell(new Phrase("Miejsce zastrzyku:"));
        table.addCell(cell);

        table.addCell("Objawy:");

        try {
            Drawable d = ContextCompat.getDrawable(context, R.drawable.ic_launcher);

            BitmapDrawable bitDw = ((BitmapDrawable) d);

            Bitmap bmp = bitDw.getBitmap();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

            Image image = Image.getInstance(stream.toByteArray());

            PdfPCell imageCell = new PdfPCell(image);
            imageCell.setRowspan(2);
            table.addCell(imageCell);

        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        com.itextpdf.text.List list=new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
        if(currentInj.isTemperature()) {
            ListItem item = new ListItem("  -temperatura\n");
            list.add(item);
        }
        if(currentInj.isAche()) {
            ListItem item = new ListItem("  -ból miesni\n");
            list.add(item);

        }
        if(currentInj.isTrembles()) {
            ListItem item = new ListItem("  -dreszcze\n");
            list.add(item);

        }

        Phrase phrase=new Phrase();
        phrase.add(list);
        cell=new PdfPCell(phrase);
        table.addCell(cell);

        table.setSpacingAfter(10f);
        return table;
    }

}
