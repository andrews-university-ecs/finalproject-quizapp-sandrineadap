package edu.andrews.cptr252.sandrine.quizapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment {
    /** key used to pass the id of a question */
    public static final String EXTRA_QUESTION_ID = "edu.andrews.cptr252.sandrine.quizapp.question_id";

    /** Tag for logging fragment messages */
    public static final String TAG = "QuestionEditorFragment";

    /** Question that is being viewed/edited */
    private Question mQuestion;

    /** Reference to question content field for Question*/
    private TextView mContentField;

    /** Reference to radio button group */
    private RadioGroup mRadioGroupChoices;

    /** user's response to question */
    private boolean mChoice;

    public QuizFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new QuizFragment with a given Question id as an argument.
     * @param questionId is the id of the question
     * @return A reference to the new QuizFragment
     */
    public static QuizFragment newInstance(UUID questionId) {
        QuizFragment fragment = new QuizFragment();

        // Create a new argument Bundle object.
        // Add the Question id as an argument.
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_QUESTION_ID, questionId);

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_question_list, menu);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.quiz_mode_label);

        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.quiz_mode_label);

        // Extract question id from Bundle
        UUID questionId = (UUID)getArguments().getSerializable(EXTRA_QUESTION_ID);
        // Get the question with the id from the Bundle.
        // This will be the question that the fragment displays.
        mQuestion = QuestionList.getInstance(getActivity()).getQuestion(questionId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quiz, container, false);

        // get reference to EditText box for question title
        mContentField = v.findViewById(R.id.quizQuestionTextView);
        mContentField.setText(mQuestion.getContent());

        // get references to radio group & buttons
        mRadioGroupChoices = v.findViewById(R.id.quizRadioGroup);

        mRadioGroupChoices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int selectedId) {

                // check which button was clicked
                switch(selectedId) {
                    case R.id.quizRadioTrue:
                        mChoice = true;
                        Log.d(TAG, mChoice + "selected");
                        break;
                    case R.id.quizRadioFalse:
                        mChoice = false;
                        Log.d(TAG, mChoice + "selected");
                        break;
                }// end of switch
            }// end of onChecked Changed
        });// end of onCheckedChangedListener

        return v;
    }
}