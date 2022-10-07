package com.example.cinenademo.cinema.controller;

import com.example.cinenademo.cinema.model.Cinema;
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
        public String findAll(Model model){
            List<Cinema> cinema = cinemaService.findAll();
            model.addAttribute("cinema",cinema);
            return "Admin/admin-cinema-list";
        }


        @GetMapping("/admin-cinema-card-form")
        public String createCinemaForm(Model model) {
            model.addAttribute("cinema", new Cinema());
            return "Admin/admin-cinema-card";
        }
    // метод сохранения кинотеатра по кнопке сохранить
        @PostMapping("/admin-cinema-card-add")
        public String createFilm(@RequestParam("name") String name, @RequestParam ("description") String description, @RequestParam("mainpicture") MultipartFile file,
                                 @RequestParam("bunner") MultipartFile file2, @RequestParam("picture1") MultipartFile[] files, @RequestParam("url") String url,
                                 @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript){
            Cinema cinema = new Cinema();
            cinema.setName(name);
            cinema.setDescription(description);
            if (file.getSize() > 0) {
                uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String uuid = UUID.randomUUID().toString();
                String FileNameUuid = uuid + "-" + file.getOriginalFilename();
                try {
                    file.transferTo(new File(uploadDir + "/" + FileNameUuid));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                cinema.setMainpicture("/img/" + FileNameUuid);
            }
                if (file2.getSize() > 0) {
                    uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }
                    String uuid = UUID.randomUUID().toString();
                    String FileNameUuid = uuid + "-" + file2.getOriginalFilename();
                    try {
                        file2.transferTo(new File(uploadDir + "/" + FileNameUuid));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    cinema.setBunner("/img/"+FileNameUuid);



            }
            if (files.length>0) {
                for (MultipartFile file1 : files) {
                    if (file1.getSize() > 0) {
                        uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }
                        String uuid = UUID.randomUUID().toString();
                        String FileNameUuid = uuid + "-" + file1.getOriginalFilename();
                        try {
                            file1.transferTo(new File(uploadDir + "/" + FileNameUuid));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        if (cinema.getPicture1()==null) {
                            cinema.setPicture1("/img/"+FileNameUuid);
                        }else if (cinema.getPicture2()==null) {
                            cinema.setPicture2("/img/"+FileNameUuid);
                        }else if (cinema.getPicture3()==null) {
                            cinema.setPicture3("/img/"+FileNameUuid);
                        }else if (cinema.getPicture4()==null) {
                            cinema.setPicture4("/img/"+FileNameUuid);
                        }else if (cinema.getPicture5()==null) {
                            cinema.setPicture5("/img/"+FileNameUuid);
                        }
                    }
                }
            }
            cinema.setUrl(url);
            cinema.setTitle(title);
            cinema.setKeywords(keywords);
            cinema.setDescript(descript);
            cinemaService.save(cinema);
            cinemaId =cinema.getIdcinema();
            return "redirect:/admin-cinema";
        }
    // метод сохранения кинотеатра по кнопке создать зал
    @PostMapping("/admin-cinema-card-add2")
    public String createFilm2(@RequestParam("name") String name, @RequestParam ("description") String description, @RequestParam("mainpicture") MultipartFile file,
                              @RequestParam("bunner") MultipartFile file2, @RequestParam("picture1") MultipartFile[] files, @RequestParam("url") String url,
                              @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript){
        Cinema cinema = new Cinema();
        cinema.setName(name);
        cinema.setDescription(description);
        if (file.getSize() > 0) {
            uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            cinema.setMainpicture("/img/" + FileNameUuid);
        }
        if (file2.getSize() > 0) {
            uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file2.getOriginalFilename();
            try {
                file2.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            cinema.setBunner("/img/"+FileNameUuid);
        }
        if (files.length>0) {
            System.out.println(files.length);
            for (MultipartFile file1 : files) {
                if (file1.getSize() > 0) {
                    uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }
                    String uuid = UUID.randomUUID().toString();
                    String FileNameUuid = uuid + "-" + file1.getOriginalFilename();
                    try {
                        file1.transferTo(new File(uploadDir + "/" + FileNameUuid));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (cinema.getPicture1()==null) {
                        cinema.setPicture1("/img/"+FileNameUuid);
                    }else if (cinema.getPicture2()==null) {
                        cinema.setPicture2("/img/"+FileNameUuid);
                    }else if (cinema.getPicture3()==null) {
                        cinema.setPicture3("/img/"+FileNameUuid);
                    }else if (cinema.getPicture4()==null) {
                        cinema.setPicture4("/img/"+FileNameUuid);
                    }else if (cinema.getPicture5()==null) {
                        cinema.setPicture5("/img/"+FileNameUuid);
                    }
                }
            }
        }
        cinema.setUrl(url);
        cinema.setTitle(title);
        cinema.setKeywords(keywords);
        cinema.setDescript(descript);
        cinemaService.save(cinema);
        cinemaId =cinema.getIdcinema();
        return "redirect:/admin-hall-card-form";
    }

        @GetMapping("cinema-delete/{id}")
        public String deleteCinema(@PathVariable("id") Long id) {
            cinemaService.deleteById(id);
            return "redirect:/admin-cinema";
        }

        @GetMapping("/admin-cinema-card-update/{id}")
        public String updateCinemaForm(@PathVariable("id") Long id, Model model) {
            Cinema cinema = cinemaService.findById(id);
            cinemaId=id;
            model.addAttribute("cinema", cinema);
            return "Admin/admin-cinema-update-card";
        }
    // метод сохранения изменений кинотеатра по кнопке сохранить
        @PostMapping("/admin-cinema-card-update-save")
        public String updateCinema(@RequestParam ("idcinema") Long idcinema, @RequestParam ("name") String name, @RequestParam ("description") String description,@RequestParam("mainpicture") MultipartFile file,
                                 @RequestParam("bunner") MultipartFile file2, @RequestParam("picture1") MultipartFile[] files, @RequestParam("url") String url,
                                 @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript) {
            Cinema cinema = new Cinema();
            cinema.setIdcinema(idcinema);
            cinema.setName(name);
            cinema.setDescription(description);
            if (file.getSize() > 0) {
                uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String uuid = UUID.randomUUID().toString();
                String FileNameUuid = uuid + "-" + file.getOriginalFilename();
                try {
                    file.transferTo(new File(uploadDir + "/" + FileNameUuid));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                cinema.setMainpicture("/img/"+FileNameUuid);


            }
            if (file2.getSize() > 0) {
                uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String uuid = UUID.randomUUID().toString();
                String FileNameUuid = uuid + "-" + file2.getOriginalFilename();
                try {
                    file2.transferTo(new File(uploadDir + "/" + FileNameUuid));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                cinema.setBunner("/img/"+FileNameUuid);



            }
            if (files.length>0) {
                System.out.println(files.length);
                for (MultipartFile file1 : files) {
                    if (file1.getSize() > 0) {
                        uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }
                        String uuid = UUID.randomUUID().toString();
                        String FileNameUuid = uuid + "-" + file1.getOriginalFilename();
                        try {
                            file1.transferTo(new File(uploadDir + "/" + FileNameUuid));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        if (cinema.getPicture1()==null) {
                            cinema.setPicture1("/img/"+FileNameUuid);
                        }else if (cinema.getPicture2()==null) {
                            cinema.setPicture2("/img/"+FileNameUuid);
                        }else if (cinema.getPicture3()==null) {
                            cinema.setPicture3("/img/"+FileNameUuid);
                        }else if (cinema.getPicture4()==null) {
                            cinema.setPicture4("/img/"+FileNameUuid);
                        }else if (cinema.getPicture5()==null) {
                            cinema.setPicture5("/img/"+FileNameUuid);
                        }
                    }
                }
            }
            cinema.setUrl(url);
            cinema.setTitle(title);
            cinema.setKeywords(keywords);
            cinema.setDescript(descript);
            cinemaService.save(cinema);
            cinemaId =cinema.getIdcinema();
            return "redirect:/admin-cinema";
        }
    // метод сохранения изменений кинотеатра по кнопке создать зал
    @PostMapping("/admin-cinema-card-update-save2")
    public String updateCinema2(@RequestParam ("idcinema") Long idcinema, @RequestParam ("name") String name, @RequestParam ("description") String description,@RequestParam("mainpicture") MultipartFile file,
                               @RequestParam("bunner") MultipartFile file2, @RequestParam("picture1") MultipartFile[] files, @RequestParam("url") String url,
                               @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript) {
        Cinema cinema = new Cinema();
        cinema.setIdcinema(idcinema);
        cinema.setName(name);
        cinema.setDescription(description);
        if (file.getSize() > 0) {
            uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadDir + "/" + FileNameUuid));
                System.out.println(uploadDir + "/" + FileNameUuid);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            cinema.setMainpicture("/img/"+FileNameUuid);


        }
        if (file2.getSize() > 0) {
            uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file2.getOriginalFilename();
            try {
                file2.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            cinema.setBunner("/img/"+FileNameUuid);



        }
        if (files.length>0) {
            for (MultipartFile file1 : files) {
                if (file1.getSize() > 0) {
                    uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }
                    String uuid = UUID.randomUUID().toString();
                    String FileNameUuid = uuid + "-" + file1.getOriginalFilename();
                    try {
                        file1.transferTo(new File(uploadDir + "/" + FileNameUuid));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (cinema.getPicture1()==null) {
                        cinema.setPicture1("/img/"+FileNameUuid);
                    }else if (cinema.getPicture2()==null) {
                        cinema.setPicture2("/img/"+FileNameUuid);
                    }else if (cinema.getPicture3()==null) {
                        cinema.setPicture3("/img/"+FileNameUuid);
                    }else if (cinema.getPicture4()==null) {
                        cinema.setPicture4("/img/"+FileNameUuid);
                    }else if (cinema.getPicture5()==null) {
                        cinema.setPicture5("/img/"+FileNameUuid);
                    }
                }
            }
        }
        cinema.setUrl(url);
        cinema.setTitle(title);
        cinema.setKeywords(keywords);
        cinema.setDescript(descript);
        cinemaService.save(cinema);
        cinemaId =cinema.getIdcinema();
        return "redirect:/admin-hall-card-form";
    }
    //метод сохранения изменений кинотеатра по кнопке редактировать зал
    @PostMapping("/admin-cinema-card-update-save3/{idh}")
    public String updateCinema3(RedirectAttributes redirectAttrs, @PathVariable("idh") Long idh, @RequestParam ("idcinema") Long idcinema, @RequestParam ("name") String name, @RequestParam ("description") String description,@RequestParam("mainpicture") MultipartFile file,
                                @RequestParam("bunner") MultipartFile file2, @RequestParam("picture1") MultipartFile[] files, @RequestParam("url") String url,
                                @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript) {

        Cinema cinema = new Cinema();
        cinema.setIdcinema(idcinema);
        cinema.setName(name);
        cinema.setDescription(description);
        if (file.getSize() > 0) {
            uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            cinema.setMainpicture("/img/"+FileNameUuid);


        }
        if (file2.getSize() > 0) {
            uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String uuid = UUID.randomUUID().toString();
            String FileNameUuid = uuid + "-" + file2.getOriginalFilename();
            try {
                file2.transferTo(new File(uploadDir + "/" + FileNameUuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            cinema.setBunner("/img/"+FileNameUuid);
        }
        if (files.length>0) {
            for (MultipartFile file1 : files) {
                if (file1.getSize() > 0) {


                    uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }
                    String uuid = UUID.randomUUID().toString();
                    String FileNameUuid = uuid + "-" + file1.getOriginalFilename();
                    try {
                        file1.transferTo(new File(uploadDir + "/" + FileNameUuid));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (cinema.getPicture1()==null) {
                        cinema.setPicture1("/img/"+FileNameUuid);
                    }else if (cinema.getPicture2()==null) {
                        cinema.setPicture2("/img/"+FileNameUuid);
                    }else if (cinema.getPicture3()==null) {
                        cinema.setPicture3("/img/"+FileNameUuid);
                    }else if (cinema.getPicture4()==null) {
                        cinema.setPicture4("/img/"+FileNameUuid);
                    }else if (cinema.getPicture5()==null) {
                        cinema.setPicture5("/img/"+FileNameUuid);
                    }
                }
            }
        }
        cinema.setUrl(url);
        cinema.setTitle(title);
        cinema.setKeywords(keywords);
        cinema.setDescript(descript);
        cinemaService.save(cinema);
        cinemaId =cinema.getIdcinema();
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

