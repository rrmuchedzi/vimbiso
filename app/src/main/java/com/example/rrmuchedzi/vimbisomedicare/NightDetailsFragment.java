package com.example.rrmuchedzi.vimbisomedicare;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

public class NightDetailsFragment extends Fragment {

    private List<DuplicatedScheduled> preSchedulededTest;

    private AdapterDayMedicationDetailsEntities mAdapterDayMedicationDetailsEntities;
    private ListView mListView;
    private static final String TAG = "NightDetailsFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_night_details, container, false);
        mListView = (ListView) view.findViewById(R.id.listview_night_medication);
        preSchedulededTest = HomeActivity.mDatabaseResourcesManager.getNight();
        mAdapterDayMedicationDetailsEntities = new AdapterDayMedicationDetailsEntities(getContext(), R.layout.browse_daymedication_feed, preSchedulededTest );
        mListView.setAdapter(mAdapterDayMedicationDetailsEntities);
        return view;
    }
}
