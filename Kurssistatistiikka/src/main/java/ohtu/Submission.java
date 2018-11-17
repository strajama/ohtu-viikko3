package ohtu;

public class Submission {

    private int week;
    private int hours;
    private String course;
    private int[] exercises;

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int[] getExercises() {
        return exercises;
    }

    public void setExercises(int[] exercises) {
        this.exercises = exercises;
    }

    public String printExercises() {
        StringBuilder s = new StringBuilder();
        for (int e : exercises) {
            s.append(e);
            s.append(", ");
        }
        s.delete(s.lastIndexOf(","), s.length());
        return s.toString();
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeek() {
        return week;
    }

    @Override
    public String toString() {
        return course + ", viikko " + week + " tehtyjä tehtäviä yhteensä "
                + exercises.length + " aikaa kului " + hours + " tehdyt tehtävät: "
                + printExercises();
    }

}
// rails2018, viikko 6 tehtyjä tehtäviä yhteensä 2 aikaa kului 2 tehdyt tehtävät: 1, 4
