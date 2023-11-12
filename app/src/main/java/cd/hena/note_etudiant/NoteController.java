package cd.hena.note_etudiant;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;


public class NoteController {

    private static final String BASE_URL = "http://192.168.91.74:8000/api/note/getAllNoteforCours/";
    // private static final String BASE_URL = "https://jsonplaceholder.typicode.com/users/";


    private Context context;


    public  NoteController(Context context) {
        this.context = context;
    }

    // Méthode pour enregistrer un nouvel étudiant à la fois dans la base de données distante et localement




    public void fetchNotes(final OnNotesFetchedListener listener,String c) {
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
                    Log.d("TAG", "reponseNote: " + response.body());
                    // Convertir responseBody en une liste d'objets Etudiant en utilisant Gson ou tout autre moyen
                    // ...

                    // List<Datum> etudiant = new Etudiant.setData
                    Note Notes = convertResponseBodyToEtudiants(responseBody);

                    if (Notes != null) {
                        Log.d("TAG", "body"+Notes);
                        listener.onNotesFetched(Notes);

                    }
                } else {
                    String errorMessage = response.message();
                    listener.onFetchErrorNotes(errorMessage);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                String errorMessage = e.getMessage();
                listener.onFetchErrorNotes(errorMessage);
            }
        });
    }
    private Note convertResponseBodyToEtudiants(String responseBody) {
        Gson gson = new Gson();

        Note note = gson.fromJson(responseBody, Note.class);
        return note;
    }

    // Méthode pour récupérer la liste des étudiants depuis la base de données distante
    //public void fetchEtudiants(final OnEtudiantsFetchedListener listener) {
    // Effectuez une requête HTTP à votre API REST pour récupérer les données des étudiants
    // Utilisez des bibliothèques telles que Retrofit ou Volley pour effectuer la requête

    // Exemple d'utilisation de Retrofit :


    // Méthode pour enregistrer un étudiant localement à l'aide des SharedPreferences
    public void saveNoteLocally(Note note, List<DataNote> data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Notes", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putString("etudiant", note.getData());
        //editor.apply();
    }

    // Méthode pour récupérer l'étudiant enregistré localement à partir des SharedPreferences
    public Note getEtudiantLocally() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String etudiantJson = sharedPreferences.getString("etudiant", null);
        if (etudiantJson != null) {
            try {
                JSONObject jsonObject = new JSONObject(etudiantJson);
                String nom = jsonObject.optString("nom");
                String prenom = jsonObject.optString("prenom");
                String post_nom = jsonObject.optString("post_nom");
                String promotion = jsonObject.optString("promotion");
                // Construisez et retournez l'objet Etudiant
                return new Note();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Interface pour notifier les événements de récupération des étudiants
    public static interface OnNotesFetchedListener {
        void onNotesFetched(Note Note);



        void onFetchErrorNotes(String errorMessage);


    }
    public static interface OnEtudiantEnregistreListener {
        void onEtudiantEnregistre();
        void onEnregistrementErreur();
    }
    public static DataNote noteEtudiant (int student_id, List<DataNote> listen ){
        Log.d("TAG", "reponselisten: " + listen);
        for (DataNote notes: listen){
            if (notes.getEtudiant()==student_id){
                return notes;
            }
        }
        return null;
    }
}


