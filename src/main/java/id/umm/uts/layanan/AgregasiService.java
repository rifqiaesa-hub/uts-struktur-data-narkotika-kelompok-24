package id.umm.uts.layanan;

import id.umm.uts.model.Putusan;
import id.umm.uts.struktur.SinglyLinkedList;

public class AgregasiService {
    public static void tampilkanRingkasan(SinglyLinkedList<Putusan> list) {
        if (list.isEmpty()) {
            System.out.println("Dataset kosong.");
            return;
        }
        int totalPerkara = list.size();
        long totalVonis = 0;
        long totalDenda = 0;

        String[] daftarZat = new String[totalPerkara];
        int[] jumlahZat = new int[totalPerkara];
        int jumlahJenisUnik = 0;

        for (int i = 0; i < totalPerkara; i++) {
            Putusan p = list.get(i);
            totalVonis += p.getVonisBulan();
            totalDenda += p.getDendaRupiah();

            String zat = p.getJenisNarkotika();
            int idx = -1;
            for (int j = 0; j < jumlahJenisUnik; j++) {
                if (daftarZat[j].equalsIgnoreCase(zat)) {
                    idx = j;
                    break;
                }
            }
            if (idx == -1) {
                daftarZat[jumlahJenisUnik] = zat;
                jumlahZat[jumlahJenisUnik] = 1;
                jumlahJenisUnik++;
            } else {
                jumlahZat[idx]++;
            }
        }

        System.out.println("=== RINGKASAN AGREGASI ===");
        System.out.println("Total Perkara   : " + totalPerkara);
        System.out.printf("Rata-rata Vonis : %.2f bulan%n", (double) totalVonis / totalPerkara);
        System.out.println("Total Denda     : Rp" + totalDenda);
        System.out.println("Distribusi Jenis Narkotika:");
        for (int i = 0; i < jumlahJenisUnik; i++) {
            System.out.println("  - " + daftarZat[i] + ": " + jumlahZat[i] + " perkara");
        }
    }
}