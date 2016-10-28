package rasyan_native_app.rasyan_ahmed_pset6.other;

import java.util.ArrayList;

/**
 * Created by Rasyan on 24-10-2016.
 * A simple recipe class that is used to store the individual recipes, these can then easily be store in firebase.
 * has 2 constructors and getters and setters for each data type it contains,
 * all of those except the second constructors are a requirement for firebase.
 */

public class Recipe {
    private String title;
    private String image;
    private String score;
    private String link;
    private ArrayList<String> ingredients;
    private String id;

    public Recipe() {
        // empty constructor required for firebase
    }

    public Recipe(String title, String image, String score, String link, ArrayList<String> ingredients, String id) {
        this.title = title;
        this.ingredients = ingredients;
        this.image = image;
        this.score = score;
        this.link = link;
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
