package com.avichai98.preferencenews.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryManager {
    private ArrayList<String> categories;
    private Map<String, Boolean> categoryState;

    public CategoryManager() {
        categories = new ArrayList<>();
        categoryState = new HashMap<>();

        // Add all categories to the list and initialize their state as false
        addAllCategories();
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public Map<String, Boolean> getCategoryState() {
        return categoryState;
    }

    public void setCategoryState(String category, boolean isSelected) {
        if (categoryState.containsKey(category)) {
            categoryState.put(category, isSelected);
        }
    }

    private void addAllCategories() {
        String[] predefinedCategories = {
                "Business", "Crime", "Domestic", "Education", "Entertainment",
                "Environment", "Food", "Health", "Lifestyle", "Other",
                "Politics", "Science", "Sports", "Technology", "Top", "Tourism"
        };

        for (String category : predefinedCategories) {
            categories.add(category);
            categoryState.put(category, false); // Default state: not selected
        }
    }
}

