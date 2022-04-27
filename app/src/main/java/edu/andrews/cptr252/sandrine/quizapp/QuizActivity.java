package edu.andrews.cptr252.sandrine.quizapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toolbar;


import java.util.ArrayList;
import java.util.UUID;

public class QuizActivity extends FragmentActivity {
    public static final String TAG = "QuizActivity";

    /** ViewPager component that allows users to browse questions by swiping */
    private ViewPager mViewPager;

    /** Array of questions */
    private ArrayList<Question> mQuestions;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = getLayoutInflater().inflate(R.layout.fragment_quiz,null);

        Toolbar myToolbar = v.findViewById(R.id.quiz_toolbar);
        myToolbar.setTitle("question");
        setActionBar(myToolbar);

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
            // Create a new QuizFragment for question at given position in list.
            @Override
            public Fragment getItem(int i) {
                Question question = mQuestions.get(i);
                // Create a new instance of the QuizFragment with question id as argument
                return QuizFragment.newInstance(question.getId(), i);
            }

            @Override
            public int getCount() {
                return mQuestions.size();
            }
        });

        // If there is an App Bar (aka ActionBar),
        // display the question number there.
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }
            @Override
            public void onPageSelected(int i) {
                myToolbar.setTitle("Question #" + (i+1));
                setActionBar(myToolbar);
                getActionBar().setTitle("Question #" + (i+1));
                Log.d(TAG, (String) myToolbar.getTitle());
                Log.d(TAG, Integer.toString(i+1));
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        }); // end of OnPageChangeListener
    }// end of onCreate
}