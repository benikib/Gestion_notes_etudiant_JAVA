package cd.hena.note_etudiant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cd.hena.note_etudiant.APIManager;
import cd.hena.note_etudiant.R;

public class MainActivity extends AppCompatActivity {

    private TextView creerBtn;
    private EditText emailEditText;
    private EditText passwordEditText;

    private Button connectBtn;

    private String email;
    private String password;
    TextView passwordoublie;

    APIManager requete;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        // Recherche des IDs dans l'interface de connexion
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordoublie=findViewById(R.id.passwordoublie);
        connectBtn = findViewById(R.id.connectBtn);
        creerBtn = findViewById(R.id.creerBtn);

        requete = new APIManager(getApplicationContext());
        setOnClickListenersConnect();
        ClicPasswordOublie();
    }

    public  void ClicPasswordOublie(){
        passwordoublie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MotDePasseOublieActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    //lien du bouton se connecter

    public void setOnClickListenersConnect() {
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                envoyerInformationsPourVerification();
            }
        });

        OnClickListenersCreer();

    }

    //lien du bouton creer votre compte
    public void OnClickListenersCreer() {
        creerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreerCompteActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void envoyerInformationsPourVerification() {
        // Récupérer les valeurs des EditText lorsqu'on clique sur le bouton "Se connecter"
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
        connexionUtilisateur(email, password);
    }

    // Méthode pour la vérification de l'utilisateur via API Laravel
    public void connexionUtilisateur(String email, String password) {
        String url = "http://192.168.224.51:8000/api/auth/login";
        JSONObject params = new JSONObject();
        try {
            params.put("email", email);
            params.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog = ProgressDialog.show(MainActivity.this, "", "Connexion en cours...", true);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            Log.d("MainActivity", "Réponse JSON : " + response.toString());
                            if (response.has("status")) {
                                int status = response.getInt("status");
                                if (status == 200) {
                                    // Connexion réussie
                                    String token = response.getJSONObject("data").getString("token");
                                    Toast.makeText(getApplicationContext(), "Connexion réussie", Toast.LENGTH_SHORT).show();
                                    // Rediriger vers l'activité suivante après la connexion réussie
                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    intent.putExtra("email", email);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    if (response.has("error")) {
                                        String error = response.getString("error");
                                        Toast.makeText(getApplicationContext(), "Erreur : " + error, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Statut non attendu dans la réponse JSON", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Clé 'status' non trouvée dans la réponse JSON", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.e("MainActivity", "Erreur de la requête : " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Erreur de la requête", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        requete.requestQueue.add(request);
    }
}