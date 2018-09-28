package com.uibenglish.aslan.mvpmindorkssample.ui.tasks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uibenglish.aslan.mvpmindorkssample.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;

public class ViewPagerAdapter extends PagerAdapter{

    @BindView(R.id.mItemImage)
    ImageView mImageView;
    @BindView(R.id.mItemText)
    TextView mItemText;
    @BindView(R.id.mItemSubText)
    TextView mItemSubtext;
    @BindView(R.id.group)
    Group mGroup;

    private List<ChoiceItemTest> testList = new ArrayList<>();
    private ViewPager mViewPager;
    private ChoiceClickListener mListener;

    public ViewPagerAdapter(ChoiceClickListener listener) {
        //mContext = context;
        mListener = listener;
    }

    @Override
    public int getCount() {
        return testList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater)container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_choice, null);
        ButterKnife.bind(this, view);

        final ChoiceItemTest choiceItemTest = testList.get(position);
        mGroup.setVisibility(View.GONE);
        mImageView.setImageResource(choiceItemTest.getLogo());
        mItemText.setText(choiceItemTest.getTitle());
        mItemSubtext.setText(choiceItemTest.getSubTitle());
        mViewPager = (ViewPager) container;
        mViewPager.addView(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Group group = v.findViewById(R.id.group);
                if (group.getVisibility()==VISIBLE) {
                    mListener.onViewPageClicked(choiceItemTest);
                }
            }
        });

        return view;
    }


    public void setNewData(List<ChoiceItemTest> choiceItemTests) {
        testList.clear();
        testList.addAll(choiceItemTests);
        notifyDataSetChanged();
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        mViewPager = (ViewPager) container;
        View view = (View) object;
        mViewPager.removeView(view);
        testList.clear();
    }

    public interface ChoiceClickListener{
        void onViewPageClicked(ChoiceItemTest choiceItemTest);
    }
}
