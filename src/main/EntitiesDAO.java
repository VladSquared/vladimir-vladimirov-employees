/**
 * Class responsible for reading the txt file and turn it into java objects
 *
 * @author Vladimir Vladimirov
 */

package main;

import java.io.File;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EntitiesDAO {

    static List<Entity> findEntitiesFromTextFile(String path) throws FileAnalyzerException {


        File textFile = new File(path);
        Scanner sc;

        try {
            sc = new Scanner(textFile);
        } catch (Exception e) {
            throw new FileAnalyzerException("Cannot read or find the file.");
        }

        List<Entity> entities = new ArrayList<>();

        int lineCounter = 1;

        while (sc.hasNextLine()) {
            String employeeLine = sc.nextLine();
            try {
                entities.add(Entity.getEmployeeFromString(employeeLine));
            } catch (DateTimeParseException e) {
                throw new FileAnalyzerException("Cannot read line " + lineCounter + " - " + e.getMessage());
            }
            lineCounter++;
        }

        return entities;
    }
}
