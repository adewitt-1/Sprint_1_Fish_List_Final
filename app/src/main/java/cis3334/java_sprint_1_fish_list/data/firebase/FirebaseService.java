package cis3334.java_sprint_1_fish_list.data.firebase;

import com.google.firebase.firestore.FirebaseFirestore;
import cis3334.java_sprint_1_fish_list.data.model.Fish;
import android.util.Log;


public class FirebaseService {
    private static final String TAG = "FirebaseService";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Add Fish to the Database in the Firestore
    public void addFish(Fish fish) {
        db.collection("fish").add(fish)

                // Error checking for LogCat.
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "Fish added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error adding fish", e);
                });
    }


}
