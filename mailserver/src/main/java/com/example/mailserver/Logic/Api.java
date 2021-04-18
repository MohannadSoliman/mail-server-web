package com.example.mailserver.Logic;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import com.example.mailserver.Logic.Attachments.FileStorageService;
import com.example.mailserver.Logic.Attachments.UploadFileResponse;
import com.example.mailserver.Logic.Proxy.SignIn;
import com.example.mailserver.Logic.Proxy.SignUp;
import com.example.mailserver.Logic.SendRequestHandler.EmailRequestData;
import com.example.mailserver.Logic.SendRequestHandler.RequestToEmailConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class Api {
    private Email processedEmail;
    // verfication
    @GetMapping("/signIn")
    public String verifySignIn(@RequestParam String emailAddress, @RequestParam String password) {
        // "false" if new user, userId if current user
        return SignIn.getInstance().signInUser(emailAddress, password);
    }

    @GetMapping("/signUp")
    public String verifySignUp(@RequestParam String emailAddress, String password) {
        // false if current user, id if new user
        return SignUp.getInstance().signUpUser(emailAddress, password);
    }

    // fetching emails
    @GetMapping("/getEmailsList")
    public String getEmailsList(@RequestParam int userId, @RequestParam String folderName, @RequestParam int sortType, 
                                @RequestParam int sortIdntifier, @RequestParam int start) {
        System.out.println(Session.getInstance().getUser(userId) + "\n\n\n\n");
        Email[] emailsList = Session.getInstance().getUser(userId).getEmailsList(folderName, sortType, sortIdntifier,
                start);
        return JsonEmailConverter.getInstance().arrayOfEmailsToJson(emailsList);
    }

    @GetMapping("/getAllCustomFolders")
    public String getCustomFolders(@RequestParam int userId) {
        String[] customFoldersNames = Session.getInstance().getUser(userId).getAllCustomFolders();
        return JsonEmailConverter.getInstance().stringArrayToJson(customFoldersNames);
    }

    @GetMapping("/getEmailsNumber")
    public int getEmailsNumber(@RequestParam int userId, @RequestParam String folderName){
        return Session.getInstance().getUser(userId).getFolderEmailNumber(folderName);
    }

    // custom folders operations
    @GetMapping("/createNewCustomFolder")
    public boolean createNewCustomFolder(@RequestParam int userId, @RequestParam String folderName) throws IOException {
        return Session.getInstance().getUser(userId).createNewCustomFolder(folderName);
    }

    @GetMapping("/renameCustomFolder")
    public boolean renameCustomFolder(@RequestParam int userId, @RequestParam String oldFolderName, 
                                      @RequestParam String newFolderName) throws IOException {
        return Session.getInstance().getUser(userId).renameCustomFolder(oldFolderName, newFolderName);
    }

    @DeleteMapping("/deleteCustomFolder")
    public void deleteCustomFolder(@RequestParam int userId, @RequestParam String folderName){
        Session.getInstance().getUser(userId).deleteCustomFolder(folderName);
    }

    //sendingEmail
    @PostMapping("/sendEmail")
    public void sendEmail(@RequestParam String emailData){
        EmailRequestData data = RequestToEmailConverter.getInstance().jsonToData(emailData);
        int userId = data.getUserId();
        String priority = data.getPriority();
        String sender = Session.getInstance().getUser(userId).getUserEmailAddress();
        String body = data.getBody();
        String title = data.getTitle();

        String[] allReceivers = data.getReceivers().split(",");
        for(String receiver: allReceivers) System.out.println(receiver);
        Queue<String> receivers = new LinkedList<>(Arrays.asList(allReceivers));
        String[] allAttachments = data.getAttachments().split(",");
        Queue<String> attachments = new LinkedList<>(Arrays.asList(allAttachments));


        Email newEmail = new Email(null, sender, receivers, title, body, null, attachments, priority);
        Queue<String> temp = new LinkedList<>(Arrays.asList(allReceivers));
        processedEmail = new Email(newEmail.getId(), newEmail.getSender(), temp, newEmail.getTitle(),
                                    newEmail.getBody(), newEmail.getTime(), newEmail.getAttachments(), newEmail.getPriority());
        Session.getInstance().getUser(userId).sendEmail(newEmail);
    }

    //deleting email
    @DeleteMapping("/deleteEmail")
    public void deleteEmail(@RequestParam int userId, @RequestParam String emailId, @RequestParam String folderName){
        Session.getInstance().getUser(userId).deleteEmail(emailId, folderName);
    }

    @DeleteMapping("/deleteForever")
    public void deleteEmailForever(@RequestParam int userId, @RequestParam String emailId){
        Session.getInstance().getUser(userId).deleteEmailForEver(emailId);
    }

    @DeleteMapping("/deleteMultipleEmails")
    public void deleteMultipleEmails(@RequestParam int userId, @RequestParam String emailsIds, @RequestParam String folderName){
        User user = Session.getInstance().getUser(userId);
        String[] emailsToBeDeleted = emailsIds.split(",");
        for (String email: emailsToBeDeleted){
            user.deleteEmail(email, folderName);
        }
    }

    //search 
    @GetMapping("/searchFile")
    public String searchFile(@RequestParam int userId, @RequestParam String required, @RequestParam String folderName, @RequestParam String criteria){
        return Session.getInstance().getUser(userId).searchFile(required, folderName, criteria);
    }

    @GetMapping("/searchAll")
    public String searchAll(@RequestParam int userId, @RequestParam String required, @RequestParam String criteria){
        return Session.getInstance().getUser(userId).searchAllFiles(required, criteria);
    }

    //filter
    @GetMapping("/filter")
    public String filter(@RequestParam int userId, @RequestParam String required, @RequestParam String fileName, @RequestParam String criteria){
        return Session.getInstance().getUser(userId).filterFile(required, fileName, criteria);
    }

    //sort
    @GetMapping("/sort")
    public String sort(@RequestParam int userId, @RequestParam String folderName, @RequestParam int sortType, @RequestParam int sortIdntifier){
        Email[] sortedEmails = Session.getInstance().getUser(userId).sortEmails(folderName, sortType, sortIdntifier);
        return JsonEmailConverter.getInstance().arrayOfEmailsToJson(sortedEmails);
    }

    @Autowired
    private FileStorageService fileStorageService;
    
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(MultipartFile[] files) {
        return Arrays.asList(files).stream().map(file -> uploadFile(file, processedEmail.getSender(), processedEmail.getReceivers().toArray(new String[processedEmail.getReceivers().size()]), processedEmail.getId())).collect(Collectors.toList());
    }

    private UploadFileResponse uploadFile(MultipartFile file, String sender, String[]receivers, String id) {
        String fileName = fileStorageService.storeFile(file, sender, receivers, id);
        String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileName).toUriString();

        return new UploadFileResponse(fileName, fileDownloadUrl, file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, @RequestParam int userId, @RequestParam String emailId, HttpServletRequest request) {
        String emailAddress = Session.getInstance().getUser(userId).getUserEmailAddress();
        Resource resource = fileStorageService.loadFileAsResource(fileName, emailAddress, emailId);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if(contentType == null) contentType = "application/octet-stream";

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }
}
