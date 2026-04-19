package com.ngocrong.model;

public class LegacySideTask {

    public LegacySideTaskTemplate template;
    public int count;
    public int maxCount;
    public int level;
    public int leftTask;
    public long receivedTime;
    public boolean notify0;
    public boolean notify10;
    public boolean notify20;
    public boolean notify30;
    public boolean notify40;
    public boolean notify50;
    public boolean notify60;
    public boolean notify70;
    public boolean notify80;
    public boolean notify90;

    public LegacySideTask() {
        this.leftTask = 30;
    }

    public boolean isActive() {
        return this.template != null;
    }

    public boolean isDone() {
        return this.template != null && this.count >= this.maxCount;
    }

    public int getPercentProcess() {
        if (this.template == null || this.maxCount <= 0) {
            return 0;
        }
        if (this.count >= this.maxCount) {
            return 100;
        }
        return (int) ((long) this.count * 100 / this.maxCount);
    }

    public String getName() {
        if (this.template == null) {
            return "Hiện tại không có nhiệm vụ nào";
        }
        return this.template.name.replace("%1", String.valueOf(this.maxCount));
    }

    public String getLevelName() {
        switch (this.level) {
            case 0:
                return "Dễ";
            case 1:
                return "Bình thường";
            case 2:
                return "Khó";
            case 3:
                return "Siêu khó";
            case 4:
                return "Địa ngục";
            default:
                return "";
        }
    }

    public void clearCurrentTask() {
        this.template = null;
        this.count = 0;
        this.level = 0;
        resetNotifyFlags();
    }

    public void syncNotifyFlags() {
        int percent = getPercentProcess();
        this.notify0 = this.count > 0;
        this.notify10 = percent >= 10;
        this.notify20 = percent >= 20;
        this.notify30 = percent >= 30;
        this.notify40 = percent >= 40;
        this.notify50 = percent >= 50;
        this.notify60 = percent >= 60;
        this.notify70 = percent >= 70;
        this.notify80 = percent >= 80;
        this.notify90 = percent >= 90;
    }

    public void resetNotifyFlags() {
        this.notify0 = false;
        this.notify10 = false;
        this.notify20 = false;
        this.notify30 = false;
        this.notify40 = false;
        this.notify50 = false;
        this.notify60 = false;
        this.notify70 = false;
        this.notify80 = false;
        this.notify90 = false;
    }
}
