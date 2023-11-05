package cd.hena.note_etudiant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

   Button aproposBtn;
    Button profilBtn;

    TextView profText;

    TextView courText;

    Button deconnexionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }


    public void init(){
        aproposBtn=findViewById(R.id.aproposBtn);
        profilBtn=findViewById(R.id.profilBtn);
        profText=findViewById(R.id.profText);
        courText=findViewById(R.id.courText);
        deconnexionButton=findViewById(R.id.deconnexionButton);
        ClicDeconnexionButton();
        ClicAproposButton();
        ClicprofilBtnButton();
        ClicProfButton();
        ClicCourButton();
    }


    public void ClicDeconnexionButton(){

        

    }

    public  void ClicAproposButton(){
        aproposBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), AproposActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public  void ClicProfButton(){
        profText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ProfesseurActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public  void ClicCourButton(){
        courText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), CourActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public  void ClicprofilBtnButton(){
        profilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ProfilActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}