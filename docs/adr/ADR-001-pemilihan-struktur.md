# ADR-001: Pemilihan Struktur Data Utama dan Sekunder

## Status
Diterima

## Keputusan
1. **Struktur Utama:** `SinglyLinkedList<T>` dengan pointer `head`, `tail`, dan pencacah `size`.
2. **Struktur Sekunder:** `AuditStack<T>` menggunakan array mentah `Object[]` dengan mekanisme *resize*.

## Alasan
- Dibangun dari nol menggunakan generic `<T>` tanpa koleksi bawaan `java.util`.
- `SinglyLinkedList<T>` memudahkan pembuktian perubahan pointer (`head`, `next`, `tail`) pada Skenario Uji #3.
- `AuditStack<T>` mendukung pencatatan riwayat aksi LIFO dan pencarian non-destruktif (`searchPreserve`).