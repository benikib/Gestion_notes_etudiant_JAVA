package cd.hena.note_etudiant;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CoursController {

    private static final String BASE_URL = "http://192.168.91.74:8000/api/cours/getAllCoursesAsOfProfessor/";
    // private static final String BASE_URL = "https://jsonplaceholder.typicode.com/users/";


    private Context context;


    public CoursController(Context context) {
        this.context = context;
    }
    // Méthode pour enregistrer un nouvel étudiant à la fois dans la base de données distante et localement




    public void fetchCours(final CoursController.OnCoursFetchedListener listener, String c) {
        OkHttpClient client = new OkHttpClient();

        String url = BASE_URL; // URL pour récupérer tous les étudiants

        Request request = new Request.Builder()
                .url(url+c+"/")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Log.d("TAG", "reponsecours: " + responseBody);
                    // Convertir responseBody en une liste d'objets Etudiant en utilisant Gson ou tout autre moyen
                    // ...

                    // List<Datum> etudiant = new Etudiant.setData
                    Cours cours = convertResponseBodyToCours(responseBody);

                    if (cours != null) {
                        Log.d("TAG", "body"+cours);
                        listener.onCoursFetched(cours);

                    }
                } else {
                    String errorMessage = response.message();
                    listener.onFetchError(errorMessage);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                String errorMessage = e.getMessage();
                listener.onFetchError(errorMessage);
            }
        });
    }
    private Cours convertResponseBodyToCours(String responseBody) {
        Gson gson = new Gson();

        Cours cours = gson.fromJson(responseBody, Cours.class);
        return cours;
    }

    // Méthode pour récupérer la liste des étudiants depuis la base de données distante
    //public void fetchEtudiants(final OnEtudiantsFetchedListener listener) {
    // Effectuez une requête HTTP à votre API REST pour récupérer les données des étudiants
    // Utilisez des bibliothèques telles que Retrofit ou Volley pour effectuer la requête

    // Exemple d'utilisation de Retrofit :


    // Méthode pour enregistrer un étudiant localement à l'aide des SharedPreferences
    public void saveCoursLocally(Cours etudiant) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefss", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putString("etudiant", etudiant.toJson());
        //editor.apply();
    }

    // Méthode pour récupérer l'étudiant enregistré localement à partir des SharedPreferences
    public Cours getEtudiantLocally() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String etudiantJson = sharedPreferences.getString("cours", null);
        if (etudiantJson != null) {
            try {
                JSONObject jsonObject = new JSONObject(etudiantJson);
                String nom = jsonObject.optString("nom");
                String prenom = jsonObject.optString("prenom");
                String post_nom = jsonObject.optString("post_nom");
                String promotion = jsonObject.optString("promotion");
                // Construisez et retournez l'objet Etudiant
                return new Cours();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Interface pour notifier les événements de récupération des étudiants
    public static interface OnCoursFetchedListener {
        void onCoursFetched(Cours cours);



        void onFetchError(String errorMessage);


    }
    public static interface OnCoursEnregistreListener {
        void onCoursEnregistre();
        void onEnregistrementErreur();
    }

}
