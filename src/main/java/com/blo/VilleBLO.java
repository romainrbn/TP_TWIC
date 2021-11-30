package com.blo;

import com.dto.Ville;

import java.util.List;

public interface VilleBLO {
    List<Ville> getListeVilles();

    List<String> getListeNomsVilles();

    Ville trouverVille(Ville ville);

    Ville getVilleParCodePostal(String codePostal);

    Ville getVilleParNomCommune(String nomCommune);

    void creerUneVille(Ville ville);

    void supprimerUneVille(String codeInsee);

    void modifierUneVille(Ville ville, String codeInsee);
}
