package com.dao;

import java.util.List;
import com.dto.Ville;

public interface VilleDAO {

    List<Ville> getListeVilles();

    List<String> getNomsVilles();

    Ville trouverVille(Ville ville);

    Ville trouverVilleParCodePostal(String codePostal);

    Ville trouverVilleParNomCommune(String nomCommune);

    void creerUneVille(Ville ville);

    void supprimerUneVille(String codeInsee);

    void modifierUneVille(Ville ville, String codeInsee);

}
