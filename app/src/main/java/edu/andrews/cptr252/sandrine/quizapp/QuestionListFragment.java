package edu.andrews.cptr252.sandrine.quizapp;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Fragment to display list of questions
 */
public class QuestionListFragment extends Fragment {
    /** Tag for message log */
    private static final String TAG = "QuestionListFragment";

    /** Reference to list of questions in display */
    private ArrayList<Question> mQuestions;

    public QuestionListFragment() {
        // required empty public constructor
    }

    /** RecyclerView that displays list of questions */
    private RecyclerView mRecyclerView;

    /** Adapter that generates/reuses views to display questions */
    private QuestionAdapter mQuestionAdapter;


    /** Create a new question, add it to the list and launch question editor. */
    private void addQuestion() {
        // create new question
        Question question = new Question();
        // add question to the list
        QuestionList.getInstance(getActivity()).addQuestion(question);
        // create an intent to send to QuestionEditorActivity with question Id as extra
        Intent intent = new Intent(getActivity(), QuestionEditorActivity.class);
        intent.putExtra(QuestionEditorFragment.EXTRA_QUESTION_ID, question.getId());
        // launch QuestionEditorActivity
        startActivityForResult(intent, 0);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_question_list, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_add_question:
                // new question icon clicked
                addQuestion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.question_list_label);

        mQuestions = QuestionList.getInstance(getActivity()).getQuestions();

        // use custom question adapter for generating views for each question
        mQuestionAdapter = new QuestionAdapter(mQuestions, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question_list, container, false);

        mRecyclerView = v.findViewById(R.id.question_list_recyclerView);
        // RecyclerView uses QuestionAdapter to create views for questions
        mRecyclerView.setAdapter(mQuestionAdapter);
        // Use a linear layout when displaying questions
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Create and attach our new touch helper for question swipes
        QuestionSwiper questionSwiper = new QuestionSwiper(mQuestionAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(questionSwiper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        return v;
    }

    /**
     * Question list fragment was paused (user was most likely editing a question).
     * Notify the adapter that the data set (question list) may have changed.
     * The adapter should update the views.
     */
    @Override
    public void onResume() {
        super.onResume(); // first execute parent's onResume method
        mQuestionAdapter.refreshQuestionListDisplay();
    }
}
