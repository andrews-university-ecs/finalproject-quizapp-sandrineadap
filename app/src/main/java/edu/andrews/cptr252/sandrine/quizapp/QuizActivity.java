package edu.andrews.cptr252.sandrine.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class QuizActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new QuizFragment();
    }
}