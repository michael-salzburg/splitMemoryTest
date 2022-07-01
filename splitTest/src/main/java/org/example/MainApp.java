package org.example;

import org.apache.camel.main.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A Camel Application
 */
@SpringBootApplication
public class MainApp {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
//    public static void main(String... args) throws Exception {
//        Main main = new Main();
//        main.configure().addRoutesBuilder(new MyRouteBuilder());
//        main.run(args);
//    }

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

}

