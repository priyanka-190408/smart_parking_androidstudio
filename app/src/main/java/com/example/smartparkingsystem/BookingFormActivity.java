package com.example.smartparkingsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookingFormActivity extends AppCompatActivity {

    TextView tvSlotNo;
    EditText etName, etStart, etEnd;
    Button btnBook;
    SharedPreferences prefs;
    String rawSlot;   // raw value passed in Intent (e.g. "Slot 1" or "slot1")
    String slotId;    // normalized key used for SharedPreferences (e.g. "slot1")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);

        tvSlotNo = findViewById(R.id.tvSlotNo);
        etName   = findViewById(R.id.etName);
        etStart  = findViewById(R.id.etStart);
        etEnd    = findViewById(R.id.etEnd);
        btnBook  = findViewById(R.id.btnBook);

        prefs = getSharedPreferences("ParkingPrefs", Context.MODE_PRIVATE);

        // Accept either "slotKey" (preferred) or "slotNo" (older code)
        rawSlot = getIntent().getStringExtra("slotKey");
        if (rawSlot == null) rawSlot = getIntent().getStringExtra("slotNo");
        if (rawSlot == null) rawSlot = "slot1";

        // Normalize to a safe key: "Slot 1" -> "slot1", "slot-1" -> "slot1"
        slotId = rawSlot.toLowerCase().replaceAll("\\s+", "").replaceAll("[^a-z0-9]", "");

        tvSlotNo.setText("Booking for " + rawSlot);

        btnBook.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String start = etStart.getText().toString().trim();
            String end = etEnd.getText().toString().trim();

            if (name.isEmpty() || start.isEmpty() || end.isEmpty()) {
                Toast.makeText(BookingFormActivity.this, "Please fill all details!", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isBooked = prefs.getBoolean(slotId + "_status", false);
            String bookedBy = prefs.getString(slotId + "_user", "");

            if (isBooked && bookedBy.equals(name)) {
                Toast.makeText(BookingFormActivity.this, "You already booked this slot!", Toast.LENGTH_SHORT).show();
            } else if (isBooked) {
                Toast.makeText(BookingFormActivity.this, "Slot already occupied!", Toast.LENGTH_SHORT).show();
            } else {
                // Save booking
                prefs.edit()
                        .putBoolean(slotId + "_status", true)
                        .putString(slotId + "_user", name)
                        .putString(slotId + "_start", start)
                        .putString(slotId + "_end", end)
                        // increment bookedCount
                        .putInt("bookedCount", prefs.getInt("bookedCount", 0) + 1)
                        .apply();

                Toast.makeText(BookingFormActivity.this, "Slot Booked Successfully!", Toast.LENGTH_SHORT).show();
                finish(); // go back to slots screen
            }
        });
    }
}
