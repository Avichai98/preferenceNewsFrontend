package com.avichai98.preferencenews.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.avichai98.preferencenews.R;

import java.util.ArrayList;
import java.util.Map;

public class PlatformAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> platforms;
    private final Map<String, Boolean> platformState;

    public PlatformAdapter(Context context, ArrayList<String> platforms, Map<String, Boolean> platformState) {
        super(context, 0, platforms);
        this.context = context;
        this.platforms = platforms;
        this.platformState = platformState;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.dropdown_item, parent, false);
        }

        CheckBox checkBox = convertView.findViewById(R.id.category_checkbox);
        String platform = platforms.get(position);

        // Set the platform name and current selection state
        checkBox.setText(platform);
        checkBox.setChecked(platformState.getOrDefault(platform, false));

        // Ensure the state only changes when the checkbox is clicked
        checkBox.setOnClickListener(v -> {
            boolean isChecked = checkBox.isChecked();
            platformState.put(platform, isChecked); // Update the state map
        });

        return convertView;
    }
}
