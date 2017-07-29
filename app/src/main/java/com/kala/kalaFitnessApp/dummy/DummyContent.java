package com.kala.kalaFitnessApp.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kala.kalaFitnessApp.R;

/**
 * Just dummy content. Nothing special.
 *
 * Created by Andreas Schrade on 14.12.2015.
 */
public class DummyContent {

    /**
     * An array of sample items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample items. Key: sample ID; Value: Item.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<>(5);

    static {
        addItem(new DummyItem("1", R.drawable.p1, "Rutina #1", "Algo Algo", "Focusing is about saying \n \n No."));
        addItem(new DummyItem("2", R.drawable.p2, "Rutina #2", "Algo Algo","A quitter never wins and a winner never quits."));
        addItem(new DummyItem("3", R.drawable.p3, "Rutina #3", "Algo mas", "Action is the foundational key to all success."));
        addItem(new DummyItem("4", R.drawable.p4, "Rutina #4", "Algo ","Our only limitations are those we set up in our own minds."));
        addItem(new DummyItem("5", R.drawable.p5, "Rutina #5", "Algo mas","Deciding what not do do is as important as deciding what to do."));
    }

    private static void addItem(DummyItem item) {

        //aqui se carga lo que el set pone, entonces deberia aqui ingresarse lo que tendria cada rutina
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class DummyItem {
        public final String id;
        public final int photoId;
        public final String title;
        public final String author;
        public final String content;

        public DummyItem(String id, int photoId, String title, String author, String content) {
            this.id = id;
            this.photoId = photoId;
            this.title = title;
            this.author = author;
            this.content = content;
        }
    }
}
