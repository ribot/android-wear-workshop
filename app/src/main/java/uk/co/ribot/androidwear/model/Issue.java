package uk.co.ribot.androidwear.model;

public class Issue {
    String state;
    String title;
    String body;
    int number;

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
