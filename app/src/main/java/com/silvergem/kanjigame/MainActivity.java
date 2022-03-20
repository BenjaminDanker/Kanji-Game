package com.silvergem.kanjigame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public static Context getAppContext() {
        return context;
    }

    private static List<List<String[]>> kanji_lists;
    public static List<List<String[]>> getKanjiLists() {
        return kanji_lists;
    }


    public List<List<String[]>> createKanjiList(){
        InputStream is = getResources().openRawResource(R.raw.kanji_list);

        byte[] buffer = new byte[0];
        try {buffer = new byte[is.available()]; }
        catch (IOException e) { e.printStackTrace(); }

        while (true) {
            try {if ((is.read(buffer) != -1)) break; }
            catch (IOException e) { e.printStackTrace(); }
        }

        String kanji_str = new String(buffer);
        String[] kanji_str_list = kanji_str.split("@@");
        String[][] kanji_list = new String[kanji_str_list.length][3];
        for (int i = 0; i < kanji_str_list.length; i++) {
            kanji_list[i] = kanji_str_list[i].split(",");
        }

        List<String[]> kanji_list_S = new ArrayList<>();
        List<String[]> kanji_list_6 = new ArrayList<>();
        List<String[]> kanji_list_5 = new ArrayList<>();
        List<String[]> kanji_list_4 = new ArrayList<>();
        List<String[]> kanji_list_3 = new ArrayList<>();
        List<String[]> kanji_list_2 = new ArrayList<>();
        List<String[]> kanji_list_1 = new ArrayList<>();
        for (String[] strings : kanji_list) {
            switch (strings[1]) {
                case "S": kanji_list_S.add(strings); break;
                case "6": kanji_list_6.add(strings); break;
                case "5": kanji_list_5.add(strings); break;
                case "4": kanji_list_4.add(strings); break;
                case "3": kanji_list_3.add(strings); break;
                case "2": kanji_list_2.add(strings); break;
                case "1": kanji_list_1.add(strings); break;
                default:
                    break;
            }
        }

        List<List<String[]>> kanji_lists = new ArrayList<>();
        kanji_lists.add(kanji_list_S);
        kanji_lists.add(kanji_list_6);
        kanji_lists.add(kanji_list_5);
        kanji_lists.add(kanji_list_4);
        kanji_lists.add(kanji_list_3);
        kanji_lists.add(kanji_list_2);
        kanji_lists.add(kanji_list_1);


        return kanji_lists;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(toolbar.getOverflowIcon()).setColorFilter(ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.SRC_ATOP);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        kanji_lists = createKanjiList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, com.silvergem.kanjigame.SettingsActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}