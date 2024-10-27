public class CrimeIncident {
    private String reportDate;
    private String shift;
    private String method;
    private String offense;
    private String block;
    private String bid;

    public CrimeIncident(String reportDate, String shift, String method, String offense, String block, String bid) {
        this.reportDate = reportDate;
        this.shift = shift;
        this.method = method;
        this.offense = offense;
        this.block = block;
        this.bid = bid;
    }

    public String getReportDate() { return reportDate; }
    public String getShift() { return shift; }
    public String getMethod() { return method; }
    public String getOffense() { return offense; }
    public String getBlock() { return block; }
    public String getBid() { return bid; }

    @Override
    public String toString() {
        return "Offense: " + offense + ", Shift: " + shift + ", Method: " + method + ", Location: " + block;
    }
}
