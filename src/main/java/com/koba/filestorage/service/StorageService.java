package com.koba.filestorage.service;

import com.koba.filestorage.entity.Storage;
import com.koba.filestorage.repository.StorageRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StorageService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final StorageRepository storageRepository;

    @Autowired
    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    public List<Storage> getStorages(){
        return  storageRepository.findAll();
    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {


//        Optional<Storage> savedfile = storageRepository.findByFilename(multipartFile.getOriginalFilename());
//        if(savedfile.isPresent()){
//            logger.info("same file name is already stored");
//            throw new IllegalStateException("same file name is already stored");
//        }

        File file = new File("./media/" + multipartFile.getOriginalFilename());
        byte[] bytes = multipartFile.getBytes();
        Files.write(file.toPath(), bytes);
        //Storage newfile = new Storage();
        //newfile.setFilename(multipartFile.getOriginalFilename());
        //Storage newStorage = storageRepository.save(newfile);
        return file.getName();

    }

    public String deleteFile(String filename) throws IOException {

        File file = new File("./media/" + filename);
        if(file.exists()){
            Files.deleteIfExists(file.toPath());
            return "success";
        }else {
            throw new FileNotFoundException("file not found");
        }
    }

    public MediaType getContentType(Path path) throws IOException {

        return MediaType.parseMediaType(Files.probeContentType(path));

    }

    public boolean checkFileExists(String filename){
        File file = new File("./media/"+filename);
        return file.exists();
    }
    public List<List<String>> getAllFiles() throws IOException {

        File files = new File("./media");
        List<List<String>> filenames = new ArrayList<>();

        for(File file : files.listFiles()){
            ArrayList<String> tmplist = new ArrayList<>();
            tmplist.add(file.getName());
            FileTime creationTime = (FileTime) Files.getAttribute(file.toPath(), "creationTime");
            tmplist.add(creationTime.toString());
            filenames.add(tmplist);
        };

        return filenames;

    }
    public boolean isFileExist(String fileName) {
        File file = new File("./media" + "/" + fileName);
        return file.exists();
    }

}
