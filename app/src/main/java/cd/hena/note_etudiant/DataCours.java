package cd.hena.note_etudiant;

public class DataCours {
    private long id;
    private String intitule;
    private long ponderation;
    private Professeur professeur;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getIntitule() { return intitule; }
    public void setIntitule(String value) { this.intitule = value; }

    public long getPonderation() { return ponderation; }
    public void setPonderation(long value) { this.ponderation = value; }

    public Professeur getProfesseur() { return professeur; }
    public void setProfesseur(Professeur value) { this.professeur = value; }
}
