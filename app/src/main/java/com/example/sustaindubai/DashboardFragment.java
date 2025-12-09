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

public class DashboardFragment extends Fragment {

    private EcoPrefs prefs;

    private TextView tvPoints;
    private TextView tvCo2;
    private TextView tvWater;
    private TextView tvWaste;
    private TextView tvLevelLabel;
    private ProgressBar progressLevel;
    private MaterialCardView cardLogActivity;
    private MaterialCardView cardViewRewards;

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
        progressLevel = view.findViewById(R.id.progressLevel);
        cardLogActivity = view.findViewById(R.id.cardLogActivity);
        cardViewRewards = view.findViewById(R.id.cardViewRewards);

        // Update UI with current data
        updateStats();

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

    private void updateStats() {
        int points = prefs.getPoints();

        // --- MODIFIED SECTION START ---
        // We comment these out so the app shows the static numbers from the XML design
        // instead of resetting them to 0.

        // int co2 = prefs.getCo2Saved();
        // int water = prefs.getWaterSaved();
        // int waste = prefs.getWasteDiverted();

        //tvPoints.setText(String.valueOf(points));

        // tvCo2.setText(co2 + " kg");
        // tvWater.setText(water + " L");
        // tvWaste.setText(waste + " kg");

        // --- MODIFIED SECTION END ---

        // Simple gamified level logic: 0–499 => L1, 500–999 => L2, etc.
        int level = (points / 500) + 1;

        // You can also comment this out if you want the static "Level 5 • Ghaf Tree" to show!
        // tvLevelLabel.setText("Level " + level + " • Desert Seedling");

        int progressToNext = points % 500; // 0–499
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