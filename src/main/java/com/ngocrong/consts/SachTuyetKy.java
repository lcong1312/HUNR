package com.ngocrong.consts;

public final class SachTuyetKy {

    public static final int OPTION_CHUA_GIAM_DINH = 237;
    public static final int OPTION_LUOT_TAY = 238;
    public static final int OPTION_DO_BEN = 239;

    private SachTuyetKy() {
    }

    public static int getBookLevelOneByGender(int gender) {
        switch (gender) {
            case 0:
                return ItemName.SACH_TUYET_KY_1_TRAI_DAT;
            case 1:
                return ItemName.SACH_TUYET_KY_1_NAMEC;
            case 2:
                return ItemName.SACH_TUYET_KY_1_XAYDA;
            default:
                return ItemName.SACH_TUYET_KY_1_TRAI_DAT;
        }
    }

    public static boolean isSachTuyetKy(int itemId) {
        return itemId == ItemName.SACH_TUYET_KY_1_TRAI_DAT
                || itemId == ItemName.SACH_TUYET_KY_2_TRAI_DAT
                || itemId == ItemName.SACH_TUYET_KY_1_NAMEC
                || itemId == ItemName.SACH_TUYET_KY_2_NAMEC
                || itemId == ItemName.SACH_TUYET_KY_1_XAYDA
                || itemId == ItemName.SACH_TUYET_KY_2_XAYDA;
    }

    public static boolean isLevelOneBook(int itemId) {
        return itemId == ItemName.SACH_TUYET_KY_1_TRAI_DAT
                || itemId == ItemName.SACH_TUYET_KY_1_NAMEC
                || itemId == ItemName.SACH_TUYET_KY_1_XAYDA;
    }

    public static int getNextLevelBook(int itemId) {
        switch (itemId) {
            case ItemName.SACH_TUYET_KY_1_TRAI_DAT:
                return ItemName.SACH_TUYET_KY_2_TRAI_DAT;
            case ItemName.SACH_TUYET_KY_1_NAMEC:
                return ItemName.SACH_TUYET_KY_2_NAMEC;
            case ItemName.SACH_TUYET_KY_1_XAYDA:
                return ItemName.SACH_TUYET_KY_2_XAYDA;
            default:
                return -1;
        }
    }
}
