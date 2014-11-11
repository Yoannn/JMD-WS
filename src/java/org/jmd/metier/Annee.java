package org.jmd.metier;

import java.util.ArrayList;
import javax.xml.bind.annotation.*;

/**
 * Classe représentant une année.
 * 
 * @author jordi charpentier - yoann vanhoeserlande
 */
@XmlRootElement
public class Annee {
    
    // En base. 
    
    /**
     * L'identifiant de l'année.
     */
    private int idAnnee;
    
    /**
     * Le nom de l'année.
     */
    @XmlElement(name="nom")
    private String nom;
    
    /**
     * Le découpage de l'année (UE/TRIMESTRE/NULL).
     */
    @XmlElement(name="decoupage")
    private String decoupage;
    
    /**
     * Booléen permettant de savoir si l'année est la dernière du diplôme.
     */
    private boolean isLastYear;
    
    /**
     * L'identifiant de l'établissement de l'année.
     */
    private int idEtablissement;
    
    /**
     * L'identifiant du diplôme dont fait partie l'année.
     */
    private int idDiplome;
    
    @XmlElement(name="isFollowed")
    private boolean isFollowed;
    
    /**
     * La liste des UE de l'année.
     */
    @XmlElement(name="ues")
    private ArrayList<UE> listeUEs;
    
    /**
     * La liste des règmes de gestion de l'année.
     */
    @XmlElement(name="regles")
    private ArrayList<Regle> listeRegles = new ArrayList();
    
    // Non en base.
    
    /**
     * Le nom de l'établissement où l'année est rattachée.
     */
    private String nomEtablissement;
    
    /**
     * Le nom du diplôme lié où l'année est rattachée.
     */
    private String nomDiplome;

    /**
     * L'établissement où l'année est rattachée.
     */
    private Etablissement eta;
    
    /**
     * Constructeur par défaut de la classe.
     */
    public Annee () {
    
    }
    
    /**
     * Méthode permettant d'ajouter une UE à l'année.
     * 
     * @param ue L'UE à ajouter à l'année.
     * 
     * @return <b>true</b> si l'UE a bien été ajoutée.
     * <b>false</b> sinon.
     */
    public boolean addUE(UE ue) {
        if (this.listeUEs == null) {
            this.listeUEs = new ArrayList<>();
        }
        
        return this.listeUEs.add(ue);
    } 
    
    /* Getters. */
    
    // En base.
    
    /**
     * Méthode retournant l'identifiant de l'année.
     * 
     * @return L'identifiant de l'année.
     */
    public int getIdAnnee() {
        return idAnnee;
    }
    
    /**
     * Méthode retournant le nom de l'année.
     * 
     * @return Le nom de l'année.
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Méthode retournant le découpage (SEMESTRE / NULL / TRIMESTRE) de l'année.
     * 
     * @return Le découpage de l'année.
     */
    public String getDecoupage() {
        return decoupage;
    }
    
    public boolean isIsLastYear() {
        return isLastYear;
    }
    
    /**
     * Méthode retournant l'identifiant de l'établissement où l'année est rattachée.
     * 
     * @return L'identifiant de l'établissement où l'année est rattachée.
     */
    public int getIdEtablissement() {
        return idEtablissement;
    }
    
    /**
     * Méthode retournant l'identifiant du diplôme où l'année est rattachée.
     * 
     * @return L'identifiant du diplôme où l'année est rattachée.
     */
    public int getIdDiplome() {
        return idDiplome;
    }
    
    public boolean isFollowed() {
        return isFollowed;
    }
    
    /**
     * Méthode retournant la liste des règles de gestion de l'année.
     * 
     * @return La liste des règles de gestion de l'année.
     */
    public ArrayList<Regle> getListeRegles() {
        return this.listeRegles;
    }
    
    /**
     * Méthode retournant la liste des UE de l'année.
     * 
     * @return La liste des UE de l'année.
     */
    public ArrayList<UE> getListeUE() {
        return this.listeUEs;
    }
    
    // Non en base.
    
    public String getNomEtablissement() {
        return nomEtablissement;
    }

    public String getNomDiplome() {
        return nomDiplome;
    }
    
    public Etablissement getEtablissement() {
        return eta;
    }
    
    /* Setters. */

    // En base.
    
    /**
     * Méthode permettant de modifier l'identifiant de l'année.
     * 
     * @param idAnnee Le nouvel identifiant de l'année.
     */
    public void setIdAnnee(int idAnnee) {
        this.idAnnee = idAnnee;
    }

    /**
     * Méthode permettant de modifier le nom de l'année.
     * 
     * @param nom Le nouveau nom de l'année.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }    

    public void setDecoupage(String decoupage) {
        this.decoupage = decoupage;
    }

    public void setIsLastYear(boolean isLastYear) {
        this.isLastYear = isLastYear;
    }

    public void setIdEtablissement(int idEtablissement) {
        this.idEtablissement = idEtablissement;
    }

    public void setIdDiplome(int idDiplome) {
        this.idDiplome = idDiplome;
    }
    
    public void setListeRegles(ArrayList<Regle> listeRegles) {
        this.listeRegles = listeRegles;
    }
    
    public void setIsFollowed(boolean isFollowed) {
        this.isFollowed = isFollowed;
    }
    
    public void setListeUE(ArrayList<UE> listeUE) {
        this.listeUEs = listeUE;
    }

    // Non en base.
    
    public void setNomEtablissement(String nomEtablissement) {
        this.nomEtablissement = nomEtablissement;
    }

    public void setNomDiplome(String nomDiplome) {
        this.nomDiplome = nomDiplome;
    }    
    
    public void setEtablissement(Etablissement eta) {
        this.eta = eta;
    }
}