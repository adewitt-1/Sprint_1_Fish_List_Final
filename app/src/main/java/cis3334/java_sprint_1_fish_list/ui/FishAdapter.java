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
 * Fish Adapter for the Fish List app.
 * Think of it as a waiter (The adapter, in this case) who connects the kitchen
 * (data from viewmodel) to the tables (recyclerview). Basically serves as a bridge between
 * the viewmodel and the recyclerview (UI).
 */
public class FishAdapter extends ListAdapter<Fish, FishAdapter.FishViewHolder> {

    /**
     * Listener for when a fish is clicked.
     */
    private OnFishClickListener listener;

    /**
     * Interface for the Fish Adapter.
     */
    public interface OnFishClickListener {
        void onFishClick(Fish fish);
    }

    /**
     * Constructor for the Fish Adapter. Runs when the adapter is created.
     * @param listener Listener for when a fish is clicked.
     */
    public FishAdapter(OnFishClickListener listener) {
        // Tells the adapter how to compare items in the list
        super(FISH_DIFF_CALLBACK);
        // Listens for when a fish is clicked
        this.listener = listener;
    }

    /**
     * DiffUtil.ItemCallback for the Fish Adapter.
     * Compares items in the list to determine if the Fish items are the same or not.
     * Used by the List Adapter to update efficiently only the changed items
     * in the RecyclerView.
     */
    private static final DiffUtil.ItemCallback<Fish> FISH_DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Fish>() {
                /**
                 * Called to check whether two objects represent the same item.
                 *
                 * @param oldItem The item in the old list.
                 * @param newItem The item in the new list.
                 * @return True if the two items represent the same object or false if they are different.
                 */
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

                /**
                 * Called to check whether two items have the same data or content.
                 *
                 * @param oldItem The item in the old list.
                 * @param newItem The item in the new list.
                 * @return True (same) or False (different).
                 */
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

    /**
     * Called when RecyclerView needs a new viewHolder to display an item.
     * This happens initially and if the user scrolls too fast and the viewHolder can't
     * keep up and creates an entirely new viewHolder.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder.
     */
    @NonNull
    @Override
    public FishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate means to turn XML layout file (list_item_fish.xml) into view object so that
        // the app can interact and display.
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_fish, parent, false);
        return new FishViewHolder(itemView, listener);
    }

    /**
     * Calls the bind() method in the ViewHolder to bind the data to the view.
     *
     * @param holder   The ViewHolder which should be updated to represent the information of the item.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull FishViewHolder holder, int position) {
        // Get the fish at the current position
        Fish currentFish = getItem(position);
        // Bind the fish to the view holder
        holder.bind(currentFish);
    }

    /**
     * ViewHolder for the Fish Adapter. Think of it as a table setup that holds
     * all the utensils, ready for the waiter (adapter) to fill with food/drink (data).
     *
     */
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

        /**
         * Constructor for the Fish ViewHolder. Sets up references to the views in the layout
         * and sets up a click listener for the view.
         *
         * @param itemView The view of the item.
         * @param listener The listener for when a fish is clicked.
         */
        public FishViewHolder(@NonNull View itemView, OnFishClickListener listener) {
            super(itemView);
            // Find the views in the layout
            speciesText = itemView.findViewById(R.id.speciesText);
            dateText = itemView.findViewById(R.id.dateText);
            weightText = itemView.findViewById(R.id.weightText);
            lengthText = itemView.findViewById(R.id.lengthText);
            locationText = itemView.findViewById(R.id.locationText);
            weatherText = itemView.findViewById(R.id.weatherText);
            battleRatingBar = itemView.findViewById(R.id.battleRatingBar);

            // Optional fish emoji
            TextView tempEmoji = itemView.findViewById(R.id.fishEmoji);
            fishEmoji = tempEmoji; // may be null if not present

            // Set up a click listener for the view
            itemView.setOnClickListener(v -> {
                if (listener != null && currentFish != null) {
                    listener.onFishClick(currentFish);
                }
            });
        }

        /**
         * Binds a fish to the view holder. The adapter assigns a fish to this viewholder,
         * and this method updates the views with the fish's data.
         *
         * @param fish
         */
        public void bind(Fish fish) {
            currentFish = fish;

            // Set the text of the views to the fish's data if it's not null
            // ? serves as a "if-else" statement, where ? represents if true.
            speciesText.setText(fish.getSpecies() != null ? fish.getSpecies() : "");
            dateText.setText(fish.getDate() != null ? fish.getDate() : "");
            weightText.setText(String.format(Locale.getDefault(), "Weight: %.2f lbs", fish.getWeight()));
            lengthText.setText(String.format(Locale.getDefault(), "Length: %.1f in", fish.getLength()));
            locationText.setText(fish.getLocation() != null ? fish.getLocation() : "");
            weatherText.setText(fish.getWeather() != null ? fish.getWeather() : "");
            battleRatingBar.setRating(fish.getBattleRating());

            // If the fish emoji is present, set it to the following emojis
            if (fishEmoji != null) {
                // Updates fish emojis based on species
                String species = fish.getSpecies() == null ? "" : fish.getSpecies().toLowerCase();
                if (species.contains("trout")) fishEmoji.setText("🐟");
                else if (species.contains("bass")) fishEmoji.setText("🐋");
                else if (species.contains("pike")) fishEmoji.setText("🐡");
                else if (species.contains("salmon")) fishEmoji.setText("🐠");
                else if (species.contains("shark")) fishEmoji.setText("🦈");
                else fishEmoji.setText("🎣");
            }
        }
    }
}
