package co.idwall.dogs.log.in;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import co.idwall.dogs.behavior.Application;
import co.idwall.dogs.behavior.exception.NoNetworkConnectionException;
import co.idwall.dogs.log.in.exception.EmptyEmailFieldException;
import co.idwall.dogs.log.in.exception.InvalidEmailFieldException;
import co.idwall.dogs.log.in.model.LogInRepository;

public class LogInPresenter implements LogInContract.Presenter {

    private LogInContract.Model mModel;
    private LogInContract.View mView;

    LogInPresenter(LogInContract.View mView) {
        mModel = new LogInRepository(this);
        this.mView = mView;
    }

    @Override
    public void logIn(String email) throws NoNetworkConnectionException, EmptyEmailFieldException, InvalidEmailFieldException {
        if (Application.haveNetworkConnection()) {
            if (TextUtils.isEmpty(email)) {
                throw new EmptyEmailFieldException("E-mail field is empty");
            } else if (Application.isValidEmail(email)) {
                mModel.logIn(buildJson(email));
            } else {
                throw new InvalidEmailFieldException("E-mail field is invalid");
            }
        } else {
            throw new NoNetworkConnectionException("Without internet");
        }
    }

    @Override
    public JSONObject buildJson(String email) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    public void makingRequisition() {
        mView.makingRequisition();
    }

    @Override
    public void requisitionCompleted(boolean success, String result) {
        mView.requisitionCompleted(success, result);
    }
}
