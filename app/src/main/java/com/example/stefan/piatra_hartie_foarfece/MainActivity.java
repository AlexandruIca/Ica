package com.example.stefan.piatra_hartie_foarfece;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    private class ImageOnClickListener implements View.OnClickListener {
        private int m_alegere;

        ImageOnClickListener(int t_alegere) {
            m_alegere = t_alegere;
        }

        @Override
        public void onClick(View v) {
            alegereAdversar(m_alegere);
        }
    }
/*
    public enum PosibilitateAlegere {
        PIATRA(0),
        HARTIE(1),
        FOARFECE(2),
        COUNT(3);

        private int m_value;

        PosibilitateAlegere(int t_value)
        { m_value = t_value; }

        public int value()
        { return m_value; }

        public static PosibilitateAlegere laNimereala() {
            Random n = new Random();

            return PosibilitateAlegere.valueOf(String.valueOf(
                        n.nextInt(COUNT.value())
                    )
            );
        }
    }
    */

/*
    public enum PosibilitateRezultat {
        CASTIGATOR(0),
        PIERZATOR(1),
        EGALITATE(2);

        private int m_value;

        PosibilitateRezultat(int t_value)
        { m_value = t_value; }

        public int value()
        { return m_value; }
    }
*/
    private final int PIATRA = 0;
    private final int HARTIE = 1;
    private final int FOARFECE = 2;
    private final int COUNT = 3;

    private final int CASTIGATOR = 0;
    private final int PIERZATOR = 1;
    private final int EGALITATE = 2;

    private final String[] posibilitateToStr = new String[]{ "piatra", "hartie", "foarfece" };

    public int tipRezultat(int t_alegereJucator, int t_alegereAdversar) {
        if(t_alegereJucator == t_alegereAdversar) {
            return EGALITATE;
        }

        switch(t_alegereJucator) {
            case PIATRA:
                if(t_alegereAdversar == HARTIE) {
                    return PIERZATOR;
                }
                else {
                    return CASTIGATOR;
                }
            case HARTIE:
                if(t_alegereAdversar == FOARFECE) {
                    return PIERZATOR;
                }
                else {
                    return CASTIGATOR;
                }
            case FOARFECE:
                if(t_alegereAdversar == PIATRA) {
                    return PIERZATOR;
                }
                else {
                    return CASTIGATOR;
                }
            default:
                throw new RuntimeException();
        }
    }

    ImageButton piatra_image_button;
    ImageButton hartie_image_button;
    ImageButton foarfece_image_button;

    ImageButton[] imageButtons = new ImageButton[COUNT];

    Intent intent_jucator = new Intent(this,Rezultat.class);

    public int jucator_input = PIATRA;

    public void alegereAdversar(int t_alegereJucator) {
        int alegereAdversar = new Random().nextInt(COUNT);
        batalie(alegereAdversar);
        startActivity(jucator_output(t_alegereJucator, alegereAdversar));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        piatra_image_button = (ImageButton) findViewById(R.id.piatra);
        hartie_image_button = (ImageButton)findViewById(R.id.hartie);
        foarfece_image_button = (ImageButton)findViewById(R.id.foarfece);

        this.imageButtons[PIATRA] = (ImageButton)findViewById(R.id.piatra);
        this.imageButtons[HARTIE] = (ImageButton)findViewById(R.id.hartie);
        this.imageButtons[FOARFECE] = (ImageButton)findViewById(R.id.foarfece);

        for(int i = 0; i < COUNT; i++) {
            this.imageButtons[i].setOnClickListener(new ImageOnClickListener(i));
        }
    }

    public void batalie(int t_alegereAdversar) {
        try {
            deschide_rezultat(tipRezultat(jucator_input, t_alegereAdversar));
        } catch(RuntimeException e) {
            System.out.printf("jucatorul luat cumva alegerea COUNT: %s\n", e.getCause());
        }
    }

    public void deschide_rezultat(int t_rezultat) {
        Intent intent_text = new Intent(this,Rezultat.class);

        String mesaj = "";

        if(t_rezultat == PIERZATOR) {
            mesaj = "Ai pierdut";
        }
        else if (t_rezultat == CASTIGATOR) {
            mesaj = "Ai castigat!";
        }
        else if (t_rezultat == EGALITATE) {
            mesaj = "Egalitate!";
        }

        intent_text.putExtra("raspuns", mesaj);
    }

    public Intent jucator_output(int t_alegereJucator, int t_alegereAdversar) {
        Intent intentImagine = new Intent(this, Rezultat.class);
/*
        ImageView[] imagini = new ImageView[COUNT];

        imagini[PIATRA] = (ImageView)findViewById(R.id.piatra);
        imagini[HARTIE] = (ImageView)findViewById(R.id.hartie);
        imagini[FOARFECE] = (ImageView)findViewById(R.id.foarfece);

        Bundle extrasJucator = new Bundle();

        Bitmap bitmapJucator = ((BitmapDrawable)imagini[t_alegereJucator].getDrawable()).getBitmap();
        Bitmap bitmapAdversar = ((BitmapDrawable)imagini[t_alegereAdversar].getDrawable()).getBitmap();

        extrasJucator.putParcelable("imagine_jucator", bitmapJucator);
        extrasJucator.putParcelable("imagine_adversar", bitmapAdversar);

        intentImagine.putExtras(extrasJucator);
*/
        Uri uriImagineJucator = Uri.fromFile(
                new File("//android_asset/" + posibilitateToStr[t_alegereJucator] + ".png")
        );

        intentImagine.putExtra("imagine_jucator", uriImagineJucator);

        return intentImagine;
    }

}


