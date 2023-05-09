package br.com.solutions.pocketmonsters.app.http;

import br.com.solutions.pocketmonsters.app.usecase.IndividualityUseCase;
import br.com.solutions.pocketmonsters.pokemon.individuality.Individuality;
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
@RequestMapping("/individuality")
@RequiredArgsConstructor
public class IndividualityResource {
    private final IndividualityUseCase individualityUseCase;

    @GetMapping
    public ResponseEntity<List<Individuality>> findAll() {
        List<Individuality> individualities = individualityUseCase.findAll();
        return ResponseEntity.ok(individualities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Individuality> findById(@PathVariable Long id) {
        return ResponseEntity.ok(individualityUseCase.findById(id));
    }

    @PostMapping
    public ResponseEntity<Individuality> save(@RequestBody Individuality individuality) {
        Individuality entity = individualityUseCase.save(individuality);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();
        return ResponseEntity.created(location).body(entity);
    }

    @PostMapping("/all")
    public ResponseEntity<List<Individuality>> saveAll(@RequestBody List<Individuality> individualities) {
        List<Individuality> entity = individualityUseCase.saveAll(individualities);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/all")
                .buildAndExpand(entity.get(0).getId())
                .toUri();
        return ResponseEntity.created(location).body(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Individuality> update(@PathVariable Long id, @RequestBody Individuality individuality) {
        individuality.setId(id);
        return ResponseEntity.ok(individualityUseCase.update(individuality, id));
    }
}
