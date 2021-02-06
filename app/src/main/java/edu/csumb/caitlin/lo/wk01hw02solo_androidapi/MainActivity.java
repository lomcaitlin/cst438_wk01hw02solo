package edu.csumb.caitlin.lo.wk01hw02solo_androidapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /* Display Variables */
    private EditText usernameInput;
    private EditText passwordInput;
    private Button login;

    /* Display Values */
    private String username;
    private String password;

    private List<User> users;
    private User loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createUsers();
        connectDisplay();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDisplayValues();
                if (validateLogin()) {
                    Intent intent = LandingActivity.getIntent(getApplicationContext(), loggedIn.getUsername(), loggedIn.getId());
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * Connect elements from layout
     */
    private void connectDisplay() {
        usernameInput = findViewById(R.id.login_username);
        passwordInput = findViewById(R.id.login_password);
        login = findViewById(R.id.login_button);
    }

    /**
     * Get values from display elements
     */
    private void getDisplayValues() {
        username = usernameInput.getText().toString();
        password = passwordInput.getText().toString();
    }

    /**
     * Validate username
     */
    private boolean validateLogin() {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    loggedIn = user;
                    return true;
                }
                Toast.makeText(this, "Invalid password", Toast.LENGTH_LONG).show();
                passwordInput.requestFocus();
                return false;
            }
        }
        Toast.makeText(this, "No user with username \"" + username + "\" found", Toast.LENGTH_LONG).show();
        usernameInput.requestFocus();
        return false;
    }

    /**
     * Create users
     */
    private void createUsers() {
        users = new ArrayList<>();
        users.add(new User(0, "din_djarin", "baby_yoda_ftw"));
        users.add(new User(1,"Bret", "password"));
        users.add(new User(2,"Antonette", "password"));
        users.add(new User(3,"Samantha", "password"));
        users.add(new User(4,"Karianne", "password"));
        users.add(new User(5,"Kamren", "password"));
        users.add(new User(6,"Leopoldo_Corkery", "password"));
        users.add(new User(7,"Elwyn.Skiles", "password"));
        users.add(new User(8,"Maxime_Nienow", "password"));
        users.add(new User(9,"Delphine", "password"));
        users.add(new User(10,"Moriah.Stanton", "password"));
    }

}