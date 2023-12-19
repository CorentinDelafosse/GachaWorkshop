package App.controllers;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RechercheDetail {

    @GetMapping("/articleDetails/{articleId}")
    public String afficherDetailsArticle(@PathVariable String articleId, Model model) {
        // Utilisez l'ID de l'article pour récupérer les détails de l'article depuis
        // votre service ou repository
        // Ajoutez l'article au modèle
        model.addAttribute("article", articleService.getArticleById(articleId));

        // Retournez le nom de la page HTML des détails de l'article
        return "articleDetails";
    }
}
