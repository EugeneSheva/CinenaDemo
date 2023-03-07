package com.example.cinenademo.cinema.controller;

import com.example.cinenademo.cinema.model.Cinema;
import com.example.cinenademo.cinema.model.Film;
import com.example.cinenademo.cinema.model.Hall;
import com.example.cinenademo.cinema.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
public class CinemaController {


    private final CinemaService cinemaService;

    @Autowired
    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Value("${upload.path}")
    private String uploadPath;
    private File uploadDir;
    public static Long cinemaId;

    @GetMapping("/admin-cinema")
    public String findAll(Model model) {
        List<Cinema> cinema = cinemaService.findAll();
        model.addAttribute("cinema", cinema);
        return "Admin/admin-cinema-list";
    }


    @GetMapping("/admin-cinema-card-form")
    public String createCinemaForm(Model model) {
        model.addAttribute("cinema", new Cinema());
        return "Admin/admin-cinema-card";
    }

    // метод сохранения кинотеатра по кнопке сохранить
    @PostMapping("/admin-cinema-card-add")
    public String createFilm(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("cinemacondition") String cinemacondition, @RequestParam("mainpicture") MultipartFile file,
                             @RequestParam("bunner") MultipartFile file2, @RequestParam("picture1") MultipartFile[] files, @RequestParam("url") String url,
                             @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript) {
        Cinema cinema = new Cinema();
        cinema.setName(name);
        cinema.setDescription(description);
        cinema.setCinemacondition(cinemacondition);

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

            cinema.setMainpicture("img/" + FileNameUuid);
        }

        //    загрузка баннера

