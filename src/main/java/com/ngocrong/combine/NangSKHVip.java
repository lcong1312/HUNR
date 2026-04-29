package com.ngocrong.combine;

import com.ngocrong.consts.CMDMenu;
import com.ngocrong.item.Item;
import com.ngocrong.item.ItemOption;
import com.ngocrong.item.ItemTemplate;
import com.ngocrong.lib.KeyValue;
import com.ngocrong.server.DragonBall;
import com.ngocrong.util.Utils;

import java.util.ArrayList;

public class NangSKHVip extends Combine {

    private static final long GOLD_REQUIRE = 1_000_000_000L;

    private static final int[] VIP_LEVEL_WEIGHTS = {
        20,
        17,
        15,
        12,
        10,
        8,
        6,
        4,
        3,
        2,
        2,
        1
    };
    private static final int VIP_MIN_LEVEL = 2;

    public NangSKHVip() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vào hành trang").append("\n");
        sb.append("Chọn đúng 3 món đồ Hủy Diệt").append("\n");
        sb.append("Sau đó chọn 'Nâng Cấp'");
        setInfo(sb.toString());

        StringBuilder sb2 = new StringBuilder();
        sb2.append("Yêu cầu: mặc 5 món đồ Hủy Diệt").append("\n");
        sb2.append("Phí: 1 tỷ vàng").append("\n");
        sb2.append("Món đầu tiên sẽ được nâng thành đồ Kích Hoạt");
        setInfo2(sb2.toString());
    }

    @Override
    public void confirm() {
        if (itemCombine == null) {
            return;
        }
        if (itemCombine.size() != 3) {
            player.service.dialogMessage("Cần đúng 3 món đồ Hủy Diệt");
            return;
        }
        if (itemCombine.get(0).equals(itemCombine.get(1)) || itemCombine.get(0).equals(itemCombine.get(2))
                || itemCombine.get(1).equals(itemCombine.get(2))) {
            player.service.dialogMessage("Không được chọn trùng trang bị");
            return;
        }
        for (int i = 0; i < 3; i++) {
            Item item = player.itemBag[itemCombine.get(i)];
            if (item == null || item.template.type >= 5 || item.template.level != 14) {
                player.service.dialogMessage("Vật phẩm không hợp lệ (cần đồ Hủy Diệt)");
                return;
            }
        }
        if (!player.isSetHuyDiet()) {
            player.service.sendThongBao("Bạn cần mặc đủ 5 món đồ Hủy Diệt mới sử dụng được");
            return;
        }
        String info = "Nâng SKH VIP: tiêu 3 đồ Hủy Diệt + 1 tỷ vàng\n"
                + "Món đầu tiên sẽ ra đồ KH ngẫu nhiên";
        player.menus.clear();
        player.menus.add(new KeyValue(CMDMenu.COMBINE, "Đồng ý\n1 tỷ vàng", -1));
        player.menus.add(new KeyValue(CMDMenu.CANCEL, "Từ chối"));
        player.service.openUIConfirm(npc.templateId, info, npc.avatar, player.menus);
    }

    @Override
    public void combine() {
        if (itemCombine == null) {
            return;
        }
        if (itemCombine.size() != 3) {
            player.service.dialogMessage("Cần đúng 3 món đồ Hủy Diệt");
            return;
        }
        if (itemCombine.get(0).equals(itemCombine.get(1)) || itemCombine.get(0).equals(itemCombine.get(2))
                || itemCombine.get(1).equals(itemCombine.get(2))) {
            player.service.dialogMessage("Không được chọn trùng trang bị");
            return;
        }
        Item firstItem = player.itemBag[itemCombine.get(0)];
        Item secondItem = player.itemBag[itemCombine.get(1)];
        Item thirdItem = player.itemBag[itemCombine.get(2)];
        if (firstItem == null || firstItem.template.type >= 5 || firstItem.template.level != 14) {
            player.service.dialogMessage("Vật phẩm không hợp lệ (cần đồ Hủy Diệt)");
            return;
        }
        if (secondItem == null || secondItem.template.type >= 5 || secondItem.template.level != 14) {
            player.service.dialogMessage("Vật phẩm không hợp lệ (cần đồ Hủy Diệt)");
            return;
        }
        if (thirdItem == null || thirdItem.template.type >= 5 || thirdItem.template.level != 14) {
            player.service.dialogMessage("Vật phẩm không hợp lệ (cần đồ Hủy Diệt)");
            return;
        }
        if (!player.isSetHuyDiet()) {
            player.service.sendThongBao("Bạn cần mặc đủ 5 món đồ Hủy Diệt mới sử dụng được");
            return;
        }
        if (player.gold < GOLD_REQUIRE) {
            player.service.sendThongBao("Bạn không đủ vàng (cần 1 tỷ)");
            return;
        }

        int gender = firstItem.template.gender;
        int genderForOption = gender;
        if (firstItem.template.type == 4) {
            if (gender == 3) {
                gender = player.gender;
            }
            genderForOption = player.gender;
        }

        int itemType = firstItem.template.type;
        int randomLevel = getVipRandomLevel();
        int templateId = getRandomItemTemplateId(itemType, gender, randomLevel);
        if (templateId == -1) {
            player.service.dialogMessage("Không tìm thấy trang bị phù hợp. Vui lòng thử lại.");
            return;
        }

        player.addGold(-GOLD_REQUIRE);

        player.removeItem(itemCombine.get(1), 1);
        player.removeItem(itemCombine.get(2), 1);

        Item newItem = new Item(templateId);
        newItem.quantity = 1;
        newItem.setDefaultOptions();

        int index = getRandomIndexByPercentage(genderForOption);
        newItem.addItemOption(new ItemOption(DoiDoKichHoat.OPTIONS[genderForOption][index][0], 0));
        newItem.addItemOption(new ItemOption(DoiDoKichHoat.OPTIONS[genderForOption][index][1], 0));
        newItem.addItemOption(new ItemOption(30, 0));

        newItem.indexUI = itemCombine.get(0);
        player.itemBag[newItem.indexUI] = newItem;
        result((byte) 2, firstItem.template.iconID, newItem.template.iconID);
        player.service.refreshItem((byte) 1, newItem);
        update();
    }

    private int getVipRandomLevel() {
        int total = 0;
        for (int w : VIP_LEVEL_WEIGHTS) {
            total += w;
        }
        int rand = Utils.nextInt(total);
        int sum = 0;
        for (int i = 0; i < VIP_LEVEL_WEIGHTS.length; i++) {
            sum += VIP_LEVEL_WEIGHTS[i];
            if (rand < sum) {
                return VIP_MIN_LEVEL + i;
            }
        }
        return VIP_MIN_LEVEL;
    }

    private int getRandomItemTemplateId(int type, int gender, int level) {
        com.ngocrong.server.Server server = DragonBall.getInstance().getServer();
        if (server == null || server.iTemplates == null) {
            return -1;
        }
        ArrayList<Integer> matchingIds = new ArrayList<>();
        for (ItemTemplate template : server.iTemplates.values()) {
            if (template.id == 693 || template.id == 691 || template.id == 692) {
                continue;
            }
            if (template.type == type && template.level == level) {
                if (type == 4) {
                    if (template.gender == 3 || template.gender == gender) {
                        matchingIds.add((int) template.id);
                    }
                } else {
                    if (template.gender == gender) {
                        matchingIds.add((int) template.id);
                    }
                }
            }
        }
        if (matchingIds.isEmpty()) {
            return -1;
        }
        return matchingIds.get(Utils.nextInt(matchingIds.size()));
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
        for (int p : percentages) {
            total += p;
        }
        int rand = Utils.nextInt(total);
        int sum = 0;
        for (int i = 0; i < percentages.length; i++) {
            sum += percentages[i];
            if (rand < sum) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void showTab() {
        player.service.combine((byte) 0, this, (short) -1, (short) -1);
    }
}
