package id.umm.uts.layanan;

import id.umm.uts.model.Putusan;
import id.umm.uts.struktur.SinglyLinkedList;

public class FilterService {

    public static SinglyLinkedList<Putusan> filterGabungan(
            SinglyLinkedList<Putusan> dataAsal,
            String jenisZat,
            double beratMin,
            double beratMax,
            String pasal,
            int vonisMin,
            int vonisMax) {

        SinglyLinkedList<Putusan> hasil = new SinglyLinkedList<>();

        for (int i = 0; i < dataAsal.size(); i++) {
            Putusan p = dataAsal.get(i);

            boolean matchZat = jenisZat.isEmpty() || p.getJenisNarkotika().toLowerCase().contains(jenisZat.toLowerCase());
            boolean matchBerat = p.getBeratBarangBuktiGram() >= beratMin && p.getBeratBarangBuktiGram() <= beratMax;
            boolean matchPasal = pasal.isEmpty() || p.getPasalTerbukti().toLowerCase().contains(pasal.toLowerCase());
            boolean matchVonis = p.getVonisBulan() >= vonisMin && p.getVonisBulan() <= vonisMax;

            if (matchZat && matchBerat && matchPasal && matchVonis) {
                hasil.addLast(p);
            }
        }

        return hasil;
    }
}