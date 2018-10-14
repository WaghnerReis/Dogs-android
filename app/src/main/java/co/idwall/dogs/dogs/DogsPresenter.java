package co.idwall.dogs.dogs;

import java.util.List;

import co.idwall.dogs.behavior.Application;
import co.idwall.dogs.behavior.exception.NoNetworkConnectionException;
import co.idwall.dogs.dogs.model.DogsRepository;

public class DogsPresenter implements DogsContract.Presenter {

    private DogsContract.Model mModel;
    private DogsContract.View mView;

    public DogsPresenter(DogsContract.View view) {
        mModel = new DogsRepository(this);
        mView = view;
    }

    @Override
    public void getDogList(String token, String category) throws NoNetworkConnectionException {
        if (Application.haveNetworkConnection()) {
            mModel.getDogList(token, category);
        } else {
            throw new NoNetworkConnectionException("Without internet");
        }
    }

    @Override
    public void makingRequisition() {
        mView.makingRequisition();
    }

    @Override
    public void requisitionCompleted(List<String> dogs) {
        mView.requisitionCompleted(dogs);
    }
}
