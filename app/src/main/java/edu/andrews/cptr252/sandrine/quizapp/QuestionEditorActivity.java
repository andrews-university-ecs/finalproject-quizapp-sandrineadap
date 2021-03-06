package edu.andrews.cptr252.sandrine.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.UUID;

public class QuestionEditorActivity extends FragmentActivity {

    public static final String TAG = "QuestionEditorActivity";

    /** ViewPager component that allows users to browse questions by swiping */
    private ViewPager mViewPager;

    /** Array of questions */
    private ArrayList<Question> mQuestions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create the ViewPager
        mViewPager = new ViewPager(this);
        // Viewpager needs a resource Id
        mViewPager.setId(R.id.viewPager);
        // Set the view for this activity to be the ViewPager
        // (Previously, it used the activity_fragment layout)
        setContentView(mViewPager);
        // get the list of questions
        mQuestions = QuestionList.getInstance(this).getQuestions();
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            // Create a new QuestionEditorFragment for question at given position in list.
            @Override
            public Fragment getItem(int i) {
                Question question = mQuestions.get(i);
                // Create a new instance of the QuestionEditorFragment with question id as argument
                return QuestionEditorFragment.newInstance(question.getId());
            }

            @Override
            public int getCount() {
                return mQuestions.size();
            }
        });

        // QuestionListFragment launches QuestionEditorActivity with a specific question id.
        // Get the Intent sent to this activity from the QuestionListFragment.
        UUID questionId;
        questionId = (UUID)getIntent()
                .getSerializableExtra(QuestionEditorFragment.EXTRA_QUESTION_ID);
        // Search through the list of questions to find question with same id as in intent
        for (int i = 0; i < mQuestions.size(); i++) {
            if (mQuestions.get(i).getId().equals(questionId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
        // If there is an App Bar (aka ActionBar),
        // display the question number there.
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }
            @Override
            public void onPageSelected(int i) {
                Question question = mQuestions.get(i);
                setTitle("Question #" + (i+1));
                Log.d(TAG, Integer.toString(i+1));
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        }); // end of OnPageChangeListener
    }// end of onCreate
}// end of QuestionEditorActivity
