package com.ngocrong.combine;

import com.ngocrong.consts.CMDMenu;
import com.ngocrong.consts.ItemName;
import com.ngocrong.item.Item;
import com.ngocrong.item.ItemOption;
import com.ngocrong.lib.KeyValue;
import com.ngocrong.util.Utils;

public class DoiDoKichHoat extends Combine {

    public static int[] SKHTD = new int[]{50, 45, 5};
    public static int[] SKHNM = new int[]{20, 10, 70};
    public static int[] SKHXD = new int[]{65, 25, 10};
    static final int goldRequire = 500_000_000;

    private static final int[][] LEVEL_1_ITEMS = {
        {ItemName.AO_VAI_3_LO, ItemName.QUAN_VAI_DEN, ItemName.GANG_VAI_DEN, ItemName.GIAY_NHUA, ItemName.RADA_CAP_1},
        {ItemName.AO_SOI_LEN, ItemName.QUAN_SOI_LEN, ItemName.GANG_SOI_LEN, ItemName.GIAY_SOI_LEN, ItemName.RADA_CAP_1},
        {ItemName.AO_VAI_THO, ItemName.QUAN_VAI_THO, ItemName.GANG_VAI_THO, ItemName.GIAY_VAI_THO, ItemName.RADA_CAP_1}
    };

    public static boolean setSKHTD(int txh, int krilin, int sgk) {
        if (txh + krilin + sgk != 100) {
            return false;
        }
        if (txh < 0 || krilin < 0 || sgk < 0) {
            return false;
        }
        SKHTD[0] = txh;
        SKHTD[1] = krilin;
        SKHTD[2] = sgk;
        return true;
    }

    public static boolean setSKHNM(int pcl, int octiu, int daimao) {
        if (pcl + octiu + daimao != 100) {
            return false;
        }
        if (pcl < 0 || octiu < 0 || daimao < 0) {
            return false;
        }
        SKHNM[0] = pcl;
        SKHNM[1] = octiu;
        SKHNM[2] = daimao;
        return true;
    }

    public static boolean setSKHXD(int kkr, int cadic, int nappa) {
        if (kkr + cadic + nappa != 100) {
            return false;
        }
        if (kkr < 0 || cadic < 0 || nappa < 0) {
            return false;
        }
        SKHXD[0] = kkr;
        SKHXD[1] = cadic;
        SKHXD[2] = nappa;
        return true;
    }

    public DoiDoKichHoat() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vào hành trang").append("\n");
        sb.append("Chọn 1 món đồ Hủy Diệt").append("\n");
        sb.append("Sau đó chọn 'Nâng Cấp'");
        setInfo(sb.toString());

        StringBuilder sb2 = new StringBuilder();
        sb2.append("Ta sẽ phù phép").append("\n");
        sb2.append("Biến đồ Hủy Diệt thành SKH ngẫu nhiên theo hành tinh");
        setInfo2(sb2.toString());
    }
    public int type;

    @Override
    public void confirm() {
        if (itemCombine == null) {
            return;
        }
        if (itemCombine.size() != 1) {
            player.service.dialogMessage("Số lượng trang bị không hợp lệ");
            return;
        }
        Item item = player.itemBag[itemCombine.get(0)];
        if (!isValidHuyDiet(item)) {
            player.service.dialogMessage("Vật phẩm không hợp lệ (cần đồ Hủy Diệt)");
            return;
        }
        String info = "Sau khi nâng cấp, sẽ tiêu 1 món đồ Hủy Diệt và nhận 1 trang bị có SKH ngẫu nhiên theo hành tinh của món đó";
        player.menus.clear();
        player.menus.add(new KeyValue(CMDMenu.COMBINE, "Nâng cấp\n500tr vàng", -1));
        player.menus.add(new KeyValue(CMDMenu.CANCEL, "Từ chối"));
        player.service.openUIConfirm(npc.templateId, info, npc.avatar, player.menus);
    }

    private boolean isValidHuyDiet(Item item) {
        return item != null && item.template != null && item.template.isHuyDiet();
    }

    private int getPlanet(Item item) {
        int gender = item.template.gender;
        if (gender >= 0 && gender < LEVEL_1_ITEMS.length) {
            return gender;
        }
        return player.gender;
    }

    private int getLevel1ItemId(Item item, int planet) {
        int type = item.template.type;
        if (planet < 0 || planet >= LEVEL_1_ITEMS.length || type < 0 || type >= LEVEL_1_ITEMS[planet].length) {
            return -1;
        }
        return LEVEL_1_ITEMS[planet][type];
    }

    public static final int[][][] OPTIONS = {
        {
            {127, 139},
            {128, 140},
            {129, 141}
        }, {
            {130, 142},
            {131, 143},
            {132, 144}
        }, {
            {133, 136},
            {134, 137},
            {135, 138}
        }
    };

    private int getRandomIndexByPercentage(int gender) {
        int[] percentages;
        switch (gender) {
            case 0:
                percentages = SKHTD;
                break;
            case 1:
                percentages = SKHNM;
                break;
            case 2:
                percentages = SKHXD;
                break;
            default:
                percentages = SKHTD;
                break;
        }

        int totalPercentage = 0;
        for (int percentage : percentages) {
            totalPercentage += percentage;
        }

        int randomValue = Utils.nextInt(totalPercentage);

        int currentSum = 0;
        for (int i = 0; i < percentages.length; i++) {
            currentSum += percentages[i];
            if (randomValue < currentSum) {
                return i;
            }
        }

        return 0;
    }

    @Override
    public void combine() {
        if (itemCombine == null) {
            return;
        }
        if (itemCombine.size() != 1) {
            player.service.dialogMessage("Số lượng trang bị không hợp lệ");
            return;
        }
        Item oldItem = player.itemBag[itemCombine.get(0)];
        if (!isValidHuyDiet(oldItem)) {
            player.service.dialogMessage("Vật phẩm không hợp lệ (cần đồ Hủy Diệt)");
            return;
        }
        if (player.gold < goldRequire) {
            player.service.sendThongBao("Bạn không đủ vàng");
            return;
        }

        int planet = getPlanet(oldItem);
        int templateId = getLevel1ItemId(oldItem, planet);
        if (templateId == -1) {
            player.service.dialogMessage("Không tìm thấy trang bị phù hợp. Vui lòng thử lại.");
            return;
        }

        player.addGold(-goldRequire);
        short oldIcon = oldItem.template.iconID;

        Item newItem = new Item(templateId);
        newItem.quantity = 1;
        newItem.setDefaultOptions();

        int index = getRandomIndexByPercentage(planet);
        newItem.addItemOption(new ItemOption(OPTIONS[planet][index][0], 0));
        newItem.addItemOption(new ItemOption(OPTIONS[planet][index][1], 0));
        newItem.addItemOption(new ItemOption(30, 0));

        newItem.indexUI = itemCombine.get(0);
        player.itemBag[newItem.indexUI] = newItem;
        result((byte) 2, oldIcon, newItem.template.iconID);
        player.service.refreshItem((byte) 1, newItem);
        update();
    }

    @Override
    public void showTab() {
        player.service.combine((byte) 0, this, (short) -1, (short) -1);
    }
}
