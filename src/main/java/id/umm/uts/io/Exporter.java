package id.umm.uts.io;

import id.umm.uts.model.Putusan;
import id.umm.uts.struktur.SinglyLinkedList;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Exporter {
    public static void exportToCsv(String filePath, SinglyLinkedList<Putusan> list) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("caseId;nomorPerkara;pengadilan;tanggalPutusan;terdakwaKode;jenisNarkotika;beratBarangBuktiGram;pasalDakwaan;pasalTerbukti;tuntutanBulan;vonisBulan;dendaRupiah;barangBukti;keadaanMemberatkan;keadaanMeringankan;sourcePages");
            for (int i = 0; i < list.size(); i++) {
                pw.println(list.get(i).toCsvRow());
            }
        }
    }
}