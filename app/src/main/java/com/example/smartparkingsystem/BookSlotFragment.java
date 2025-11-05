package com.example.smartparkingsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BookSlotFragment extends Fragment {

    Button slot1, slot2, slot3, slot4;
    SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookslot, container, false);

        slot1 = view.findViewById(R.id.slot1);
        slot2 = view.findViewById(R.id.slot2);
        slot3 = view.findViewById(R.id.slot3);
        slot4 = view.findViewById(R.id.slot4);

        prefs = requireActivity().getSharedPreferences("ParkingPrefs", Context.MODE_PRIVATE);

        updateSlotUI(slot1, "slot1");
        updateSlotUI(slot2, "slot2");
        updateSlotUI(slot3, "slot3");
        updateSlotUI(slot4, "slot4");

        View.OnClickListener listener = v -> {
            String slotKey = getSlotKey(v.getId());
            Intent intent = new Intent(getActivity(), BookingFormActivity.class);
            intent.putExtra("slotKey", slotKey);
            startActivity(intent);
        };

        slot1.setOnClickListener(listener);
        slot2.setOnClickListener(listener);
        slot3.setOnClickListener(listener);
        slot4.setOnClickListener(listener);

        return view;
    }

    private String getSlotKey(int id) {
        if (id == R.id.slot1) return "slot1";
        if (id == R.id.slot2) return "slot2";
        if (id == R.id.slot3) return "slot3";
        return "slot4";
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh slot colors when coming back from BookingFormActivity
        updateSlotUI(slot1, "slot1");
        updateSlotUI(slot2, "slot2");
        updateSlotUI(slot3, "slot3");
        updateSlotUI(slot4, "slot4");
    }

    private void updateSlotUI(Button button, String key) {
        boolean isBooked = prefs.getBoolean(key + "_status", false);
        if (isBooked) {
            button.setBackgroundColor(Color.RED);
        } else {
            button.setBackgroundColor(Color.GREEN);
        }
    }
}
