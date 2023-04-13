package com.koba.filestorage.controller;
import com.koba.filestorage.bo.ExceptionResponseBo;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.koba.filestorage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("storage")
public class storageController {

    private final StorageService storageService;

    @Autowired
    public storageController(StorageService storageService) {
        this.storageService = storageService;
    }


    @PostMapping(path="/api/v1/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        try {
            if (multipartFile.isEmpty()) {
                return new ResponseEntity<>(new ExceptionResponseBo("500", "file is empty"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(storageService.uploadFile(multipartFile), HttpStatus.OK);
        } catch (IOException ex) {
            return new ResponseEntity<>(new ExceptionResponseBo("500", ex.toString()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping (path="/api/v1/delete/{filename}")
    public @ResponseBody ResponseEntity<?> deleteFile(@PathVariable String filename) {

        try {
            return new ResponseEntity<>(storageService.deleteFile(filename), HttpStatus.NO_CONTENT);
        } catch (IOException e) {
            return new ResponseEntity(new ExceptionResponseBo("500",e.toString()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/api/v1/download/{filename}")
    public @ResponseBody ResponseEntity<?> downloadFile(@PathVariable String filename)  {

        try {
            Path path = Paths.get("./media/"+filename);
            Resource resource = new PathResource(path);

            return ResponseEntity.ok().contentType(storageService.getContentType(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException e) {
            return new ResponseEntity<>(new ExceptionResponseBo("500", e.toString()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/api/v1/files")
    public @ResponseBody ResponseEntity<?> getAllFileNames() throws IOException {
        return new ResponseEntity<>(storageService.getAllFiles(),HttpStatus.OK);
    }

    @RequestMapping(value = "/api/v1/file/{filename}", method=RequestMethod.HEAD)
    public @ResponseBody ResponseEntity<?> checkFileExists(@PathVariable String filename){

        try{
            if(storageService.checkFileExists(filename)){
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.notFound().build();
            }

        }catch(Exception e){

            return new ResponseEntity<>(new ExceptionResponseBo("500",e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @GetMapping("")
    public String index(Model model) throws IOException {

        model.addAttribute("files", storageService.getAllFiles());
        return "index";
    }

}
