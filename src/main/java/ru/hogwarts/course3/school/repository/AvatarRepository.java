package ru.hogwarts.course3.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.course3.school.model.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

}
