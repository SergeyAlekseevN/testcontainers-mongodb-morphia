package bz.alekseev.code.testcontainers.morphia;

import com.mongodb.DB;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by Sergey Alekseev on 26.02.19.
 */
@Testcontainers
class MorphiaContainerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MorphiaContainerTest.class);

    @Container
    public static final MorphiaContainer morphia = new MorphiaContainer("mongo:3.4.19")
            .withLogConsumer(new Slf4jLogConsumer(LOGGER));

    @Test
    void checkVersionMongoDbTest() {
        String version = morphia.getDB().command("buildInfo").getString("version");
        assertEquals("3.4.19", version);
    }

    @Test
    void getMorphia() {
        assertNotNull(morphia.getMorphia());
    }

    @Test
    void getMongoClient() {
        assertNotNull(morphia.getMongoClient());
    }

    @Test
    void getDB() {
        final DB db = morphia.getDB();
        assertNotNull(db);
        assertEquals(MorphiaContainer.DEFAULT_DB_NAME, db.getName());
    }

    @Test
    void getDatastore() {
        assertNotNull(morphia.getDatastore());
    }
}