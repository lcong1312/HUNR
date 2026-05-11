package com.ngocrong.combine;

import com.ngocrong.consts.CMDMenu;
import com.ngocrong.consts.ItemName;
import com.ngocrong.item.Item;
import com.ngocrong.item.ItemOption;
import com.ngocrong.lib.KeyValue;
import com.ngocrong.util.Utils;

public class DoiDoThienSu extends Combine {

    private static final int FRAGMENT_REQUIRE = 99;
    private static final int THIEN_SU_ITEM_OFFSET = 114;

    public DoiDoThienSu() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vào hành trang").append("\n");
        sb.append("Chọn 99 mảnh Thiên Sứ cùng loại").append("\n");
        sb.append("Sau đó chọn 'Đổi Đồ'");
        setInfo(sb.toString());

        StringBuilder sb2 = new StringBuilder();
        sb2.append("99 mảnh Thiên Sứ").append("\n");
        sb2.append("đổi thành trang bị Thiên Sứ tương ứng");
        setInfo2(sb2.toString());
    }

    private boolean isThienSuFragment(Item item) {
        return item != null && item.template != null
                && item.template.id >= ItemName.MANH_AO_THIEN_SU_TD
                && item.template.id <= ItemName.MANH_NHAN_THIEN_SU_XD;
    }

    @Override
    public void confirm() {
        if (itemCombine == null) {
            return;
        }
        if (itemCombine.size() != 1) {
            player.service.dialogMessage("Số lượng vật phẩm không hợp lệ");
            return;
        }
        Item item = player.itemBag[itemCombine.get(0)];
        if (!isThienSuFragment(item)) {
            player.service.dialogMessage("Vật phẩm không hợp lệ (cần mảnh Thiên Sứ)");
            return;
        }
        if (item.quantity < FRAGMENT_REQUIRE) {
            player.service.dialogMessage("Cần x99 Mảnh thiên sứ");
            return;
        }
        String info = "Sau khi đổi sẽ tiêu x99 " + item.template.name
                + " và nhận trang bị Thiên Sứ tương ứng";
        player.menus.clear();
        player.menus.add(new KeyValue(CMDMenu.COMBINE, "Đồng ý\nx99 mảnh"));
        player.menus.add(new KeyValue(CMDMenu.CANCEL, "Từ chối"));
        player.service.openUIConfirm(npc.templateId, info, npc.avatar, player.menus);
    }

    @Override
    public void combine() {
        if (itemCombine == null) {
            return;
        }
        if (itemCombine.size() != 1) {
            player.service.dialogMessage("Số lượng vật phẩm không hợp lệ");
            return;
        }
        Item item = player.itemBag[itemCombine.get(0)];
        if (!isThienSuFragment(item)) {
            player.service.dialogMessage("Vật phẩm không hợp lệ (cần mảnh Thiên Sứ)");
            return;
        }
        if (item.quantity < FRAGMENT_REQUIRE) {
            player.service.dialogMessage("Cần x99 Mảnh thiên sứ");
            return;
        }
        if (player.getSlotNullInBag() < 1 && item.quantity > FRAGMENT_REQUIRE) {
            player.service.sendThongBao("Hành trang không đủ ô trống");
            return;
        }

        Item itemCreate = new Item(item.template.id + THIEN_SU_ITEM_OFFSET);
        itemCreate.setDefaultOptions();
        int[] optionBonus = new int[]{77, 103, 50, item.template.gender == 2 ? 94 : 5};
        itemCreate.addItemOption(new ItemOption(optionBonus[Utils.nextInt(optionBonus.length)], Utils.nextInt(1, 5)));
        itemCreate.addItemOption(new ItemOption(30, 0));
        itemCreate.quantity = 1;

        short oldIcon = item.template.iconID;
        if (item.quantity == FRAGMENT_REQUIRE) {
            itemCreate.indexUI = item.indexUI;
            player.itemBag[item.indexUI] = itemCreate;
            player.service.refreshItem((byte) 1, itemCreate);
        } else if (player.addItem(itemCreate)) {
            player.removeItem(item.indexUI, FRAGMENT_REQUIRE);
        } else {
            player.service.sendThongBao("Hành trang không đủ ô trống");
            return;
        }
        result((byte) 2, oldIcon, itemCreate.template.iconID);
        update();
    }
}
