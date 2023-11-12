package cd.hena.note_etudiant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import okhttp3.OkHttpClient;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class NoteActivity extends AppCompatActivity {

    private LinearLayout linearCoursContainer;
    private LinearLayout linearEtudiantContainer;
    private ArrayList<JSONObject> data = new ArrayList<>();
    private String cours;
    okhttp3.Request requesthttpurl;
    private EditText searchEditText;
    List<DataNote> notesList;
    List<DataNote> rengistreNote = new ArrayList();
    private SharedPreferences sharedPreferences;
    OkHttpClient client;
    private EtudiantController etudiantController;
    Etudiant etudiant;
    Datum etudiantes;
    private List<Datum> etudiantsList;
    List<DataCours> coursList;
    private CoursController coursController;
    private NoteController noteController;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        linearCoursContainer = findViewById(R.id.idbutton);
        linearEtudiantContainer = findViewById(R.id.buttonContainer);
        searchEditText = findViewById(R.id.searchEditText);
        searchEditText.setBackgroundResource(R.drawable.background_rectangle);
        coursController = new CoursController(getApplicationContext());
        noteController = new NoteController(getApplicationContext());
        etudiantController = new EtudiantController(getApplicationContext());
        OkHttpClient client = new OkHttpClient();
        drawerLayout = findViewById(R.id.drawerLayout);

        coursController.fetchCours(new CoursController.OnCoursFetchedListener() {


            @Override
            public void onCoursFetched(Cours cours) {
                coursList = cours.getData();

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        for (DataCours cour : coursList) {
                            Log.d("TAG", "variablecours" + cour.getIntitule());
                            String id_cours = String.valueOf(cour.getID());
                            creatLenearCours(cour.getIntitule(), id_cours);

                        }

                    }
                });
            }

            @Override
            public void onFetchError(String errorMessage) {

            }
        }, "1");



    }

    private void etudiantNote(String c) {
        seachEtudiant(c);
        noteController.fetchNotes(new NoteController.OnNotesFetchedListener() {
            @Override
            public void onNotesFetched(Note note) {
                notesList = note.getData();
            }

            @Override
            public void onFetchErrorNotes(String errorMessage) {
                Log.d("TAG", "responsesoppo: " + errorMessage);
            }


        }, c);
        etudiantController.fetchEtudiants(new EtudiantController.OnEtudiantsFetchedListener() {
            @Override
            public void onEtudiantsFetched(Etudiant etudiant) {
                etudiantsList = etudiant.getData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        for (Datum etudiantes : etudiantsList) {
                            Log.d("TAG", "reponselistene: " + notesList);
                            String completNom = etudiantes.getNom() + " " + etudiantes.getPrenom();
                            DataNote noteEtudiant = NoteController.noteEtudiant((int) etudiantes.getID(), notesList);
                            String cote;
                            if (noteEtudiant.getCote() != null && !noteEtudiant.getCote().isEmpty()) {
                                cote = noteEtudiant.getCote();
                            } else {
                                cote = "";
                            }
                            creatLenearEtudiant(completNom, cote,noteEtudiant);
                            Log.d("TAG", "lorie:" + etudiantes.getID());


                        }


                    }
                });


            }

            @Override
            public void onFetchError(String errorMessage) {
                Log.d("TAG", "responses: " + errorMessage);
            }


        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void seachEtudiant(String c) {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Effectuer la recherche à chaque modification du texte
                performSearch(s.toString(), c);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void performSearch(String searchText, String c) {
        linearEtudiantContainer.removeAllViews();
        if (searchText.isEmpty()) {
            TextView textView = new TextView(this);
            textView.setText("Rechercher un etudiant");
            linearEtudiantContainer.addView(textView);
        } else {
            linearEtudiantContainer.removeAllViews();
            // Le texte n'est pas vide, filtrer la liste pour les éléments correspondants à la recherche
            List<Datum> filteredList = new ArrayList<>(); // Initialisation de la liste vide

            for (Datum item : etudiantsList) {
                if (item.getNom().contains(searchText) || item.getPrenom().contains(searchText)) {
                    filteredList.add(item);
                }
            }

            // Créer une nouvelle interface avec les éléments correspondants à la recherche
            for (Datum item : filteredList) {
                // Créez les vues nécessaires pour afficher chaque élément

                DataNote noteEtudiant = NoteController.noteEtudiant((int) item.getID(), notesList);
                String cote;
                if (noteEtudiant.getCote() != null && !noteEtudiant.getCote().isEmpty()) {
                    cote = noteEtudiant.getCote();
                } else {
                    cote = "";
                }
                creatLenearEtudiant(item.getNom() + " " + item.getPrenom(), cote,noteEtudiant);
            }
        }

        Toast.makeText(this, "Recherche : " + searchText, Toast.LENGTH_SHORT).show();
    }


    public void creatLenearCours(String cours, String id_cours) {

        TextView linearCours = new TextView(this);
        linearCours.setText(cours);
        linearCours.setPadding(20, 0, 0, 0);
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                7.0f
        );
        textViewParams.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        linearCours.setLayoutParams(textViewParams);

        linearCours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearEtudiantContainer.removeAllViews();
                etudiantNote(id_cours);


            }
        });


        linearCoursContainer.addView(linearCours);
        // addSeparator();
    }

    public LinearLayout.LayoutParams l_LayutParams(int x, float y) {
        LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(
                x,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                y
        );
        return editTextParams;
    }

    public void  creatLenearEtudiant(String nom, String cote, DataNote note) {


        LinearLayout linearLayoutHorizontal = new LinearLayout(this);
        linearLayoutHorizontal.setOrientation(LinearLayout.HORIZONTAL);


        // Création de la TextView
        TextView textView = new TextView(this);
        textView.setText(nom);
        textView.setPadding(20, 0, 0, 0);
        LinearLayout.LayoutParams textViewParams = l_LayutParams(0, 7);
        textViewParams.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        textView.setLayoutParams(textViewParams);

        // Création de l'EditText
        EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        editText.setText(cote);
        editText.setGravity(Gravity.CENTER);
        editText.setBackgroundResource(0);
        LinearLayout.LayoutParams editTextParams = l_LayutParams(0, 3);
        editTextParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        editText.setLayoutParams(editTextParams);

        // Récupération des valeurs actuelles du TextView et de l'EditText
        String textViewValue = textView.getText().toString();
        String editTextValue = editText.getText().toString();

        // Sauvegarde des valeurs dans les SharedPreferences
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                }
            }
        });
        enregistreNote( editText,note);

        // Ajout de la TextView et de l'EditText au LinearLayout
        linearLayoutHorizontal.addView(textView);
        linearLayoutHorizontal.addView(editText);
        addSeparator();
        linearLayoutHorizontal.setBackgroundResource(R.drawable.banner_ligne);
        //background_rectangle
        // Ajout du LinearLayout horizontal au LinearLayout parent
        linearEtudiantContainer.addView(linearLayoutHorizontal);

    }

    private void addSeparator() {
        View separator = new View(NoteActivity.this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                convertDpToPx(10) // Modifier l'épaisseur de la ligne ici (en pixels)
        );
        separator.setLayoutParams(params);

        linearEtudiantContainer.addView(separator);
    }

    public void borderSeach() {
        // Créer un objet ShapeDrawable avec une bordure
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        //shapeDrawable.getPaint().setColor(Color.BLACK); // Couleur de la bordure
        shapeDrawable.getPaint().setStyle(Paint.Style.STROKE); // Style de la bordure
        shapeDrawable.getPaint().setStrokeWidth(2); // Épaisseur de la bordure en pixels

// Appliquer le drawable avec la bordure à l'EditText

        searchEditText.setBackground(shapeDrawable);
    }

    private int convertDpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_bar) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void enregistreNote(EditText cotation,DataNote note) {
        cotation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Effectuer la recherche à chaque modification du texte



            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString()!=note.getCote()){

                    rengistreNote.add(note);
                    Log.d("TAG", "lorie:pas de modification"+note.getID());
                }
                else{
                    note.setCote(s.toString());
                    rengistreNote.add(note);
                    Log.d("TAG", "lorie: ok" +note);


                }


            }
        });

    }

}