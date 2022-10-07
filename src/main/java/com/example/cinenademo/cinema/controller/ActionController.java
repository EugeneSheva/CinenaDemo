package com.example.cinenademo.cinema.controller;

import com.example.cinenademo.cinema.model.Action;
import com.example.cinenademo.cinema.model.News;
import com.example.cinenademo.cinema.service.ActionService;
import com.example.cinenademo.cinema.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Controller
public class ActionController {

        private final ActionService actionService;
        @Autowired
        public ActionController(ActionService actionService) {
            this.actionService = actionService;
        }

        @Value("${upload.path}")
        private String uploadPath;
        private File uploadDir;

        @GetMapping("/admin-action")
        public String findAll(Model model) {
            List<Action> actions = actionService.findAll();
            model.addAttribute("action", actions);
            return "Admin/admin-action-list";
        }




        @GetMapping("/admin-action-card-form")
        public String createActionForm(Model model) {
            model.addAttribute("action", new Action());
            return "Admin/admin-action-card";
        }


        @PostMapping("/admin-action-card-add")
        public String createNews(@RequestParam("name") String name, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @RequestParam ("description") String description, @RequestParam("mainpicture") MultipartFile file,
                                 @RequestParam("picture1") MultipartFile[] files,
                                 @RequestParam("video") String video, @RequestParam("url") String url,
                                 @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript){
            Action action = new Action();
            action.setName(name);
            action.setDate(date);
            action.setDescription(description);
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
                action.setMainpicture("/img/"+FileNameUuid);


            }
            if (files.length>0) {
                System.out.println(files.length);
                for (MultipartFile file1 : files) {
                    if (file1.getSize() > 0) {


                        uploadDir = new File(uploadPath);
                        System.out.println(uploadDir);
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
                        if (action.getPicture1()==null) {
                            action.setPicture1("/img/"+FileNameUuid);
                        }else if (action.getPicture2()==null) {
                            action.setPicture2("/img/"+FileNameUuid);
                        }else if (action.getPicture3()==null) {
                            action.setPicture3("/img/"+FileNameUuid);
                        }else if (action.getPicture4()==null) {
                            action.setPicture4("/img/"+FileNameUuid);
                        }else if (action.getPicture5()==null) {
                            action.setPicture5("/img/"+FileNameUuid);
                        }
                    }
                }
            }
            action.setVideo(video);
            action.setUrl(url);
            action.setTitle(title);
            action.setKeywords(keywords);
            action.setDescript(descript);
            actionService.save(action);

            return "redirect:/admin-action";
        }


        @GetMapping("action-delete/{id}")
        public String deleteAction(@PathVariable("id") Long id) {
            actionService.deleteById(id);
            return "redirect:/admin-action";
        }


        @GetMapping("/admin-action-card-update/{id}")
        public String updateActionForm(@PathVariable("id") Long id, Model model) {
            Action action = actionService.findById(id);
            model.addAttribute("action", action);
            return "Admin/admin-action-update-card";
        }


        @PostMapping("/admin-action-card-update-save")
        public String updateNews(@RequestParam ("idaction") Long idaction, @RequestParam("name") String name, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @RequestParam ("description") String description, @RequestParam("mainpicture") MultipartFile file,
                                 @RequestParam("picture1") MultipartFile[] files,
                                 @RequestParam("video") String video, @RequestParam("url") String url,
                                 @RequestParam("title") String title, @RequestParam("keywords") String keywords, @RequestParam("descript") String descript){
            Action action = new Action();
            action.setIdaction(idaction);
            action.setName(name);
            action.setDate(date);
            action.setDescription(description);
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
                action.setMainpicture("/img/"+FileNameUuid);


            }
            if (files.length>0) {
                System.out.println(files.length);
                for (MultipartFile file1 : files) {
                    if (file1.getSize() > 0) {


                        uploadDir = new File(uploadPath);
                        System.out.println(uploadDir);
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
                        if (action.getPicture1()==null) {
                            action.setPicture1("/img/"+FileNameUuid);
                        }else if (action.getPicture2()==null) {
                            action.setPicture2("/img/"+FileNameUuid);
                        }else if (action.getPicture3()==null) {
                            action.setPicture3("/img/"+FileNameUuid);
                        }else if (action.getPicture4()==null) {
                            action.setPicture4("/img/"+FileNameUuid);
                        }else if (action.getPicture5()==null) {
                            action.setPicture5("/img/"+FileNameUuid);
                        }
                    }
                }
            }
            action.setVideo(video);
            action.setUrl(url);
            action.setTitle(title);
            action.setKeywords(keywords);
            action.setDescript(descript);
            actionService.save(action);

            return "redirect:/admin-action";
        }



}
