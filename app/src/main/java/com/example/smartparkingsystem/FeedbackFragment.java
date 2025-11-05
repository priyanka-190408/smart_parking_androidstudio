package com.example.smartparkingsystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FeedbackFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        EditText etFeedback = view.findViewById(R.id.etFeedback);
        Button btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            String feedback = etFeedback.getText().toString();
            if (!feedback.isEmpty()) {
                Toast.makeText(getActivity(), "Feedback Submitted. Thank you!", Toast.LENGTH_SHORT).show();
                etFeedback.setText("");
            } else {
                Toast.makeText(getActivity(), "Please enter feedback.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
