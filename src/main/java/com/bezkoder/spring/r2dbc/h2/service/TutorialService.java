package com.bezkoder.spring.r2dbc.h2.service;

import java.util.Optional;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bezkoder.spring.r2dbc.h2.model.Tutorial;
import com.bezkoder.spring.r2dbc.h2.repository.TutorialRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TutorialService {

  @Autowired
  TutorialRepository tutorialRepository;

  public Flux<Tutorial> findAll() {
    return tutorialRepository.findAll();
  }

  public Flux<Tutorial> findByTitleContaining(String title) {
    return tutorialRepository.findByTitleContaining(title);
  }

  public Mono<Tutorial> findById(int id) {
    return tutorialRepository.findById(id);
  }

  public Mono<Tutorial> save(Tutorial tutorial) {
    return tutorialRepository.save(tutorial);
  }

  public Mono<Tutorial> update(int id, Tutorial tutorial) {
    BiFunction<String, String, String>  process = (old, nuevo) -> Optional.ofNullable(nuevo)
                                                                        .orElse(old);

    return tutorialRepository.findById(id)
            .map(t -> {
              t.setTitle(process.apply(t.getTitle(), tutorial.getTitle()));
              t.setDescription(process.apply(t.getDescription(), tutorial.getDescription()));
              t.setPublished(t.getPublished() || tutorial.getPublished());

              return t;
            })
            .flatMap(t -> tutorialRepository.save(t));
  }

  public Mono<Void> deleteById(int id) {
    return tutorialRepository.deleteById(id);
  }

  public Mono<Void> deleteAll() {
    return tutorialRepository.deleteAll();
  }

  public Flux<Tutorial> findByPublished(boolean isPublished) {
    return tutorialRepository.findByPublished(isPublished);
  }
}
