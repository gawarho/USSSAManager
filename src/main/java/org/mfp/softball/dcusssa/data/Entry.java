package org.mfp.softball.dcusssa.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gawarho on 2/19/18.
 */
public class Entry {
    private Team team;
    private Tournament tourney;

    private Date entryDate;

    private long checkNo;
    private boolean paidCC;
    private Date paidDate;
    private Long entryFee;

    private Date droppedDate;

    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");


    public Entry(Tournament tourney, Team team, long eventAge, String eventClassification, String entryDate, Long entryFee) {
        this.team = team;
        this.tourney = tourney;
        tourney.seteAge(eventAge);
        tourney.seteClass(eventClassification);
        this.entryFee = entryFee;
        try {
            this.entryDate = sdf.parse(entryDate);
        } catch (ParseException e) {
            e.printStackTrace();
            this.entryDate = new Date();
        }
    }

    public Entry(Tournament tourney, Team team, String eventAge, String eventClassification, String entryDate, Long entryFee) {
        this.team = team;
        this.tourney = tourney;
        tourney.seteAge(Long.parseLong(eventAge));
        tourney.seteClass(eventClassification);
        this.entryFee = entryFee;
        try {
            this.entryDate = sdf.parse(entryDate);
        } catch (ParseException e) {
            e.printStackTrace();
            this.entryDate = new Date();
        }
    }

    public Entry(Tournament tourney, Team team, long eventAge, String eventClassification, Date entryDate, Long entryFee) {
        this.team = team;
        this.tourney = tourney;
        tourney.seteAge(eventAge);
        tourney.seteClass(eventClassification);
        this.entryDate = entryDate;
        this.entryFee = entryFee;
    }

    public Entry(Tournament tourney, Team team, String eventAge, String eventClassification, Date entryDate, Long entryFee) {
        this.team = team;
        this.tourney = tourney;
        tourney.seteAge(Long.parseLong(eventAge));
        tourney.seteClass(eventClassification);
        this.entryDate = entryDate;
        this.entryFee = entryFee;
    }

    public Team getTeam() {
        return team;
    }

    public Long getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(Long entryFee) {
        this.entryFee = entryFee;
    }

    public Tournament getTourney() {
        return tourney;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public long getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(long checkNo) {
        this.checkNo = checkNo;
    }

    public boolean isPaidCC() {
        return paidCC;
    }

    public void setPaidCC(boolean paidCC) {
        this.paidCC = paidCC;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public Date getDroppedDate() {
        return droppedDate;
    }

    public void setDroppedDate(Date droppedDate) {
        this.droppedDate = droppedDate;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "team=" + team +
                ", tourney=" + tourney +
                ", entryDate=" + entryDate +
                ", checkNo=" + checkNo +
                ", paidCC=" + paidCC +
                ", paidDate=" + paidDate +
                ", droppedDate=" + droppedDate +
                ", sdf=" + sdf +
                '}';
    }
}
