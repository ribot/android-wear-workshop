package uk.co.ribot.androidwear.model;

public class Issue {
    public String title;
    public String body;
    public int number;
    public User user;

    @Override
    public boolean equals(Object issue) {
        if (issue != null && issue instanceof Issue) {
            return this.number == ((Issue) issue).number;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        //TODO implement a proper hashcode ;)
        return number;
    }

    @Override
    public String toString() {
        return title;
    }
}
