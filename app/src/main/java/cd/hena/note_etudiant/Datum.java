package cd.hena.note_etudiant;

public class Datum {
    private long id;
    private String nom;
    private String postNom;
    private String prenom;
    private String promotion;

    public long getID() {
        return id;
    }

    public void setID(long value) {
        this.id = value;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String value) {
        this.nom = value;
    }

    public String getPostNom() {
        return postNom;
    }

    public void setPostNom(String value) {
        this.postNom = value;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String value) {
        this.prenom = value;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String value) {
        this.promotion = value;
    }
}
