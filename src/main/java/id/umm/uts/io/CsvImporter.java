package id.umm.uts.io;

import id.umm.uts.model.Putusan;
import id.umm.uts.struktur.SinglyLinkedList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class CsvImporter {
    public static int importFromCsv(String filePath, SinglyLinkedList<Putusan> list) {
        int errorCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Lewati header
            String line;
            int lineNumber = 1;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                String[] cols = line.split(";", -1);
                if (cols.length < 16) {
                    System.out.println("[ERROR Baris " + lineNumber + "] Kolom kurang dari 16.");
                    errorCount++;
                    continue;
                }

                try {
                    String caseId = cols[0].trim();
                    String nomorPerkara = cols[1].trim();
                    String pengadilan = cols[2].trim();
                    LocalDate tanggal = LocalDate.parse(cols[3].trim());
                    String terdakwaKode = cols[4].trim();
                    String jenisNarkotika = cols[5].trim();
                    double berat = Double.parseDouble(cols[6].trim());
                    String pasalDakwaan = cols[7].trim();
                    String pasalTerbukti = cols[8].trim();
                    int tuntutan = Integer.parseInt(cols[9].trim());
                    int vonis = Integer.parseInt(cols[10].trim());
                    long denda = Long.parseLong(cols[11].trim());
                    String barangBukti = cols[12].trim();
                    String memberatkan = cols[13].trim();
                    String meringankan = cols[14].trim();
                    String sourcePages = cols[15].trim();

                    if (caseId.isEmpty() || nomorPerkara.isEmpty() || berat < 0 || tuntutan < 0 || vonis < 0 || denda < 0) {
                        throw new IllegalArgumentException("Nilai kosong atau angka negatif");
                    }

                    list.addLast(new Putusan(caseId, nomorPerkara, pengadilan, tanggal, terdakwaKode,
                            jenisNarkotika, berat, pasalDakwaan, pasalTerbukti, tuntutan, vonis,
                            denda, barangBukti, memberatkan, meringankan, sourcePages));
                } catch (Exception e) {
                    System.out.println("[ERROR Baris " + lineNumber + "] Record ditolak: " + e.getMessage());
                    errorCount++;
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca file: " + e.getMessage());
        }
        return errorCount;
    }
}