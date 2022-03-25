package ru.hogwarts.course3.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class InfoServiceTest implements InfoSerivce {

    @Value("${server.port}")
    private int port;

    @Override
    public int getPort() {
        return port;
    }
}
