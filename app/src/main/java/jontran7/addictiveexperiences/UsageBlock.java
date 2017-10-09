package jontran7.addictiveexperiences;

/**
 * Created by jonat on 8/23/2017.
 */

public class UsageBlock {
    private int duration;
    private String packageName;
    private int lastUsed;
    private int isActivated;


    public UsageBlock(String packageName, int duration, int lastUsed) {
        this.duration = duration;
        this.packageName = packageName;
        this.lastUsed = lastUsed;
    }

    public int getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(int isActivated) {
        this.isActivated = isActivated;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(int lastUsed) {
        this.lastUsed = lastUsed;
    }
}
