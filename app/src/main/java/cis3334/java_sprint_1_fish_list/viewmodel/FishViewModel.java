package cis3334.java_sprint_1_fish_list.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.UUID;
import cis3334.java_sprint_1_fish_list.data.firebase.FirebaseService;
import cis3334.java_sprint_1_fish_list.data.model.Fish;


public class FishViewModel extends ViewModel {
    public final String instanceId = UUID.randomUUID().toString();

    private final MutableLiveData<List<Fish>> fishList = new MutableLiveData<>();
    private final FirebaseService firebaseService = new FirebaseService();
}
