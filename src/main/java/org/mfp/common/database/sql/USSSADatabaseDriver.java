package org.mfp.common.database.sql;

import org.mfp.softball.dcusssa.data.Entry;
import org.mfp.softball.dcusssa.data.Team;
import org.mfp.softball.dcusssa.data.Tournament;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by gawarho on 2/23/18.
 */
public class USSSADatabaseDriver extends DatabaseDriver {
    public USSSADatabaseDriver(String db, String user, String pw) {
        super(db, user, pw);
    }

    public void addEntryFlowTableRow(Entry en, String table) {
        Tournament e = en.getTourney();
        Team t = en.getTeam();
        Calendar cal = Calendar.getInstance();
        cal.setTime(e.geteDate());
        java.sql.Date eDate = new java.sql.Date(cal.getTime().getTime());
        cal.setTime(en.getEntryDate());
        java.sql.Date enDate = new java.sql.Date(cal.getTime().getTime());
        String sqlCommand = "INSERT INTO " + table + " (e_season, e_id, e_name, e_date, e_state, e_location, e_age, e_class, " +
                "t_id, t_name, t_age, t_class, t_state, t_location, t_coach, t_email, t_cell, t_enter) VALUES (" +
           e.geteSeason() + ", " + e.geteId() + ", '" + e.geteName().replaceAll("'", "") + "', '" + eDate + "', '" + e.geteState() + "', '" +
                e.geteLocation().replaceAll("'", "") + "', " + e.geteAge() + ", '" + e.geteClass() + "', " +
                t.getId() + ", '" + t.getName().replaceAll("'", "") + "', " + t.getAge() + ", '" + t.getClassification() + "', '" + t.getState() + "', '" +
                t.getLocation().replaceAll("'", "") + "', '" + t.getCoach().replaceAll("'", "") + "', '" + t.getEmail().replaceAll("'", "") + "', '" + t.getTelephone().replaceAll("'", "") + "', '" + enDate + "')";

        //System.err.println(sqlCommand);
        try {
            this.doStatement(sqlCommand);
            if(en.getPaidDate() != null && isEntryPaid(en, table) == null) {
                updateEntry(en, table);
            }       } catch (SQLException e1) {
            if(e1.getSQLState().startsWith("23000") == false) { // ignore duplicate keys
                System.err.println(e1.getErrorCode() + " " + e1.getSQLState());
                e1.printStackTrace();
            } else {
                if(en.getPaidDate() != null && isEntryPaid(en, table) == null) {
                    updateEntry(en, table);
                }
            }
        }
    }

    public void updateEntry(Entry en, String table) {
        Tournament e = en.getTourney();
        Calendar cal = Calendar.getInstance();
        cal.setTime(en.getPaidDate());
        java.sql.Date pDate = new java.sql.Date(cal.getTime().getTime());
        String cmd = "UPDATE " + table + " SET t_fee = " + en.getEntryFee() + ", t_paid = '" + pDate + "', t_cc = 1 WHERE e_id = " + e.geteId() + " AND t_id = " + en.getTeam().getId();
        //System.err.println(cmd);
        try {
            this.doStatement(cmd);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public boolean doesEntryExist(Entry en, String table) {
        boolean retVal = false;

        String cmd = "SELECT t_paid FROM " + table + " WHERE e_id = " + en.getTourney().geteId() + " AND t_id = " + en.getTeam().getId();
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery(cmd);
            if(!rs.isBeforeFirst()) {
                retVal = false;
            } else {
                retVal = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retVal;
    }

    public Date isEntryPaid(Entry en, String table) {
        Date retVal = null;

        String cmd = "SELECT t_paid FROM " + table + " WHERE e_id = " + en.getTourney().geteId() + " AND t_id = " + en.getTeam().getId();
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery(cmd);
            while(rs.next()) {
                retVal = rs.getDate("t_paid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retVal;
    }

    public List<Entry> getTournamentEntries(String eName, String table) {
        return getTournamentEntries(eName, null, table);
    }
    public List<Entry> getTournamentEntries(String eName, java.sql.Date eDate, String table) {
        StringBuilder cmd = new StringBuilder();
        cmd.append("SELECT * FROM " + table + " WHERE e_name = '" + eName + "'");
        if(eDate != null) {
            cmd.append(" AND e_date = '" + eDate + "'");
        }

        List<Entry> retVal = new ArrayList<>();
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery(cmd.toString());
            while(rs.next()) {
                //retVal = rs.getDate("t_paid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retVal;
    }

    public static void main(String [] args) {
        Tournament tourney = new Tournament(2018, 100, "Test", new Date(), "MD", "Central MD", null);
        Team team = new Team(1L, "TestTeam", 14L, "A", 2018L, "Glen", "MM", "Mount Airy");
        Entry en = new Entry(tourney, team, 14, "A", new Date(), 525L);
        USSSADatabaseDriver dd = new USSSADatabaseDriver("usssa", "gawarho", "1q2w3e4r5t");
        en.setPaidCC(true);
        en.setPaidDate(new Date());
        dd.addEntryFlowTableRow(en, "entry_flow_table");
        System.err.println(dd.isEntryPaid(en, "entry_flow_table"));
        //dd.updateEntry(en, "entry_flow_table");
        System.err.println(dd.isEntryPaid(en, "entry_flow_table"));
    }
}