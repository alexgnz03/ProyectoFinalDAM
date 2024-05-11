package engine.jobs.mcClicker;

import java.util.Timer;
import java.util.TimerTask;

public class ClickerModel {
    private int clickCount;
    private Timer timer;
    private int secondsLeft;
    private ClickerController controller;

    public ClickerModel(ClickerController controller) {
        this.controller = controller;
    }

    public void incrementClickCount() {
        clickCount++;
        controller.updateClickCount(clickCount);
    }
}