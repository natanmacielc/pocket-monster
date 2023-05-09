package br.com.solutions.pocketmonsters.app.http;

import br.com.solutions.pocketmonsters.app.usecase.BaseStatUseCase;
import br.com.solutions.pocketmonsters.pokemon.skill.BaseStat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/baseStat")
@RequiredArgsConstructor
public class BaseStatResource {
    private final BaseStatUseCase baseStatUseCase;

    @PostMapping
    public ResponseEntity<List<BaseStat>> saveAll(@RequestBody List<BaseStat> baseStatList) {
        List<BaseStat> entities = baseStatUseCase.saveAll(baseStatList);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entities.get(0).getId())
                .toUri();
        return ResponseEntity.created(location).body(entities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseStat> findById(@PathVariable Long id) {
        return ResponseEntity.ok(baseStatUseCase.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<BaseStat>> findAll() {
        List<BaseStat> baseStatList = baseStatUseCase.findAll();
        return ResponseEntity.ok(baseStatList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseStat> update(@PathVariable Long id, @RequestBody BaseStat baseStat) {
        baseStat.setId(id);
        return ResponseEntity.ok(baseStatUseCase.update(baseStat, id));
    }
}
