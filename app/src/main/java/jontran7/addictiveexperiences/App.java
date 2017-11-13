package jontran7.addictiveexperiences;

/**
 * Created by jonat on 10/23/2017.
 */

public class App {
    private String name;
    private float startTime;
    private float duration;

    public App(String name, float startTime, float duration) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }
}
