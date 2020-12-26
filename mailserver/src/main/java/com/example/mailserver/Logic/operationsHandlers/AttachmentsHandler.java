package com.example.mailserver.Logic.operationsHandlers;

import com.example.mailserver.Logic.Session;
import com.example.mailserver.Logic.Attachments.*;
import com.example.mailserver.Logic.SendRequestHandler.EmailRequestData;
import com.example.mailserver.Logic.SendRequestHandler.RequestToEmailConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// @RestController
// @CrossOrigin
public class AttachmentsHandler {

    @Autowired
    private FileStorageService fileStorageService;
    
    // @PostMapping("/uploadMultipleFiles")
    // // @RequestMapping(value=("/uploadMultipleFiles"),headers=("content-type=multipart/*"),method=RequestMethod.POST)
    // public List<UploadFileResponse> uploadMultipleFiles(MultipartFile[] files,@RequestParam String emailData, String emailId) {
    //     EmailRequestData data = RequestToEmailConverter.getInstance().jsonToData(emailData);
    //     int userId = data.getUserId();
    //     String sender = Session.getInstance().getUser(userId).getUserEmailAddress();

    //     String[] receivers= data.getReceivers().split(",");

    //     return Arrays.asList(files).stream().map(file -> uploadFile(file, sender, receivers, emailId)).collect(Collectors.toList());
    // }

    // private UploadFileResponse uploadFile(MultipartFile file, String sender, String[]receivers, String id) {
    //     String fileName = fileStorageService.storeFile(file, sender, receivers, id);
    //     String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileName).toUriString();

    //     return new UploadFileResponse(fileName, fileDownloadUrl, file.getContentType(), file.getSize());
    // }

    // @GetMapping("/downloadFile/{fileName:.+}")
    // public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request, @RequestParam String emailAddress, @RequestParam String id) {
    //     Resource resource = fileStorageService.loadFileAsResource(fileName, emailAddress, id);

    //     String contentType = null;
    //     try {
    //         contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
    //     } catch (IOException ex) {
    //         ex.printStackTrace();
    //     }

    //     if(contentType == null) contentType = "application/octet-stream";

    //     return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    // }
}