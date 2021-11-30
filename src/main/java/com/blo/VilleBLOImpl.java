package com.blo;

import com.dao.VilleDAO;
import com.dto.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleBLOImpl implements VilleBLO {

    @Autowired
    private VilleDAO villeDAO;

    @Override
    public List<Ville> getListeVilles() {
        return villeDAO.getListeVilles();
    }

    @Override
    public List<String> getListeNomsVilles() {
        return villeDAO.getNomsVilles();
    }

    @Override
    public Ville trouverVille(Ville ville) {
        return villeDAO.trouverVille(ville);
    }

    @Override
    public Ville getVilleParCodePostal(String codePostal) {
        return villeDAO.trouverVilleParCodePostal(codePostal);
    }

    @Override
    public Ville getVilleParNomCommune(String nomCommune) {
        return villeDAO.trouverVilleParNomCommune(nomCommune);
    }

    @Override
    public void creerUneVille(Ville ville) {
        villeDAO.creerUneVille(ville);
    }

    @Override
    public void supprimerUneVille(String codeInsee) {
        villeDAO.supprimerUneVille(codeInsee);
    }

    @Override
    public void modifierUneVille(Ville ville, String codeInsee) {
        villeDAO.modifierUneVille(ville, codeInsee);
    }
}
