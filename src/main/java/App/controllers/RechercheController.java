package App.controllers;

// Importez les classes nécessaires
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Controller affichant la page d'ajout d'article FrontEnd/NewArticle.html
@Controller 
public class RechercheController {

    @GetMapping("/recherche")
    public String recherche(Model model) {
        return "recherche";
    }

    @PostMapping("/recherche")
    public String rechercheSubmit(@ModelAttribute RechercheController recherche, Model model) {
        model.addAttribute("recherche", recherche);
        return "recherche";
    }

}
