package org.example.lab1L;

public class Album {
    int year;
    String title;
    String group;
    String studio;

    public void setGroup(String group) {
        this.group = group;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Album: " +
                "\n\tyear = " + year +
                "\n\ttitle = " + title +
                "\n\tgroup = " + group +
                "\n\tstudio = " + studio;
    }
}
