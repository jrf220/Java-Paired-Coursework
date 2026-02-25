public class Incident{
    private String incidentType;
    private int incidentID;
    private static int numberOfIncidents = 0;

    public void Incident(String incidentType){
        this.incidentType = incidentType;
        incidentID = ++numberOfIncidents;
    }

    public String getIncidentType(){
        return this.incidentType;
    }
    public void setIncidentType(String incidentType){
        this.incidentType = incidentType;
    }
    public int getIncidentID(){
        return this.incidentID;
    }
    public static int getNumberOfIncidents(){
        return numberOfIncidents;
    }
}