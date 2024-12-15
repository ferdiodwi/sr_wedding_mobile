package com.example.sr_wedding.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sr_wedding.CateringActivity;
import com.example.sr_wedding.CustomAdapter;
import com.example.sr_wedding.R;
import com.example.sr_wedding.SpinnerItem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VendorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VendorFragment extends Fragment {

    private ListView locationListView, dressListView, menDressListView;
    private EditText guestCountEditText;
    private Button calculateButton, cateringButton;
    private TextView welcomeText, estimatedCostTextView;
    private SpinnerItem selectedLocation, selectedDress, selectedMenDress;
    private static final String PREFS_NAME = "UserPrefs";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VendorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VendorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VendorFragment newInstance(String param1, String param2) {
        VendorFragment fragment = new VendorFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vendor, container, false);

        initializeViews(view);
        setWelcomeMessage();
        setupListViews();
        setupListeners();

        return view;
    }

    private void initializeViews(View view) {
        welcomeText = view.findViewById(R.id.welcomeText);
        locationListView = view.findViewById(R.id.locationListView);
        dressListView = view.findViewById(R.id.dressListView);
        menDressListView = view.findViewById(R.id.menDressListView);
        guestCountEditText = view.findViewById(R.id.editTextGuestCount);
        calculateButton = view.findViewById(R.id.buttonCalculate);
        cateringButton = view.findViewById(R.id.buttonCatering);
        estimatedCostTextView = view.findViewById(R.id.textViewEstimatedCost);
    }

    private void setWelcomeMessage() {
        SharedPreferences preferences = requireActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        String username = preferences.getString("username", "User");
        welcomeText.setText("Welcome, " + username + "!");
    }

    private void setupListViews() {
        List<SpinnerItem> locations = new ArrayList<>();
        locations.add(new SpinnerItem("Beach ", 500, R.drawable.beach));
        locations.add(new SpinnerItem("Garden ", 300, R.drawable.garden));
        locations.add(new SpinnerItem("Hall ", 700, R.drawable.hall));
        locations.add(new SpinnerItem("Home ", 1000, R.drawable.home));

        List<SpinnerItem> dresses = new ArrayList<>();
        dresses.add(new SpinnerItem("Simple Dress ", 200, R.drawable.simpledress));
        dresses.add(new SpinnerItem("Elegant Dress ", 500, R.drawable.elegantdress));
        dresses.add(new SpinnerItem("Designer Dress ", 1000, R.drawable.designerdress));
        dresses.add(new SpinnerItem("Custom Dress ", 800, R.drawable.customdress));

        List<SpinnerItem> menDresses = new ArrayList<>();
        menDresses.add(new SpinnerItem("Classic Suit ", 300, R.drawable.m_simpledress));
        menDresses.add(new SpinnerItem("Elegant Suit ", 600, R.drawable.m_elegantdress));
        menDresses.add(new SpinnerItem("Designer Wear ", 400, R.drawable.m_designerdress));
        menDresses.add(new SpinnerItem("Custom Suit ", 200, R.drawable.m_customdress));

        CustomAdapter locationAdapter = new CustomAdapter(getContext(), locations);
        locationListView.setAdapter(locationAdapter);

        CustomAdapter dressAdapter = new CustomAdapter(getContext(), dresses);
        dressListView.setAdapter(dressAdapter);

        CustomAdapter menDressAdapter = new CustomAdapter(getContext(), menDresses);
        menDressListView.setAdapter(menDressAdapter);
    }

    private void setupListeners() {
        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedLocation = (SpinnerItem) parent.getItemAtPosition(position);
            }
        });

        dressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedDress = (SpinnerItem) parent.getItemAtPosition(position);
            }
        });

        menDressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedMenDress = (SpinnerItem) parent.getItemAtPosition(position);
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCost();
            }
        });

        cateringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CateringActivity.class));
            }
        });
    }

    private void calculateCost() {
        String guestCountString = guestCountEditText.getText().toString();

        if (guestCountString.isEmpty() || selectedLocation == null || selectedDress == null || selectedMenDress == null) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int guestCount = Integer.parseInt(guestCountString);
            if (guestCount <= 0) {
                Toast.makeText(getContext(), "Guest count must be greater than zero", Toast.LENGTH_SHORT).show();
                return;
            }

            double estimatedCost = calculateWeddingCost(selectedLocation, selectedDress, selectedMenDress, guestCount);
            String costDetails = "Selected Location: " + selectedLocation.getName() + "\n" +
                    "Selected Dress: " + selectedDress.getName() + "\n" +
                    "Selected Men's Dress: " + selectedMenDress.getName() + "\n" +
                    "Base Cost: $1000\n" +
                    "Location Cost: $" + selectedLocation.getCost() + "\n" +
                    "Dress Cost: $" + selectedDress.getCost() + "\n" +
                    "Men's Dress Cost: $" + selectedMenDress.getCost() + "\n" +
                    "Accommodation Cost per person: $50\n" +
                    "Total Guest Count: " + guestCount + "\n" +
                    "Estimated Cost: $" + estimatedCost;

            estimatedCostTextView.setText(costDetails);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid guest count", Toast.LENGTH_SHORT).show();
        }
    }

    private double calculateWeddingCost(SpinnerItem location, SpinnerItem dress, SpinnerItem menDress, int guestCount) {
        double baseCost = 1000;
        double locationCost = location.getCost();
        double dressCost = dress.getCost();
        double menDressCost = menDress.getCost();
        return baseCost + locationCost + dressCost + menDressCost + (guestCount * 50);
    }
}