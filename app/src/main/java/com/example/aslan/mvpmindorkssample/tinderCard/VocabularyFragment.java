package com.example.aslan.mvpmindorkssample.tinderCard;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aslan.mvpmindorkssample.R;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;



public class VocabularyFragment extends Fragment {

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vocabulary, container, false);
        mSwipeView = view.findViewById(R.id.swipeView);
        mContext = getActivity().getApplicationContext();
        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.words_swipe_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.words_swipe_out_msg_view));

        Profile profile = new Profile();
        profile.setName("asfddf");
        profile.setLocation("asd");
        profile.setAge(20);
        profile.setImageUrl("http://data.whicdn.com/images/60103426/large.jpg");

        mSwipeView.addView(new WordsCard(mContext, profile, mSwipeView));


        view.findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        view.findViewById(R.id.knownBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });

        return view;
    }

}
