package co.idwall.dogs.log.in;

import android.app.Dialog;

import org.json.JSONObject;

import co.idwall.dogs.behavior.exception.NoNetworkConnectionException;
import co.idwall.dogs.log.in.exception.EmptyEmailFieldException;
import co.idwall.dogs.log.in.exception.InvalidEmailFieldException;

public interface LogInContract {

    interface Model {

        void logIn(JSONObject json);
    }

    interface View {

        void onClick(android.view.View view);

        void emptyEmailField();

        void invalidEmailField();

        void makingRequisition();

        void showProgressDialog();

        void requisitionCompleted(boolean success, String result);

        void hideProgressDialog();

        Dialog getErrorDialog(String message);

        void saveToken(String token);

        void nextActivity();
    }

    interface Presenter {

        void logIn(String email) throws NoNetworkConnectionException, EmptyEmailFieldException, InvalidEmailFieldException;

        JSONObject buildJson(String email);

        void makingRequisition();

        void requisitionCompleted(boolean success, String result);
    }
}
