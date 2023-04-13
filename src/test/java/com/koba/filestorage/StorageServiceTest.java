package com.koba.filestorage;

import com.koba.filestorage.service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StorageServiceTest {

    @InjectMocks
    private StorageService storageService;

    @BeforeEach
    public void beforeEach() throws IOException {

        String filename = "test.txt";
        File file = new File("./media/"+filename);
        FileWriter writer = new FileWriter(file);
        writer.write("hello test");
        writer.close();

    }

    @Test
    public void asserEqualFileExistsTrueTest(){
        assertTrue(storageService.checkFileExists("./test.txt"));
    }
    @Test
    public void asserEqualFileExistsFalseTest(){
        assertFalse(storageService.checkFileExists("./test1.txt"));
    }

}
