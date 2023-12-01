package ch.zhaw.feelancer4u.model.voucher;

import ch.zhaw.feelancer4u.model.Job;
import ch.zhaw.feelancer4u.model.JobType;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TwoForOneVoucherTest {

    @Test
    public void testDifferentTypes() {
        Job job1 = new Job();
        job1.setEarnings(100.0);
        job1.setJobType(JobType.IMPLEMENT);
        
        Job job2 = new Job();
        job2.setEarnings(200.0);
        job2.setJobType(JobType.REVIEW);

        TwoForOneVoucher voucher = new TwoForOneVoucher(JobType.TEST);
        double discount = voucher.getDiscount(Arrays.asList(job1, job2));
        
        assertEquals(0.0, discount, 0.001);
    }

    @Test
    public void testSameTypeTwoJobs() {
        Job job1 = new Job();
        job1.setEarnings(77.0);
        job1.setJobType(JobType.TEST);

        Job job2 = new Job();
        job2.setEarnings(33.0);
        job2.setJobType(JobType.TEST);

        TwoForOneVoucher voucher = new TwoForOneVoucher(JobType.TEST);
        double discount = voucher.getDiscount(Arrays.asList(job1, job2));

        assertEquals(55.0, discount, 0.001);
    }

    @Test
    public void testSameTypeThreeJobs() {
        Job job1 = new Job();
        job1.setEarnings(77.0);
        job1.setJobType(JobType.REVIEW);

        Job job2 = new Job();
        job2.setEarnings(33.0);
        job2.setJobType(JobType.REVIEW);

        Job job3 = new Job();
        job3.setEarnings(99.0);
        job3.setJobType(JobType.REVIEW);

        TwoForOneVoucher voucher = new TwoForOneVoucher(JobType.REVIEW);
        double discount = voucher.getDiscount(Arrays.asList(job1, job2, job3));

        assertEquals(104.5, discount, 0.001);
    }

    @Test
    public void testTwoSameOneDifferent() {
        Job job1 = new Job();
        job1.setEarnings(77.0);
        job1.setJobType(JobType.REVIEW);

        Job job2 = new Job();
        job2.setEarnings(33.0);
        job2.setJobType(JobType.REVIEW);

        Job job3 = new Job();
        job3.setEarnings(99.0);
        job3.setJobType(JobType.TEST);

        TwoForOneVoucher voucher = new TwoForOneVoucher(JobType.REVIEW);
        double discount = voucher.getDiscount(Arrays.asList(job1, job2, job3));

        assertEquals(55.0, discount, 0.001);
    }

    @ParameterizedTest
    @CsvSource({"0,0", "1,0", "2,77", "3,115.5", "4,154"})
    public void testCsvSource(int numberOfJobs, double expectedDiscount) {
        List<Job> jobs = IntStream.range(0, numberOfJobs)
                                  .mapToObj(i -> {
                                      Job job = new Job();
                                      job.setEarnings(77.0);
                                      job.setJobType(JobType.TEST);
                                      return job;
                                  })
                                  .collect(Collectors.toList());

        TwoForOneVoucher voucher = new TwoForOneVoucher(JobType.TEST);
        double discount = voucher.getDiscount(jobs);

        assertEquals(expectedDiscount, discount, 0.001);
    }
}

