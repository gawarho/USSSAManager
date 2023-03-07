package org.mfp.softball.dcusssa;

import com.beust.jcommander.JCommander;
import org.mfp.common.database.sql.USSSADatabaseDriver;
import org.mfp.softball.dcusssa.data.Entry;

/**
 * Created by gawarho on 2/21/18.
 */
public class EntryFlow {
    private USSSADatabaseDriver dd;
    //private EntryProcessor ep;

    public EntryFlow(AppArgs aa) {
        String startDate = aa.startDate;
        String endDate = aa.endDate;
        String state = aa.state;
        long season = Long.parseLong(aa.season);
        String month = aa.month;
        Boolean showMine = aa.showMine;
        String table = aa.table;

        EntryHandler eh = new EntryFlowDBHandler();

        //ep = new EntryProcessor(startDate, endDate, season, state, month, showMine, table, eh);

        dd = new USSSADatabaseDriver("usssa", "gawarho", "1q2w3e4r5t");

    }

    public void process() {
        //ep.searchEvents();

    }

    public void close() {
        dd.close();

        //ep.close();
    }

    class EntryFlowDBHandler implements EntryHandler {
        public void process(Entry en, String table) {
            dd.addEntryFlowTableRow(en, table);
            //System.out.println(en);
        }
    }

    public static void main(String [] args) {
        AppArgs appArgs = new AppArgs();
        JCommander jc = new JCommander(appArgs, args);

        //EntryFlow ef = new EntryFlow(appArgs);

        //ef.process();

        //ef.close();
    }
}

