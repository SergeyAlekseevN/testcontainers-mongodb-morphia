package bz.alekseev.code.testcontainers.morphia;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.testcontainers.containers.GenericContainer;
import xyz.morphia.Datastore;
import xyz.morphia.Morphia;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 * Created by Sergey Alekseev on 25.02.19.
 */
public class MorphiaContainer extends GenericContainer<MorphiaContainer> {
    public static final String DEFAULT_DB_NAME = "db";
    public static final String DEFAULT_MONGODB_IMAGE = "mongo:latest";
    private static final int PORT = 27017;
    private final Morphia morphia;
    private Datastore datastore = null;

    public MorphiaContainer(String mongoDbImage) {
        super(mongoDbImage);
        morphia = new Morphia();
    }

    public Morphia getMorphia() {
        return morphia;
    }

    @Override
    public void start() {
        super.start();
        ServerAddress localhost = new ServerAddress("localhost", getMappedPort(PORT));
        List<ServerAddress> serverAddresses = Collections.singletonList(localhost);
        datastore = morphia.createDatastore(new MongoClient(serverAddresses), DEFAULT_DB_NAME);
    }

    @Override
    protected void configure() {
        super.configure();
        setExposedPorts(Collections.singletonList(PORT));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MorphiaContainer)) return false;
        if (!super.equals(o)) return false;
        MorphiaContainer that = (MorphiaContainer) o;
        return Objects.equals(getMorphia(), that.getMorphia()) &&
                Objects.equals(getDatastore(), that.getDatastore());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMorphia(), getDatastore());
    }

    public MongoClient getMongoClient() {
        return datastore == null ? null : datastore.getMongo();
    }

    public DB getDB() {
        return datastore == null ? null : datastore.getDB();
    }

    public Datastore getDatastore() {
        return datastore;
    }

    public MorphiaContainer mapPackage(String packageName) {
        morphia.mapPackage(packageName);
        return self();
    }

    public MorphiaContainer mapPackage(String packageName, boolean ignoreInvalidClasses) {
        morphia.mapPackage(packageName, ignoreInvalidClasses);
        return self();
    }

    public MorphiaContainer mapPackageFromClass(Class clazz) {
        morphia.mapPackageFromClass(clazz);
        return self();
    }

    public MorphiaContainer map(Class... entityClasses) {
        morphia.map(entityClasses);
        return self();
    }

    public MorphiaContainer map(Set<Class> entityClasses) {
        morphia.map(entityClasses);
        return self();
    }
}
