package App.controllers;

import java.text.DecimalFormat;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
// Importez les classes nécessaires
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import App.BD.MongoDB;

// Controller affichant la page d'ajout d'article FrontEnd/NewArticle.html
@Controller 
public class RechercheController {

    @GetMapping("/recherche")
    public String searchArticle(Model model) {
        // Implémentez la logique de recherche et récupérez la liste d'articles
        List<Document> articles = MongoDB.getAllArticles(); // Assurez-vous d'implémenter cette méthode dans votre classe MongoDB

        //foreach articles pour changer prix en 2 décimal
        for (Document article : articles) {
            double prix = article.getDouble("prix");
            
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String resultatFormate = decimalFormat.format(prix);

            article.put("prix", resultatFormate);
        }

        // Ajoutez la liste d'articles au modèle
        model.addAttribute("articles", articles);

        return "recherche";
    }

    @PostMapping("/recherche")
    public String deleteArticle(@RequestParam("articleId") String articleId) {
        // Implémentez la logique de suppression de l'article

        System.out.println("Article à supprimer : " + articleId);
        // le meme print mais en ObjectId de mongoDB
        System.out.println("Article à supprimer : " + new ObjectId(articleId));
        MongoDB.deleteArticle(articleId); // Assurez-vous d'implémenter cette méthode dans votre classe MongoDB

        // Redirigez vers la page de recherche d'articles après la suppression
        return "redirect:/recherche";
    }


    @GetMapping("/rechercheArticle")
    public String rechercheArticle(@RequestParam(name = "rechercheTerm", required = false) String rechercheTerm,
                                Model model) {
        // Si aucun terme de recherche n'est spécifié, récupérez tous les articles
        if (rechercheTerm == null || rechercheTerm.isEmpty()) {
            return "redirect:/recherche";
        }

        // Implémentez la logique de recherche et récupérez la liste d'articles filtrés
        List<Document> filteredArticles = MongoDB.rechercheArticle(rechercheTerm); // Assurez-vous d'implémenter cette méthode dans votre classe MongoDB

        //foreach articles pour changer prix en 2 décimal
        for (Document article : filteredArticles) {
            double prix = article.getDouble("prix");
            
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String resultatFormate = decimalFormat.format(prix);

            article.put("prix", resultatFormate);
        }

        // Ajoutez la liste d'articles filtrés au modèle
        model.addAttribute("articles", filteredArticles);

        return "recherche";
    }

}