        if (file2.getSize() > 0) {
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file2.getOriginalFilename();
            try {
                file2.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            cinema.setBunner("img/" + FileNameUuid);
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
                    if (cinema.getPicture1() == null) {
                        cinema.setPicture1("img/" + FileNameUuid);
                    } else if (cinema.getPicture2() == null) {
                        cinema.setPicture2("img/" + FileNameUuid);
                    } else if (cinema.getPicture3() == null) {
                        cinema.setPicture3("img/" + FileNameUuid);
                    } else if (cinema.getPicture4() == null) {
                        cinema.setPicture4("img/" + FileNameUuid);
                    } else if (cinema.getPicture5() == null) {
                        cinema.setPicture5("img/" + FileNameUuid);
                    }
                }
            }
        }
        cinema.setUrl(url);
        cinema.setTitle(title);
        cinema.setKeywords(keywords);
        cinema.setDescript(descript);
        cinemaService.save(cinema);
        cinemaId = cinema.getIdcinema();
        return "redirect:/admin-cinema";
    }

    // метод сохранения кинотеатра по кнопке создать зал
    @PostMapping("/admin-cinema-card-add2")
    public String createFilm2(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("cinemacondition") String cinemacondition, @RequestParam("mainpicture") MultipartFile file,
                              @RequestParam("bunner") MultipartFile file2, @RequestParam("picture1") MultipartFile[] files, @RequestParam("url") String url,
                              @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript) {
        Cinema cinema = new Cinema();
        cinema.setName(name);
        cinema.setDescription(description);
        cinema.setCinemacondition(cinemacondition);

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

            cinema.setMainpicture("img/" + FileNameUuid);
        }

        //    загрузка баннера

        if (file2.getSize() > 0) {
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file2.getOriginalFilename();
            try {
                file2.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            cinema.setBunner("img/" + FileNameUuid);
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
                    if (cinema.getPicture1() == null) {
                        cinema.setPicture1("img/" + FileNameUuid);
                    } else if (cinema.getPicture2() == null) {
                        cinema.setPicture2("img/" + FileNameUuid);
                    } else if (cinema.getPicture3() == null) {
                        cinema.setPicture3("img/" + FileNameUuid);
                    } else if (cinema.getPicture4() == null) {
                        cinema.setPicture4("img/" + FileNameUuid);
                    } else if (cinema.getPicture5() == null) {
                        cinema.setPicture5("img/" + FileNameUuid);
                    }
                }
            }
        }
        cinema.setUrl(url);
        cinema.setTitle(title);
        cinema.setKeywords(keywords);
        cinema.setDescript(descript);
        cinemaService.save(cinema);
        cinemaId = cinema.getIdcinema();
        return "redirect:/admin-hall-card-form";
    }

    @GetMapping("cinema-delete/{id}")
    public String deleteCinema(@PathVariable("id") Long id) {
        Cinema cinema = cinemaService.findById(id);
        List<Hall> halls = cinema.getHalls();
        for (Hall hall : halls) {
            try {
                Files.deleteIfExists(Path.of(uploadPath + hall.getHallschema()));
                Files.deleteIfExists(Path.of(uploadPath + hall.getBunner()));
                Files.deleteIfExists(Path.of(uploadPath + hall.getPicture1()));
                Files.deleteIfExists(Path.of(uploadPath + hall.getPicture2()));
                Files.deleteIfExists(Path.of(uploadPath + hall.getPicture3()));
                Files.deleteIfExists(Path.of(uploadPath + hall.getPicture4()));
                Files.deleteIfExists(Path.of(uploadPath + hall.getPicture5()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Files.deleteIfExists(Path.of(uploadPath + cinema.getMainpicture()));
            Files.deleteIfExists(Path.of(uploadPath + cinema.getBunner()));
            Files.deleteIfExists(Path.of(uploadPath + cinema.getPicture1()));
            Files.deleteIfExists(Path.of(uploadPath + cinema.getPicture2()));
            Files.deleteIfExists(Path.of(uploadPath + cinema.getPicture3()));
            Files.deleteIfExists(Path.of(uploadPath + cinema.getPicture4()));
            Files.deleteIfExists(Path.of(uploadPath + cinema.getPicture5()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cinemaService.deleteById(id);
        return "redirect:/admin-cinema";
    }

    @GetMapping("/admin-cinema-card-update/{id}")
    public String updateCinemaForm(@PathVariable("id") Long id, Model model) {
        Cinema cinema = cinemaService.findById(id);
        cinemaId = id;
        model.addAttribute("cinema", cinema);
        return "Admin/admin-cinema-update-card";
    }

    // метод сохранения изменений кинотеатра по кнопке сохранить
    @PostMapping("/admin-cinema-card-update-save")
    public String updateCinema(@RequestParam("idcinema") Long idcinema, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("cinemacondition") String cinemacondition, @RequestParam("mainpicture") MultipartFile file,
                               @RequestParam("bunner") MultipartFile file2, @RequestParam("picture1") MultipartFile[] files, @RequestParam("url") String url,
                               @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript) throws IOException {
        Cinema cinema = new Cinema();
        cinema.setIdcinema(idcinema);
        cinema.setName(name);
        cinema.setDescription(description);
        cinema.setCinemacondition(cinemacondition);
        //            сохранение главного фото

        Cinema oldcinema = cinemaService.findById(idcinema);
        uploadDir = new File(uploadPath + "/img/");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        if (file.getSize() > 0) {
            Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getMainpicture()));
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            cinema.setMainpicture("img/" + FileNameUuid);
        } else {
            if (oldcinema.getMainpicture() != null)
                cinema.setMainpicture(oldcinema.getMainpicture());
        }

        //            сохранение баннера

        if (file2.getSize() > 0) {
            Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getBunner()));
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file2.getOriginalFilename();
            try {
                file2.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            cinema.setBunner("img/" + FileNameUuid);
        } else {
            if (oldcinema.getBunner() != null)
                cinema.setBunner(oldcinema.getBunner());
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
                        Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getPicture1()));
                        cinema.setPicture1("img/" + FileNameUuid);
                    } else if (i == 2) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getPicture2()));
                        cinema.setPicture2("img/" + FileNameUuid);
                    } else if (i == 3) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getPicture3()));
                        cinema.setPicture3("img/" + FileNameUuid);
                    } else if (i == 4) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getPicture4()));
                        cinema.setPicture4("img/" + FileNameUuid);
                    } else if (i == 5) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getPicture5()));
                        cinema.setPicture5("img/" + FileNameUuid);
                    }
                } else {
                    if (i == 1) {
                        if (oldcinema.getPicture1() != null)
                            cinema.setPicture1(oldcinema.getPicture1());
                    } else if (i == 2) {
                        if (oldcinema.getPicture2() != null)
                            cinema.setPicture2(oldcinema.getPicture2());
                    } else if (i == 3) {
                        if (oldcinema.getPicture3() != null)
                            cinema.setPicture3(oldcinema.getPicture3());
                    } else if (i == 4) {
                        if (oldcinema.getPicture4() != null)
                            cinema.setPicture4(oldcinema.getPicture4());
                    } else if (i == 5) {
                        if (oldcinema.getPicture5() != null)
                            cinema.setPicture5(oldcinema.getPicture5());
                    }
                }
            }
        }
        cinema.setUrl(url);
        cinema.setTitle(title);
        cinema.setKeywords(keywords);
        cinema.setDescript(descript);
        cinemaService.save(cinema);
        cinemaId = cinema.getIdcinema();
        return "redirect:/admin-cinema";
    }

    // метод сохранения изменений кинотеатра по кнопке создать зал
    @PostMapping("/admin-cinema-card-update-save2")
    public String updateCinema2(@RequestParam("idcinema") Long idcinema, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("cinemacondition") String cinemacondition, @RequestParam("mainpicture") MultipartFile file,
                                @RequestParam("bunner") MultipartFile file2, @RequestParam("picture1") MultipartFile[] files, @RequestParam("url") String url,
                                @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript) throws IOException {
        Cinema cinema = new Cinema();
        cinema.setIdcinema(idcinema);
        cinema.setName(name);
        cinema.setDescription(description);
        cinema.setCinemacondition(cinemacondition);
        //            сохранение главного фото

        Cinema oldcinema = cinemaService.findById(idcinema);
        uploadDir = new File(uploadPath + "/img/");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        if (file.getSize() > 0) {
            Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getMainpicture()));
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            cinema.setMainpicture("img/" + FileNameUuid);
        } else {
            if (oldcinema.getMainpicture() != null)
                cinema.setMainpicture(oldcinema.getMainpicture());
        }

        //            сохранение баннера

        if (file2.getSize() > 0) {
            Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getBunner()));
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file2.getOriginalFilename();
            try {
                file2.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            cinema.setBunner("img/" + FileNameUuid);
        } else {
            if (oldcinema.getBunner() != null)
                cinema.setBunner(oldcinema.getBunner());
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
                        Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getPicture1()));
                        cinema.setPicture1("img/" + FileNameUuid);
                    } else if (i == 2) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getPicture2()));
                        cinema.setPicture2("img/" + FileNameUuid);
                    } else if (i == 3) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getPicture3()));
                        cinema.setPicture3("img/" + FileNameUuid);
                    } else if (i == 4) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getPicture4()));
                        cinema.setPicture4("img/" + FileNameUuid);
                    } else if (i == 5) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getPicture5()));
                        cinema.setPicture5("img/" + FileNameUuid);
                    }
                } else {
                    if (i == 1) {
                        if (oldcinema.getPicture1() != null)
                            cinema.setPicture1(oldcinema.getPicture1());
                    } else if (i == 2) {
                        if (oldcinema.getPicture2() != null)
                            cinema.setPicture2(oldcinema.getPicture2());
                    } else if (i == 3) {
                        if (oldcinema.getPicture3() != null)
                            cinema.setPicture3(oldcinema.getPicture3());
                    } else if (i == 4) {
                        if (oldcinema.getPicture4() != null)
                            cinema.setPicture4(oldcinema.getPicture4());
                    } else if (i == 5) {
                        if (oldcinema.getPicture5() != null)
                            cinema.setPicture5(oldcinema.getPicture5());
                    }
                }
            }
        }
        cinema.setUrl(url);
        cinema.setTitle(title);
        cinema.setKeywords(keywords);
        cinema.setDescript(descript);
        cinemaService.save(cinema);
        cinemaId = cinema.getIdcinema();
        return "redirect:/admin-hall-card-form";
    }

    //метод сохранения изменений кинотеатра по кнопке редактировать зал
    @PostMapping("/admin-cinema-card-update-save3/{idh}")
    public String updateCinema3(RedirectAttributes redirectAttrs, @PathVariable("idh") Long idh, @RequestParam("idcinema") Long idcinema, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("cinemacondition") String cinemacondition, @RequestParam("mainpicture") MultipartFile file,
                                @RequestParam("bunner") MultipartFile file2, @RequestParam("picture1") MultipartFile[] files, @RequestParam("url") String url,
                                @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript) throws IOException {

        Cinema cinema = new Cinema();
        cinema.setIdcinema(idcinema);
        cinema.setName(name);
        cinema.setDescription(description);
        cinema.setCinemacondition(cinemacondition);
        //            сохранение главного фото

        Cinema oldcinema = cinemaService.findById(idcinema);
        uploadDir = new File(uploadPath + "/img/");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        if (file.getSize() > 0) {
            Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getMainpicture()));
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            cinema.setMainpicture("img/" + FileNameUuid);
        } else {
            if (oldcinema.getMainpicture() != null)
                cinema.setMainpicture(oldcinema.getMainpicture());
        }

        //            сохранение баннера

        if (file2.getSize() > 0) {
            Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getBunner()));
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file2.getOriginalFilename();
            try {
                file2.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            cinema.setBunner("img/" + FileNameUuid);
        } else {
            if (oldcinema.getBunner() != null)
                cinema.setBunner(oldcinema.getBunner());
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
                        Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getPicture1()));
                        cinema.setPicture1("img/" + FileNameUuid);
                    } else if (i == 2) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getPicture2()));
                        cinema.setPicture2("img/" + FileNameUuid);
                    } else if (i == 3) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getPicture3()));
                        cinema.setPicture3("img/" + FileNameUuid);
                    } else if (i == 4) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getPicture4()));
                        cinema.setPicture4("img/" + FileNameUuid);
                    } else if (i == 5) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldcinema.getPicture5()));
                        cinema.setPicture5("img/" + FileNameUuid);
                    }
                } else {
                    if (i == 1) {
                        if (oldcinema.getPicture1() != null)
                            cinema.setPicture1(oldcinema.getPicture1());
                    } else if (i == 2) {
                        if (oldcinema.getPicture2() != null)
                            cinema.setPicture2(oldcinema.getPicture2());
                    } else if (i == 3) {
                        if (oldcinema.getPicture3() != null)
                            cinema.setPicture3(oldcinema.getPicture3());
                    } else if (i == 4) {
                        if (oldcinema.getPicture4() != null)
                            cinema.setPicture4(oldcinema.getPicture4());
                    } else if (i == 5) {
                        if (oldcinema.getPicture5() != null)
                            cinema.setPicture5(oldcinema.getPicture5());
                    }
                }
            }
        }
        cinema.setUrl(url);
        cinema.setTitle(title);
        cinema.setKeywords(keywords);
        cinema.setDescript(descript);
        cinemaService.save(cinema);
        cinemaId = cinema.getIdcinema();
        redirectAttrs.addFlashAttribute("idh", idh);
        return "redirect:/admin-hall-card-update";
    }


    //метод сохранения кинотеатра и зала
    @GetMapping("/admin-cinema-hall-card-update")
    public String updateCinemaHallForm(Model model, @ModelAttribute("hall") Hall hall) {
        Cinema cinema = cinemaService.findById(cinemaId);
        cinema.addHallToCinema(hall);
        cinemaService.save(cinema);
        model.addAttribute("cinema", cinema);
        return "Admin/admin-cinema-update-card";
    }

    //    метод перехода на редактируемый кинотеатр после сохранения отредактированного зала
    @GetMapping("/admin-cinema-hall-card-update2")
    public String updateCinemaHallForm(Model model) {
        Cinema cinema = cinemaService.findById(cinemaId);
        model.addAttribute("cinema", cinema);
        return "Admin/admin-cinema-update-card";
    }
}

