package uk.co.ribot.androidwear.api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import uk.co.ribot.androidwear.model.Board;

public interface TrelloService {
    @GET("/boards/{id}")
    void getBoard(@Path("id") String id, Callback<Board> callback);
}
