package com.example.cinenademo.cinema.controller;

import com.example.cinenademo.cinema.model.Cinema;
import com.example.cinenademo.cinema.model.Film;
import com.example.cinenademo.cinema.model.News;
import com.example.cinenademo.cinema.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @Value("${upload.path}")
    private String uploadPath;
    private File uploadDir;

    @GetMapping("/admin-film")
    public String findAll(Model model) {
        List<Film> films = filmService.findAll();
        model.addAttribute("films", films);
        return "Admin/admin-film-list";
    }


    @GetMapping("/admin-film-card-form")
    public String createFilmForm(Model model) {
        model.addAttribute("film", new Film());
        return "Admin/admin-film-card";
    }
//    @PostMapping("/admin-film-card-add")
//    public String createFilm(@ModelAttribute("film") Film film) {
//        filmService.saveFilm(film);
//        return "redirect:/admin-film";
//    }


    @PostMapping("/admin-film-card-add")
    public String createFilm(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("mainpicture") MultipartFile file,
                             @RequestParam("picture1") MultipartFile[] files,
                             @RequestParam("video") String video, @RequestParam("type") String type, @RequestParam("url") String url,
                             @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript, @RequestParam("status") String status) {
        Film film = new Film();
        film.setName(name);

//    загрузка главного фото

        uploadDir = new File(uploadPath + "/img/");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        if (file.getSize() > 0) {
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            film.setMainpicture("img/" + FileNameUuid);
        }

//       загрузка остальных фото

        if (files.length > 0) {
            for (MultipartFile file1 : files) {
                if (file1.getSize() > 0) {
                    String uuid = UUID.randomUUID().toString();
                    String FileNameUuid = uuid + "-" + file1.getOriginalFilename();
                    try {
                        file1.transferTo(new File(uploadDir + "/" + FileNameUuid));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (film.getPicture1() == null) {
                        film.setPicture1("img/" + FileNameUuid);
                    } else if (film.getPicture2() == null) {
                        film.setPicture2("img/" + FileNameUuid);
                    } else if (film.getPicture3() == null) {
                        film.setPicture3("img/" + FileNameUuid);
                    } else if (film.getPicture4() == null) {
                        film.setPicture4("img/" + FileNameUuid);
                    } else if (film.getPicture5() == null) {
                        film.setPicture5("img/" + FileNameUuid);
                    }
                }
            }
        }
        film.setVideo(video);
        film.setUrl(url);
        film.setTitle(title);
        film.setKeywords(keywords);
        film.setDescript(descript);
        System.out.println(status);
        System.out.println(type);
        film.setStatus(status);
        film.setType(type.toString());
        System.out.println("get " + film.getType());
        filmService.saveFilm(film);

        return "redirect:/admin-film";
    }

    @GetMapping("film-delete/{id}")
    public String deleteFilm(@PathVariable("id") Long id) {
        Film film = filmService.findById(id);
        try {
            Files.deleteIfExists(Path.of(uploadPath + film.getMainpicture()));
            Files.deleteIfExists(Path.of(uploadPath + film.getPicture1()));
            Files.deleteIfExists(Path.of(uploadPath + film.getPicture2()));
            Files.deleteIfExists(Path.of(uploadPath + film.getPicture3()));
            Files.deleteIfExists(Path.of(uploadPath + film.getPicture4()));
            Files.deleteIfExists(Path.of(uploadPath + film.getPicture5()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        filmService.deleteById(id);
        return "redirect:/admin-film";
    }

    @GetMapping("/admin-film-card-update/{id}")
    public String updateFilmForm(@PathVariable("id") Long id, Model model) {
        Film film = filmService.findById(id);
        model.addAttribute("film", film);
        return "Admin/admin-film-update-card";
    }

    @PostMapping("/admin-film-card-update-save")
    public String updateFilm(@RequestParam("idfilm") Long idfilm, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("mainpicture") MultipartFile file,
                             @RequestParam("picture1") MultipartFile[] files,
                             @RequestParam("video") String video, @RequestParam("type") String type, @RequestParam("url") String url,
                             @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript, @RequestParam("status") String status) throws IOException {
        Film oldFilm = filmService.findById(idfilm);
        Film film = new Film();
        film.setIdfilm(idfilm);
        film.setName(name);
        film.setDescription(description);

//             сохранение гловного фото

        uploadDir = new File(uploadPath + "/img/");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        if (file.getSize() > 0) {
            Files.deleteIfExists(Paths.get(uploadPath + oldFilm.getMainpicture()));
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            film.setMainpicture("img/" + FileNameUuid);
        } else {
            if (oldFilm.getMainpicture() != null)
                film.setMainpicture(oldFilm.getMainpicture());
        }

//             сохранение остальных фото

        if (files.length > 0) {
            int i = 0;
            for (MultipartFile file1 : files) {
                i++;
                if (file1.getSize() > 0) {
                    String uuid = UUID.randomUUID().toString();
                    String FileNameUuid = uuid + "-" + file1.getOriginalFilename();
                    try {
                        file1.transferTo(new File(uploadDir + "/" + FileNameUuid));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (i == 1) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldFilm.getPicture1()));
                        film.setPicture1("img/" + FileNameUuid);
                    } else if (i == 2) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldFilm.getPicture2()));
                        film.setPicture2("img/" + FileNameUuid);
                    } else if (i == 3) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldFilm.getPicture3()));
                        film.setPicture3("img/" + FileNameUuid);
                    } else if (i == 4) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldFilm.getPicture4()));
                        film.setPicture4("img/" + FileNameUuid);
                    } else if (i == 5) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldFilm.getPicture5()));
                        film.setPicture5("img/" + FileNameUuid);
                    }
                } else {
                    if (i == 1) {
                        if (oldFilm.getPicture1() != null)
                            film.setPicture1(oldFilm.getPicture1());
                    } else if (i == 2) {
                        if (oldFilm.getPicture2() != null)
                            film.setPicture2(oldFilm.getPicture2());
                    } else if (i == 3) {
                        if (oldFilm.getPicture3() != null)
                            film.setPicture3(oldFilm.getPicture3());
                    } else if (i == 4) {
                        if (oldFilm.getPicture4() != null)
                            film.setPicture4(oldFilm.getPicture4());
                    } else if (i == 5) {
                        if (oldFilm.getPicture5() != null)
                            film.setPicture5(oldFilm.getPicture5());
                    }
                }

            }

        }
        film.setVideo(video);
        film.setType(type);
        film.setUrl(url);
        film.setTitle(title);
        film.setKeywords(keywords);
        film.setDescript(descript);
        film.setStatus(status);

        filmService.saveFilm(film);


        return "redirect:/admin-film";
    }
}
