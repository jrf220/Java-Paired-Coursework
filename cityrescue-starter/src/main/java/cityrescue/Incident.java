public class Incident{
    private String incidentType;
    private int incidentID;
    private static int numberOfIncidents = 0;
    private int severity = 1;
    private int incidentStatus;

    public void Incident(String incidentType){
        this.incidentType = incidentType;
        incidentID = ++numberOfIncidents;
    }

    public String getIncidentType() {return this.incidentType;}
    public int getIncidentID() {return this.incidentID;}
    public static int getNumberOfIncidents() {return numberOfIncidents;}
    public String getIncidentStatus() {return incidentStatus;}
    public void setIncidentStatus(String incidentStatus) {this.incidentStatus = incidentStatus;}
    
    public void cancelIncident(){
        if (incidentStatus == "REPORTED" || incidentStatus == "DISPATCHED"){
            this.setIncidentStatus("CANCELLED");
                }
    }

}

