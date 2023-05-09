package br.com.solutions.pocketmonsters.app.http;

import br.com.solutions.pocketmonsters.pokemon.skill.Stats;
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
class StatsResourceTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final String URL = "/stats/";

    @BeforeAll
    void init(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void findAll() {
        Stats request = Stats.builder().id(1L).build();
        ResponseEntity<Stats[]> response = testRestTemplate
                .exchange(URL, HttpMethod.GET, null, Stats[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(request.getId(), response.getBody()[0].getId());
    }

    @Test
    void findById() {
        Stats request = Stats.builder().id(1L).build();
        ResponseEntity<Stats> response = testRestTemplate
                .exchange(URL + request.getId(), HttpMethod.GET, null, Stats.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(request.getId(), response.getBody().getId());
    }

    @Test
    void save() {
        Stats request = Stats.builder().id(2L).build();
        HttpEntity<Stats> httpEntity = new HttpEntity<>(request);

        ResponseEntity<Stats> response = testRestTemplate
                .exchange(URL, HttpMethod.POST, httpEntity, Stats.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(request.getId(), response.getBody().getId());
    }

    @Test
    void saveAll() {
        Stats[] request = Arrays.array(Stats.builder().id(3L).build(), Stats.builder().id(4L).build());
        HttpEntity<Stats[]> httpEntity = new HttpEntity<>(request);

        ResponseEntity<Stats[]> response = testRestTemplate
                .exchange(URL + "all", HttpMethod.POST, httpEntity, Stats[].class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(request[0].getId(), response.getBody()[0].getId());
    }

    @Test
    void update() {
        Stats request = Stats.builder().total(200.0).build();
        HttpEntity<Stats> httpEntity = new HttpEntity<>(request);

        ResponseEntity<Stats> response = testRestTemplate
                .exchange(URL + 1, HttpMethod.PUT, httpEntity, Stats.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(request.getTotal(), response.getBody().getTotal());
    }
}