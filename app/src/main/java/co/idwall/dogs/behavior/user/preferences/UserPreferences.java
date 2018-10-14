package co.idwall.dogs.behavior.user.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    public UserPreferences(Context context) {
        mPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public void setToken(String token) {
        mEditor.putString(Constants.TOKEN, token);
        mEditor.apply();
    }

    public String getToken() {
        return mPreferences.getString(Constants.TOKEN, Constants.ERROR);
    }
}
