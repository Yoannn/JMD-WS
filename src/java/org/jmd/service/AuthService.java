package org.jmd.service;

import java.sql.*;
import java.util.logging.*;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.jmd.SQLUtils;

/**
 * Service web g�rant l'authentification et la d�connexion d'un utilisateur.
 * 
 * @author jordi charpentier - yoann vanhoeserlande
 */
@Path("authentification")
public class AuthService {
    
    private Connection connexion;
 
    public AuthService() {
        
    }

    /**
     * M�thode permettant de connecter un utilisateur (si les identifiants sont
     * bons). 
     * Si les identifiants sont bons, une session est cr��e qui aura comme dur�e
     * de vie celle d�finie dans le web.xml du projet.
     * 
     * @param pseudo Le pseudo de l'utilisateur.
     * @param password Le mot de passe de l'utilisateur.
     * @param request La requ�te HTTP ayant appel�e le service.
     * 
     * @return 2 possibilit�s :
     * - Un code HTTP 200 si les identifiants sont bons.
     * - Un code HTTP 401 si les identifiants n'�taient pas bons.
     */
    @POST
    @Path("login")
    public Response login(  @QueryParam("username")
                            String pseudo,
                            @QueryParam("password")
                            String password,
                            @Context 
                            HttpServletRequest request) {
        
        if (connexion == null) {
            connexion = SQLUtils.getConnexion();
        }
        
        try {
            Statement stmt = connexion.createStatement();  
            ResultSet results = stmt.executeQuery("SELECT * FROM administrateur WHERE (pseudo ='" + pseudo + "') AND (password ='" + password + "')");
            
            while (results.next()) {
                if (results.getString("PSEUDO") != null) {                    
                    request.getSession(true);
                    
                    return Response.status(200).build();
                }
            }
            
            results.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.status(401).build();
    }
    
    /**
     * M�thode permettant de d�connecter un utilisateur.
     * La session actuelle de l'utilisateur est invalid�e (= termin�e).
     * 
     * @param request La requ�te HTTP ayant appel�e le service.
     * 
     * @return Un code HTTP 200 si la session a bien �t� termin�e.
     */
    @Path("logout")
    public Response logout(@Context 
                           HttpServletRequest request) {
        
        request.getSession().invalidate();
        
        return Response.status(200).build();
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
                Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}