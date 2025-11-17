package vn.ledeem.jobhunter.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Value("${ledeem.upload-file.base-uri}")
    private String baseURI;

    public void createDirectory(String folder) throws URISyntaxException {
        URI uri = new URI(folder);
        Path path = Paths.get(uri);
        File tmpDir = new File(path.toString());
        if (!tmpDir.isDirectory()) {
            try {
                Files.createDirectory(tmpDir.toPath());
                System.out.println(">>> CREATE NEW DIRECTORY SUCCESSFUL, PATH = " + tmpDir.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(">>> SKIP MAKING DIRECTORY, ALREADY EXISTS");
        }

    }

    public String store(MultipartFile file, String folder) throws URISyntaxException,
            IOException {
        // create unique filename
        String finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        URI uri = new URI(baseURI + folder + "/" + finalName);
        Path path = Paths.get(uri);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path,
                    StandardCopyOption.REPLACE_EXISTING);
        }
        return finalName;
    }

    public long getFileLength(String fileName, String folder) throws URISyntaxException {

        // Tạo URI từ baseURI + folder + fileName
        URI uri = new URI(baseURI + folder + "/" + fileName);

        // Convert URI → Path
        Path path = Paths.get(uri);

        // Convert Path → File
        File tmpDir = new File(path.toString());

        // Nếu file không tồn tại hoặc là thư mục → trả về 0
        if (!tmpDir.exists() || tmpDir.isDirectory()) {
            return 0;
        }

        // Trả về dung lượng file (bytes)
        return tmpDir.length();
    }

    public InputStreamResource getResource(String fileName, String folder)
            throws URISyntaxException, FileNotFoundException {

        // Tạo URI từ baseURI + folder + fileName
        URI uri = new URI(baseURI + folder + "/" + fileName);

        // Convert URI → Path
        Path path = Paths.get(uri);

        // Convert Path → File
        File file = new File(path.toString());

        // Trả về InputStreamResource
        return new InputStreamResource(new FileInputStream(file));
    }

}
