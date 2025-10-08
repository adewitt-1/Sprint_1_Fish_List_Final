package cis3334.java_sprint_1_fish_list.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import cis3334.java_sprint_1_fish_list.R;
import cis3334.java_sprint_1_fish_list.ui.FishAdapter;
import cis3334.java_sprint_1_fish_list.viewmodel.FishViewModel;



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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}