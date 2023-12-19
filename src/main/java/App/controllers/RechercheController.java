package App.controllers;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
// Importez les classes nécessaires
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import App.BD.MongoDB;
import App.BD.RedisFunctions;

// Controller affichant la page d'ajout d'article FrontEnd/NewArticle.html
@Controller
public class RechercheController {

    @GetMapping("/recherche")
    public String searchArticle(Model model) {
        // Implémentez la logique de recherche et récupérez la liste d'articles
        List<Document> articles = MongoDB.getAllArticles(); // Assurez-vous d'implémenter cette méthode dans votre
                                                            // classe MongoDB
                                                            // Ajoutez la liste d'articles au modèle
        System.out.println("Liste des articles : " + articles);
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
        List<String> result = RedisFunctions.getSearchResult(rechercheTerm);
        if (result != null && !result.isEmpty()) {
            List<Document> filteredArticles = new ArrayList<>();
            int length = result.size(); // Récupérez la taille de la liste
            // Parcourez les éléments par groupe de 4
            for (int i = 0; i < length; i += 5) {
                // Créez un nouveau document
                Document document = new Document();

                // Ajoutez les éléments au document avec les clés correspondantes
                document.append("_id", result.get(i));
                document.append("image", result.get(i + 1));
                document.append("nom", result.get(i + 2));
                document.append("description", result.get(i + 3));
                document.append("prix", Double.parseDouble(result.get(i + 4)));

                // Ajoutez le document à la liste
                filteredArticles.add(document);
            }

            // Ajoutez la liste d'articles filtrés au modèle
            model.addAttribute("articles", filteredArticles);

        }
        // Si aucun terme de recherche n'est spécifié, récupère tous les articles
        else if (rechercheTerm == null || rechercheTerm.isEmpty()) {
            return "redirect:/recherche";
        } else {
            // Implémentez la logique de recherche et récupérez la liste d'articles filtrés
            List<Document> filteredArticles = MongoDB.rechercheArticle(rechercheTerm); // Assurez-vous d'implémenter
                                                                                       // cette
                                                                                       // méthode dans votre classe
            for (Document article : filteredArticles) {
                String nomValue = article.getString("nom");
                String descriptionValue = article.getString("description");
                String image = article.getString("image");
                String prixValue = article.getDouble("prix").toString();
                String idValue = article.get("_id").toString();

                RedisFunctions.saveSearchResult(rechercheTerm, nomValue, descriptionValue, prixValue, idValue, image);
            }
            // Ajoutez la liste d'articles filtrés au modèle
            model.addAttribute("articles", filteredArticles);
        }

        return "recherche";
    }

}
