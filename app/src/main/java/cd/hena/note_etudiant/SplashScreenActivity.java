package cd.hena.note_etudiant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //regiriger vers la page introscreen apres 3 secons
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), IntroScreenActivity.class);
                startActivity(intent);
                finish();
            }
        };
        //envoyer avec un certain delai
        new Handler().postDelayed(runnable,3000 );

    }


}