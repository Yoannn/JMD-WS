package org.jmd.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.*;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.jmd.SQLUtils;
import org.jmd.metier.Matiere;

/**
 * Service web g�rant les mati�res (cr�ation / suppression / ...).
 * 
 * @author jordi charpentier - yoann vanhoeserlande
 */
@Path("matiere")
public class MatiereService {
    
    private Connection connexion;
 
    public MatiereService() {
        
    }

    /**
     * M�thode permettant de cr�er une mati�re.
     * 
     * @param nom Le nom de la mati�re � cr�er.
     * @param coefficient Le coefficient de la mati�re � cr�er.
     * @param isOption Si la mati�re � cr�er est une option ou non.
     * @param request La requ�te HTTP ayant appel�e le service.
     * 
     * @return 2 possibilit�s :
     * - Un code HTTP 200 si l'utilisateur ayant fait la demande de cr�ation est
     * connect� (donc autoris�).
     * - Un code HTTP 401 si c'est un utilisateur non connect� (donc non autoris�)
     * qui a fait la demande.
     */
    @GET
    @Path("creer")
    public Response creer(@QueryParam("nom")
                          String nom,
                          @QueryParam("coefficient")
                          float coefficient,
                          @QueryParam("isOption")
                          boolean isOption,
                          @Context 
                          HttpServletRequest request) {
        
        if (request.getSession(false) != null) {
            if (connexion == null) {
                connexion = SQLUtils.getConnexion();
            }

            try {
                Statement stmt = connexion.createStatement();  
                stmt.execute("INSERT INTO MATIERE (nom, coefficient, isOption) VALUES ('" + nom + "', " + coefficient + ", " + isOption + ")");
                stmt.close();    
            } catch (SQLException ex) {
                Logger.getLogger(MatiereService.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return Response.status(200).build();
        } else {
            return Response.status(401).build();
        }
    }
    
    /**
     * M�thode permettant de supprimer une mati�re.
     * 
     * @param id L'identifiant de la mati�re � supprimer.
     * @param request La requ�te HTTP ayant appel�e le service.
     * 
     * @return 2 possibilit�s :
     * - Un code HTTP 200 si l'utilisateur ayant fait la demande de supprim� est
     * connect� (donc autoris�).
     * - Un code HTTP 401 si c'est un utilisateur non connect� (donc non autoris�)
     * qui a fait la demande.
     */
    @DELETE
    @Path("{id}")
    public Response supprimer(@QueryParam("id")
                              String id,
                              @Context 
                              HttpServletRequest request) {
        
        if (request.getSession(false) != null) {
            if (connexion == null) {
                connexion = SQLUtils.getConnexion();
            }

            try {
                try (Statement stmt = connexion.createStatement()) {
                    stmt.executeUpdate("DELETE FROM MATIERE WHERE (ID = " + id + ")");
                }    
            } catch (SQLException ex) {
                Logger.getLogger(MatiereService.class.getName()).log(Level.SEVERE, null, ex);
                
                return Response.status(500).build();
            }
            
            return Response.status(200).build();
        } else {
            return Response.status(401).build();
        }
    }
    
    @GET
    @Path("getAllMatieretOfUE/{idUE}")
    @Produces("application/json")
    public ArrayList<Matiere> getAllMatieretOfUE(@PathParam("idUE") 
                                                 int idUE) {
        
        ArrayList<Matiere> matieres = new ArrayList<>();
                
        if (connexion == null) {
            connexion = SQLUtils.getConnexion();
        }
        
        try {
            Statement stmt = connexion.createStatement();
            ResultSet results = stmt.executeQuery("SELECT MATIERE.ID, MATIERE.NOM, MATIERE.COEFFICIENT, MATIERE.IS_OPTION " +
                                                  "FROM MATIERE, UE " +
                                                  "WHERE (UE.ID = " + idUE + ") AND (MATIERE.ID_UE = UE.ID)");
            
            Matiere m = null;
            
            while (results.next()) {
                m = new Matiere();
                m.setIdMatiere(results.getInt("ID"));
                m.setNom(results.getString("NOM"));
                m.setCoefficient(results.getFloat("COEFFICIENT"));
                m.setIsOption(results.getBoolean("IS_OPTION"));
                
                matieres.add(m);
            }

            results.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MatiereService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return matieres;
    }
    
    @GET
    @Path("getAllMatiereOfYear/{idAnnee}")
    @Produces("application/json")
    public ArrayList<Matiere> getAllMatiereOfYear(@PathParam("idAnnee") 
                                                  int idAnnee) {
        
        ArrayList<Matiere> matieres = new ArrayList<>();
                
        if (connexion == null) {
            connexion = SQLUtils.getConnexion();
        }
        
        try {
            Statement stmt = connexion.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * " +
                                                  "FROM MATIERE, UE_MAT, UE, ANN_UE, ANNEE " +
                                                  "WHERE (UE.ID = UE_MAT.ID_UE) AND (MATIERE.ID = UE_MAT.ID_MAT) AND (ANN_UE.ID_ANN = ANNEE.ID) AND (ANN_UE.ID_UE = UE.ID) AND (ANNEE.ID = " + idAnnee + ")");
            
            Matiere m = null;
            
            while (results.next()) {
                m = new Matiere();
                m.setIdMatiere(results.getInt("ID"));
                m.setNom(results.getString("NOM"));
                m.setCoefficient(results.getFloat("COEFFICIENT"));
                m.setIsOption(results.getBoolean("IS_OPTION"));
                
                matieres.add(m);
            }

            results.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MatiereService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return matieres;
    }
    
    /**
     * M�thode ex�cut�e avant la fin de vie du service.
     * La connexion � la base est ferm�e.
     */
    @PreDestroy
    public void onDestroy() {
        if (connexion != null) {
            try {
                connexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(MatiereService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}