package com.ngocrong.combine;

import com.ngocrong.consts.CMDMenu;
import com.ngocrong.consts.ItemName;
import com.ngocrong.item.Item;
import com.ngocrong.item.ItemOption;
import com.ngocrong.lib.KeyValue;
import com.ngocrong.util.Utils;

public class NangSKHCap2 extends Combine {

    private static final long GOLD_REQUIRE = 10_000_000_000L;

    private static final int[][] LEVEL_1_ITEMS = {
        {ItemName.AO_VAI_3_LO, ItemName.QUAN_VAI_DEN, ItemName.GANG_VAI_DEN, ItemName.GIAY_NHUA, ItemName.RADA_CAP_1},
        {ItemName.AO_SOI_LEN, ItemName.QUAN_SOI_LEN, ItemName.GANG_SOI_LEN, ItemName.GIAY_SOI_LEN, ItemName.RADA_CAP_1},
        {ItemName.AO_VAI_THO, ItemName.QUAN_VAI_THO, ItemName.GANG_VAI_THO, ItemName.GIAY_VAI_THO, ItemName.RADA_CAP_1}
    };

    public static final int[][][] OPTIONS_CAP_2 = {
        {
            {198, 199},
            {200, 201},
            {202, 203}
        }, {
            {204, 205},
            {206, 207},
            {208, 209}
        }, {
            {210, 211},
            {212, 213},
            {214, 215}
        }
    };

    public NangSKHCap2() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mặc đủ 5 món Thiên Sứ").append("\n");
        sb.append("Chọn 1 món Thiên Sứ trong hành trang").append("\n");
        sb.append("Sau đó chọn 'Nâng Cấp'");
        setInfo(sb.toString());

        StringBuilder sb2 = new StringBuilder();
        sb2.append("Tiêu 1 đồ Thiên Sứ + 10 tỷ vàng").append("\n");
        sb2.append("Nhận đồ cấp 1 SKH cấp 2 cùng hành tinh");
        setInfo2(sb2.toString());
    }

    private boolean isThienSu(Item item) {
        return item != null && item.template != null
                && item.template.id >= ItemName.AO_THIEN_SU_TD
                && item.template.id <= ItemName.NHAN_THIEN_SU_XD
                && item.template.type >= 0
                && item.template.type < 5;
    }

    private boolean isWearingFullThienSu() {
        if (player.itemBody == null) {
            return false;
        }
        boolean[] types = new boolean[5];
        for (Item item : player.itemBody) {
            if (isThienSu(item)) {
                types[item.template.type] = true;
            }
        }
        for (boolean type : types) {
            if (!type) {
                return false;
            }
        }
        return true;
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

    private int getRandomIndexByPercentage(int gender) {
        int[] percentages;
        switch (gender) {
            case 0:
                percentages = DoiDoKichHoat.SKHTD;
                break;
            case 1:
                percentages = DoiDoKichHoat.SKHNM;
                break;
            case 2:
                percentages = DoiDoKichHoat.SKHXD;
                break;
            default:
                percentages = DoiDoKichHoat.SKHTD;
                break;
        }

        int total = 0;
        for (int percentage : percentages) {
            total += percentage;
        }
        int randomValue = Utils.nextInt(total);
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
    public void confirm() {
        if (itemCombine == null) {
            return;
        }
        if (itemCombine.size() != 1) {
            player.service.dialogMessage("Số lượng trang bị không hợp lệ");
            return;
        }
        Item item = player.itemBag[itemCombine.get(0)];
        if (!isThienSu(item)) {
            player.service.dialogMessage("Vật phẩm không hợp lệ (cần đồ Thiên Sứ)");
            return;
        }
        if (!isWearingFullThienSu()) {
            player.service.sendThongBao("Bạn cần mặc đủ 5 món Thiên Sứ mới nâng cấp được");
            return;
        }
        int planet = getPlanet(item);
        int templateId = getLevel1ItemId(item, planet);
        if (templateId == -1) {
            player.service.dialogMessage("Không tìm thấy trang bị phù hợp. Vui lòng thử lại.");
            return;
        }
        String info = "Sau khi nâng cấp sẽ tiêu 1 " + item.template.name
                + " và 10 tỷ vàng để nhận đồ cấp 1 SKH cấp 2 ngẫu nhiên";
        player.menus.clear();
        player.menus.add(new KeyValue(CMDMenu.COMBINE, "Nâng cấp\n10 tỷ vàng"));
        player.menus.add(new KeyValue(CMDMenu.CANCEL, "Từ chối"));
        player.service.openUIConfirm(npc.templateId, info, npc.avatar, player.menus);
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
        if (!isThienSu(oldItem)) {
            player.service.dialogMessage("Vật phẩm không hợp lệ (cần đồ Thiên Sứ)");
            return;
        }
        if (!isWearingFullThienSu()) {
            player.service.sendThongBao("Bạn cần mặc đủ 5 món Thiên Sứ mới nâng cấp được");
            return;
        }
        if (player.gold < GOLD_REQUIRE) {
            player.service.sendThongBao("Bạn không đủ vàng (cần 10 tỷ)");
            return;
        }

        int planet = getPlanet(oldItem);
        int templateId = getLevel1ItemId(oldItem, planet);
        if (templateId == -1) {
            player.service.dialogMessage("Không tìm thấy trang bị phù hợp. Vui lòng thử lại.");
            return;
        }

        player.addGold(-GOLD_REQUIRE);
        short oldIcon = oldItem.template.iconID;

        Item newItem = new Item(templateId);
        newItem.quantity = 1;
        newItem.setDefaultOptions();

        int index = getRandomIndexByPercentage(planet);
        newItem.addItemOption(new ItemOption(OPTIONS_CAP_2[planet][index][0], 0));
        newItem.addItemOption(new ItemOption(OPTIONS_CAP_2[planet][index][1], 0));
        newItem.addItemOption(new ItemOption(30, 0));

        newItem.indexUI = itemCombine.get(0);
        player.itemBag[newItem.indexUI] = newItem;
        result((byte) 2, oldIcon, newItem.template.iconID);
        player.service.refreshItem((byte) 1, newItem);
        update();
    }
}
