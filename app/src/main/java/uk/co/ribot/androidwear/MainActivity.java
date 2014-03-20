package uk.co.ribot.androidwear;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.ribot.androidwear.api.TrelloApi;
import uk.co.ribot.androidwear.model.Board;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final TextView textView = (TextView) findViewById(R.id.name);

        TrelloApi.get().getBoard("2uxruMdR", new Callback<Board>() {
            @Override
            public void success(Board board, Response response) {
                textView.setText(board.getName());
            }

            @Override
            public void failure(RetrofitError error) {
                textView.setText("Error: " + error);
            }
        });
    }
}
