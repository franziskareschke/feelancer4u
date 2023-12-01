package ch.zhaw.feelancer4u.model.voucher;

import ch.zhaw.feelancer4u.model.Job;
import java.util.List;

public class FiveBucksVoucher implements Voucher {

    @Override
    public double getDiscount(List<Job> jobs) {
        double totalEarnings = jobs.stream()
                                   .mapToDouble(Job::getEarnings)
                                   .sum();
                                   
        return totalEarnings >= 10.0 ? 5.0 : 0.0;
    }
}
