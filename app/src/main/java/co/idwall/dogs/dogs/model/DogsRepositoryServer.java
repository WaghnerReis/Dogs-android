package co.idwall.dogs.dogs.model;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.idwall.dogs.behavior.Application;
import co.idwall.dogs.behavior.URLs;
import co.idwall.dogs.dogs.DogsContract;

public class DogsRepositoryServer implements DogsRepositoryContract,
        Response.Listener<JSONObject>, Response.ErrorListener {

    private DogsContract.Presenter mPresenter;

    DogsRepositoryServer(DogsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getDogList(String token, String category) {
        mPresenter.makingRequisition();
        getDogListInServer(token, category);
    }

    @Override
    public void getDogListInServer(final String token, String category) {
        String url = URLs.FEED + category;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null, this, this) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", token);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Application.self());
        requestQueue.add(request);
    }

    @Override
    public void onResponse(JSONObject response) {
        if (response.isNull("list")) {
            mPresenter.requisitionCompleted(null);
        } else {
            try {
                List<String> dogList = new ArrayList<>();

                JSONArray dogs = response.getJSONArray("list");
                for (int i = 0; i < dogs.length(); i++) {
                    String dog = dogs.getString(i);
                    dogList.add(dog);
                }

                mPresenter.requisitionCompleted(dogList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mPresenter.requisitionCompleted(null);
    }
}
