package com.nikimnafis.bridgeorganizer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private CrewAdapter crewadapter;
    private ArrayList<CrewHelper> crewHelper;

    public CrewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrewFragment newInstance(String param1, String param2) {
        CrewFragment fragment = new CrewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        getData();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crew, container, false);

        recyclerView = view.findViewById(R.id.recViewCrew);
        crewadapter = new CrewAdapter(crewHelper);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(crewadapter);

        return view;
    }

    private void getData() {
        crewHelper = new ArrayList<>();
        crewHelper.add(new CrewHelper("Nabila", "Dokumentasi", R.drawable.crew1));
        crewHelper.add(new CrewHelper("Ahmad", "Dokumentasi", R.drawable.crew2));
        crewHelper.add(new CrewHelper("Andi", "Dokumentasi", R.drawable.crew3));
        crewHelper.add(new CrewHelper("Nafis", "Dokumentasi", R.drawable.crew3));
        crewHelper.add(new CrewHelper("Adi", "Dokumentasi", R.drawable.crew3));
        crewHelper.add(new CrewHelper("Dedi", "Dokumentasi", R.drawable.crew3));
    }
}