package com.example.pulkit.darcpleazurchocolates;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pulkit.darcpleazurchocolates.Models.User;
import com.example.pulkit.darcpleazurchocolates.Utils.ResetPasswordActivity;
import com.example.pulkit.darcpleazurchocolates.Utils.SignUpActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LogInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;//for facebook
    private EditText inputEmail, inputPassword;
    private LoginButton loginButton;
    private SignInButton signInButton;
    private static final int RC_SIGN_IN = 9001;
    private DatabaseReference mDatabase;
    public ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LogInActivity.this, MainActivity.class));
            finish();
        }
        bindingview();
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccesToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        signInButton.setOnClickListener(this);
        facebookSignIn();
    }


    private void bindingview() {

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        Button btnSignup = findViewById(R.id.btn_signup);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnReset = findViewById(R.id.btn_reset_password);
        signInButton = findViewById(R.id.btn_gSignIn);
        loginButton = findViewById(R.id.login_button);


        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Please Wait....");
        dialog.setTitle("Loading");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    public void buttonclicks(View view) {
        switch (view.getId()) {
            case R.id.btn_signup:
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
                break;
            case R.id.btn_login:
                emailSignIn();
                break;
            case R.id.btn_reset_password:
                startActivity(new Intent(LogInActivity.this, ResetPasswordActivity.class));
                break;
            default:
                break;

        }
    }

    private void emailSignIn() {
        String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            inputEmail.setError("Enter email address!");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Enter password!");
            return;
        }
        dialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            dialog.hide();
                            // there was an error
                            if (password.length() < 6) {
                                inputPassword.setError(getString(R.string.minimum_password));
                            } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(LogInActivity.this, "Email id Exist.Try using any other method", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LogInActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            dialog.hide();
                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    public void gSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("TAG", "gSignIn:" + requestCode);

        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.i("TAG", "onActivityResult: " + requestCode);
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.i("TAG", "onActivityResult: " + result.getStatus());
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                Log.i("TAG", "onActivityResult: ");
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                Log.i("TAG", "gSignIn: kbsldblds" + "here it is3");
                // [END_EXCLUDE]
            }
        }
    }

    private void facebookSignIn() {
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.i("TAG", "gSignIn: kbsldblds" + "here it is3");
        final AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            final String userId = mAuth.getCurrentUser().getUid();
                            User user = new User("Pulkit Aggarwal", credential + "", "9582054664");
                            mDatabase.child("users").child(userId).setValue(user);
                            dialog.hide();
                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            dialog.hide();
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(LogInActivity.this, "Email id Exist.Try using any other method", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(LogInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void handleFacebookAccesToken(AccessToken accessToken) {
        dialog.show();
        final AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                             Sign in success, update UI with the signed-in user's information
                            final String userId = mAuth.getCurrentUser().getUid();
                            User user = new User(mAuth.getCurrentUser().getDisplayName(), mAuth.getCurrentUser().getEmail(), "9582054664");
                            mDatabase.child("users").child(userId).setValue(user);
                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            dialog.hide();
                        } else {
                            dialog.hide();
                            LoginManager.getInstance().logOut();
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                LoginManager.getInstance().logOut();
                                Toast.makeText(LogInActivity.this, "Email id Exist.Try using any other method", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(LogInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_gSignIn:
                gSignIn();
                break;
        }

    }
}
