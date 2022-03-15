package stanford.core.nlp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import stanford.core.nlp.model.Data;
import stanford.core.nlp.service.CoreNlpService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class MainController {

    @Autowired
    private CoreNlpService coreNlpService;

//    @PostMapping("/upload")
//    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
//
//        String fileName = file.getOriginalFilename();
//        try {
//            if (!fileName.endsWith(".txt")) {
//                return ResponseEntity.badRequest().body("Allowed file type is .txt");
//            }
//            file.transferTo(new File("C:\\Data\\upload\\" + fileName));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//        return ResponseEntity.ok("File uploaded successfully.");
//    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        // check if file is empty
        if (file.isEmpty()) {
            return "redirect:/";
        }
        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // save the file on the local file system
        try {
            Path path = Paths.get("c:/nlp/" + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            String text = Files.readString(path);
            Data data = new Data();
            data.setContent(text);
            coreNlpService.ner(data);
            model.addAttribute("data", data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/";
        }
        return "welcome";
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadFile(@RequestParam("content") String content, HttpServletResponse response) throws IOException {
        if (content == null || content.equalsIgnoreCase("") || content.equalsIgnoreCase("null")) {
            return ResponseEntity.ok("");
        }
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename =  stanford_" + System.currentTimeMillis() + ".txt";
        response.setHeader(headerKey, headerValue);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(content.getBytes());
        outputStream.close();
        return ResponseEntity.ok("file downloaded");
    }


    @GetMapping("/")
    public String greetingForm(Model model) {
        model.addAttribute("data", new Data());
        return "welcome";
    }

    @PostMapping("/")
    public String greetingSubmit(@ModelAttribute Data data, Model model) {
        coreNlpService.ner(data);
        model.addAttribute("data", data);
        return "welcome";
    }


}
