package uz.cosinus.thinkstore.enums;

public enum OrderStatus {
    CREATED,
    IN_PROGRESS,
    DELIVERED,  /// BU YETQIZIB BERILGAN PUNKITGAMI YOKI UYIGACHAMI
    APPROVED,  /// YAKUNLANDI YANI USER QABUL QILDI ZAKASINI
    CANCELLED,  /// ADMIN TARAFDAN ATMEN
    REJECT      ///USER TARAFDAN ATMEN QILINGANI

}
