package id.umm.uts;

import id.umm.uts.io.CsvImporter;
import id.umm.uts.model.Putusan;
import id.umm.uts.struktur.SinglyLinkedList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

public class CsvImporterTest {
    @Test
    void skenario1_memuat50RecordValid() {
        SinglyLinkedList<Putusan> list = new SinglyLinkedList<>();
        int errorCount = CsvImporter.importFromCsv("data/putusan-50.csv", list);
        assertEquals(0, errorCount);
        assertEquals(50, list.size());
        assertTrue(list.validateInvariant());
    }

    @Test
    void skenario7_recordTidakValidDitolak(@TempDir Path tempDir) throws Exception {
        Path csvRusak = tempDir.resolve("rusak.csv");
        String konten = """
                caseId;nomorPerkara;pengadilan;tanggalPutusan;terdakwaKode;jenisNarkotika;beratBarangBuktiGram;pasalDakwaan;pasalTerbukti;tuntutanBulan;vonisBulan;dendaRupiah;barangBukti;keadaanMemberatkan;keadaanMeringankan;sourcePages
                P001;10/Pid.Sus/2026/PN Bdg;PN Bandung;2026-05-01;T001;Metamfetamina;0.5;114(1);112(1);60;48;800000000;1 paket;Memberatkan;Meringankan;amar=10
                P002;11/Pid.Sus/2026/PN Bdg;PN Bandung;SALAH-TANGGAL;T002;Ganja;-5.0;111(1);111(1);48;36;500000000;1 paket;Memberatkan;Meringankan;amar=12
                """;
        Files.writeString(csvRusak, konten);

        SinglyLinkedList<Putusan> list = new SinglyLinkedList<>();
        int errorCount = CsvImporter.importFromCsv(csvRusak.toString(), list);
        assertEquals(1, errorCount);
        assertEquals(1, list.size());
        assertTrue(list.validateInvariant());
    }
}