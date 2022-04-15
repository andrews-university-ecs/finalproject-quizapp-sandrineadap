package edu.andrews.cptr252.sandrine.quizapp;

import androidx.fragment.app.Fragment;

public class MainScreenActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MainScreenFragment();
    }
}