package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blo.VilleBLO;
import com.dto.Ville;

@RestController
public class VilleController {

    @Autowired
    VilleBLO villeBLO;

    @RequestMapping(value = "/villes", method = RequestMethod.GET)
    @ResponseBody
    public List<Ville> appelVilles() {
        System.out.println("liste des villes");
        return villeBLO.getListeVilles();
    }

    @RequestMapping(value = "/villes/noms", method = RequestMethod.GET)
    @ResponseBody
    public List<String> appelNomVilles() {
        System.out.println("liste des noms de villes");
        return villeBLO.getListeNomsVilles();
    }

    @GetMapping(value = "/ville", params = "codeInsee")
    public Ville trouverParInsee(@RequestParam String codeInsee) {
        Ville ville = new Ville(codeInsee);
        System.out.println("ville par code insee");
        System.out.println(codeInsee);
        return villeBLO.trouverVille(ville);
    }

    @GetMapping(value = "/ville", params = "codePostal")
    public List<Ville> trouverParCodePostal(@RequestParam String codePostal) {
        System.out.println("ville par code postal");
        System.out.println(codePostal);
        System.out.print(villeBLO.getVilleParCodePostal(codePostal));
        return villeBLO.getVilleParCodePostal(codePostal);
    }

    @GetMapping(value = "/ville", params = "nomCommune")
    public Ville trouverParNomCommune(@RequestParam String nomCommune) {
        System.out.println("ville par nom de commune");
        System.out.println(nomCommune);
        System.out.print(villeBLO.getVilleParNomCommune(nomCommune));
        return villeBLO.getVilleParNomCommune(nomCommune);
    }

    @PostMapping(value = "/ville")
    public void creerUneVille(@RequestBody Ville Ville) {
        System.out.println("ville créée");
        villeBLO.creerUneVille(Ville);
    }

    @PutMapping(value = "/ville")
    public void modifier(@RequestBody Ville ville, @RequestParam String codeInsee) {
        System.out.println("ville modifiée");
        villeBLO.modifierUneVille(ville, codeInsee);
    }

    @DeleteMapping(value = "/ville")
    public void supprimer(@RequestParam String codeInsee) {
        System.out.println("ville supprimée");
        System.out.println(codeInsee);
        System.out.println(codeInsee);
        villeBLO.supprimerUneVille(codeInsee);
    }
}
