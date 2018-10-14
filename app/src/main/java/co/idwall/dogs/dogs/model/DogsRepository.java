package co.idwall.dogs.dogs.model;

import co.idwall.dogs.dogs.DogsContract;

public class DogsRepository implements DogsContract.Model {

    private DogsRepositoryContract mServer;

    public DogsRepository(DogsContract.Presenter presenter) {
        mServer = new DogsRepositoryServer(presenter);
    }

    @Override
    public void getDogList(String token, String category) {
        mServer.getDogList(token, category);
    }
}
