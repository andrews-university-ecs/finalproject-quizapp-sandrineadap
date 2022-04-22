package edu.andrews.cptr252.sandrine.quizapp;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class QuestionEditorActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        // QuestionListFragment launches QuestionEditorActivity with a specific bug id.
        // Get the Intent sent to this activity from the QuestionListFragment.
        UUID questionId;
        questionId = (UUID)getIntent()
                .getSerializableExtra(QuestionEditorFragment.EXTRA_QUESTION_ID);
        // Create a new instance of the QuestionEditorFragment with the question id as an argument.
        return QuestionEditorFragment.newInstance(questionId);
    }

}
