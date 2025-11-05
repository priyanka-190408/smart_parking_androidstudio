package com.example.smartparkingsystem;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private boolean[] occupied;
    private Button[] slotButtons;
    private TextView availableCountText;
    private GridLayout gridLayout;
    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 6 slots (change number if you want)
        final int SLOTS = 6;
        occupied = new boolean[SLOTS];
        slotButtons = new Button[SLOTS];

        availableCountText = findViewById(R.id.availableCount);
        gridLayout = findViewById(R.id.slotGrid);
        logoutBtn = findViewById(R.id.logoutBtn);

        // create buttons for slots programmatically (so layout remains clean)
        for (int i = 0; i < SLOTS; i++) {
            final int idx = i;
            Button b = new Button(this);
            b.setText("Slot " + (i + 1) + "\nAvailable");
            b.setAllCaps(false);
            b.setTextSize(14f);
            b.setPadding(8, 8, 8, 8);
            b.setBackgroundResource(R.drawable.slot_available_bg);
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.width = 0;
            lp.height = GridLayout.LayoutParams.WRAP_CONTENT;
            lp.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            lp.setMargins(10, 10, 10, 10);
            b.setLayoutParams(lp);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleSlot(idx);
                }
            });

            slotButtons[i] = b;
            gridLayout.addView(b);
        }

        updateAvailableCount();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void toggleSlot(int index) {
        occupied[index] = !occupied[index];
        Button b = slotButtons[index];
        if (occupied[index]) {
            b.setText("Slot " + (index + 1) + "\nOccupied");
            b.setBackgroundResource(R.drawable.slot_occupied_bg);
        } else {
            b.setText("Slot " + (index + 1) + "\nAvailable");
            b.setBackgroundResource(R.drawable.slot_available_bg);
        }
        updateAvailableCount();
    }

    private void updateAvailableCount() {
        int free = 0;
        for (boolean occ : occupied) if (!occ) free++;
        availableCountText.setText("Available Slots: " + free);
    }
}
