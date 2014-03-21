package uk.co.ribot.androidwear.model;

import com.google.gson.annotations.SerializedName;

public class Issue {
    public String title;
    public String body;
    @SerializedName("number") public int id;
    public User user;
    public String state;

    @Override
    public boolean equals(Object issue) {
        if (issue != null && issue instanceof Issue) {
            return this.id == ((Issue) issue).id;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        //TODO implement a proper hashcode ;)
        return id;
    }

    @Override
    public String toString() {
        return title;
    }
}
