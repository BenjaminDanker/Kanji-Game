package com.silvergem.kanjigame;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewLst.add(view);

        firstCreateCards();
    }



    static final List<Button> btnLst1 = new ArrayList<>();
    static final List<Button> btnLst2 = new ArrayList<>();
    static final List<View> viewLst = new ArrayList<>();
    static List<String[]> kanji_list_type;


    public static void firstCreateCards(){

        getKanjiList();
        createCards();
    }

    public static void getKanjiList(){

        List<List<String[]>> kanji_lists = com.silvergem.kanjigame.MainActivity.getKanjiLists();

        Context contxt = com.silvergem.kanjigame.MainActivity.getAppContext();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(contxt);
        int type = Integer.parseInt(sp.getString("card_type","1"));

        switch(type){
            case 1: kanji_list_type = kanji_lists.get(6); break;
            case 2: kanji_list_type = kanji_lists.get(5); break;
            case 3: kanji_list_type = kanji_lists.get(4); break;
            case 4: kanji_list_type = kanji_lists.get(3); break;
            case 5: kanji_list_type = kanji_lists.get(2); break;
            case 6: kanji_list_type = kanji_lists.get(1); break;
            case 7: kanji_list_type = kanji_lists.get(0); break;
        }
    }

    public static void removeCards(){

        LinearLayout ll = viewLst.get(0).findViewById(R.id.choice_grid1);
        for (int i = 0; i < btnLst1.size(); i++) {
            ll.removeView(btnLst1.get(i));
        }
        LinearLayout ll2 = viewLst.get(0).findViewById(R.id.choice_grid2);
        for (int i = 0; i < btnLst2.size(); i++) {
            ll2.removeView(btnLst2.get(i));
        }

    }

    public static void createCards(){

        Context contxt = com.silvergem.kanjigame.MainActivity.getAppContext();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(contxt);
        int count = Integer.parseInt(sp.getString("card_count","2"));


        Random random = new Random();
        Set<Integer> set = new LinkedHashSet<>();
        String[][] kanji_list = new String[count][];
        while (set.size() < count){
            set.add(random.nextInt(kanji_list_type.size()));
        }
        Iterator<Integer> iterate = set.iterator();
        for (int i = 0; i < count; i++) {
            kanji_list[i] = kanji_list_type.get(iterate.next());
        }
        String[] correct_kanji = kanji_list[new Random().nextInt(kanji_list.length)];


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

        LinearLayout ll = viewLst.get(0).findViewById(R.id.choice_grid1);
        for (int i = 0; i < count / 2; i++) {

            Button myButton = new Button(contxt);
            myButton.setText(kanji_list[i][3].replace("!"," "));
            myButton.setId(i + 1);
            myButton.setAllCaps(false);

            btnLst1.add(myButton);

            ll.addView(myButton, lp);

            if (kanji_list[i][0].equals(correct_kanji[0])) {
                myButton.setOnClickListener(v -> {
                    removeCards();
                    createCards();
                });
            }
        }

        LinearLayout ll2 = viewLst.get(0).findViewById(R.id.choice_grid2);
        for (int i = 0; i < count / 2; i++) {
            Button myButton = new Button(contxt);
            myButton.setText(kanji_list[i + (count / 2)][3].replace("!"," "));
            myButton.setId(i + 1);
            myButton.setAllCaps(false);

            btnLst2.add(myButton);

            ll2.addView(myButton, lp);

            if (kanji_list[i + (count / 2)][0].equals(correct_kanji[0])) {
                myButton.setOnClickListener(v -> {
                    removeCards();
                    createCards();
                });
            }
        }


        TextView kanji_big = viewLst.get(0).findViewById(R.id.kanji_big);
        kanji_big.setText(correct_kanji[0]);

        TextView hirakana = viewLst.get(0).findViewById(R.id.hirakana);
        hirakana.setText(correct_kanji[2]);
    }
}