package cis3334.java_sprint_1_fish_list.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import cis3334.java_sprint_1_fish_list.data.firebase.FirebaseService;
import cis3334.java_sprint_1_fish_list.data.model.Fish;

/**
 * THIS IS ALSO TEMPORARY. JUST CAPABLE OF HOLDING THE HARD-CODED FISH IN THE LIST BEFORE
 * FINALIZING, REVIEWING, AND FULLY SETTING UP DATABASE.
 */


public class FishViewModel extends ViewModel {
    public final String instanceId = UUID.randomUUID().toString();

    private final MutableLiveData<List<Fish>> fishList = new MutableLiveData<>();
    private final FirebaseService firebaseService = new FirebaseService();

    public FishViewModel() {
        // TEMPORARY added hard-coded fish
        List<Fish> sampleFish = new ArrayList<>();
        sampleFish.add(new Fish("1", "Salmon", "2025-10-07", 4, 5, "Lake Superior", "Sunny", 4f));
        sampleFish.add(new Fish("2", "Bass", "2025-09-28", 3, 5, "Boundary Waters", "Cloudy", 3.0f));
        fishList.setValue(sampleFish);
    }

    // updates data
    public LiveData<List<Fish>> getFishList() {
        return fishList;
    }

    // adds fish to the list
    public void addFish(Fish fish) {
        List<Fish> currentList = fishList.getValue();
        currentList.add(fish);
        fishList.setValue(currentList);
    }

    // clears the list
    public void clearFishList() {
        fishList.setValue(new ArrayList<>());
    }

}
