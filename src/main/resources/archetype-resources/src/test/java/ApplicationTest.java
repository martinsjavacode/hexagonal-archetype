package ${package};

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.flyway.enabled=false",
    "spring.r2dbc.url=r2dbc:h2:mem:///testdb-test"
})
class ApplicationTest {

    @Test
    void contextLoads() {
    }
}
