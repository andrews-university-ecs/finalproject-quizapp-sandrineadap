package edu.andrews.cptr252.sandrine.quizapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    private static final String TAG="QuestionAdapter";

    /** Used to store reference to list of questions */
    private ArrayList<Question> mQuestions;

    /** Activity hosting the list fragment */
    private Activity mActivity;


    /**
     * Constructor for QuestionAdapter. Initialize adapter with given list of questions.
     * @param questions list of questions to display.
     */
    public QuestionAdapter(ArrayList<Question> questions, Activity activity) {
        mQuestions = questions;
        mActivity = activity;
    }

    /** Return reference to activity hosting the question list fragment */
    public Context getActivity() {
        return mActivity;
    }


    /**
     * Create snackbar with ability to undo question deletion.
     */
    private void showUndoSnackbar(final Question question, final int position) {
        // get root view for activity hosting question list fragment
        View view = mActivity.findViewById(android.R.id.content);
        // build message stating which question was deleted
        String questionDeletedText = mActivity.getString(R.string.question_deleted_msg,
                "Question");
        // create the snackbar
        Snackbar snackbar = Snackbar.make(view, questionDeletedText, Snackbar.LENGTH_LONG);
        // add the Undo option to the snackbar
        snackbar.setAction(R.string.undo_option, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // undo is selected, restore the deleted item
                restoreQuestion(question, position);
            }
        });

        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                    // Officially delete question from list
                    QuestionList.getInstance(mActivity).deleteQuestion(question);
                }
            }
        });

        // Text for UNDO will be yellow
        snackbar.setActionTextColor(Color.YELLOW);
        // display snackbar
        snackbar.show();
    }
    /**
     * Remove question from list
     * @param position index of question to remove
     */
    public void deleteQuestion(int position) {
        // Save deleted question so we can undo delete if needed.
        final Question question = mQuestions.get(position);

        // delete question from question array used by adapter (not official list)
        mQuestions.remove(position);

        // update list of questions in recyclerview
        notifyItemRemoved(position);

        // display snackbar so user may undo delete
        showUndoSnackbar(question, position);
    }

    /** Force adapter to load new question list and regenerate views. */
    public void refreshQuestionListDisplay() {
        mQuestions = QuestionList.getInstance(mActivity).getQuestions();
        notifyDataSetChanged();
    }

    /**
     * Put deleted question back into list
     * @param question to restore
     * @param position in list where question will go
     */
    public void restoreQuestion(Question question, int position) {
        refreshQuestionListDisplay();
    }


    /**
     * Class to hold references to widgets on a given view.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /** TextView that displays question */
        public TextView questionTextView;
        /** Radio Buttons to display answer */
        public RadioButton trueChoiceButton;
        public RadioButton falseChoiceButton;

        /** Context hosting the view */
        public Context mContext;

        /** Create a new view holder for a given view item in the question list */
        public ViewHolder(View itemView) {
            super(itemView);
            // Store references to the widgets on the view item
            questionTextView = itemView.findViewById(R.id.question_list_item_titleTextView);
            trueChoiceButton = itemView.findViewById(R.id.question_list_item_radioButton_true);
            falseChoiceButton = itemView.findViewById(R.id.question_list_item_radioButton_false);

            // Get the context of the view. This will be the activity hosting the view.
            mContext = itemView.getContext();

            itemView.setOnClickListener(this);
        }

        /**
         * OnClick listener for question in the question list.
         * Triggered when user clicks on a question in the list
         * @param v is view for question that was clicked
         */
        @Override
        public void onClick(View v) {
            // Get index of question that was clicked.
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Question question = mQuestions.get(position);
                Log.d(TAG, question.getContent() + " was clicked");

                // start an instance of QuestionEditorFragment
                Intent i = new Intent(mContext, QuestionEditorActivity.class);
                // pass the id of the question as an intent
                i.putExtra(QuestionEditorFragment.EXTRA_QUESTION_ID, question.getId());
                mContext.startActivity(i);
            }
        }
    } // end ViewHolder

    /**
     * Create a new view to display a question.
     * Return the ViewHolder that stores references to the widgets on the new view.
     */
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the layout inflater from parent that contains the question view item
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the layout to display a question in the list
        View questionView = inflater.inflate(R.layout.list_item_question, parent, false);
        // Create a view holder to store references to the widgets on the new view.
        ViewHolder viewHolder = new ViewHolder(questionView);
        return viewHolder;
    }
    /**
     * Display given question in the view referenced by the given ViewHolder.
     * @param viewHolder Contains references to widgets used to display question.
     * @param position Index of the question in the list
     */
    @Override
    public void onBindViewHolder(QuestionAdapter.ViewHolder viewHolder, int position) {
        // Get question for given index in question list
        Question question = mQuestions.get(position);

        // Get references to widgets stored in the ViewHolder
        TextView questionTextView = viewHolder.questionTextView;
        RadioButton trueChoiceButton = viewHolder.trueChoiceButton;
        RadioButton falseChoiceButton = viewHolder.falseChoiceButton;

        // Update widgets on view with question details
        questionTextView.setText(question.getContent());

        trueChoiceButton.setChecked(question.getAnswer());
        falseChoiceButton.setChecked(!question.getAnswer());
    }

    /**
     * Get number of questions in question list.
     */
    @Override
    public int getItemCount() {
        return mQuestions.size();
    }
}// end of QuestionAdapter
