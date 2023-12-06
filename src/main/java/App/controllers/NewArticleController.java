package App.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Importez les classes nécessaires
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import App.BD.MongoDB;

// Controller affichant la page d'ajout d'article FrontEnd/NewArticle.html
@Controller 
public class NewArticleController {

    @GetMapping("/newArticle")
    public String newArticle(Model model) {
        return "newArticle";
    }

    @PostMapping("/newArticle")
    public String newArticleSubmit(@RequestParam("nom") String nom,
                                   @RequestParam("description") String description,
                                   @RequestParam("prix") double prix,
                                   Model model) {

        // Ajouter l'article à la base de données
        MongoDB.addProduct(nom, description, prix);

        // Vous pouvez ajouter ces valeurs au modèle si nécessaire
        model.addAttribute("nom", nom);
        model.addAttribute("description", description);
        model.addAttribute("prix", prix);

        return "newArticle";
    }

    @PostMapping("/newArticleCSV")
    public String newArticleCSV(@RequestParam("file") MultipartFile file, 
                                      Model model) {

        
        // Assurez-vous que le fichier n'est pas vide
        if (file.isEmpty()) {
            System.out.println("iciError");
            // Gérer le cas où le fichier est vide
            return "redirect:/errorPage"; // Redirigez vers une page d'erreur appropriée
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            List<List<String>> records = new ArrayList<>();
            String ligne;
            while((ligne = br.readLine()) != null) {
                String[] values = ligne.split(";");
                records.add(Arrays.asList(values));
            }

            System.out.println("ici");
            for (List<String> record : records) {
                String nomProduit = record.get(0);
                String descriptionProduit = record.get(1);
                double prixProduit = Double.parseDouble(record.get(2));

                System.out.println("nomProduit : " + nomProduit);
                System.out.println("descriptionProduit : " + descriptionProduit);
                System.out.println("prixProduit : " + prixProduit);

                MongoDB.addProduct(nomProduit, descriptionProduit, prixProduit);
            }

            return "newArticle";
        } catch (IOException e) {
            // Gérer les exceptions liées à la lecture du fichier
            e.printStackTrace(); // Vous pouvez également rediriger vers une page d'erreur appropriée
            return "redirect:/errorPage";
        }
    }

}
