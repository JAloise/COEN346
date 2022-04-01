public class Process {

    private int StartTime, Duration;
    private int PID = 0;
    Process(int StartTime, int Duration) {
        this.StartTime = StartTime;
        this.Duration = Duration;
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        this.Duration = duration;
    }

    public int getStartTime() {
        return StartTime;
    }

    public void setStartTime(int startTime) {
        this.StartTime = startTime;
    }
}
