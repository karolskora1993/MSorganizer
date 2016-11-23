package com.karolskora.msorgranizer.java;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
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
import com.itextpdf.text.pdf.PdfAWriter;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.karolskora.msorgranizer.models.Injection;
import com.karolskora.msorgranizer.models.User;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class PdfGenerator{

    private static Bitmap resizedBitmap;

    public static void generate(Context context, List<Injection> list, String fileName){
        try {

            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                    + fileName);
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
        }
        catch (FileNotFoundException| DocumentException e){

            Log.e(PdfGenerator.class.toString(), "blad przy generowaniu pdf");

        }
    }

    private static PdfPTable addTable(Injection currentInj, Context context){

        PdfPTable table = new PdfPTable(2);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentInj.getTimeInMilis());
        String injectionDate = calendar.get(Calendar.DAY_OF_MONTH) + "." +
                calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR) +
                " " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
        PdfPCell cell=new PdfPCell(new Phrase("Data zastrzyku: "+injectionDate));
        table.addCell(cell);
        cell=new PdfPCell(new Phrase("Miejsce zastrzyku:"));
        table.addCell(cell);
        table.addCell("Objawy:");
        try {
            String field="f"+ String.valueOf(currentInj.getArea()) + String.valueOf(currentInj.
                    getPoint());
            Drawable d = ContextCompat.getDrawable(context, context.getResources().
                    getIdentifier(field, "drawable", context.getPackageName()));
            BitmapDrawable bitDw = ((BitmapDrawable) d);
            Bitmap bmp = bitDw.getBitmap();
            bmp=getResizedBitmap(bmp,200,200);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());
            PdfPCell imageCell = new PdfPCell(image);
            imageCell.setRowspan(2);
            table.addCell(imageCell);
        } catch (BadElementException | IOException  e) {
            e.printStackTrace();
        }
        com.itextpdf.text.List list=new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
        if(currentInj.isTemperature()) {
            ListItem item = new ListItem("  -temperatura\n");
            list.add(item);
        } if(currentInj.isAche()) {
            ListItem item = new ListItem("  -ból miesni\n");
            list.add(item);

        } if(currentInj.isTrembles()) {
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


    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        if(resizedBitmap == null) {
            resizedBitmap = Bitmap.createBitmap(
                    bm, 0, 0, width, height, matrix, false);
            bm.recycle();
        }

        return resizedBitmap;
    }

}
