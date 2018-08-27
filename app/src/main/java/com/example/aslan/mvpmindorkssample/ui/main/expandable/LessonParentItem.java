package com.example.aslan.mvpmindorkssample.ui.main.expandable;


import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class LessonParentItem extends ExpandableGroup<LessonChildItem>{


    public LessonParentItem(String title, List<LessonChildItem> items) {
        super(title, items);
    }

}
