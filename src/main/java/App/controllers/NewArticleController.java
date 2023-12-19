package App.controllers;

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
            @RequestParam("image") MultipartFile image,
            Model model) {
        // Ajoutez votre logique pour traiter les détails de l'article du formulaire
        // Utilisez les valeurs de titre et contenu récupérées du formulaire
        MongoDB.addProduct(nom, description, prix, image);

        // Vous pouvez ajouter ces valeurs au modèle si nécessaire
        model.addAttribute("nom", nom);
        model.addAttribute("description", description);
        model.addAttribute("prix", prix);
        model.addAttribute("image", image);

        return "newArticle";
    }

}
