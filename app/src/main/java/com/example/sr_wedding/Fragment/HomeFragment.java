package com.example.sr_wedding.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sr_wedding.InvitationsActivity;
import com.example.sr_wedding.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private static final String PREFS_NAME = "UserPrefs"; // Name of the SharedPreferences file

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Get the username passed from LoginActivity or retrieved from SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, requireContext().MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "User"); // Default to "User" if not found

        // Display the username on the HomeFragment
        TextView welcomeText = view.findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome, " + username + "!");

        // Initialize EditText fields for bride and groom names
        EditText editTextBrideName = view.findViewById(R.id.editTextBrideName);
        EditText editTextGroomName = view.findViewById(R.id.editTextGroomName);

        // Initialize buttons
        Button buttonInvitations = view.findViewById(R.id.buttonInvitations);
        Button buttonExit = view.findViewById(R.id.buttonExit);

        // Set up onClickListener for Plan Wedding button


        // Set up onClickListener for Catering button


        // Set up onClickListener for Wedding Theme button


        // Set up onClickListener for Invitations button
        buttonInvitations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get bride's and groom's names from EditText
                String brideName = editTextBrideName.getText().toString().trim();
                String groomName = editTextGroomName.getText().toString().trim();

                // Check if names are not empty
                if (brideName.isEmpty() || groomName.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter both names", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create intent to start InvitationsActivity
                Intent intent = new Intent(requireActivity(), InvitationsActivity.class);
                intent.putExtra("BRIDE_NAME", brideName);
                intent.putExtra("GROOM_NAME", groomName);
                startActivity(intent);
            }
        });

        // Set up onClickListener for Exit button
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(), "Logging out...", Toast.LENGTH_SHORT).show();
                // Finish the activity (if needed) and return to LoginActivity
                requireActivity().finish();
            }
        });

        return view;
    }
}
