package com.bezkoder.spring.r2dbc.h2.handler;

import com.bezkoder.spring.r2dbc.h2.model.Tutorial;
import com.bezkoder.spring.r2dbc.h2.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class RoutesHandler {

    @Autowired
    TutorialService tutorialService;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok().body(tutorialService.findAll(), Tutorial.class);
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        return ServerResponse.ok().body(tutorialService.findById(id), Tutorial.class);
    }

    public Mono<ServerResponse> addTutorial(ServerRequest request) {
        return ServerResponse.ok().body(request.bodyToMono(Tutorial.class)
                .flatMap(tutorialService::save), Tutorial.class);
    }

}
