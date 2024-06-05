package application.model;


import java.util.Date;

public class Promotion {
    private int idPromo;
    private Date dateDebut;
    private Date dateFin;
    private float pourcentageReduction;
    private float prixOriginal;
    private String codePromo;
    private int idPlat;
    private String nomPlat;
    private float NouvPrix;


    

	public Promotion(Integer idPromo, Date dateDebut, Date dateFin, Float pourcentageReduction, String codePromo, Float NouvPrix) {
        this.idPromo = idPromo;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.pourcentageReduction = pourcentageReduction;
        this.codePromo = codePromo;
        this.NouvPrix = NouvPrix;
    }

    public Promotion(){}

    // Getters and setters
    
    public float getNouvPrix() {
		return NouvPrix;
	}

	public void setNouvPrix(float nouvPrix) {
		NouvPrix = nouvPrix;
	}
	
    public int getIdPromo() {
        return idPromo;
    }

    public void setIdPromo(int idPromo) {
        this.idPromo = idPromo;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public float getPourcentageReduction() {
        return pourcentageReduction;
    }

    public void setPourcentageReduction(float pourcentageReduction) {
        this.pourcentageReduction = pourcentageReduction;
    }

    public float getPrixOriginal() {
        return prixOriginal;
    }

    public void setPrixOriginal(float prixOriginal) {
        this.prixOriginal = prixOriginal;
    }

    public String getCodePromo() {
        return codePromo;
    }

    public void setCodePromo(String codePromo) {
        this.codePromo = codePromo;
    }

    public int getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(int idPlat) {
        this.idPlat = idPlat;
    }


    public void setNomPlat(String nomPlat) {
        this.nomPlat = nomPlat;
    }

    public String getNomPlat() {
        return nomPlat;
    }
    
    
    
}