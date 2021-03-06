package pl.javastart.daogenerator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    private static final String PACKAGE = "pl.javastart.ap.database.greendao.database";

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "greendao");
        schema.setDefaultJavaPackageDao(PACKAGE);
        Entity user = schema.addEntity("User");
        user.setJavaPackage(PACKAGE);
        user.addIdProperty();
        user.addStringProperty("name");
        user.addStringProperty("surname");

        new DaoGenerator().generateAll(schema, args[0]);
    }
}
