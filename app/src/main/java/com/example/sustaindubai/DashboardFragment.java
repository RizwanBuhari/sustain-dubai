package com.example.sustaindubai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

import java.util.Random;

public class DashboardFragment extends Fragment {

    private EcoPrefs prefs;

    private TextView tvPoints;
    private TextView tvCo2;
    private TextView tvWater;
    private TextView tvWaste;
    private TextView tvLevelLabel;
    private TextView tvDailyTip; // <--- NEW
    private ProgressBar progressLevel;
    private MaterialCardView cardLogActivity;
    private MaterialCardView cardViewRewards;

    // --- NEW: List of Tips ---
    private final String[] ecoTips = {
            "Use the Dubai Metro today to save 3kg of CO2!",
            "Turn off the tap while brushing your teeth to save 10L of water.",
            "Recycle your plastic bottles at a reverse vending machine.",
            "Switch to LED bulbs to reduce energy consumption by 75%.",
            "Carry a reusable water bottle to avoid single-use plastic.",
            "Set your AC to 24°C—it's the optimal eco-friendly temperature.",
            "Unplug electronics when not in use to stop 'vampire power'.",
            "Wash clothes in cold water to save energy on heating.",
            "Use a reusable bag when shopping at the supermarket.",
            "Report water leaks immediately to DEWA to prevent waste."
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        prefs = new EcoPrefs(requireContext());

        tvPoints = view.findViewById(R.id.tvPoints);
        tvCo2 = view.findViewById(R.id.tvCo2);
        tvWater = view.findViewById(R.id.tvWater);
        tvWaste = view.findViewById(R.id.tvWaste);
        tvLevelLabel = view.findViewById(R.id.tvLevelLabel);
        tvDailyTip = view.findViewById(R.id.tvDailyTip); // <--- NEW
        progressLevel = view.findViewById(R.id.progressLevel);
        cardLogActivity = view.findViewById(R.id.cardLogActivity);
        cardViewRewards = view.findViewById(R.id.cardViewRewards);

        // Update UI with current data
        updateStats();

        // Show a random tip
        showRandomTip();

        // When user taps "Log activity", switch to Activities tab
        cardLogActivity.setOnClickListener(v -> switchBottomTab(R.id.nav_activities));

        // When user taps "View rewards", switch to Rewards tab
        cardViewRewards.setOnClickListener(v -> switchBottomTab(R.id.nav_rewards));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateStats();
    }

    private void showRandomTip() {
        // Pick a random number between 0 and the length of the list
        int randomIndex = new Random().nextInt(ecoTips.length);
        tvDailyTip.setText(ecoTips[randomIndex]);
    }

    private void updateStats() {
        int points = prefs.getPoints();

        // IMPORTANT: We kept these un-commented so the "Log Activity" feature works!
        int co2 = prefs.getCo2Saved();
        int water = prefs.getWaterSaved();
        int waste = prefs.getWasteDiverted();

        tvPoints.setText(String.valueOf(points));
        tvCo2.setText(co2 + " kg");
        tvWater.setText(water + " L");
        tvWaste.setText(waste + " kg");

        int level = (points / 500) + 1;
        // You can update this text if you want dynamic levels
        // tvLevelLabel.setText("Level " + level);

        int progressToNext = points % 500;
        progressLevel.setMax(500);
        progressLevel.setProgress(progressToNext);
    }

    private void switchBottomTab(int menuItemId) {
        if (getActivity() == null) return;
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_nav);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(menuItemId);
        }
    }
}