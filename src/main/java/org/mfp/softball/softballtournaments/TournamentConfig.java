package org.mfp.softball.softballtournaments;

/**
 * Created by gawarho on 1/1/18.
 */
public class TournamentConfig {
    private String name;
    private String complex;
    private String city;
    private String state;
    private int startMonth;
    private int startDay;
    private int endMonth;
    private int endDay;
    private int year;
    private String classification;
    private String association;
    private String comments;
    private String pocEmail;

    public TournamentConfig(String name, String complex, String city, String state,
                            int startMonth, int startDay, int endMonth, int endDay, int year,
                            String classification, String association, String comments, String pocEmail) {
        this.name = name;
        this.complex = complex;
        this.city = city;
        this.state = state;
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.endMonth = endMonth;
        this.endDay = endDay;
        this.year = year;
        this.classification = classification;
        this.association = association;
        this.comments = comments;
        this.pocEmail = pocEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComplex() {
        return complex;
    }

    public void setComplex(String complex) {
        this.complex = complex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public int getEndDay() {
        return endDay;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPocEmail() {
        return pocEmail;
    }

    public void setPocEmail(String pocEmail) {
        this.pocEmail = pocEmail;
    }

    @Override
    public String toString() {
        return "TournamentConfig{" +
                "name='" + name + '\'' +
                ", complex='" + complex + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", startMonth=" + startMonth +
                ", startDay=" + startDay +
                ", endMonth=" + endMonth +
                ", endDay=" + endDay +
                ", year=" + year +
                ", classification='" + classification + '\'' +
                ", association='" + association + '\'' +
                ", comments='" + comments + '\'' +
                ", pocEmail='" + pocEmail + '\'' +
                '}';
    }
}


