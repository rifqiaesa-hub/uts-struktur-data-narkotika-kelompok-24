package id.umm.uts;

import id.umm.uts.io.CsvImporter;
import id.umm.uts.io.Exporter;
import id.umm.uts.layanan.*;
import id.umm.uts.model.Putusan;
import id.umm.uts.struktur.AuditStack;
import id.umm.uts.struktur.SinglyLinkedList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class App {
    private static final SinglyLinkedList<Putusan> daftarPutusan = new SinglyLinkedList<>();
    private static final AuditStack<String> auditStack = new AuditStack<>();
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static void catatAudit(String aksi) {
        auditStack.push(LocalDateTime.now().format(FMT) + " | " + aksi);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int error = CsvImporter.importFromCsv("data/putusan-50.csv", daftarPutusan);
        catatAudit("IMPOR awal: " + daftarPutusan.size() + " valid, " + error + " ditolak");

        SinglyLinkedList<Putusan> hasilTerakhir = daftarPutusan;

        while (true) {
            System.out.println("\n=== APLIKASI PUTUSAN NARKOTIKA (Total: " + daftarPutusan.size() + ") ===");
            System.out.println("1. Tampil Semua Putusan");
            System.out.println("2. Tambah Putusan Baru");
            System.out.println("3. Hapus Putusan (berdasarkan indeks)");
            System.out.println("4. Cari Putusan (Case ID / Nomor Perkara)");
            System.out.println("5. Filter Gabungan");
            System.out.println("6. Agregasi & Statistik");
            System.out.println("7. Urutkan Berdasarkan Vonis");
            System.out.println("8. Lihat Riwayat Audit (Stack)");
            System.out.println("9. Ekspor Hasil Terakhir ke CSV");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            String pilih = sc.nextLine();

            switch (pilih) {
                case "1" -> {
                    for (int i = 0; i < daftarPutusan.size(); i++) {
                        System.out.println(i + ". " + daftarPutusan.get(i));
                    }
                    hasilTerakhir = daftarPutusan;
                    catatAudit("TAMPIL semua data");
                }
                case "2" -> {
                    System.out.print("Case ID (mis. P051): "); String id = sc.nextLine();
                    System.out.print("Nomor Perkara: "); String no = sc.nextLine();
                    System.out.print("Kode Terdakwa (mis. T051): "); String tk = sc.nextLine();
                    System.out.print("Jenis Narkotika: "); String zat = sc.nextLine();
                    System.out.print("Berat (gram): "); double brt = Double.parseDouble(sc.nextLine());
                    System.out.print("Pasal Terbukti: "); String psl = sc.nextLine();
                    System.out.print("Vonis (bulan): "); int vns = Integer.parseInt(sc.nextLine());
                    System.out.print("Denda (rupiah): "); long dnd = Long.parseLong(sc.nextLine());

                    Putusan baru = new Putusan(id, no, "PN Bandung", LocalDate.now(), tk, zat, brt,
                            psl, psl, vns, vns, dnd, "-", "-", "-", "manual=1");
                    daftarPutusan.addLast(baru);
                    catatAudit("TAMBAH " + id);
                    System.out.println("Berhasil ditambahkan.");
                }
                case "3" -> {
                    System.out.print("Indeks yang dihapus (0 - " + (daftarPutusan.size() - 1) + "): ");
                    int idx = Integer.parseInt(sc.nextLine());
                    Putusan dihapus = daftarPutusan.remove(idx);
                    catatAudit("HAPUS indeks " + idx + " (" + dihapus.getCaseId() + ")");
                    System.out.println("Dihapus: " + dihapus.getCaseId());
                }
                case "4" -> {
                    System.out.print("Masukkan Case ID atau Nomor Perkara: ");
                    String kw = sc.nextLine();
                    PencarianService.HasilCari hc = PencarianService.cariByCaseIdAtauNomor(daftarPutusan, kw);
                    if (hc.putusan != null) {
                        System.out.println("Ditemukan di indeks " + hc.indeks + " setelah " + hc.langkah + " langkah:");
                        System.out.println(hc.putusan);
                    } else {
                        System.out.println("Tidak ditemukan setelah " + hc.langkah + " langkah.");
                    }
                    catatAudit("CARI '" + kw + "' (" + hc.langkah + " langkah)");
                }
                case "5" -> {
                    System.out.print("Filter Jenis Zat (kosongkan jika semua): "); String zat = sc.nextLine();
                    System.out.print("Berat Min (gram, 0 jika bebas): "); double minB = Double.parseDouble(sc.nextLine());
                    System.out.print("Berat Max (gram, 99999 jika bebas): "); double maxB = Double.parseDouble(sc.nextLine());
                    System.out.print("Pasal Terbukti (kosongkan jika semua): "); String psl = sc.nextLine();
                    System.out.print("Vonis Min (bulan, 0 jika bebas): "); int minV = Integer.parseInt(sc.nextLine());
                    System.out.print("Vonis Max (bulan, 9999 jika bebas): "); int maxV = Integer.parseInt(sc.nextLine());

                    hasilTerakhir = FilterService.filterGabungan(daftarPutusan, zat, minB, maxB, psl, minV, maxV);
                    System.out.println("Ditemukan " + hasilTerakhir.size() + " putusan:");
                    for (int i = 0; i < hasilTerakhir.size(); i++) System.out.println("- " + hasilTerakhir.get(i));
                    catatAudit("FILTER ditemukan " + hasilTerakhir.size() + " record");
                }
                case "6" -> {
                    AgregasiService.tampilkanRingkasan(daftarPutusan);
                    catatAudit("AGREGASI statistik");
                }
                case "7" -> {
                    System.out.print("Urutkan menaik/ascending? (y/n): ");
                    boolean asc = sc.nextLine().equalsIgnoreCase("y");
                    hasilTerakhir = UrutanService.urutkanBerdasarkanVonis(daftarPutusan, asc);
                    for (int i = 0; i < hasilTerakhir.size(); i++) System.out.println("- " + hasilTerakhir.get(i));
                    catatAudit("URUT vonis (" + (asc ? "ASC" : "DESC") + ")");
                }
                case "8" -> {
                    System.out.println("=== RIWAYAT AUDIT (TERBARU DI ATAS) ===");
                    for (int i = auditStack.size() - 1; i >= 0; i--) {
                        System.out.println((auditStack.size() - i) + ". " + auditStack.getAt(i));
                    }
                }
                case "9" -> {
                    System.out.print("Nama file tujuan (mis. hasil_ekspor.csv): ");
                    String namaFile = sc.nextLine();
                    try {
                        Exporter.exportToCsv(namaFile, hasilTerakhir);
                        catatAudit("EKSPOR ke " + namaFile);
                        System.out.println("Berhasil diekspor ke " + namaFile);
                    } catch (Exception e) {
                        System.out.println("Gagal ekspor: " + e.getMessage());
                    }
                }
                case "0" -> {
                    System.out.println("Selesai.");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }
}