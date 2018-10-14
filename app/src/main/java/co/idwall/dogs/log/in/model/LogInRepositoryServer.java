package co.idwall.dogs.log.in.model;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import co.idwall.dogs.R;
import co.idwall.dogs.behavior.Application;
import co.idwall.dogs.behavior.URLs;
import co.idwall.dogs.log.in.LogInContract;

public class LogInRepositoryServer implements LogInRepositoryContract,
        Response.Listener<JSONObject>, Response.ErrorListener {

    private LogInContract.Presenter mPresenter;

    LogInRepositoryServer(LogInContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void logIn(JSONObject json) {
        mPresenter.makingRequisition();
        logInServer(json);
    }

    @Override
    public void logInServer(JSONObject json) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLs.LOG_IN,
                json, this, this);

        RequestQueue requestQueue = Volley.newRequestQueue(Application.self());
        requestQueue.add(request);
    }

    @Override
    public void onResponse(JSONObject response) {
        if (response.isNull("user")) {
            try {
                JSONObject error = response.getJSONObject("error");

                String message = error.getString("message");
                mPresenter.requisitionCompleted(false, message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                JSONObject user = response.getJSONObject("user");

                String token = user.getString("token");
                mPresenter.requisitionCompleted(true, token);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mPresenter.requisitionCompleted(false, Application.self().getString(R.string.error_generic));
    }
}
