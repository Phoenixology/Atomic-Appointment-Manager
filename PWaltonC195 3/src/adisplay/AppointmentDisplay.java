package adisplay;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentDisplay {

    private int appointmentId;
    private String userName;
    private String customerName;
    private LocalDateTime start;
    private LocalDateTime end;
    private String type;
    private int customerId;
    private int userId;
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

    private static DateTimeFormatter ddf = DateTimeFormatter.ofPattern("MM-dd-YY");




    public  AppointmentDisplay(int appointmentId, int customerId, int userId, String userName, String customerName, LocalDateTime start, LocalDateTime end, String type) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.userName = userName;
        this.customerName = customerName;
        this.start = start;
        this.end = end;
        this.type = type;
    }
    public int getUserId() {
        return userId;
    }

    public int getCustomerId() {
        return customerId;
    }


    public int getAppointmentId() {
        return appointmentId;
    }

    public String getUserName() {
        return userName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public String getType() {
        return type;
    }

    public String getStartV() {
        String startV =dtf.format(start);
        return startV; }
    public String getEndV() {
        String endV = dtf.format(end);
        return endV;}
    public String getDateV() {
        String dateD =  ddf.format(start);
        return dateD;
    }
}

