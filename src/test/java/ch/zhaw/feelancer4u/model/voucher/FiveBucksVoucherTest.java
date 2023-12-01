package ch.zhaw.feelancer4u.model.voucher;

import ch.zhaw.feelancer4u.model.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FiveBucksVoucherTest {

    private FiveBucksVoucher voucher;

    @BeforeEach
    public void setup() {
        voucher = new FiveBucksVoucher();
    }

    @Test
    public void testEmpty() {
        List<Job> emptyJobList = new ArrayList<>();
        double discount = voucher.getDiscount(emptyJobList);
        assertEquals(0.0, discount, "Discount should be 0.0 for an empty job list");
    }

    @Test
    public void testTen() {
        Job job = new Job();
        job.setEarnings(10.0);
        List<Job> jobList = Arrays.asList(job);

        double discount = voucher.getDiscount(jobList);
        assertEquals(5.0, discount, "Discount should be 5.0 for a job list with total earnings of 10.0");
    }
}

