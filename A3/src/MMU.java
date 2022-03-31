public class MMU extends Thread{
    private int NumOfCores;
    private int NumOfProcess;
    private int NumOfPages;

    MMU(int NumOfCores, int NumOfProcess, int NumOfPages) {
        this.NumOfCores = NumOfCores;
        this.NumOfProcess = NumOfProcess;
        this.NumOfPages = NumOfPages;
    }
}
