package com.example.stefan.piatra_hartie_foarfece;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;

public class Rezultat extends AppCompatActivity {
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultat);

        ImageView jucator_output = (ImageView)findViewById(R.id.jucator_output) ;
        ImageView adversar_output = (ImageView)findViewById(R.id.adversar_output);

        Intent second_intent_text = getIntent();

        TextView text_output;
        String mesaj = second_intent_text.getStringExtra("raspuns");

        text_output = (TextView)findViewById(R.id.output);
        text_output.setText(mesaj);

        Bundle extras_jucator = getIntent().getExtras();
/*
        Bitmap imagineJucator = (Bitmap) extras_jucator.getParcelable("imagine_jucator");
        Bitmap imagineAdversar = (Bitmap) extras_jucator.getParcelable("imagine_adversar");

        jucator_output.setImageBitmap(imagineJucator);
        adversar_output.setImageBitmap(imagineAdversar);
        */

        Uri uriImagineJucator = (Uri)extras_jucator.getParcelable("imagine_jucator");

        try {
            Bitmap imgJucator = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriImagineJucator);

            jucator_output.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.id.jucator_output, 100, 100));
        } catch(Exception e) {
            System.out.printf("Error: %s\n", e.getStackTrace());
        }
    }
}
