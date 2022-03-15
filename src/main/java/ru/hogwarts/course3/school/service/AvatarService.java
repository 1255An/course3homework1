package ru.hogwarts.course3.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.course3.school.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {
    Long uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar findAvatar(Long id);

    List<Avatar> getPageAvatar(int page, int size);
}
