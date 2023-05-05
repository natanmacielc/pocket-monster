package br.com.solutions.pocketmonsters.app.http;

import br.com.solutions.pocketmonsters.pokemon.skill.BaseStat;
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
class BaseStatResourceTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final String URL = "/baseStat/";

    @BeforeAll
    void init(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void saveAll() {
        BaseStat[] request = Arrays.array(BaseStat.builder().id(2L).build(), BaseStat.builder().id(3L).build());
        HttpEntity<BaseStat[]> httpEntity = new HttpEntity<>(request);

        ResponseEntity<BaseStat[]> response = testRestTemplate
                .exchange(URL, HttpMethod.POST, httpEntity, BaseStat[].class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(request[0].getId(), response.getBody()[0].getId());
    }

    @Test
    void findById() {
        BaseStat request = BaseStat.builder().id(1L).build();
        ResponseEntity<BaseStat> response = testRestTemplate
                .exchange(URL + request.getId(), HttpMethod.GET, null, BaseStat.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(request.getId(), response.getBody().getId());
    }

    @Test
    void findAll() {
        BaseStat request = BaseStat.builder().id(1L).build();
        ResponseEntity<BaseStat[]> response = testRestTemplate
                .exchange(URL, HttpMethod.GET, null, BaseStat[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(request.getId(), response.getBody()[0].getId());
    }
}