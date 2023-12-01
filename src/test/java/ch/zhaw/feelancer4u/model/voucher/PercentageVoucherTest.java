package ch.zhaw.feelancer4u.model.voucher;

import ch.zhaw.feelancer4u.model.Job;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PercentageVoucherTest {

    private PercentageVoucher voucher;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testEmptyJobList() {
        voucher = new PercentageVoucher(10); // Any percentage value will do for this test
        double discount = voucher.getDiscount(Collections.emptyList());
        assertEquals(0.0, discount, 0.001, "Discount should be 0.0 for an empty job list");
    }

   @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 20, 49, 50})
    void testPercentageVoucher(int percentage) {
        PercentageVoucher voucher = new PercentageVoucher(percentage);
        List<Job> jobs = new ArrayList<>();
        Job job = new Job();
        job.setEarnings(50.0);
        jobs.add(job);

        double discount = voucher.getDiscount(jobs);

        double expectedDiscount = (percentage / 100.0) * 50.0;
        assertEquals(expectedDiscount, discount, 0.01);
    }

    @Test
    void testMultipleJobs() {
        PercentageVoucher voucher = new PercentageVoucher(42);

        // Create mock jobs
        Job job1 = mock(Job.class);
        when(job1.getEarnings()).thenReturn(42.0);

        Job job2 = mock(Job.class);
        when(job2.getEarnings()).thenReturn(77.0);

        List<Job> jobs = new ArrayList<>();
        jobs.add(job1);
        jobs.add(job2);

        double discount = voucher.getDiscount(jobs);

        assertEquals(49.98, discount);
    }

    @Test
    void testEmpty() {
        PercentageVoucher voucher = new PercentageVoucher(10);
        List<Job> jobs = new ArrayList<>();

        double discount = voucher.getDiscount(jobs);

        assertEquals(0, discount);
    }

    @Test
    void testDiscountValueGreaterThan50() {
        assertThrows(RuntimeException.class, () -> {
            new PercentageVoucher(51);
        }, "Error: Discount value must be less or equal to 50.");
    }

    @Test
    void testDiscountValueEquals50() {
        assertDoesNotThrow(() -> {
            new PercentageVoucher(50);
        });
    }

    @Test
    void testDiscountValueLessThanZero() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            new PercentageVoucher(-1);
        });

        String expectedMessage = "Error: Discount value must be greater than zero.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDiscountValueEqualsZero() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            new PercentageVoucher(0);
        });

        String expectedMessage = "Error: Discount value must be greater than zero.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}

