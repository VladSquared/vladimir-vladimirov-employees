package main;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Predicate;

public class EmployeesAnalyzer {

    static String printEmployeesWorkedMostTogether(String path) throws FileAnalyzerException {

        path = path.toLowerCase();
        if (!path.endsWith(".txt")) {
            throw new FileAnalyzerException("Wrong or incomplete path. You should use path that leads to TXT file");
        }

        List<Entity> entities;

        try {
            entities = EntitiesDAO.findEntitiesFromTextFile(path);
        } catch (FileAnalyzerException e) {
            throw new FileAnalyzerException("There is a problem with your file\n" +
                    "reason: " + e.getMessage());
        }

        if (entities.size() < 2) {
            throw new FileAnalyzerException("Your file should contain more than 2 entities");
        }

        checkForOverlappingEntities(entities);

        class DaysCounter implements Comparable<DaysCounter>{
            int totalDays;
            int weekendDays;

            public DaysCounter(int totalDays, int weekendDays) {
                this.totalDays = totalDays;
                this.weekendDays = weekendDays;
            }

            @Override
            public int compareTo(DaysCounter o) {
                return (this.totalDays > o.totalDays)? 1 : -1;
            }
        }

        HashMap<String, DaysCounter> coupleHours = new HashMap<>();

        for (int i = 0; i < entities.size() - 1; i++) {
            for (int j = i + 1; j < entities.size(); j++) {

                if (entities.get(i).getEmpId() == entities.get(j).getEmpId()){
                    continue;
                }

                if (entities.get(i).getProjectId() == entities.get(j).getProjectId()){

                    int workedTogether = findMatchingPeriodInDays(entities.get(i), entities.get(j));
                    int weekendDays = findWeekendDaysInPeriodInDays(entities.get(i), entities.get(j));

                    String couple = entities.get(i).getEmpId() + " and " + entities.get(j).getEmpId();
                    String reverseCouple = entities.get(j).getEmpId() + " and " + entities.get(i).getEmpId();
                    if (!coupleHours.containsKey(couple) && !coupleHours.containsKey(reverseCouple)){
                        coupleHours.put(couple, new DaysCounter(0, 0));
                    }
                    if (coupleHours.containsKey(reverseCouple)){
                        coupleHours.get(reverseCouple).totalDays = coupleHours.get(reverseCouple).totalDays + workedTogether;
                        coupleHours.get(reverseCouple).weekendDays = coupleHours.get(reverseCouple).weekendDays + weekendDays;
                    }else {
                        coupleHours.get(couple).totalDays = coupleHours.get(couple).totalDays + workedTogether;
                        coupleHours.get(couple).weekendDays = coupleHours.get(couple).weekendDays + weekendDays;
                    }
                }
            }
        }

        TreeMap<DaysCounter, String> orderedByHoursEntities = new TreeMap<>();
        Iterator it = coupleHours.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, DaysCounter> entry = (Map.Entry)it.next();

            orderedByHoursEntities.put(entry.getValue(), entry.getKey());

        }

        Map.Entry<DaysCounter, String> winningCouple = orderedByHoursEntities.lastEntry();

        String response = "Here are no employees that had worked together.";
        if (winningCouple != null) {
            if(winningCouple.getKey().totalDays != 0) {
                response = "\nThe employees that had spent the most time working together are with IDs "
                        + winningCouple.getValue()
                        + " they had worked: \n"
                        + winningCouple.getKey().totalDays
                        + " total days on common projects,\n"
                        + "from which "
                        + winningCouple.getKey().weekendDays
                        + " are days from weekends.";
            }
        }

        return response;
    }

    private static int findMatchingPeriodInDays(Entity entity1, Entity entity2){

        LocalDate[] dates = findStartAndEndDates(entity1, entity2);

        LocalDate startingDate = dates[0];
        LocalDate endingDate = dates[1];

        Duration duration = Duration.between(startingDate.atStartOfDay(), endingDate.atStartOfDay());
        int diff = (int) duration.toDays() + 1; // including ending date
        if (diff < 0){
            diff = 0;
        }

        return diff;
    }

    //first date is starting, second is ending
    //is no matching period -> endDate will be before startDate
    private static LocalDate[] findStartAndEndDates(Entity entity1, Entity entity2){

        LocalDate startingDate;
        LocalDate endingDate;

        if (entity1.getDateFrom().isBefore(entity2.getDateFrom())){
            startingDate = entity2.getDateFrom();
        }else{
            startingDate = entity1.getDateFrom();
        }

        if (entity1.getDateTo().isAfter(entity2.getDateTo())){
            endingDate = entity2.getDateTo();
        }else{
            endingDate = entity1.getDateTo();
        }

        LocalDate[] response = {startingDate, endingDate};

        return response;
    }

    private static int findWeekendDaysInPeriodInDays(Entity entity1, Entity entity2){

        LocalDate[] dates = findStartAndEndDates(entity1, entity2);

        LocalDate startDate = dates[0];
        LocalDate endDate = dates[1];

        LocalDate iterateDate = startDate;
        int weekendDays = 0;

        while (iterateDate.isBefore(endDate) || iterateDate.equals(endDate) ){
            DayOfWeek day = iterateDate.getDayOfWeek();
            if (day.getValue() == 6 || day.getValue() == 7){
                weekendDays++;
            }
            iterateDate = iterateDate.plusDays(1);
        }

        return weekendDays;
    }

    private static void checkForOverlappingEntities(List<Entity> entities) throws FileAnalyzerException{

        for (int i = 0; i < entities.size() - 1; i++) {
            for (int j = i + 1; j < entities.size(); j++) {

                if (entities.get(i).getEmpId() == entities.get(j).getEmpId()
                    && entities.get(i).getProjectId() == entities.get(j).getProjectId()){

                    int days = findMatchingPeriodInDays(entities.get(i), entities.get(j));

                    if (days > 0){
                        throw new FileAnalyzerException("Overlapping dates in entities for the same employee and project");
                    }
                }
            }
        }
    }
}
