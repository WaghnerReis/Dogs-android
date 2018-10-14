package co.idwall.dogs.log.in;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import co.idwall.dogs.behavior.exception.NoNetworkConnectionException;
import co.idwall.dogs.dogs.view.DogsActivity;
import co.idwall.dogs.R;
import co.idwall.dogs.behavior.Application;
import co.idwall.dogs.behavior.user.preferences.UserPreferences;
import co.idwall.dogs.log.in.exception.EmptyEmailFieldException;
import co.idwall.dogs.log.in.exception.InvalidEmailFieldException;

public class LogInActivity extends AppCompatActivity implements LogInContract.View {

    private ProgressDialog mProgress;
    private TextInputLayout mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mProgress = Application.getProgress(this, R.string.message_sending);
        mEmail = findViewById(R.id.input_email);
    }

    @Override
    public void onClick(View view) {
        try {
            mEmail.setError(null);

            LogInContract.Presenter presenter = new LogInPresenter(this);
            presenter.logIn(mEmail.getEditText().getText().toString());
        } catch (NoNetworkConnectionException e) {
            Dialog errorDialog = getErrorDialog(getString(R.string.error_without_network));
            errorDialog.show();
        } catch (EmptyEmailFieldException e) {
            emptyEmailField();
        } catch (InvalidEmailFieldException e) {
            invalidEmailField();
        }
    }

    @Override
    public void emptyEmailField() {
        mEmail.setError(getString(R.string.error_empty_field));
    }

    @Override
    public void invalidEmailField() {
        mEmail.setError(getString(R.string.error_email_field_invalid));
    }

    @Override
    public void makingRequisition() {
        showProgressDialog();
    }

    @Override
    public void showProgressDialog() {
        if (!mProgress.isShowing()) mProgress.show();
    }

    @Override
    public void requisitionCompleted(boolean success, String result) {
        hideProgressDialog();

        if (success) {
            saveToken(result);
        } else {
            Dialog errorDialog = getErrorDialog(result);
            errorDialog.show();
        }
    }

    @Override
    public void hideProgressDialog() {
        if (mProgress.isShowing()) mProgress.dismiss();
    }

    @Override
    public Dialog getErrorDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }

    @Override
    public void saveToken(String token) {
        UserPreferences userPreferences = new UserPreferences(this);
        userPreferences.setToken(token);

        nextActivity();
    }

    @Override
    public void nextActivity() {
        Intent intent = new Intent(this, DogsActivity.class);
        startActivity(intent);
        finish();
    }
}