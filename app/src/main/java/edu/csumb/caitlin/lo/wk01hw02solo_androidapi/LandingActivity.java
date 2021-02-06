package edu.csumb.caitlin.lo.wk01hw02solo_androidapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LandingActivity extends AppCompatActivity {

    private TextView textViewResult;

    private String username;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        textViewResult = findViewById(R.id.text_view_results);
        getExtras();
        textViewResult.append("Welcome, " + username + ". UserId: " + userId + "\n\n");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for (Post post : posts) {
                    if (post.getUserId() == userId) {
                        String content = "";
                        content += "PostID: " + post.getId() + "\n";
                        content += "UserId: " + post.getUserId() + "\n";
                        content += "Title: " + post.getTitle() + "\n";
                        content += "Text: " + post.getText() + "\n\n";

                        textViewResult.append(content);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            username = "null";
            userId = 0;
        } else {
            username = extras.getString("username");
            userId = extras.getInt("userId");
        }
    }

    /**
     * Intent factory
     * @param context
     * @param username
     * @param userId
     * @return
     */
    public static Intent getIntent(Context context, String username, int userId) {
        Intent intent = new Intent(context, LandingActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("userId", userId);
        return intent;
    }
}