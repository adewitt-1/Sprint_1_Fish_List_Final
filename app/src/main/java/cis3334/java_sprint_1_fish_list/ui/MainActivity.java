package cis3334.java_sprint_1_fish_list.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.UUID;

import cis3334.java_sprint_1_fish_list.R;
import cis3334.java_sprint_1_fish_list.viewmodel.FishViewModel;
import cis3334.java_sprint_1_fish_list.data.model.Fish;


/**
 * Main Activity for the Fish List app. Sets up the UI environment, initializes variables, creates
 * the RecyclerView, and the LiveData observer. Updates recyclerview through the FishViewModel.
 */
public class MainActivity extends AppCompatActivity {

    // Ids for the UI elements
    private FishViewModel fishViewModel;
    private FishAdapter adapter;
    private EditText speciesInput;
    private EditText dateInput;
    private EditText weightInput;
    private EditText lengthInput;
    private EditText locationInput;
    private EditText weatherInput;
    private RatingBar battleRatingInput;
    private Button insertFishButton;
    private Button clearFishButton;
    private RecyclerView fishRecyclerView;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState Contains data if shut down and re-created.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // ViewModel (Connection between UI and Firebase)
        fishViewModel = new ViewModelProvider(this).get(FishViewModel.class);

        // Initializing user input fields and buttons
        speciesInput = findViewById(R.id.speciesInput);
        dateInput = findViewById(R.id.dateInput);
        weightInput = findViewById(R.id.weightInput);
        lengthInput = findViewById(R.id.lengthInput);
        locationInput = findViewById(R.id.locationInput);
        weatherInput = findViewById(R.id.weatherInput);
        battleRatingInput = findViewById(R.id.battleRatingInput);
        insertFishButton = findViewById(R.id.insertFishButton);
        clearFishButton = findViewById(R.id.clearFishButton);

        // Makes the insertFishButton clickable
        // Calls the addNewFish() method when the button is clicked
        insertFishButton.setOnClickListener(v -> addFish());

        // Makes the clearFishButton clickable
        // Calls the clearFish() method when the button is clicked
        clearFishButton.setOnClickListener(v -> clearFish());

        // Run setup methods
        setupRecyclerView();
        setupLiveDataObserver();
    }


    /**
     * Adds a new fish to the list.
     */
    private void addFish() {
        // Get input values
        String species = speciesInput.getText().toString().trim();
        String date = dateInput.getText().toString().trim();
        String location = locationInput.getText().toString().trim();
        String weather = weatherInput.getText().toString().trim();
        float battleRating = battleRatingInput.getRating();

        // Convert weight and length to strings due to app crash prevention if user did not enter a numerical value.
        String weightStr = weightInput.getText().toString().trim();
        String lengthStr = lengthInput.getText().toString().trim();

        // Check for empty fields
        if (species.isEmpty() || date.isEmpty() || weightStr.isEmpty() || lengthStr.isEmpty() || location.isEmpty() || weather.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert weight and length back to doubles
        double weight;
        double length;
        try {
            weight = Double.parseDouble(weightStr);
            length = Double.parseDouble(lengthStr);

            // Error checking for if the user incorrectly put in the length and weight values.
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter numerical values for weight and length.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create new fish object
        Fish newFish = new Fish(
                UUID.randomUUID().toString(),
                species,
                date,
                weight,
                length,
                location,
                weather,
                battleRating
        );

        // Add new fish to the list for fishViewModel to handle
        fishViewModel.addFish(newFish);

        // Clear inputs after addition of new fish
        speciesInput.setText("");
        dateInput.setText("");
        weightInput.setText("");
        lengthInput.setText("");
        locationInput.setText("");
        weatherInput.setText("");
        battleRatingInput.setRating(0);

        // Toast (Pop-Up Message!) For Insertion
        CharSequence text2 = "Added Fish!";
        int duration2 = Toast.LENGTH_SHORT;
        Toast toast2 = Toast.makeText(this, text2, duration2);
        toast2.show();

    }

    /**
     * Clears the fish list.
     */
    private void clearFish() {
        fishViewModel.clearFishList();

        // Toast (Pop-Up Message!) For Clearing
        CharSequence text1 = "Cleared Fish!";
        int duration1 = Toast.LENGTH_SHORT;
        Toast toast1 = Toast.makeText(this, text1, duration1);
        toast1.show();
    }


    /**
     * Sets up the RecyclerView. Think of it as a dining area with limited number
     * of tables (cards). There many be a lot of people, but only a limited number
     * of tables can be used at once. Once people leave, new people can sit.
     */
    private void setupRecyclerView() {
        // Find the RecyclerView in the layout
        fishRecyclerView = findViewById(R.id.fishRecyclerView);
        fishRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Provide a simple click listener to avoid null
        // Otherwise, app crashes when clicking on a fish.
        adapter = new FishAdapter(fish -> {
            Toast.makeText(this, "Great Catch! This is an awesome " + fish.getSpecies() + "!", Toast.LENGTH_SHORT).show();
        });

        // Set the adapter for the RecyclerView
        fishRecyclerView.setAdapter(adapter);
    }

    /**
     * Sets up the LiveData observer. It watches the LiveData variable in the FishViewModel
     * and updates the RecyclerView when the data changes.
     */
    private void setupLiveDataObserver() {
        fishViewModel.getFishList().observe(this, fishList -> {
            adapter.submitList(new ArrayList<>(fishList));
        });
    }




}