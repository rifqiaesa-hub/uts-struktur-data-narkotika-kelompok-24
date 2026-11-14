package id.umm.uts;

import id.umm.uts.layanan.FilterService;
import id.umm.uts.model.Putusan;
import id.umm.uts.struktur.AuditStack;
import id.umm.uts.struktur.SinglyLinkedList;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class AuditStackTest {
    @Test
    void skenario6_searchPreserveTidakMengubahUrutan() {
        AuditStack<String> stack = new AuditStack<>();
        stack.push("AKSI_1");
        stack.push("AKSI_2_TARGET");
        stack.push("AKSI_3");

        assertTrue(stack.searchPreserve("TARGET"));
        assertEquals(3, stack.size());
        assertEquals("AKSI_3", stack.pop());
        assertEquals("AKSI_2_TARGET", stack.pop());
        assertEquals("AKSI_1", stack.pop());
        assertTrue(stack.validateInvariant());
    }

    @Test
    void skenario5_filterGabungan() {
        SinglyLinkedList<Putusan> list = new SinglyLinkedList<>();
        list.addLast(new Putusan("P001", "1/Pid.Sus/2026/PN Bdg", "PN Bandung", LocalDate.now(),
                "T001", "Metamfetamina", 1.5, "114(1)", "112(1)", 72, 60, 800000000L, "-", "-", "-", "1"));
        list.addLast(new Putusan("P002", "2/Pid.Sus/2026/PN Bdg", "PN Bandung", LocalDate.now(),
                "T002", "Ganja", 10.0, "111(1)", "111(1)", 48, 36, 500000000L, "-", "-", "-", "1"));

        SinglyLinkedList<Putusan> hasil = FilterService.filterGabungan(list, "Metamfetamina", 1.0, 5.0, "112", 50, 80);
        assertEquals(1, hasil.size());
        assertEquals("P001", hasil.get(0).getCaseId());
        assertTrue(hasil.validateInvariant());
    }
}