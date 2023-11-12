package cd.hena.note_etudiant;// Note.java


import java.util.List;

public class Note {
    private List<DataNote> data;

    public List<DataNote> getData() { return data; }
    public void setData(List<DataNote> value) { this.data = value; }
}

// DataNote.java



class DataNote {
    private long id;
    private String cote;
    private long etudiant;
    private long cours;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getCote() { return cote; }
    public void setCote(String value) { this.cote = value; }

    public long getEtudiant() { return etudiant; }
    public void setEtudiant(long value) { this.etudiant = value; }

    public long getCours() { return cours; }
    public void setCours(long value) { this.cours = value; }
}
