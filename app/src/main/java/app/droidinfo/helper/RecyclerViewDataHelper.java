package app.droidinfo.helper;

import java.util.ArrayList;
import java.util.List;

import app.droidinfo.adapter.Item;

public class RecyclerViewDataHelper {

    public static List<Item> recyclerViewFragment(String[] string1, String[] string2) {
        List<Item> list = new ArrayList<>();
        Item item;

        if (string1.length == string2.length) {
            for (int i = 0; i < string1.length; i++) {
                item = new Item(string1[i], string2[i]);
                list.add(item);
            }
        }

        return list;
    }

}

