package css.cis3334.firebaseauthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView textViewStatus;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonGoogleLogin;
    private Button buttonCreateLogin;
    private Button buttonSignOut;
    private FirebaseAuth mAuth;


    /**
     * Method invoked when MainActivity is created. Responsible for initializing class attributes
     * and setting onClickListeners for buttons
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonGoogleLogin = (Button) findViewById(R.id.buttonGoogleLogin);
        buttonCreateLogin = (Button) findViewById(R.id.buttonCreateLogin);
        buttonSignOut = (Button) findViewById(R.id.buttonSignOut);
        mAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CIS3334", "normal login ");
                signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        buttonCreateLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CIS3334", "Create Account ");
                createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CIS3334", "Google login ");
                googleSignIn();
            }
        });

        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CIS3334", "Logging out - signOut ");
                signOut();
            }
        });


    }

    /**
     * Creates an account for the email and password combination
     * This is done by using the instance of mAuth created during creation and calling it
     * CreateUserWithEmailAndPassword() method
     * @param email String email for user
     * @param password String password for user
     */
    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("CIS3334", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            textViewStatus.setText("Signed In");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("CIS3334", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            textViewStatus.setText("Signed Out");
                        }

                        // ...
                    }
                });
    }


    /**
     * Uses existing email password combination to sign in using mAuth.signInWithEmailAndPassword
     * Requires user to exist in order to be successful
     * @param email
     * @param password
     */
    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("CIS3334", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            textViewStatus.setText("Signed In");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("CIS3334", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            textViewStatus.setText("Signed Out");
                        }

                        // ...
                    }
                });
    }

    private void signOut () {
        mAuth.signOut();
        textViewStatus.setText("Signed Out");
    }

    private void googleSignIn() {

    }


    /**
     * Method that executes after onCreate()
     * This ensures that mAuth will have been instantiated before this method and its members
     */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);

        if (currentUser != null) {
            // User is signed in
            Log.d("CIS3334", "onAuthStateChanged:signed_in:" + currentUser.getUid());
            Toast.makeText(MainActivity.this, "User Signed In", Toast.LENGTH_LONG).show();
            textViewStatus.setText("Signed In");
        } else {
            // User is signed out
            Log.d("CIS3334", "onAuthStateChanged:signed_out");
            Toast.makeText(MainActivity.this, "User Signed Out", Toast.LENGTH_LONG).show();
            textViewStatus.setText("Signed Out");
        }
    }



}
