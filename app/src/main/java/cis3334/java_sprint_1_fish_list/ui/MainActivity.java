package cis3334.java_sprint_1_fish_list.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.UUID;

import cis3334.java_sprint_1_fish_list.R;
import cis3334.java_sprint_1_fish_list.ui.FishAdapter;
import cis3334.java_sprint_1_fish_list.viewmodel.FishViewModel;
import cis3334.java_sprint_1_fish_list.data.model.Fish;
import cis3334.java_sprint_1_fish_list.data.firebase.FirebaseService;

public class MainActivity extends AppCompatActivity {

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
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // ViewModel
        fishViewModel = new ViewModelProvider(this).get(FishViewModel.class);

        /**
         * TEMP HARD CODED
         */

        // --- Add these lines below ---
        Fish fish1 = new Fish(
                UUID.randomUUID().toString(),
                "Rainbow Trout",
                "2025-05-12",
                2.5,
                18.0,
                "Lake Superior",
                "Sunny",
                4.5f
        );

        Fish fish2 = new Fish(
                UUID.randomUUID().toString(),
                "Largemouth Bass",
                "2025-06-01",
                3.2,
                20.0,
                "Mississippi River",
                "Cloudy",
                3.5f
        );

        // Add to ViewModel list
        fishViewModel.addFish(fish1);
        fishViewModel.addFish(fish2);

        // Initializing inputs
        speciesInput = findViewById(R.id.speciesInput);
        dateInput = findViewById(R.id.dateInput);
        weightInput = findViewById(R.id.weightInput);
        lengthInput = findViewById(R.id.lengthInput);
        locationInput = findViewById(R.id.locationInput);
        weatherInput = findViewById(R.id.weatherInput);
        battleRatingInput = findViewById(R.id.battleRatingInput);
        insertFishButton = findViewById(R.id.insertFishButton);
        clearFishButton = findViewById(R.id.clearFishButton);

        // Insert Fish Button
        insertFishButton.setOnClickListener(v -> addNewFish());

        // Clear Fish Button
        clearFishButton.setOnClickListener(v -> {
            fishViewModel.clearFishList();

            // Toast (Pop-Up Message!) For Clearing
            CharSequence text1 = "Cleared Fish!";
            int duration1 = Toast.LENGTH_SHORT;
            Toast toast1 = Toast.makeText(this, text1, duration1);
            toast1.show();
        });

        // Run setup methods
        setupRecyclerView();
        setupLiveDataObserver();
    }


    /**
     * Adds a new fish to the list.
     */
    private void addNewFish() {
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

        // Convert weight and length to doubles
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

        // Add new fish to the list
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
     * Sets up the RecyclerView.
     */
    private void setupRecyclerView() {
        fishRecyclerView = findViewById(R.id.fishRecyclerView);
        fishRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Provide a simple click listener to avoid null
        adapter = new FishAdapter(fish -> {
            Toast.makeText(this, "Clicked: " + fish.getSpecies(), Toast.LENGTH_SHORT).show();
        });

        fishRecyclerView.setAdapter(adapter);
    }

    /**
     * Sets up the LiveData observer.
     */
    private void setupLiveDataObserver() {
        fishViewModel.getFishList().observe(this, fishList -> {
            adapter.submitList(new ArrayList<>(fishList));
        });
    }




}