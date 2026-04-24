package com.ngocrong.combine;

import com.ngocrong.consts.CMDMenu;
import com.ngocrong.consts.ItemName;
import com.ngocrong.consts.SachTuyetKy;
import com.ngocrong.item.Item;
import com.ngocrong.item.ItemOption;
import com.ngocrong.lib.KeyValue;
import com.ngocrong.util.Utils;

public class SachTuyetKyCombine extends Combine {

    private static final int REQUIRE_KIM_NANG_CAP = 10;
    private static final int REQUIRE_CUON_SACH_PHUC_HOI = 10;
    private static final long REQUIRE_GOLD = 10_000_000L;

    private final CombineType type;

    public SachTuyetKyCombine(CombineType type) {
        this.type = type;
        setInfo("Vào hành trang\nChọn vật phẩm Sách tuyệt kỹ cần xử lý\nSau đó chọn 'Đồng ý'");
        setInfo2("Ta sẽ xử lý Sách tuyệt kỹ giúp ngươi");
    }

    @Override
    public void confirm() {
        if (itemCombine == null) {
            return;
        }
        switch (type) {
            case GIAM_DINH_SACH:
                confirmGiamDinh();
                break;
            case TAY_SACH:
                confirmTaySach();
                break;
            case NANG_CAP_SACH_TUYET_KY:
                confirmNangCap();
                break;
            case PHUC_HOI_SACH:
                confirmPhucHoi();
                break;
            case PHAN_RA_SACH:
                confirmPhanRa();
                break;
            default:
                break;
        }
    }

    @Override
    public void combine() {
        if (itemCombine == null) {
            return;
        }
        switch (type) {
            case GIAM_DINH_SACH:
                giamDinh();
                break;
            case TAY_SACH:
                taySach();
                break;
            case NANG_CAP_SACH_TUYET_KY:
                nangCap();
                break;
            case PHUC_HOI_SACH:
                phucHoi();
                break;
            case PHAN_RA_SACH:
                phanRa();
                break;
            default:
                break;
        }
    }

    private void confirmGiamDinh() {
        SelectedItems selected = getSelectedItems();
        if (selected.book == null || selected.buaGiamDinh == null) {
            showCancel("Cần Sách Tuyệt Kỹ và bùa giám định");
            return;
        }
        String info = "|1|" + selected.book.template.name + "\n"
                + "|2|" + selected.buaGiamDinh.template.name + " " + selected.buaGiamDinh.quantity + "/1";
        openConfirm(info, "Giám định");
    }

    private void confirmTaySach() {
        SelectedItems selected = getSelectedItems();
        if (selected.book == null || itemCombine.size() != 1) {
            showCancel("Cần Sách Tuyệt Kỹ để tẩy");
            return;
        }
        openConfirm("|2|Tẩy Sách Tuyệt Kỹ", "Đồng ý");
    }

    private void confirmNangCap() {
        SelectedItems selected = getSelectedItems();
        if (selected.book == null || selected.kimBamGiay == null || !SachTuyetKy.isLevelOneBook(selected.book.id)) {
            showCancel("Cần Sách Tuyệt Kỹ 1 và 10 Kìm bấm giấy.");
            return;
        }
        String info = "|2|Nâng cấp sách tuyệt kỹ\n"
                + "Cần 10 Kìm bấm giấy\n"
                + "Tỉ lệ thành công: 10%\n"
                + "Nâng cấp thất bại sẽ mất 10 Kìm bấm giấy";
        openConfirm(info, "Nâng cấp");
    }

    private void confirmPhucHoi() {
        SelectedItems selected = getSelectedItems();
        if (selected.book == null || itemCombine.size() != 1) {
            showCancel("Không tìm thấy vật phẩm");
            return;
        }
        String info = "|2|Phục hồi " + selected.book.template.name + "\n"
                + "Cần 10 cuốn sách cũ\n"
                + "Phí phục hồi 10 triệu vàng";
        openConfirm(info, "Đồng ý");
    }

