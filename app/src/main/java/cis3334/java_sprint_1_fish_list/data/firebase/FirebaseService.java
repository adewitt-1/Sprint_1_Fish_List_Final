package cis3334.java_sprint_1_fish_list.data.firebase;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import cis3334.java_sprint_1_fish_list.data.model.Fish;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Firebase Service for the Fish List app. Handles all interactions with the Firebase firestore.
 */
public class FirebaseService {
    // Sets up TAG for the LogCat error checking and handling.
    private static final String TAG = "FirebaseService";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();


    /**
     * Adds a fish to the database.
     * @param fish The fish to add.
     */
    public void addFish(Fish fish) {
        // Adds fish to collections in Firestore
        db.collection("fish").add(fish)

                // Error checking for LogCat.
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "Fish added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error adding fish", e);
                });
    }


    /**
     * Loads fish from the Firebase database.
     *
     * @param callback A function that runs after the data is finished loading,
     *                 which the ViewModel uses to take the list of Fish and update the UI.
     */
    public void loadFish(Consumer<List<Fish>> callback) {
        // Gets fish from collections in the Firestore
        db.collection("fish").get()
                // Once Firebase finishes task:
                .addOnCompleteListener(task -> {
                    // Checks if the task is successful.
                    if (task.isSuccessful()) {

                        // Creates a list of fish.
                        ArrayList<Fish> fishList = new ArrayList<>();

                        // For each document, convert to Fish object, set ID, and add
                        // to the list.
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Fish fish = document.toObject(Fish.class);
                            fish.setId(document.getId());
                            fishList.add(fish);
                        }
                        // Send the list of fish to the callback.
                        // The ViewModel calls loadFish(), Firebase fetches data in the background, and once it’s done,
                        //     Firebase triggers the callback (written in the ViewModel) to update the LiveData
                        //     with the new fish list so the UI refreshes.
                        callback.accept(fishList);
                    } else {
                        // If the task fails, error checking for LogCat.
                        Log.d(TAG, "Error getting documents: ", task.getException());

                        // Sends back an empty array to the callback.
                        callback.accept(new ArrayList<>());
                    }
                });
    }


    /**
     * Clears the fish list from the Firebase database.
     */
    public void clearFishList() {
        // Gets fish from collections in the Firestore
        db.collection("fish").get()
                // Once Firebase finishes task:
                .addOnCompleteListener(task -> {

                    // Checks if the task is successful, checks every document and deletes.
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.collection("fish").document(document.getId()).delete();
                        }
                    } else {
                        // If the task fails, error checking for LogCat.
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }
}
