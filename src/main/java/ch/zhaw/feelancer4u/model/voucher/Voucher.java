package ch.zhaw.feelancer4u.model.voucher;

import ch.zhaw.feelancer4u.model.Job;
import java.util.List;

public interface Voucher {
    public double getDiscount (
    List<Job> jobs
    );
}
