package id.umm.uts.layanan;

import id.umm.uts.model.Putusan;
import id.umm.uts.struktur.SinglyLinkedList;

public class UrutanService {
    public static SinglyLinkedList<Putusan> urutkanBerdasarkanVonis(SinglyLinkedList<Putusan> sumber, boolean ascending) {
        SinglyLinkedList<Putusan> hasil = new SinglyLinkedList<>();
        for (int i = 0; i < sumber.size(); i++) {
            Putusan kandidat = sumber.get(i);
            int posisiSisip = 0;
            while (posisiSisip < hasil.size()) {
                int vonisSaatIni = hasil.get(posisiSisip).getVonisBulan();
                boolean geser = ascending
                        ? kandidat.getVonisBulan() > vonisSaatIni
                        : kandidat.getVonisBulan() < vonisSaatIni;
                if (!geser) break;
                posisiSisip++;
            }
            hasil.insert(posisiSisip, kandidat);
        }
        return hasil;
    }
}