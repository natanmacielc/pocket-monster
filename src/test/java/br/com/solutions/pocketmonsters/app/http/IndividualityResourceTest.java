package br.com.solutions.pocketmonsters.app.http;

import br.com.solutions.pocketmonsters.pokemon.individuality.Individuality;
import org.assertj.core.util.Arrays;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IndividualityResourceTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private Individuality individuality;

    private static final String URL = "/individuality/";

    @BeforeAll
    void init(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();

        individuality = Individuality.builder()
                .id(1L)
                .name("Charizard")
                .shiny(true)
                .happiness(255.0)
                .gender(Individuality.Gender.MALE)
                .build();
    }

    @Test
    void save() {
        Individuality request = Individuality.builder().id(2L).build();
        HttpEntity<Individuality> httpEntity = new HttpEntity<>(request);

        ResponseEntity<Individuality> response = testRestTemplate
                .exchange(URL, HttpMethod.POST, httpEntity, Individuality.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(request.getId(), response.getBody().getId());
    }

    @Test
    void findAll() {
        ResponseEntity<Individuality[]> response = testRestTemplate
                .exchange(URL, HttpMethod.GET, null, Individuality[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(individuality.getId(), response.getBody()[0].getId());
    }

    @Test
    void findById() {
        ResponseEntity<Individuality> response = testRestTemplate
                .exchange(URL + individuality.getId(), HttpMethod.GET, null, Individuality.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(individuality.getId(), response.getBody().getId());
    }

    @Test
    void saveAll() {
        Individuality[] request = Arrays.array(Individuality.builder().id(3L).build(), Individuality.builder().id(4L).build());
        HttpEntity<Individuality[]> httpEntity = new HttpEntity<>(request);

        ResponseEntity<Individuality[]> response = testRestTemplate
                .exchange(URL + "all", HttpMethod.POST, httpEntity, Individuality[].class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(request[0].getId(), response.getBody()[0].getId());
    }

    @Test
    void update() {
        Individuality request = Individuality.builder().name("Blastoise").build();
        HttpEntity<Individuality> httpEntity = new HttpEntity<>(request);

        ResponseEntity<Individuality> response = testRestTemplate
                .exchange(URL + 1, HttpMethod.PUT, httpEntity, Individuality.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(request.getName(), response.getBody().getName());
    }
}