package com.example.appstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appstudy.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "google_login";
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText Id = findViewById(R.id.Id);
        EditText Pw = findViewById(R.id.Pw);
        Button LoginBtn = findViewById(R.id.LoginBtn);
        TextView FindIdText= findViewById(R.id.FindIdText);
        TextView FindPwText= findViewById(R.id.FindPwText);
        TextView SignupText= findViewById(R.id.SignupText);
        TextView LoginKakao= findViewById(R.id.LoginKakao);
        TextView LoginGoogle= findViewById(R.id.LoginGoogle);
        TextView LoginNaver= findViewById(R.id.LoginNaver);

        //앱 자체 회원가입 창으로 이동
        SignupText.setOnClickListener(view->{
                Intent intent = new Intent(this, SignupActivity.class);
                startActivity(intent);
        });
        //구글 로그인 클릭
        LoginGoogle.setOnClickListener(view->{
            // Configure Google Sign In
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            // Initialize Firebase Auth
            mAuth = FirebaseAuth.getInstance();

            signIn();
        });
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent(); //구글로그인 페이지로 가는 인텐트 객체
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //구글로그인 인텐트에서 생성된 결과값이 리턴됨
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) { //requestCode를 받은 경우
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                //GoogleSignInAccount 객체에서 ID 토큰을 가져와서 firebaseAuthWithGoogle함수로 전달
                firebaseAuthWithGoogle(account.getIdToken(), account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    //전달받은 ID 토큰을 Firebase 사용자 인증 정보로 교환하고 해당 정보를 사용해 Firebase에 인증
    private void firebaseAuthWithGoogle(String idToken, GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Toast.makeText(LoginActivity.this,"구글 로그인 성공",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
                            intent.putExtra("UserName", account.getDisplayName());
                            intent.putExtra("PhotoUrl", account.getPhotoUrl());
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this,"구글 로그인 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}