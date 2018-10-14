package co.idwall.dogs.dogs.model;

public interface DogsRepositoryContract {

    void getDogList(String token, String category);

    void getDogListInServer(String token, String category);
}
