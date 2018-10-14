package co.idwall.dogs.log.in.model;

import org.json.JSONObject;

import co.idwall.dogs.log.in.LogInContract;

public class LogInRepository implements LogInContract.Model {

    private LogInRepositoryContract mServer;

    public LogInRepository(LogInContract.Presenter presenter) {
        mServer = new LogInRepositoryServer(presenter);
    }

    @Override
    public void logIn(JSONObject json) {
        mServer.logIn(json);
    }
}
