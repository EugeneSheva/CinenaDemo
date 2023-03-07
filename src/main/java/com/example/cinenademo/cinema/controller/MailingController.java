package com.example.cinenademo.cinema.controller;

import com.example.cinenademo.cinema.model.Mails;
import com.example.cinenademo.cinema.model.User;
import com.example.cinenademo.cinema.service.MailService;
import com.example.cinenademo.cinema.service.MailsService;
import com.example.cinenademo.cinema.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MailingController {

    private final MailService mailService;
    private final UserService userService;
    private final MailsService mailsService;
    @Value("${upload.path}")
    private String uploadPath;
    List<Long> selectedUsersMain = new ArrayList<>();


    @GetMapping("/admin-mailing-list")
    public String findAll(Model model, @RequestParam(name = "selectedUsers", defaultValue = "0") List<Long> ids) {
//        if (ids.get(0) == 0) ids.remove(0);
        boolean isEmpty;
//        Образцы писем
        List<Mails> mailsList = null;
        if (mailsService.findAll() == null) {
            mailsList.add(new Mails("Образец"));
        } else {
            mailsList = mailsService.findAll();
        }
//        список
        model.addAttribute("mailsList", mailsList);
        if (selectedUsersMain.size() == 0) {
            isEmpty = true;
            System.out.println(isEmpty);
            model.addAttribute("size", "Не выбрано");
        } else {
            isEmpty = false;
            model.addAttribute("size", selectedUsersMain.size());
        }
        model.addAttribute("isListEmpty", isEmpty);
        model.addAttribute("ids", ids);

        return "Admin/admin-mailing-list-page";
    }

    @GetMapping("/admin-select-users")
    public String selectUsers(Model model, @PageableDefault(sort = {"idusers"}, direction = Sort.Direction.ASC, size = 10) Pageable pageable) {
        Page<User> users = userService.findAll(pageable);
        model.addAttribute("user", users);
        model.addAttribute("selectedUsers", selectedUsersMain);
//        model.addAttribute("pageable", pageable);
        return "Admin/admin-mailing-users";
    }
//    @GetMapping("/admin-users")
//    public String selectUsers(Model model) {
//        List<User> users = userService.findAll();
//        model.addAttribute("user", users);
//        return "Admin/admin-users-list";
//    }

    @SneakyThrows
    @RequestMapping(value = "/admin-send-mail", method = RequestMethod.POST)
    public String send(@RequestParam("file") MultipartFile file,
//                       @RequestParam("selectedUsers") List<Long> selectedUsers,
                       @RequestParam("userList") String userList, @RequestParam(name = "selectedId", defaultValue = "0") Long fileOldId, Model model) {
        //recepient
        System.out.println("1");
        List<User> users = null;
        String[] recepients;
        if (userList.equalsIgnoreCase("all")) {
            users = userService.findAll();
            System.out.println(userList);
            System.out.println(users);

        } else if (userList.equalsIgnoreCase("selected")) {
            users = userService.findAllById(selectedUsersMain);
            System.out.println(userList);
            System.out.println(users);
        }
        recepients = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            recepients[i] = user.getEmail();
            System.out.println(user.getEmail());
        }
        //message
        File htmlFile;
        Mails mails = null;
        File uploadDir = new File(uploadPath + "/mails/");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        if (fileOldId != 0) {
            mails = mailsService.findById(fileOldId);
            htmlFile = new File(uploadPath + mails.getFilePath());
        } else {
            mails = new Mails();
            UUID uuid = UUID.randomUUID();
            System.out.println(file.getOriginalFilename());
            htmlFile = new File(uploadPath + "/mails/" + uuid + file.getOriginalFilename());
            mails.setFilePath("/mails/" + uuid + file.getOriginalFilename());
            mails.setName(file.getOriginalFilename());
            mailsService.save(mails);
            file.transferTo(htmlFile);
        }


        Document doc = Jsoup.parse(htmlFile, "UTF-8");
        System.out.println("1");
        String title = doc.toString();
        System.out.println("2");
        //sending
        mailService.send(recepients,
                "Заголовок",
                title);

//        Files.deleteIfExists(Paths.get(uploadPath + "/tmp.html"));
        selectedUsersMain.clear();
        System.out.println("fin");
        return "redirect:/admin-mailing-list";
    }

    @GetMapping("mails-delete/{id}")
    public String deleteMail(@PathVariable("id") Long id) throws IOException {
        Files.deleteIfExists(Paths.get(uploadPath + mailsService.findById(id).getFilePath()));
//        mailsService.findById(id).getFilePath();
        mailsService.deleteById(id);
        return "redirect:/admin-mailing-list";
    }

    @PostMapping("/admin-users-list-create")
    public String updateFilm(RedirectAttributes redirectAttrs, @RequestParam(name = "id", defaultValue = "0") List<Long> ids, @RequestParam(name = "idOfHiddenInputs", defaultValue = "0") List<Long> idOfHiddenInput) {

        if (ids.size() > 0) {
            redirectAttrs.addAttribute("selectedUsers", ids);
        }
        return "redirect:/admin-mailing-list";
    }


    @GetMapping("/send-list")
    public @ResponseBody String sendList(@RequestParam("check") Long[] userList) {
        if (selectedUsersMain == null) selectedUsersMain = new ArrayList<>();
        for (Long aLong : userList) {
            if (selectedUsersMain.contains(aLong)) {
                selectedUsersMain.remove(aLong);
                System.out.println("remove " + aLong);
            } else {
                System.out.println("- " + aLong);
                selectedUsersMain.add(aLong);
            }
        }
        System.out.println(selectedUsersMain);
        return "";
    }

}
