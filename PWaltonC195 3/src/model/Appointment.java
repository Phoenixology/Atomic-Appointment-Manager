package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Appointment {
    public static int NOID = -1;
    private int appointmentId;

    private int customerId;
    private int userId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private LocalDateTime start;
    private LocalDateTime end;
    public static ObservableList<String> allTypes = FXCollections.observableArrayList("Presentation", "Speech", "Scrum Session", "Mastermind", "Star Chamber Intro", "Deprogramming", "Manifesting", "Coaching", "Design your Destiny", "Harrier-Hawk Overview", "Cultural Re-framing");


    public Appointment(int appointmentId,

                       int customerId,
                       int userId,
                       String title,
                       String description,
                       String location,
                       String contact,
                       String type,
                       String url,
                       LocalDateTime start,
                       LocalDateTime end
    ) {
        this.appointmentId = appointmentId;

        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;


    }

    public Appointment(

            int customerId,
            int userId,
            String title,
            String description,
            String location,
            String contact,
            String type,
            String url,
            LocalDateTime start,
            LocalDateTime end
    ) {
        this.appointmentId = NOID;

        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;


    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }


    @Override
    public String toString() {
        return (Integer.toString(appointmentId) + " " + customerId + " " + userId + " " + title + " " + description + " " + location + " " + contact + " " + type + " " + url + " " + start + " " + end);
    }

    public static boolean appointmentInWeek(Appointment aW) {

                LocalDateTime startDay = LocalDateTime.now();
                startDay.getDayOfWeek();
               while (startDay.getDayOfWeek() != DayOfWeek.MONDAY){
                   startDay = startDay.minusDays(1);
               }
               boolean found = false;

               do {
                   if ( startDay.getYear() == aW.getStart().getYear() &&
                           startDay.getMonth() == aW.getStart().getMonth() &&
                           startDay.getDayOfMonth() == aW.getStart().getDayOfMonth()) {

                       found = true;
                       break;

                   }


                   startDay = startDay.plusDays(1);
               }// end do
               while  (startDay.getDayOfWeek() != DayOfWeek.MONDAY);

               if (!found ){
                   return false;

               }


        return true;
    }

    public static boolean appointmentInMonth(Appointment aM) {
        LocalDateTime today = LocalDateTime.now();
        if (aM.getStart().getYear() != today.getYear() || aM.getStart().getMonth() != today.getMonth()) {
            return false;

        }
        return true;



    }
}