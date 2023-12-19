package App.controllers;

import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import App.BD.MongoDB;

@Controller
public class RechercheDetail {

    @GetMapping("/articleDetails/{articleId}")
    public String afficherDetailsArticle(@PathVariable String articleId, Model model) {
        Document articleService = MongoDB.getArticleById(articleId);
        model.addAttribute("article", articleService);

        return "RechercheDetail";
    }
}
