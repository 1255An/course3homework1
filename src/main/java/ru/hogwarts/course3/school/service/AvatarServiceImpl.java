package ru.hogwarts.course3.school.service;

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
        Student student = studentService.findStudent(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student with id " + studentId + " not found");
        }
        Path filePath = createImageFilePath(avatarFile, student);
        saveImageToFile(avatarFile, filePath);
        Avatar avatar = findOrCreateAvatar(studentId);
        updateAvatar(avatarFile, student, filePath, avatar);
        return avatarRepository.save(avatar).getId();
    }

    @Override
    public Avatar findAvatar(Long id) {
        return avatarRepository.getById(id);
    }

    @Override
    public List<Avatar> getPageAvatar(int page, int size) {
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

