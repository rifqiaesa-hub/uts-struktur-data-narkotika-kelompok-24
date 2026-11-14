package id.umm.uts.layanan;

import id.umm.uts.model.Putusan;
import id.umm.uts.struktur.SinglyLinkedList;

public class PencarianService {
    public static class HasilCari {
        public final Putusan putusan;
        public final int indeks;
        public final int langkah;

        public HasilCari(Putusan putusan, int indeks, int langkah) {
            this.putusan = putusan;
            this.indeks = indeks;
            this.langkah = langkah;
        }
    }

    public static HasilCari cariByCaseIdAtauNomor(SinglyLinkedList<Putusan> list, String keyword) {
        int langkah = 0;
        for (int i = 0; i < list.size(); i++) {
            langkah++;
            Putusan p = list.get(i);
            if (p.getCaseId().equalsIgnoreCase(keyword.trim()) ||
                    p.getNomorPerkara().equalsIgnoreCase(keyword.trim())) {
                return new HasilCari(p, i, langkah);
            }
        }
        return new HasilCari(null, -1, langkah);
    }
}