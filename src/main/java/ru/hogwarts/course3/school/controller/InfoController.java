package ru.hogwarts.course3.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.course3.school.service.InfoSerivce;

@RestController
@RequestMapping("/info")
public class InfoController {

    Logger logger = LoggerFactory.getLogger(InfoController.class);

    private final InfoSerivce infoService;

    public InfoController(InfoSerivce infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/port")
    public int getPort () {
        logger.info("Requesting port");
        int port = infoService.getPort();
        logger.debug ("Requesting port is {}", port);
        return port;
    }
}
