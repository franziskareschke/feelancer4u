package ch.zhaw.feelancer4u.model.voucher;

import java.util.List;

import ch.zhaw.feelancer4u.model.Job;

public class PercentageVoucher implements Voucher {

    private int discount;

    public PercentageVoucher(int discount) {
        setDiscount(discount);
    }    

   @Override
    public double getDiscount(List<Job> jobs) {
        if (jobs == null || jobs.isEmpty()) {
            return 0.0;
        }

        double totalEarnings = jobs.stream()
                                   .mapToDouble(Job::getEarnings)
                                   .sum();
        return (discount / 100.0) * totalEarnings;
    }

    public void setDiscount(int discount) {
        if (discount > 50) {
            throw new RuntimeException("Error: Discount value must be less or equal to 50.");
        } else if (discount <= 0) {
            throw new RuntimeException("Error: Discount value must be greater than zero.");
        } else {
            this.discount = discount;
        }
    }
}
