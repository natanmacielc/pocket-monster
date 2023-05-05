package br.com.solutions.pocketmonsters.app.http;

import br.com.solutions.pocketmonsters.app.usecase.StatsUseCase;
import br.com.solutions.pocketmonsters.pokemon.skill.Stats;
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
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsResource {

    private final StatsUseCase statsUseCase;

    @GetMapping
    public ResponseEntity<List<Stats>> findAll() {
        return ResponseEntity.ok(statsUseCase.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stats> findById(@PathVariable Long id) {
        return ResponseEntity.ok(statsUseCase.findById(id));
    }

    @PostMapping
    public ResponseEntity<Stats> save(@RequestBody Stats stats) {
        Stats entity = statsUseCase.save(stats);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();
        return ResponseEntity.created(location).body(entity);
    }

    @PostMapping("/all")
    public ResponseEntity<List<Stats>> saveAll(@RequestBody List<Stats> statsList) {
        List<Stats> entity = statsUseCase.saveAll(statsList);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.get(0).getId())
                .toUri();
        return ResponseEntity.created(location).body(entity);
    }

    @PutMapping
    public ResponseEntity<Stats> update(@RequestBody Stats stats) {
        Stats entity = statsUseCase.update(stats);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();
        return ResponseEntity.created(location).body(entity);
    }
}
