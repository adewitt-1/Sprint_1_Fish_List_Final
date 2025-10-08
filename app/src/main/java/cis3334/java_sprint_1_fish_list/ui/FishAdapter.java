package cis3334.java_sprint_1_fish_list.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;
import java.util.Objects;

import cis3334.java_sprint_1_fish_list.R;
import cis3334.java_sprint_1_fish_list.data.model.Fish;

/**
* SIMILAR TO PARK LIST ADAPTER AND ALTERED TO FIT FISH. STILL NEEDS REVISION AND REVIEW.
 */

public class FishAdapter extends ListAdapter<Fish, FishAdapter.FishViewHolder> {

    private OnFishClickListener listener;

    public interface OnFishClickListener {
        void onFishClick(Fish fish);
    }

    public FishAdapter(OnFishClickListener listener) {
        super(FISH_DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<Fish> FISH_DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Fish>() {
                @Override
                public boolean areItemsTheSame(@NonNull Fish oldItem, @NonNull Fish newItem) {
                    // Use id if available, otherwise fall back to species+date as a best-effort unique key
                    String oldId = oldItem.getId();
                    String newId = newItem.getId();
                    if (oldId != null && newId != null) {
                        return oldId.equals(newId);
                    }
                    return Objects.equals(oldItem.getSpecies(), newItem.getSpecies())
                            && Objects.equals(oldItem.getDate(), newItem.getDate());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Fish oldItem, @NonNull Fish newItem) {
                    // Compare visible fields
                    return Objects.equals(oldItem.getSpecies(), newItem.getSpecies())
                            && Objects.equals(oldItem.getDate(), newItem.getDate())
                            && Objects.equals(oldItem.getLocation(), newItem.getLocation())
                            && Objects.equals(oldItem.getWeather(), newItem.getWeather())
                            && Double.compare(oldItem.getWeight(), newItem.getWeight()) == 0
                            && Double.compare(oldItem.getLength(), newItem.getLength()) == 0
                            && Float.compare(oldItem.getBattleRating(), newItem.getBattleRating()) == 0;
                }
            };

    @NonNull
    @Override
    public FishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_fish, parent, false);
        return new FishViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull FishViewHolder holder, int position) {
        Fish currentFish = getItem(position);
        holder.bind(currentFish);
    }

    static class FishViewHolder extends RecyclerView.ViewHolder {
        private final TextView speciesText;
        private final TextView dateText;
        private final TextView weightText;
        private final TextView lengthText;
        private final TextView locationText;
        private final TextView weatherText;
        private final RatingBar battleRatingBar;
        private final TextView fishEmoji; // optional
        private Fish currentFish;

        public FishViewHolder(@NonNull View itemView, OnFishClickListener listener) {
            super(itemView);
            speciesText = itemView.findViewById(R.id.speciesText);
            dateText = itemView.findViewById(R.id.dateText);
            weightText = itemView.findViewById(R.id.weightText);
            lengthText = itemView.findViewById(R.id.lengthText);
            locationText = itemView.findViewById(R.id.locationText);
            weatherText = itemView.findViewById(R.id.weatherText);
            battleRatingBar = itemView.findViewById(R.id.battleRatingBar);

            TextView tempEmoji = itemView.findViewById(R.id.fishEmoji);
            fishEmoji = tempEmoji; // may be null if not present

            itemView.setOnClickListener(v -> {
                if (listener != null && currentFish != null) {
                    listener.onFishClick(currentFish);
                }
            });
        }

        public void bind(Fish fish) {
            currentFish = fish;

            speciesText.setText(fish.getSpecies() != null ? fish.getSpecies() : "");
            dateText.setText(fish.getDate() != null ? fish.getDate() : "");
            weightText.setText(String.format(Locale.getDefault(), "Weight: %.2f lbs", fish.getWeight()));
            lengthText.setText(String.format(Locale.getDefault(), "Length: %.1f in", fish.getLength()));
            locationText.setText(fish.getLocation() != null ? fish.getLocation() : "");
            weatherText.setText(fish.getWeather() != null ? fish.getWeather() : "");
            battleRatingBar.setRating(fish.getBattleRating());

            if (fishEmoji != null) {
                // simple mapping; change as you like
                String species = fish.getSpecies() == null ? "" : fish.getSpecies().toLowerCase();
                if (species.contains("trout")) fishEmoji.setText("🐟");
                else if (species.contains("bass")) fishEmoji.setText("🎣");
                else if (species.contains("pike")) fishEmoji.setText("🐡");
                else fishEmoji.setText("🐋");
            }
        }
    }
}