    private void confirmPhanRa() {
        SelectedItems selected = getSelectedItems();
        if (selected.book == null || itemCombine.size() != 1) {
            showCancel("Không tìm thấy vật phẩm");
            return;
        }
        String info = "|2|Phân rã sách\n"
                + "Nhận lại 5 cuốn sách cũ\n"
                + "Phí rã 10 triệu vàng";
        openConfirm(info, "Đồng ý");
    }

    private void giamDinh() {
        SelectedItems selected = getSelectedItems();
        if (selected.book == null || selected.buaGiamDinh == null) {
            player.service.sendThongBao("Cần Sách Tuyệt Kỹ và bùa giám định");
            return;
        }
        if (!hasOption(selected.book, SachTuyetKy.OPTION_CHUA_GIAM_DINH)) {
            player.service.sendThongBao("Sách đã được giám định");
            return;
        }
        Item newBook = new Item(selected.book.id);
        newBook.quantity = 1;
        int rate = Utils.nextInt(0, 99);
        if (rate <= 33) {
            newBook.addItemOption(new ItemOption(50, Utils.nextInt(5, 10)));
        } else if (rate <= 66) {
            newBook.addItemOption(new ItemOption(77, Utils.nextInt(10, 15)));
        } else {
            newBook.addItemOption(new ItemOption(103, Utils.nextInt(10, 15)));
        }
        copyOptions(selected.book, newBook, 1);
        player.removeItem(selected.book.indexUI, 1);
        player.removeItem(selected.buaGiamDinh.indexUI, 1);
        player.addItem(newBook);
        result((byte) 5, newBook.template.iconID);
        update();
    }

    private void taySach() {
        SelectedItems selected = getSelectedItems();
        if (selected.book == null) {
            player.service.sendThongBao("Cần Sách Tuyệt Kỹ để tẩy");
            return;
        }
        ItemOption luotTay = selected.book.getItemOption(SachTuyetKy.OPTION_LUOT_TAY);
        if (luotTay == null || luotTay.param <= 0 || hasOption(selected.book, SachTuyetKy.OPTION_CHUA_GIAM_DINH)) {
            player.service.sendThongBao("Không thể tẩy sách này");
            return;
        }
        luotTay.param--;
        Item newBook = new Item(selected.book.id);
        newBook.quantity = 1;
        newBook.addItemOption(new ItemOption(SachTuyetKy.OPTION_CHUA_GIAM_DINH, 0));
        copyOptions(selected.book, newBook, 1);
        player.removeItem(selected.book.indexUI, 1);
        player.addItem(newBook);
        result((byte) 5, newBook.template.iconID);
        update();
    }

    private void nangCap() {
        SelectedItems selected = getSelectedItems();
        int nextId = selected.book == null ? -1 : SachTuyetKy.getNextLevelBook(selected.book.id);
        if (selected.book == null || selected.kimBamGiay == null || nextId == -1) {
            player.service.sendThongBao("Cần Sách Tuyệt Kỹ 1 và 10 Kìm bấm giấy.");
            return;
        }
        if (selected.kimBamGiay.quantity < REQUIRE_KIM_NANG_CAP) {
            player.service.sendThongBao("Không đủ Kìm bấm giấy mà đòi nâng cấp");
            return;
        }
        if (hasOption(selected.book, SachTuyetKy.OPTION_CHUA_GIAM_DINH)) {
            player.service.sendThongBao("Chưa giám định mà đòi nâng cấp");
            return;
        }
        player.removeItem(selected.kimBamGiay.indexUI, REQUIRE_KIM_NANG_CAP);
        if (Utils.isTrue(10, 100)) {
            Item newBook = new Item(nextId);
            newBook.quantity = 1;
            copyOptions(selected.book, newBook, 0);
            player.removeItem(selected.book.indexUI, 1);
            player.addItem(newBook);
            result((byte) 5, newBook.template.iconID);
        } else {
            result((byte) 3);
        }
        update();
    }

