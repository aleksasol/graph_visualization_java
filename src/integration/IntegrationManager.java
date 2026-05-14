package integration;

import model.Graph;
import java.io.File;

public class IntegrationManager {

    public Graph runCProgram(String inputFilePath) {
        Graph resultGraph = new Graph();

        try {
            ProcessBuilder builder = new ProcessBuilder("./main.exe", inputFilePath);
            builder.directory(new File("engine"));

            System.out.println("Uruchom program C");
            Process process = builder.start();

            process.waitFor();
            System.out.println("Koniec pracy programu w C");

        } catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage());
        }

        return resultGraph;
    }
}