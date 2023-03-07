package com.example.cinenademo.cinema.controller;


import com.example.cinenademo.cinema.model.Cinema;
import com.example.cinenademo.cinema.model.Hall;
import com.example.cinenademo.cinema.service.CinemaService;
import com.example.cinenademo.cinema.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class HallController {


    private final HallService hallService;

    @Autowired
    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @Value("${upload.path}")
    private String uploadPath;
    private File uploadDir;

    @GetMapping("admin-hall-card-form")
    public String createHallForm(Model model) {
        model.addAttribute("hall", new Hall());
        return "Admin/admin-hall-card";
    }

    @PostMapping("/admin-hall-card-add")
    public String createHall(RedirectAttributes redirectAttrs, @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateofregistry, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("hallschema") MultipartFile file,
                             @RequestParam("bunner") MultipartFile file2, @RequestParam("picture1") MultipartFile[] files, @RequestParam("url") String url,
                             @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript) {

        Hall hall = new Hall();
        hall.setName(name);
        hall.setDescription(description);

        //    загрузка схемы зала

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
            hall.setHallschema("img/" + FileNameUuid);
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
            hall.setBunner("img/" + FileNameUuid);
        }

        //    загрузка остальных фото

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
                    if (hall.getPicture1() == null) {
                        hall.setPicture1("img/" + FileNameUuid);
                    } else if (hall.getPicture2() == null) {
                        hall.setPicture2("img/" + FileNameUuid);
                    } else if (hall.getPicture3() == null) {
                        hall.setPicture3("img/" + FileNameUuid);
                    } else if (hall.getPicture4() == null) {
                        hall.setPicture4("img/" + FileNameUuid);
                    } else if (hall.getPicture5() == null) {
                        hall.setPicture5("img/" + FileNameUuid);
                    }
                }
            }
        }
        hall.setDateofregistry(dateofregistry);
        hall.setUrl(url);
        hall.setTitle(title);
        hall.setKeywords(keywords);
        hall.setDescript(descript);
        System.out.println(hall);
        redirectAttrs.addFlashAttribute("hall", hall);
        return "redirect:/admin-cinema-hall-card-update";
    }

    @GetMapping("hall-delete/{id}")
    public String deleteHall(@PathVariable("id") Long id) {
        Hall hall = hallService.findById(id);
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
        Cinema cinema = hall.getCinema();
        cinema.delHallFromCinema(hall);
        hallService.save(hall);
        hallService.deleteById(id);
        return "redirect:/admin-cinema-hall-card-update2";
    }

    //        @GetMapping("/admin-hall-card-update/{id}")
//        public String updateHallForm(@PathVariable("id") Long id, Model model) {
//            Hall hall = hallService.findById(id);
//            model.addAttribute("hall", hall);
//            return "Admin/admin-hall-update-card";
//        }
    @GetMapping("/admin-hall-card-update")
    public String updateHallForm(Model model, @ModelAttribute("idh") Long idh) {
        Hall hall = hallService.findById(idh);
        model.addAttribute("hall", hall);
        return "Admin/admin-hall-update-card";
    }

    @PostMapping("/admin-hall-card-update-save")
    public String updateHall(RedirectAttributes redirectAttrs, @RequestParam("idhall") Long idhall, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("hallschema") MultipartFile file,
                             @RequestParam("bunner") MultipartFile file2, @RequestParam("picture1") MultipartFile[] files, @RequestParam("cinema") Cinema cinema, @RequestParam("url") String url,
                             @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript, @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateofregistry) throws IOException {
        Hall hall = new Hall();
        hall.setIdhall(idhall);
        hall.setName(name);
        hall.setDescription(description);

        //            сохранение главного фото

        Hall oldhall = hallService.findById(idhall);
        uploadDir = new File(uploadPath + "/img/");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        if (file.getSize() > 0) {
            Files.deleteIfExists(Paths.get(uploadPath + oldhall.getHallschema()));
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            hall.setHallschema("img/" + FileNameUuid);
        } else {
            if (oldhall.getHallschema() != null)
                hall.setHallschema(oldhall.getHallschema());
        }

        //            сохранение баннера

        if (file2.getSize() > 0) {
            Files.deleteIfExists(Paths.get(uploadPath + oldhall.getBunner()));
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file2.getOriginalFilename();
            try {
                file2.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            hall.setBunner("img/" + FileNameUuid);
        } else {
            if (oldhall.getBunner() != null)
                hall.setBunner(oldhall.getBunner());
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
                        Files.deleteIfExists(Paths.get(uploadPath + oldhall.getPicture1()));
                        hall.setPicture1("img/" + FileNameUuid);
                    } else if (i == 2) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldhall.getPicture2()));
                        hall.setPicture2("img/" + FileNameUuid);
                    } else if (i == 3) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldhall.getPicture3()));
                        hall.setPicture3("img/" + FileNameUuid);
                    } else if (i == 4) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldhall.getPicture4()));
                        hall.setPicture4("img/" + FileNameUuid);
                    } else if (i == 5) {
                        Files.deleteIfExists(Paths.get(uploadPath + oldhall.getPicture5()));
                        hall.setPicture5("img/" + FileNameUuid);
                    }
                } else {
                    if (i == 1) {
                        if (oldhall.getPicture1() != null)
                            hall.setPicture1(oldhall.getPicture1());
                    } else if (i == 2) {
                        if (oldhall.getPicture2() != null)
                            hall.setPicture2(oldhall.getPicture2());
                    } else if (i == 3) {
                        if (oldhall.getPicture3() != null)
                            hall.setPicture3(oldhall.getPicture3());
                    } else if (i == 4) {
                        if (oldhall.getPicture4() != null)
                            hall.setPicture4(oldhall.getPicture4());
                    } else if (i == 5) {
                        if (oldhall.getPicture5() != null)
                            hall.setPicture5(oldhall.getPicture5());
                    }
                }
            }
        }
        hall.setCinema(cinema);
        hall.setDateofregistry(dateofregistry);
        hall.setUrl(url);
        hall.setTitle(title);
        hall.setKeywords(keywords);
        hall.setDescript(descript);
        System.out.println(hall);
        hallService.save(hall);

        return "redirect:/admin-cinema-hall-card-update2";

    }

}