    private void phucHoi() {
        SelectedItems selected = getSelectedItems();
        Item cuonSachCu = player.getItemInBag(ItemName.CUON_SACH_CU);
        if (selected.book == null || cuonSachCu == null || cuonSachCu.quantity < REQUIRE_CUON_SACH_PHUC_HOI) {
            player.service.dialogMessage("Cần sách tuyệt kỹ và 10 cuốn sách cũ");
            return;
        }
        if (player.gold < REQUIRE_GOLD) {
            player.service.sendThongBao("Không có tiền mà đòi phục hồi à");
            return;
        }
        ItemOption doBen = selected.book.getItemOption(SachTuyetKy.OPTION_DO_BEN);
        if (doBen != null && doBen.param >= 1000) {
            player.service.sendThongBao("Sách vẫn còn dùng được");
            return;
        }
        if (doBen == null) {
            selected.book.addItemOption(new ItemOption(SachTuyetKy.OPTION_DO_BEN, 1000));
        } else {
            doBen.param = 1000;
        }
        player.addGold(-REQUIRE_GOLD);
        player.removeItem(cuonSachCu.indexUI, REQUIRE_CUON_SACH_PHUC_HOI);
        player.service.setItemBag();
        player.service.setItemBody();
        result((byte) 2);
        update();
    }

    private void phanRa() {
        SelectedItems selected = getSelectedItems();
        if (selected.book == null) {
            player.service.sendThongBao("Không tìm thấy vật phẩm");
            return;
        }
        ItemOption luotTay = selected.book.getItemOption(SachTuyetKy.OPTION_LUOT_TAY);
        if (luotTay != null && luotTay.param > 0) {
            player.service.sendThongBao("Sách vẫn còn dùng được");
            return;
        }
        if (player.gold < REQUIRE_GOLD) {
            player.service.sendThongBao("Không có tiền mà đòi phân rã à");
            return;
        }
        player.addGold(-REQUIRE_GOLD);
        player.removeItem(selected.book.indexUI, 1);
        Item cuonSachCu = new Item(ItemName.CUON_SACH_CU);
        cuonSachCu.quantity = 5;
        cuonSachCu.addItemOption(new ItemOption(30, 0));
        player.addItem(cuonSachCu);
        result((byte) 5, cuonSachCu.template.iconID);
        update();
    }

    private SelectedItems getSelectedItems() {
        SelectedItems selected = new SelectedItems();
        for (byte index : itemCombine) {
            if (index < 0 || index >= player.itemBag.length) {
                continue;
            }
            Item item = player.itemBag[index];
            if (item == null || item.template == null) {
                continue;
            }
            if (item.template.type == Item.TYPE_SACH_TUYET_KY && SachTuyetKy.isSachTuyetKy(item.id)) {
                selected.book = item;
            } else if (item.id == ItemName.BUA_GIAM_DINH) {
                selected.buaGiamDinh = item;
            } else if (item.id == ItemName.KIM_BAM_GIAY) {
                selected.kimBamGiay = item;
            }
        }
        return selected;
    }

    private void openConfirm(String info, String confirmText) {
        player.menus.clear();
        player.menus.add(new KeyValue(CMDMenu.COMBINE, confirmText));
        player.menus.add(new KeyValue(CMDMenu.CANCEL, "Từ chối"));
        player.service.openUIConfirm(npc.templateId, info, npc.avatar, player.menus);
    }

    private boolean hasOption(Item item, int optionId) {
        return item != null && item.getItemOption(optionId) != null;
    }

    private void copyOptions(Item from, Item to, int startIndex) {
        if (from == null || from.options == null) {
            return;
        }
        for (int i = startIndex; i < from.options.size(); i++) {
            ItemOption option = from.options.get(i);
            to.addItemOption(new ItemOption(option.id, option.param));
        }
    }

    private static class SelectedItems {
        private Item book;
        private Item buaGiamDinh;
        private Item kimBamGiay;
    }
}
