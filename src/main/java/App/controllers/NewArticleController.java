package App.controllers;

// Importez les classes nécessaires
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Controller affichant la page d'ajout d'article FrontEnd/NewArticle.html
@Controller 
public class NewArticleController {

    @GetMapping("/newArticle")
    public String newArticle(Model model) {
        return "newArticle";
    }

}
