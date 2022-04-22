package edu.andrews.cptr252.sandrine.quizapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionEditorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionEditorFragment extends Fragment {
    /** key used to pass the id of a bug */
    public static final String EXTRA_QUESTION_ID = "edu.andrews.cptr252.sandrine.quizapp.question_id";

    /** Tag for logging fragment messages */
    public static final String TAG = "QuestionEditorFragment";
    /** Question that is being viewed/edited */
    private Question mQuestion;
    /** Reference to question content field for Question*/
    private EditText mContentField;
    /** Reference to radio button group */
    // private RadioButton mRadioButtonTrue;
    // private RadioButton mRadioButtonFalse;
    private RadioGroup mRadioGroupChoices;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuestionEditorFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new BugDetailsFragment with a given Bug id as an argument.
     * @param questionId
     * @return A reference to the new BugDetailsFragment
     */
    public static QuestionEditorFragment newInstance(UUID questionId) {
        QuestionEditorFragment fragment = new QuestionEditorFragment();

        // Create a new argument Bundle object.
        // Add the Question id as an argument.
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_QUESTION_ID, questionId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.question_editor_label);

        // Extract question id from Bundle
        UUID questionId = (UUID)getArguments().getSerializable(EXTRA_QUESTION_ID);
        // Get the question with the id from the Bundle.
        // This will be the question that the fragment displays.
        mQuestion = QuestionList.getInstance(getActivity()).getQuestion(questionId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question_editor, container, false);

        // get reference to EditText box for question title
        mContentField = v.findViewById(R.id.question_editor_TextMultiLine);
        mContentField.setText(mQuestion.getContent());
        mContentField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // left intentionally blank
            }

            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count) {
                // user typed text, update the question content
                mQuestion.setContent(s.toString());
                // write the new question content to the message log for debugging
                Log.d(TAG, "Content changed to " + mQuestion.getContent());
            }
            @Override
            public void afterTextChanged(Editable s) {
                // left intentionally blank
            }
        });

        mRadioGroupChoices = (RadioGroup) v.findViewById(R.id.radioButtonGroup_choices);
        mRadioGroupChoices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int selectedId) {

                // check which button was clicked
                switch(selectedId) {
                    case R.id.radioButton_true:
                        mQuestion.setAnswer(true);
                        break;
                    case R.id.radioButton_false:
                        mQuestion.setAnswer(false);
                        break;
                }// end of switch
            }// end of onChecked Changed
        });// end of onCheckedChangedListener

        return v;
    }// end of OnCreateView
}// end of QuestionEditor Fragment