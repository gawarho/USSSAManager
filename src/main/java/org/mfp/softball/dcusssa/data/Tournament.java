package org.mfp.softball.dcusssa.data;

import org.openqa.selenium.WebElement;

import java.util.Date;

/**
 * Created by gawarho on 2/18/18.
 */

public class Tournament {
    private long eId;
    private String eName;
    private Date eDate;
    private String eState;
    private String eLocation;
    private long eAge;
    private String eClass;
    private long eSeason;
    private WebElement d;

    public Tournament(String eSeason, String eId, String eName, Date eDate, String eState, String eLocation, WebElement d) {
        this(Long.parseLong(eSeason), Long.parseLong(eId), eName, eDate, eState, eLocation, d);
    }

    public Tournament(long eSeason, long eId, String eName, Date eDate, String eState, String eLocation, WebElement d) {
        this.eSeason = eSeason;
        this.eId = eId;

        this.eName = eName;
        this.eDate = eDate;

        this.eState = eState;
        this.eLocation = eLocation;

        this.d = d;
    }

    public long geteSeason() {
        return eSeason;
    }

    public long geteId() {
        return eId;
    }

    public String geteName() {
        return eName;
    }

    public Date geteDate() {
        return eDate;
    }

    public WebElement getD() {
        return d;
    }

    public String geteState() {
        return eState;
    }

    public String geteLocation() {
        return eLocation;
    }

    public long geteAge() {
        return eAge;
    }

    public void seteAge(long eAge) {
        this.eAge = eAge;
    }

    public String geteClass() {
        return eClass;
    }

    public void seteClass(String eClass) {
        this.eClass = eClass;
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "eId='" + eId + '\'' +
                ", eName='" + eName + '\'' +
                ", eDate=" + eDate +
                ", eState='" + eState + '\'' +
                ", eLocation='" + eLocation + '\'' +
                ", eAge=" + eAge +
                ", eClass='" + eClass + '\'' +
                '}';
    }
}
