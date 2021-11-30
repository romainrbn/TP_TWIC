package com.dao;

import com.config.JDBCConfiguration;
import com.dto.Ville;
import org.springframework.stereotype.Repository;
import java.util.logging.Level;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class VilleDAOImpl implements VilleDAO {
    private String codeCommuneKey = "Code_commune_INSEE";
    private String nomCommuneKey = "Nom_commune";
    private String ligne5Key = "Ligne_5";
    private String codePostalKey = "Code_postal";
    private String libelleKey = "Libelle_acheminement";
    private String latitudeKey = "Latitude";
    private String longitudeKey = "Longitude";
    private static Logger logger = Logger.getLogger("VilleDAOImpl");

    @Override
    public List<Ville> getListeVilles() {
        ResultSet resultat = null;
        List<Ville> listVilles = new ArrayList<>();

        Connection connexion;
        try {
            connexion = JDBCConfiguration.getConnection();

            try(Statement statement = connexion.createStatement()) {

                resultat = statement.executeQuery("SELECT * FROM ville_france;");
                while (resultat.next()) {
                    Ville v = new Ville(
                            resultat.getString(codeCommuneKey),
                            resultat.getString(nomCommuneKey),
                            resultat.getString(codePostalKey),
                            resultat.getString(libelleKey),
                            resultat.getString(ligne5Key),
                            resultat.getString(latitudeKey),
                            resultat.getString(longitudeKey)
                    );
                    listVilles.add(v);
                }

            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.getLocalizedMessage());
            }

        } catch (DaoException e1) {
            logger.log(Level.SEVERE, e1.getLocalizedMessage());
        }
        return listVilles;
    }

    @Override
    public List<String> getNomsVilles() {
        ResultSet resultat = null;
        List<String> nom = new ArrayList<>();
        Connection connexion;
        try {
            connexion = JDBCConfiguration.getConnection();
            try(Statement statement = connexion.createStatement()) {
                resultat = statement.executeQuery("SELECT Nom_commune FROM ville_france;");
                while (resultat.next()) {
                    nom.add(resultat.getString("Nom_commune"));
                }

            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.getLocalizedMessage());
            }

        } catch (DaoException e1) {
            logger.log(Level.SEVERE, e1.getLocalizedMessage());
        }
        return nom;
    }

    @Override
    public Ville trouverVille(Ville ville) {
        Connection connexion;
        Ville resultVille = null;
        ResultSet resultat = null;
        try {
            connexion = JDBCConfiguration.getConnection();

            try(Statement statement = connexion.createStatement()) {

                resultat = statement.executeQuery(
                        "SELECT * FROM ville_france WHERE Code_commune_INSEE =" + ville.getCodeCommuneINSEE());

                if(resultat.next()) {
                    ville = new Ville(
                            resultat.getString(codeCommuneKey),
                            resultat.getString(nomCommuneKey),
                            resultat.getString(codePostalKey),
                            resultat.getString(libelleKey),
                            resultat.getString(ligne5Key),
                            resultat.getString(latitudeKey),
                            resultat.getString(longitudeKey)
                    );

                    resultVille = ville;
                }

            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.getLocalizedMessage());
            }

        } catch (DaoException e1) {
            logger.log(Level.SEVERE, e1.getLocalizedMessage());
        }
        return resultVille;
    }

    @Override
    public List<Ville> trouverVilleParCodePostal(String codePostal) {
        Connection connexion;
        Ville ville = null;
        ResultSet resultat = null;

        List<Ville> villes = new ArrayList<>();

        try {
            connexion = JDBCConfiguration.getConnection();

            try(Statement statement = connexion.createStatement()) {

                resultat = statement.executeQuery("SELECT * FROM ville_france where Code_Postal =" + codePostal);

                while (resultat.next()) {
                    ville = new Ville(
                            resultat.getString(codeCommuneKey),
                            resultat.getString(nomCommuneKey),
                            resultat.getString(codePostalKey),
                            resultat.getString(libelleKey),
                            resultat.getString(ligne5Key),
                            resultat.getString(latitudeKey),
                            resultat.getString(longitudeKey)
                    );
                    villes.add(ville);
                }

            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.getLocalizedMessage());
            }

        } catch (DaoException e1) {
            logger.log(Level.SEVERE, e1.getLocalizedMessage());
        }
        return villes;
    }

    @Override
    public Ville trouverVilleParNomCommune(String nomCommune) {
        Connection connexion;
        Ville ville = null;
        ResultSet resultat = null;

        try {
            connexion = JDBCConfiguration.getConnection();

            try(Statement statement = connexion.createStatement()) {

                resultat = statement.executeQuery("SELECT * FROM ville_france where Nom_commune ='" + nomCommune+"'");

                while (resultat.next()) {
                    ville = new Ville(
                            resultat.getString(codeCommuneKey),
                            resultat.getString(nomCommuneKey),
                            resultat.getString(codePostalKey),
                            resultat.getString(libelleKey),
                            resultat.getString(ligne5Key),
                            resultat.getString(latitudeKey),
                            resultat.getString(longitudeKey)
                    );
                }

            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.getLocalizedMessage());
            }

        } catch (DaoException e1) {
            logger.log(Level.SEVERE, e1.getLocalizedMessage());
        }
        return ville;
    }

    @Override
    public void creerUneVille(Ville ville) {
        Connection connexion;
        try {
            connexion = JDBCConfiguration.getConnection();

            try(PreparedStatement preparedStatement = connexion
                    .prepareStatement("INSERT INTO ville_france VALUES(?,?,?,?,?,?,?)")) {
                preparedStatement.setString(1, ville.getCodeCommuneINSEE());
                preparedStatement.setString(2, ville.getNomCommune());
                preparedStatement.setString(3, ville.getCodePostal());
                preparedStatement.setString(4, ville.getLibelleAcheminement());
                preparedStatement.setString(5, ville.getLigne5());
                preparedStatement.setString(6, ville.getLatitude());
                preparedStatement.setString(7, ville.getLongitude());
                try {
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    logger.log(Level.SEVERE, e.getLocalizedMessage());
                }
                connexion.commit();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.getLocalizedMessage());
            }
        } catch (DaoException e1) {
            logger.log(Level.SEVERE, e1.getLocalizedMessage());
        }
    }

    @Override
    public void supprimerUneVille(String codeInsee) {
        Connection connexion;
        try {
            connexion = JDBCConfiguration.getConnection();
            try(PreparedStatement preparedStatement = connexion
                    .prepareStatement("DELETE FROM ville_france WHERE code_commune_INSEE = ?")) {

                preparedStatement.setString(1, codeInsee);
                try {
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    logger.log(Level.SEVERE, e.getLocalizedMessage());
                }
                connexion.commit();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.getLocalizedMessage());
            }
        } catch (DaoException e1) {
            logger.log(Level.SEVERE, e1.getLocalizedMessage());
        }
    }

    @Override
    public void modifierUneVille(Ville ville, String codeInsee) {
        Connection connexion;
        try {
            connexion = JDBCConfiguration.getConnection();

            try(PreparedStatement preparedStatement = connexion.prepareStatement(
                    "UPDATE ville_france SET nom_commune = ?, code_postal = ?, libelle_acheminement = ?," +
                            " ligne_5 = ?, latitude = ?, longitude= ? WHERE code_commune_INSEE = ?")) {
                preparedStatement.setString(1, ville.getNomCommune());
                preparedStatement.setString(2, ville.getCodePostal());
                preparedStatement.setString(3, ville.getLibelleAcheminement());
                preparedStatement.setString(4, ville.getLigne5());
                preparedStatement.setString(5, ville.getLatitude());
                preparedStatement.setString(6, ville.getLongitude());
                preparedStatement.setString(7, codeInsee);
                try {
                    preparedStatement.executeUpdate();
                    System.out.println("execute");
                } catch (SQLException e) {
                    logger.log(Level.SEVERE, e.getLocalizedMessage());
                }
                connexion.commit();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.getLocalizedMessage());
            }
        } catch (DaoException e1) {
            logger.log(Level.SEVERE, e1.getLocalizedMessage());
        }
    }
}
