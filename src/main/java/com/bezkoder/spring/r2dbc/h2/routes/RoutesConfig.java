package com.bezkoder.spring.r2dbc.h2.routes;

import com.bezkoder.spring.r2dbc.h2.handler.RoutesHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoutesConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(RoutesHandler handler) {
        return route()
                .path("/api/routes", builder1 -> builder1
                        .nest(GET("/tutorials"),
                                builder2 -> builder2
                                        .GET("/{id}", handler::get)
                                        .GET(handler::getAll))
                        .POST("/tutorials", handler::addTutorial)
                        .build())
                .build();

    }
}
