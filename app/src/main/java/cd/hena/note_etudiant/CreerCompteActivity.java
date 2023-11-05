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

public class CreerCompteActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordconfirmedEditText;

    EditText nomEditText;

    EditText prenomEditText;
    TextView connexionBtn;
    Button creerCompteBtn;

    APIManager requete;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);
        init();
    }

    public void init() {
        // Recherche des IDs dans l'interface de création de compte
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        nomEditText=findViewById(R.id.nomEditText);
        passwordconfirmedEditText = findViewById(R.id.passwordconfirmedEditText);
        prenomEditText=findViewById(R.id.prenomEditText);
        creerCompteBtn = findViewById(R.id.creercomptesBtn);
        connexionBtn = findViewById(R.id.connexionBtn);


        requete = new APIManager(getApplicationContext());
        setOnClickListener();
        ClicConnexionBtn();


    }

    public void setOnClickListener() {
        creerCompteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creerCompte();
            }
        });
    }

    public  void ClicConnexionBtn(){
        connexionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void creerCompte() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String nom=nomEditText.getText().toString() ;
        String prenom=prenomEditText.getText().toString();
        String confirmPassword = passwordconfirmedEditText.getText().toString();

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "Les mots de passe ne correspondent pas.", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://192.168.224.51:8000/api/auth/register";
        JSONObject params = new JSONObject();
        try {
            params.put("name",nom);
            params.put("email", email);
            params.put("prenom", prenom);
            params.put("password", password);
            params.put("password_confirmation", confirmPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog = ProgressDialog.show(CreerCompteActivity.this, "", "Création du compte en cours...", true);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            Log.d("CreerCompteActivity", "Réponse JSON : " + response.toString());
                            if (response.has("status")) {
                                boolean status = response.getBoolean("status");
                                if (status) {
                                    // Compte créé avec succès
                                    Toast.makeText(getApplicationContext(), "Compte créé avec succès", Toast.LENGTH_SHORT).show();
                                    // Rediriger vers l'activité de connexion après la création du compte
                                    Intent intent = new Intent(CreerCompteActivity.this, MainActivity.class);
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
                        Log.e("CreerCompteActivity", "Erreur de la requête : " + error.getMessage());
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