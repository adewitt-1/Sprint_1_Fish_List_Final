package cis3334.java_sprint_1_fish_list.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import cis3334.java_sprint_1_fish_list.data.firebase.FirebaseService;
import cis3334.java_sprint_1_fish_list.data.model.Fish;

/**
 * Fish ViewModel for the Fish List app. Serves as a bridge between the firebase database
 * and the UI. Provides LiveData to be observed.
 */
public class FishViewModel extends ViewModel {
    // Instance ID for the ViewModel (Universally unique identifier)
    public final String instanceId = UUID.randomUUID().toString();

    // LiveData fish list
    private final MutableLiveData<List<Fish>> _fishList = new MutableLiveData<>();

    // Firebase Service connection
    private final FirebaseService firebaseService = new FirebaseService();

    /**
     * Constructor for the Fish ViewModel.
     */
    public FishViewModel() {
        Log.d("FishViewModel", "Created new FishViewModel instance: " + instanceId);
        loadFish();
    }

    /**
     * Gets the fish list.
     *
     * @return The fish list.
     */
    public LiveData<List<Fish>> getFishList() {
        return _fishList;
    }

    /**
     * Adds a fish to the list. Also adds to the firebase database.
     *
     * @param fish The fish to add.
     */
    public void addFish(Fish fish) {
        // LogCat notes
        Log.d("FishViewModel", "Adding fish: " + fish.getSpecies());

        // Add to Firebase:
        firebaseService.addFish(fish);

        // Update LiveData for UI refresh!
        List<Fish> currentList = _fishList.getValue();
        currentList.add(fish);
        _fishList.setValue(currentList);
    }

    /**
     * Loads fish from the Firebase database. Adds a default fish if the list is empty.
     */
    public void loadFish() {
        // Tells Firebase to load fish from the database, and then runs the callback function
        // when the data is finished loading by updating the LiveData variable with the new list.
        firebaseService.loadFish(fishList -> {
            _fishList.setValue(fishList);
            // If list is empty, ensure there's one fish.
            if (fishList.isEmpty()) {
                addFish(new Fish("1", "First Trout!", "Sept 18, 2025", 4.2, 22, "Lake Superior", "Sunny, 72°F", 4));
            }
            // LogCat notes
            Log.d("FishViewModel", "Loaded fish list: " + fishList);
        });
    }

    /**
     * Clears the fish list from the Firebase database and LiveData.
     */
    public void clearFishList() {
        // Clears the list in Firebase
        firebaseService.clearFishList();
        // Clears the list in LiveData
        _fishList.setValue(new ArrayList<>());
        // LogCat notes
        Log.d("FishViewModel", "Cleared fish list" + instanceId);
    }

}
