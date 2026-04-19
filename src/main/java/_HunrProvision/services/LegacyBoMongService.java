package _HunrProvision.services;

import com.ngocrong.model.LegacySideTask;
import com.ngocrong.model.LegacySideTaskTemplate;
import com.ngocrong.util.Utils;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class LegacyBoMongService {

    public static final byte EASY = 0;
    public static final byte NORMAL = 1;
    public static final byte HARD = 2;
    public static final byte VERY_HARD = 3;
    public static final byte HELL = 4;
    public static final int MAX_SIDE_TASK = 30;

    public static final int GOLD_EASY = 10_000_000;
    public static final int GOLD_NORMAL = 30_000_000;
    public static final int GOLD_HARD = 50_000_000;
    public static final int GOLD_VERY_HARD = 80_000_000;
    public static final int GOLD_HELL = 100_000_000;

    private static final List<LegacySideTaskTemplate> TEMPLATES = buildTemplates();
    private static final Map<Integer, LegacySideTaskTemplate> TEMPLATE_BY_ID = buildTemplateMap();
    private static final Map<Integer, Integer> TEMPLATE_TO_MOB = buildTemplateMobMap();

    private LegacyBoMongService() {
    }

    public static List<LegacySideTaskTemplate> getTemplates() {
        return Collections.unmodifiableList(TEMPLATES);
    }

    public static LegacySideTaskTemplate getTemplate(int id) {
        return TEMPLATE_BY_ID.get(id);
    }

    public static Integer getMobIdByTemplate(int templateId) {
        return TEMPLATE_TO_MOB.get(templateId);
    }

    public static boolean isPickGoldTask(LegacySideTask task) {
        return task != null && task.template != null && task.template.id == 58;
    }

    public static LegacySideTask createTask(byte level, int leftTask) {
        LegacySideTask task = new LegacySideTask();
        task.level = level;
        task.leftTask = Math.max(0, leftTask);
        task.receivedTime = System.currentTimeMillis();
        LegacySideTaskTemplate template = TEMPLATES.get(Utils.nextInt(TEMPLATES.size()));
        task.template = template;
        task.maxCount = Utils.nextInt(template.getMinCount(level), template.getMaxCount(level));
        task.count = 0;
        return task;
    }

    public static LegacySideTask load(String raw) {
        if (raw == null || raw.isEmpty() || "null".equalsIgnoreCase(raw)) {
            return new LegacySideTask();
        }
        try {
            JSONArray arr = new JSONArray(raw);
            LegacySideTask task = new LegacySideTask();
            task.level = arr.optInt(0, 0);
            task.count = arr.optInt(1, 0);
            task.leftTask = clampLeftTask(arr.optInt(2, MAX_SIDE_TASK));
            int templateId = arr.optInt(3, -1);
            task.receivedTime = arr.optLong(4, 0);
            task.maxCount = Math.max(0, arr.optInt(5, 0));
            if (task.receivedTime > 0 && !isSameDay(task.receivedTime, System.currentTimeMillis())) {
                return new LegacySideTask();
            }
            if (templateId >= 0) {
                task.template = getTemplate(templateId);
                if (task.template == null) {
                    task.clearCurrentTask();
                    task.leftTask = clampLeftTask(arr.optInt(2, MAX_SIDE_TASK));
                } else {
                    task.syncNotifyFlags();
                }
            }
            return task;
        } catch (Exception ignored) {
            return new LegacySideTask();
        }
    }

    public static String save(LegacySideTask task) {
        if (task == null) {
            return null;
        }
        JSONArray arr = new JSONArray();
        arr.put(task.level);
        arr.put(Math.max(0, task.count));
        arr.put(clampLeftTask(task.leftTask));
        arr.put(task.template != null ? task.template.id : -1);
        arr.put(task.receivedTime);
        arr.put(Math.max(0, task.maxCount));
        return arr.toString();
    }

    public static boolean isSameDay(long first, long second) {
        if (first <= 0 || second <= 0) {
            return false;
        }
        Calendar firstCal = Utils.nowVietnamCalendar();
        firstCal.setTimeInMillis(first);
        Calendar secondCal = Utils.nowVietnamCalendar();
        secondCal.setTimeInMillis(second);
        return firstCal.get(Calendar.YEAR) == secondCal.get(Calendar.YEAR)
                && firstCal.get(Calendar.DAY_OF_YEAR) == secondCal.get(Calendar.DAY_OF_YEAR);
    }

    public static int getRewardGold(int level) {
        switch (level) {
            case EASY:
                return GOLD_EASY;
            case NORMAL:
                return GOLD_NORMAL;
            case HARD:
                return GOLD_HARD;
            case VERY_HARD:
                return GOLD_VERY_HARD;
            case HELL:
                return GOLD_HELL;
            default:
                return 0;
        }
    }

    public static int getRewardLockedGoldBar(int level) {
        switch (level) {
            case EASY:
                return 1;
            case NORMAL:
                return 2;
            case HARD:
                return 3;
            case VERY_HARD:
                return 4;
            case HELL:
                return 5;
            default:
                return 0;
        }
    }

    private static int clampLeftTask(int leftTask) {
        return Math.max(0, Math.min(MAX_SIDE_TASK, leftTask));
    }

    private static Map<Integer, LegacySideTaskTemplate> buildTemplateMap() {
        Map<Integer, LegacySideTaskTemplate> map = new HashMap<>();
        for (LegacySideTaskTemplate template : TEMPLATES) {
            map.put(template.id, template);
        }
        return map;
    }

    private static Map<Integer, Integer> buildTemplateMobMap() {
        Map<Integer, Integer> map = new HashMap<>();
        int[] mobIds = {
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                16, 17, 18, 13, 14, 15, 31, 32, 33, 19, 20, 21,
                25, 26, 27, 39, 40, 41, 42, 43, 44, 45, 46, 47,
                48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59,
                60, 61, 62, 63, 64, 65, 66, 67, 68, 69
        };
        for (int i = 0; i < mobIds.length; i++) {
            map.put(i, mobIds[i]);
        }
        return map;
    }

    private static List<LegacySideTaskTemplate> buildTemplates() {
        List<LegacySideTaskTemplate> templates = new ArrayList<>();
        templates.add(template(0, "Tiêu diệt %1 khủng long", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(1, "Tiêu diệt %1 lợn lòi", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(2, "Tiêu diệt %1 quỷ đất", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(3, "Tiêu diệt %1 khủng long mẹ", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(4, "Tiêu diệt %1 lợn lòi mẹ", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(5, "Tiêu diệt %1 quỷ đất mẹ", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(6, "Tiêu diệt %1 thằn lằn bay", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(7, "Tiêu diệt %1 phi long", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(8, "Tiêu diệt %1 quỷ bay", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(9, "Tiêu diệt %1 thằn lằn mẹ", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(10, "Tiêu diệt %1 phi long mẹ", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(11, "Tiêu diệt %1 quỷ bay mẹ", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(12, "Tiêu diệt %1 heo rừng", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(13, "Tiêu diệt %1 heo da xanh", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(14, "Tiêu diệt %1 heo xayda", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(15, "Tiêu diệt %1 ốc mượn hồn", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(16, "Tiêu diệt %1 ốc sên", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(17, "Tiêu diệt %1 heo xayda mẹ", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(18, "Tiêu diệt %1 không tặc", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(19, "Tiêu diệt %1 quỷ đầu to", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(20, "Tiêu diệt %1 quỷ địa ngục", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(21, "Tiêu diệt %1 heo rừng mẹ", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(22, "Tiêu diệt %1 heo xanh mẹ", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(23, "Tiêu diệt %1 alien", "1-20|20-100|100-500|500-2000|2000-5000"));
        templates.add(template(24, "Tiêu diệt %1 tambourine", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(25, "Tiêu diệt %1 drum", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(26, "Tiêu diệt %1 akkuman", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(27, "Tiêu diệt %1 nappa", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(28, "Tiêu diệt %1 soldier", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(29, "Tiêu diệt %1 appule", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(30, "Tiêu diệt %1 raspberry", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(31, "Tiêu diệt %1 thằn lằn xanh", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(32, "Tiêu diệt %1 quỷ đầu nhọn", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(33, "Tiêu diệt %1 quỷ đầu vàng", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(34, "Tiêu diệt %1 quỷ da tím", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(35, "Tiêu diệt %1 quỷ già", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(36, "Tiêu diệt %1 cá sấu", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(37, "Tiêu diệt %1 dơi da xanh", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(38, "Tiêu diệt %1 quỷ chim", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(39, "Tiêu diệt %1 lính đầu trọc", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(40, "Tiêu diệt %1 lính tai dài", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(41, "Tiêu diệt %1 lính vũ trụ", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(42, "Tiêu diệt %1 khỉ lông đen", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(43, "Tiêu diệt %1 khỉ giáp sắt", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(44, "Tiêu diệt %1 khỉ lông đỏ", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(45, "Tiêu diệt %1 khỉ lông vàng", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(46, "Tiêu diệt %1 xên con cấp 1", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(47, "Tiêu diệt %1 xên con cấp 2", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(48, "Tiêu diệt %1 xên con cấp 3", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(49, "Tiêu diệt %1 xên con cấp 4", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(50, "Tiêu diệt %1 xên con cấp 5", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(51, "Tiêu diệt %1 xên con cấp 6", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(52, "Tiêu diệt %1 xên con cấp 7", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(53, "Tiêu diệt %1 xên con cấp 8", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(54, "Tiêu diệt %1 tai tím", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(55, "Tiêu diệt %1 abo", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(56, "Tiêu diệt %1 kado", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(57, "Tiêu diệt %1 da xanh", "1-5|5-20|20-100|100-500|500-1000"));
        templates.add(template(58, "Nhặt %1 vàng", "1000-3000|3000-20000|20000-100000|100000-10000000|10000000-100000000"));
        return templates;
    }

    private static LegacySideTaskTemplate template(int id, String name, String rawCounts) {
        String[] levels = rawCounts.split("\\|");
        int[][] counts = new int[5][2];
        for (int i = 0; i < levels.length && i < counts.length; i++) {
            String[] parts = levels[i].split("-");
            counts[i][0] = Integer.parseInt(parts[0]);
            counts[i][1] = Integer.parseInt(parts[1]);
        }
        return new LegacySideTaskTemplate(id, name, counts);
    }
}
