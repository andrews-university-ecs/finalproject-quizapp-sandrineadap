package edu.andrews.cptr252.sandrine.quizapp;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.question_list_label);
        mQuestions = QuestionList.getInstance(getActivity()).getQuestions();
        // for now, list questions in log
        for (Question question: mQuestions){
            Log.d(TAG, question.getQuestion());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question_list, container, false);

        return v;
    }


}
