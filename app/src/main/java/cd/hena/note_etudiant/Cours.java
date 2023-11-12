package cd.hena.note_etudiant;// Cours.java


import java.util.List;

public class Cours {
    private List <DataCours> data;

    public List<DataCours> getData() { return data; }
    public void setData(List<DataCours> value) { this.data = value; }
}

// Datum.java


// Professeur.java



class Professeur {
    private long id;
    private String nom;
    private String postNom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
    private Object emailVerifiedAt;
    private String password;
    private Object rememberToken;
    private Object createdAt;
    private Object updatedAt;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getNom() { return nom; }
    public void setNom(String value) { this.nom = value; }

    public String getPostNom() { return postNom; }
    public void setPostNom(String value) { this.postNom = value; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String value) { this.prenom = value; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String value) { this.adresse = value; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String value) { this.telephone = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public Object getEmailVerifiedAt() { return emailVerifiedAt; }
    public void setEmailVerifiedAt(Object value) { this.emailVerifiedAt = value; }

    public String getPassword() { return password; }
    public void setPassword(String value) { this.password = value; }

    public Object getRememberToken() { return rememberToken; }
    public void setRememberToken(Object value) { this.rememberToken = value; }

    public Object getCreatedAt() { return createdAt; }
    public void setCreatedAt(Object value) { this.createdAt = value; }

    public Object getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Object value) { this.updatedAt = value; }
}
