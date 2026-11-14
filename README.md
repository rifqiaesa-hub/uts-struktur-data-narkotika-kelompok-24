# uts-struktur-data-narkotika-kelompok-24
## Identitas Kelompok
- **Anggota A:** RIFQIA SALMA (202510370110270) — GitHub: `@rifqiaesa-hub`
- **Anggota B:** n=NAZWA AZZAHRA (202510370110231) — GitHub: `@nazwaazzahra-crypto`
- **Sumber Data:** 50 Putusan Pengadilan Negeri Bandung (`putusan3.mahkamahagung.go.id`)

## Pilihan Struktur Data
1. **Struktur Utama:** `SinglyLinkedList<T>` (generic, dibangun dari nol dengan pointer `head`, `tail`, dan pencacah `size`).
2. **Struktur Sekunder:** `AuditStack<T>` (generic, berbasis array mentah `Object[]` dengan mekanisme *dynamic resizing*).

## Aturan Agregasi Dataset
- **Berat Barang Bukti (`beratBarangBuktiGram`):** Mengambil berat netto awal dari hasil pemeriksaan laboratorium (satuan gram). Jika terdapat lebih dari satu paket narkotika, seluruh berat netto dijumlahkan. Jika berat netto lab tidak tercantum, digunakan berat netto sisa sampel/amar.
- **Hukuman Penjara (`tuntutanBulan` & `vonisBulan`):** Dikonversi ke satuan bulan (`tahun × 12 + bulan`).

## Cara Build, Test, dan Menjalankan Aplikasi
- **Menjalankan Unit Test (7 Skenario):**
  ```bash
  mvn clean test