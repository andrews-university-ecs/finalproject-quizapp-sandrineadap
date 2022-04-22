package edu.andrews.cptr252.sandrine.quizapp;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
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

    /** Adapter that generates/reuses views to display bugs */
    private QuestionAdapter mQuestionAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.question_list_label);

        mQuestions = QuestionList.getInstance(getActivity()).getQuestions();

        // use custom question adapter for generating views for each question
        mQuestionAdapter = new QuestionAdapter(mQuestions);

        // for now, list questions in log
        for (Question question: mQuestions){
            Log.d(TAG, question.getContent());
        }
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
        mQuestionAdapter.notifyDataSetChanged();
    }
}
