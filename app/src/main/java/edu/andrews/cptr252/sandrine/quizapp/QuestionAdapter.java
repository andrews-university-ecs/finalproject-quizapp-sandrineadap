package edu.andrews.cptr252.sandrine.quizapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    private static final String TAG="QuestionAdapter";

    /** Used to store reference to list of bugs*/
    private ArrayList<Question> mQuestions;

    /**
     * Constructor for QuestionAdapter. Initialize adapter with given list of questions.
     * @param questions list of bugs to display.
     */
    public QuestionAdapter(ArrayList<Question> questions) {
        mQuestions = questions;
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

        /** Create a new view holder for a given view item in the bug list */
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
         * @param v is view for bug that was clicked
         */
        @Override
        public void onClick(View v) {
            // Get index of bug that was clicked.
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
     * Create a new view to display a bug.
     * Return the ViewHolder that stores references to the widgets on the new view.
     */
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the layout inflater from parent that contains the bug view item
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the layout to display a bug in the list
        View bugView = inflater.inflate(R.layout.list_item_question, parent, false);
        // Create a view holder to store references to the widgets on the new view.
        ViewHolder viewHolder = new ViewHolder(bugView);
        return viewHolder;
    }
    /**
     * Display given bug in the view referenced by the given ViewHolder.
     * @param viewHolder Contains references to widgets used to display bug.
     * @param position Index of the bug in the list
     */
    @Override
    public void onBindViewHolder(QuestionAdapter.ViewHolder viewHolder, int position) {
        // Get bug for given index in bug list
        Question question = mQuestions.get(position);
        // Get references to widgets stored in the ViewHolder
        TextView questionTextView = viewHolder.questionTextView;
        RadioButton trueChoiceButton = viewHolder.trueChoiceButton;
        RadioButton falseChoiceButton = viewHolder.falseChoiceButton;
        // Update widgets on view with bug details
        questionTextView.setText(question.getContent());

        //TODO: Figure out how to check the correct answer
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


}
