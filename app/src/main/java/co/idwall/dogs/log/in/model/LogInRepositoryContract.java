package co.idwall.dogs.log.in.model;

import org.json.JSONObject;

public interface LogInRepositoryContract {

    void logIn(JSONObject json);

    void logInServer(JSONObject json);

}
