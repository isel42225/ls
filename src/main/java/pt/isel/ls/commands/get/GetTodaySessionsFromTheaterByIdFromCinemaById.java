package pt.isel.ls.commands.get;

import pt.isel.ls.mappers.sql.CinemaSQLMapper;
import pt.isel.ls.model.Cinema;
import pt.isel.ls.model.Session;
import pt.isel.ls.exceptions.CommandException;
import pt.isel.ls.mappers.sql.SessionSQLMapper;
import pt.isel.ls.result.SessionListFromCinemaResult;
import pt.isel.ls.result.SessionListResult;
import pt.isel.ls.utils.SqlConnectionManager;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

public class GetTodaySessionsFromTheaterByIdFromCinemaById extends Get {

    private final String description = "GET /cinemas/{cid}/theaters/{tid}/sessions/today -> returns a " +
            "list with all sessions for the current day in the cinema and theater identified by {cid} " +
            "and {tid} respectively";

    @Override
    public SessionListFromCinemaResult execute(HashMap<String, String> parameters, SqlConnectionManager manager) throws CommandException {
        Connection c = manager.get();
        SessionSQLMapper mapper = new SessionSQLMapper(c);
        CinemaSQLMapper cinMap = new CinemaSQLMapper(c);

        int cid = Integer.parseInt(parameters.get("{cid}"));
        int tid = Integer.parseInt(parameters.get("{tid}"));

        Cinema cin = cinMap.selectById(cid);
        List<Session> todaySessions = mapper.getTodaySessionsOfCinemaOfTheater(cid , tid);
        return new SessionListFromCinemaResult(cin, todaySessions);
    }

    public String getDescription(){
        return description;
    }
}
