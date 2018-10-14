package co.idwall.dogs.dogs;

import android.app.Activity;
import android.app.Dialog;

import java.util.List;

import co.idwall.dogs.behavior.exception.NoNetworkConnectionException;

public interface DogsContract {

    interface Model {

        void getDogList(String token, String category);
    }

    interface View {

        String getToken();

        void makingRequisition();

        void showProgressDialog();

        void requisitionCompleted(List<String> dogs);

        void hideProgressDialog();

        void showEmptyMessage();

        Dialog getErrorDialog(int message);

        Activity self();

        void hideEmptyMessage();

        void setupDogList();
    }

    interface Presenter {

        void getDogList(String token, String category) throws NoNetworkConnectionException;

        void makingRequisition();

        void requisitionCompleted(List<String> dogs);
    }
}
