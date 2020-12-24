package com.example.mailserver.Logic.operationsHandlers;

import com.example.mailserver.Logic.Attachments.*;
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

@RestController
public class AttachmentsHandler {

    @Autowired
    private FileStorageService fileStorageService;
    
    public UploadFileResponse uploadFile(MultipartFile file, String sender, String[]receivers, String id) {
        String fileName = fileStorageService.storeFile(file, sender, receivers, id);
        String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileName).toUriString();

        return new UploadFileResponse(fileName, fileDownloadUrl, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,@RequestParam("sender") String sender,@RequestParam("receivers") String[] receivers,@RequestParam("id") String id) {
        return Arrays.asList(files).stream().map(file -> uploadFile(file, sender, receivers, id)).collect(Collectors.toList());
    }

    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request, String emailAddress, String id) {
        Resource resource = fileStorageService.loadFileAsResource(fileName, emailAddress, id);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }
}