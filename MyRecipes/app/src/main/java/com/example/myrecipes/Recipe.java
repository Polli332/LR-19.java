package com.example.myrecipes;

public class Recipe {
    private int id;
    private String title;
    private String ingredients;
    private String instructions;
    private String imagePath;
    private String category;
    private int prepTime;
    private int servings;
    private boolean isFavorite;

    public Recipe() {
    }

    public Recipe(int id, String title, String ingredients, String instructions,
                  String imagePath, String category, int prepTime, int servings, boolean isFavorite) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.imagePath = imagePath;
        this.category = category;
        this.prepTime = prepTime;
        this.servings = servings;
        this.isFavorite = isFavorite;
    }

    // Геттеры
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getIngredients() { return ingredients; }
    public String getInstructions() { return instructions; }
    public String getImagePath() { return imagePath; }
    public String getCategory() { return category; }
    public int getPrepTime() { return prepTime; }
    public int getServings() { return servings; }
    public boolean isFavorite() { return isFavorite; }

    // Сеттеры
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public void setCategory(String category) { this.category = category; }
    public void setPrepTime(int prepTime) { this.prepTime = prepTime; }
    public void setServings(int servings) { this.servings = servings; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}
