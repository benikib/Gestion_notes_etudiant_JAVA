package cd.hena.note_etudiant;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;



public class APIManager {
    private static final String TAG = "APIManager";
    private Context context;
    RequestQueue requestQueue;

    public APIManager(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

   /* public void loginUser(String email, String password) {
        String url = "http://192.168.224.51:8000/api/auth/login";

        JSONObject params = new JSONObject();
        try {
            params.put("email", email);
            params.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Traitement de la réponse JSON en cas de succès
                        try {
                            boolean status = response.getBoolean("status");
                            if (status) {
                                // Connexion réussie
                                String token = response.getString("token");
                                // Faites quelque chose avec le token, par exemple, le stocker localement
                                Toast.makeText(context, "Connexion réussie", Toast.LENGTH_SHORT).show();
                            } else {
                                String error = response.getString("error");
                                Toast.makeText(context, "Erreur : " + error, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Traitement de l'erreur de la requête
                        Log.e(TAG, "Erreur de la requête : " + error.getMessage());
                        Toast.makeText(context, "Erreur de la requête", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Ajouter les en-têtes de votre choix, par exemple, pour spécifier le type de contenu
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        // Ajouter la requête à la file d'attente de Volley pour l'exécuter
        requestQueue.add(request);
    }*/
}