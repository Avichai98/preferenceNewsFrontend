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

public class CategoryAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> categories;
    private final Map<String, Boolean> categoryState;

    public CategoryAdapter(Context context, ArrayList<String> categories, Map<String, Boolean> categoryState) {
        super(context, 0, categories);
        this.context = context;
        this.categories = categories;
        this.categoryState = categoryState;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.dropdown_item, parent, false);
        }

        CheckBox checkBox = convertView.findViewById(R.id.category_checkbox);
        String category = categories.get(position);

        // Set the category name and current selection state
        checkBox.setText(category);
        checkBox.setChecked(categoryState.getOrDefault(category, false));

        // Ensure the state only changes when the checkbox is clicked
        checkBox.setOnClickListener(v -> {
            boolean isChecked = checkBox.isChecked();
            categoryState.put(category, isChecked); // Update the state map
        });

        return convertView;
    }
}
