package com.ngocrong.consts;

public final class TransformSkill {

    public static final short CAST_EFFECT_SKILL_ID = 97;

    public static final byte[][] AURA = {
            {6, 7, 9, 30, 31},
            {6, 7, 9, 30, 31},
            {6, 7, 9, 30, 31}
    };

    public static final short[][] HEAD = {
            {1400, 1401, 1402, 1403, 1404},
            {1405, 1406, 1407, 1408, 1409},
            {1410, 1411, 1412, 1413, 1414}
    };

    public static final short[] BODY = {1394, 1396, 1398};
    public static final short[] LEG = {1395, 1397, 1399};
    public static final short[][] ITEM_TIME_ICON = {
            {31254, 31256, 31257, 31258, 31259},
            {31267, 31268, 31269, 31270, 31271},
            {31261, 31262, 31263, 31264, 31265}
    };

    private TransformSkill() {
    }

    public static int getBonusPercent(int level) {
        return (Math.max(1, level) + 3) * 10;
    }

    public static short getItemTimeIcon(int gender, int level) {
        int genderIndex = Math.max(0, Math.min(ITEM_TIME_ICON.length - 1, gender));
        int stageIndex = Math.max(0, Math.min(ITEM_TIME_ICON[genderIndex].length - 1, level - 1));
        return ITEM_TIME_ICON[genderIndex][stageIndex];
    }

    public static int getItemTimeLevel(int gender, int icon) {
        int genderIndex = Math.max(0, Math.min(ITEM_TIME_ICON.length - 1, gender));
        short[] icons = ITEM_TIME_ICON[genderIndex];
        for (int i = 0; i < icons.length; i++) {
            if (icons[i] == icon) {
                return i + 1;
            }
        }
        return 0;
    }
}
