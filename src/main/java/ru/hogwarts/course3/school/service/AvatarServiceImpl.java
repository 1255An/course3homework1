package ru.hogwarts.course3.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.course3.school.model.Avatar;
import ru.hogwarts.course3.school.model.Student;
import ru.hogwarts.course3.school.repository.AvatarRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {

    private static final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    @Value("/avatars")
    private String avatarsDir;

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;


    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    @Override
    public Long uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Method for uploading avatar was invoked");
        Student student = studentService.findStudent(studentId);
        if (student == null) {
            String errMsg = "Student with id " + studentId + " not found";
            logger.error(errMsg);
            throw new IllegalArgumentException(errMsg);
        }
        Path filePath = createImageFilePath(avatarFile, student);
        saveImageToFile(avatarFile, filePath);
        Avatar avatar = findOrCreateAvatar(studentId);
        updateAvatar(avatarFile, student, filePath, avatar);
        logger.info("Avatar for student with id: {} was upload", studentId);
        return avatarRepository.save(avatar).getId();
    }

    @Override
    public Avatar findAvatar(Long id) {
        logger.info("Method for finding avatar was invoked");
        return avatarRepository.getById(id);
    }

    @Override
    public List<Avatar> getPageAvatar(int page, int size) {
        logger.info("Method for getting avatars page {} of size {} was invoked", page, size);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return avatarRepository.findAll(pageRequest).toList();
    }

    private Path createImageFilePath(MultipartFile avatarFile, Student student) throws IOException {
        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        return filePath;
    }

    private void saveImageToFile(MultipartFile avatarFile, Path filePath) throws IOException {
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
    }

    private Avatar findOrCreateAvatar(Long id) {
        return avatarRepository.findById(id).orElse(new Avatar());
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private void updateAvatar(MultipartFile avatarFile, Student student, Path filePath, Avatar avatar) throws IOException {
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
    }
}

