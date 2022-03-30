public class Process {

    private int StartTime, Duration;
    private String command;

    Process(int StartTime, int Duration, String command) {
        this.setStartTime(StartTime);
        this.setDuration(Duration);
        this.setCommand(command);
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
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
