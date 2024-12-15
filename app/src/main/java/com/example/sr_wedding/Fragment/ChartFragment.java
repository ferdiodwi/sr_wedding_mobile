package com.example.sr_wedding.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sr_wedding.GeminiPro;
import com.example.sr_wedding.R;
import com.example.sr_wedding.ResponseCallback;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragment extends Fragment {

    private EditText inputColors;
    private Spinner inputVenue, inputSeason;
    private TextView themeSuggestionText;
    private Button btnGetTheme;
    private ProgressBar progressBar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChartFragment newInstance(String param1, String param2) {
        ChartFragment fragment = new ChartFragment();
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
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        // Initialize UI components
        inputColors = view.findViewById(R.id.inputColors);
        inputVenue = view.findViewById(R.id.inputVenue);
        inputSeason = view.findViewById(R.id.inputSeason);
        themeSuggestionText = view.findViewById(R.id.themeSuggestionText);
        btnGetTheme = view.findViewById(R.id.btnGetTheme);
        progressBar = view.findViewById(R.id.progressBar);

        // Set button click listener
        btnGetTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user inputs
                String colors = inputColors.getText().toString();
                String venue = inputVenue.getSelectedItem().toString();
                String season = inputSeason.getSelectedItem().toString();

                // Validate inputs
                if (colors.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter your color preferences", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Call method to get AI theme suggestions
                getAIThemeSuggestions(colors, venue, season);
            }
        });

        return view;
    }


    private void getAIThemeSuggestions(String warna, String tempat, String musim) {
        // Create query string
        String query = String.format("Sarankan tema pernikahan menggunakan warna: %s, venue: %s, season: %s.", warna, tempat, musim);

        GeminiPro geminiPro = new GeminiPro();
        progressBar.setVisibility(View.VISIBLE); // Show the progress bar

        // Clear previous suggestion text
        themeSuggestionText.setText("");

        // Call the GeminiPro method to get the response
        geminiPro.getResponse(query, new ResponseCallback() {
            @Override
            public void onResponse(String response) {
                // Display the AI-generated theme suggestion
                themeSuggestionText.setText(response);
                themeSuggestionText.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE); // Hide the progress bar
            }

            @Override
            public void onError(Throwable throwable) {
                // Handle API failure
                Toast.makeText(getContext(), "API call failed: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE); // Hide the progress bar
            }
        });
    }

}