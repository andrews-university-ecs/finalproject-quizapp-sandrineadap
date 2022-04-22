package edu.andrews.cptr252.sandrine.quizapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionEditorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionEditorFragment extends Fragment {

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
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionEditorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionEditorFragment newInstance(String param1, String param2) {
        QuestionEditorFragment fragment = new QuestionEditorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            mQuestion = new Question();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question_editor, container, false);

        // get reference to EditText box for question title
        mContentField = v.findViewById(R.id.question_editor_TextMultiLine);
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

        mRadioGroupChoices = v.findViewById(R.id.radioButtonGroup_choices);
        //mRadioGroupChoices.setOnCheckedChangeListener();

        return v;
    }
}