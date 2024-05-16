package engine.jobs.mcclicker;

import java.util.Timer;

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