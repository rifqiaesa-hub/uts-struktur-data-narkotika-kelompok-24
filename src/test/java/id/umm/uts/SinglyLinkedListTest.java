package id.umm.uts;

import id.umm.uts.struktur.SinglyLinkedList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SinglyLinkedListTest {
    @Test
    void skenario2_cariAdaTidakAdaDanBatas() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.addLast("P001");
        list.addLast("P025");
        list.addLast("P050");

        assertEquals(0, list.search("P001"));
        assertEquals(1, list.getLangkahTerakhir());
        assertEquals(2, list.search("P050"));
        assertEquals(3, list.getLangkahTerakhir());
        assertEquals(-1, list.search("P999"));
        assertTrue(list.validateInvariant());
    }

    @Test
    void skenario3Dan4_hapusDanTambahKembali() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.addLast("P001");
        list.addLast("P002");
        list.addLast("P003");
        list.addLast("P004");

        // Hapus tengah, pertama, terakhir
        assertEquals("P002", list.remove(1));
        assertTrue(list.validateInvariant());
        assertEquals("P001", list.remove(0));
        assertTrue(list.validateInvariant());
        assertEquals("P004", list.remove(list.size() - 1));
        assertEquals("P003", list.get(0));
        assertTrue(list.validateInvariant());

        // Tambah kembali 3 record
        list.addFirst("P001_NEW");
        list.insert(1, "P002_NEW");
        list.addLast("P004_NEW");
        assertEquals(4, list.size());
        assertTrue(list.validateInvariant());
    }
}