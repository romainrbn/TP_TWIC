package com.dao;

import com.config.JDBCConfiguration;
import com.dto.Ville;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VilleDAOImpl implements VilleDAO {
    @Override
    public List<Ville> getListeVilles() {
        Statement statement = null;
        ResultSet resultat = null;
        List<Ville> listVilles = new ArrayList<>();

        Connection connexion;
        try {
            connexion = JDBCConfiguration.getConnection();

            try {
                statement = connexion.createStatement();
                resultat = statement.executeQuery("SELECT * FROM ville_france;");
                while (resultat.next()) {
                    Ville v = new Ville(
                            resultat.getString("Code_commune_INSEE"),
                            resultat.getString("Nom_commune"),
                            resultat.getString("Code_postal"),
                            resultat.getString("Libelle_acheminement"),
                            resultat.getString("Ligne_5"),
                            resultat.getString("Latitude"),
                            resultat.getString("Longitude")
                    );
                    listVilles.add(v);
                }

            } catch (SQLException e) {
                System.err.println(e.getLocalizedMessage());
            }

        } catch (DaoException e1) {
            System.err.println(e1.getLocalizedMessage());
        }
        return listVilles;
    }

    @Override
    public List<String> getNomsVilles() {
        Statement statement = null;
        ResultSet resultat = null;
        List<String> nom = new ArrayList<>();

        Connection connexion;
        try {
            connexion = JDBCConfiguration.getConnection();

            try {
                statement = connexion.createStatement();
                resultat = statement.executeQuery("SELECT Nom_commune FROM ville_france;");
                while (resultat.next()) {
                    nom.add(resultat.getString("Nom_commune"));
                }

            } catch (SQLException e) {
                System.err.println(e.getLocalizedMessage());
            }

        } catch (DaoException e1) {
            System.err.println(e1.getLocalizedMessage());
        }
        return nom;
    }

    @Override
    public Ville trouverVille(Ville ville) {
        Connection connexion;
        Ville resultVille = null;
        Statement statement = null;
        ResultSet resultat = null;
        try {
            connexion = JDBCConfiguration.getConnection();

            try {

                statement = connexion.createStatement();
                resultat = statement.executeQuery(
                        "SELECT * FROM ville_france WHERE Code_commune_INSEE =" + ville.getCodeCommuneINSEE());

                if(resultat.next()) {
                    ville = new Ville(
                            resultat.getString("Code_commune_INSEE"),
                            resultat.getString("Nom_Commune"),
                            resultat.getString("Code_postal"),
                            resultat.getString("Libelle_acheminement"),
                            resultat.getString("Ligne_5"),
                            resultat.getString("Latitude"),
                            resultat.getString("Longitude")
                    );

                    resultVille = ville;
                }

            } catch (SQLException e) {
                System.err.println(e.getLocalizedMessage());
            }

        } catch (DaoException e1) {
            System.err.println(e1.getLocalizedMessage());
        }
        return resultVille;
    }

    @Override
    public List<Ville> trouverVilleParCodePostal(String codePostal) {
        Connection connexion;
        Ville ville = null;
        Statement statement = null;
        ResultSet resultat = null;

        List<Ville> villes = new ArrayList<>();

        try {
            connexion = JDBCConfiguration.getConnection();

            try {

                statement = connexion.createStatement();
                resultat = statement.executeQuery("SELECT * FROM ville_france where Code_Postal =" + codePostal);

                while (resultat.next()) {
                    ville = new Ville(
                            resultat.getString("Code_commune_INSEE"),
                            resultat.getString("Nom_Commune"),
                            resultat.getString("Code_postal"),
                            resultat.getString("Libelle_acheminement"),
                            resultat.getString("Ligne_5"),
                            resultat.getString("Latitude"),
                            resultat.getString("Longitude")
                    );
                    villes.add(ville);
                }

            } catch (SQLException e) {
                System.err.println(e.getLocalizedMessage());
            }

        } catch (DaoException e1) {
            System.err.println(e1.getLocalizedMessage());
        }
        return villes;
    }

    @Override
    public Ville trouverVilleParNomCommune(String nomCommune) {
        Connection connexion;
        Ville ville = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = JDBCConfiguration.getConnection();

            try {

                statement = connexion.createStatement();
                resultat = statement.executeQuery("SELECT * FROM ville_france where Nom_commune ='" + nomCommune+"'");

                while (resultat.next()) {
                    ville = new Ville(
                            resultat.getString("Code_commune_INSEE"),
                            resultat.getString("Nom_Commune"),
                            resultat.getString("Code_postal"),
                            resultat.getString("Libelle_acheminement"),
                            resultat.getString("Ligne_5"),
                            resultat.getString("Latitude"),
                            resultat.getString("Longitude")
                    );
                }

            } catch (SQLException e) {
                System.err.println(e.getLocalizedMessage());
            }

        } catch (DaoException e1) {
            System.err.println(e1.getLocalizedMessage());
        }
        return ville;
    }

    @Override
    public void creerUneVille(Ville ville) {
        Connection connexion;
        try {
            connexion = JDBCConfiguration.getConnection();

            try {
                PreparedStatement preparedStatement = connexion
                        .prepareStatement("INSERT INTO ville_france VALUES(?,?,?,?,?,?,?)");
                preparedStatement.setString(1, ville.getCodeCommuneINSEE());
                preparedStatement.setString(2, ville.getNomCommune());
                preparedStatement.setString(3, ville.getCodePostal());
                preparedStatement.setString(4, ville.getLibelleAcheminement());
                preparedStatement.setString(5, ville.getLigne5());
                preparedStatement.setString(6, ville.getLatitude());
                preparedStatement.setString(7, ville.getLongitude());
                try {
                    preparedStatement.executeUpdate();
                    System.out.println("execute");
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.err.println(e.getLocalizedMessage());
                }
                connexion.commit();
            } catch (SQLException e) {
                System.err.println(e.getLocalizedMessage());
            }
        } catch (DaoException e1) {
            System.err.println(e1.getLocalizedMessage());
        }
    }

    @Override
    public void supprimerUneVille(String codeInsee) {
        Connection connexion;
        try {
            connexion = JDBCConfiguration.getConnection();
            try {
                PreparedStatement preparedStatement = connexion
                        .prepareStatement("DELETE FROM ville_france WHERE code_commune_INSEE = ?");
                preparedStatement.setString(1, codeInsee);
                try {
                    preparedStatement.executeUpdate();
                    System.out.println("execute");
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.err.println(e.getLocalizedMessage());
                }
                connexion.commit();
            } catch (SQLException e) {
                System.err.println(e.getLocalizedMessage());
            }
        } catch (DaoException e1) {
            System.err.println(e1.getLocalizedMessage());
        }
    }

    @Override
    public void modifierUneVille(Ville ville, String codeInsee) {
        Connection connexion;
        try {
            connexion = JDBCConfiguration.getConnection();

            try {
                PreparedStatement preparedStatement = connexion.prepareStatement(
                        "UPDATE ville_france SET nom_commune = ?, code_postal = ?, libelle_acheminement = ?," +
                                " ligne_5 = ?, latitude = ?, longitude= ? WHERE code_commune_INSEE = ?");
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
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.err.println(e.getLocalizedMessage());
                }
                connexion.commit();
            } catch (SQLException e) {
                System.err.println(e.getLocalizedMessage());
            }
        } catch (DaoException e1) {
            System.err.println(e1.getLocalizedMessage());
        }
    }
}
