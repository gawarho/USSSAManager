package org.mfp.softball.dcusssa;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gawarho on 2/21/18.
 */
public class AppArgs {

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = {"-st", "--state"}, description = "State")
    public String state = "All States";

    @Parameter(names = {"-sd", "--startDate"}, description = "Start Date")
    public String startDate = "";

    @Parameter(names = {"-ed", "--endDate"}, description = "End Date")
    public String endDate = "";

    @Parameter(names = {"-s", "--season"}, description = "Season")
    public String season = "2018";

    @Parameter(names = {"-m", "--month"}, description = "Month")
    public String month = "";

    @Parameter(names = { "-sm", "--showMine"}, description = "Show Mine")
    public Boolean showMine = false;

    @Parameter(names = { "-t", "--table"}, description = "Table")
    public String table = "entry_flow_table";

    @Parameter(names = "-debug", description = "Debug mode")
    public boolean debug = false;

}
