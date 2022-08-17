package cinema.controller;

import cinema.model.Film;
import cinema.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin-film")
public class FilmController {
    @Autowired
    private FilmService filmService;
    @GetMapping
    public String findAll(Model model){
        List<Film> films = filmService.findAll();
        model.addAttribute("films",films);
        return "Admin/admin-film-list";
    }
}
