package co.idwall.dogs.dogs.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.idwall.dogs.R;
import co.idwall.dogs.behavior.Application;
import co.idwall.dogs.behavior.exception.NoNetworkConnectionException;
import co.idwall.dogs.behavior.user.preferences.UserPreferences;
import co.idwall.dogs.dogs.DogsContract;
import co.idwall.dogs.dogs.DogsPresenter;
import co.idwall.dogs.dogs.OnItemClickListener;
import co.idwall.dogs.dogs.image.fullscreen.ImageFullscreenActivity;

public class DogsFragment extends Fragment implements DogsContract.View, OnItemClickListener {

    private ProgressDialog mProgress;
    private RecyclerView mDogList;
    private TextView mEmptyText;
    private List<String> mDogs;

    public static DogsFragment newInstance(String category) {

        Bundle args = new Bundle();
        args.putString("category", category);

        DogsFragment fragment = new DogsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            mProgress = Application.getProgress(self(), R.string.message_loading);
            String category = getArguments().getString("category");

            DogsContract.Presenter presenter = new DogsPresenter(this);
            presenter.getDogList(getToken(), category);
        } catch (NoNetworkConnectionException e) {
            Dialog errorDialog = getErrorDialog(R.string.error_without_network);
            errorDialog.show();
        }

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dogs, container, false);

        mEmptyText = root.findViewById(R.id.empty_text);
        mDogList = root.findViewById(R.id.dog_list);

        return root;
    }

    @Override
    public String getToken() {
        UserPreferences userPreferences = new UserPreferences(self());
        return userPreferences.getToken();
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
    public void requisitionCompleted(List<String> dogs) {
        hideProgressDialog();

        if (dogs == null) {
            showEmptyMessage();

            Dialog errorDialog = getErrorDialog(R.string.error_generic);
            errorDialog.show();
        } else {
            hideEmptyMessage();

            mDogs = dogs;
            setupDogList();
        }
    }

    @Override
    public void hideProgressDialog() {
        if (mProgress.isShowing()) mProgress.dismiss();
    }

    @Override
    public void showEmptyMessage() {
        mEmptyText.setVisibility(View.VISIBLE);
    }

    @Override
    public Dialog getErrorDialog(int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(self());
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
    public void hideEmptyMessage() {
        mEmptyText.setVisibility(View.GONE);
    }

    @Override
    public void setupDogList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(self());
        mDogList.setLayoutManager(layoutManager);
        mDogList.setAdapter(new DogsAdapter(mDogs, this));
    }

    @Override
    public void onItemClick(View view, int position) {
        ImageView image = view.findViewById(R.id.image);

        Intent intent = new Intent(self(), ImageFullscreenActivity.class);
        intent.putExtra("TRANSITION", image.getTransitionName());
        intent.putExtra("URL", mDogs.get(position));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(self(), image, image.getTransitionName());
        startActivity(intent, options.toBundle());
    }

    @Override
    public Activity self() {
        return getActivity();
    }
}
