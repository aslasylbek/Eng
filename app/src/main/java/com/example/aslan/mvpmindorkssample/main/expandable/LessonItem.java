package com.example.aslan.mvpmindorkssample.main.expandable;


import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class LessonItem extends ExpandableGroup<LessonTopicItem>{


    public LessonItem(String title, List<LessonTopicItem> items) {
        super(title, items);
    }

}
