package ch.zhaw.feelancer4u.model.voucher;

import ch.zhaw.feelancer4u.model.Job;
import ch.zhaw.feelancer4u.model.JobType;

import java.util.List;
import java.util.stream.Collectors;

public class TwoForOneVoucher implements Voucher {
    private JobType affectedJobType;

    public TwoForOneVoucher(JobType affectedJobType) {
        this.affectedJobType = affectedJobType;
    }

    @Override
    public double getDiscount(List<Job> jobs) {
        if (jobs == null || jobs.isEmpty()) {
            return 0.0;
        }
    
        // Filter jobs of the affected type
        List<Job> affectedJobs = jobs.stream()
                                     .filter(job -> job.getJobType() == affectedJobType)
                                     .collect(Collectors.toList());
    
        // If there are less than 2 jobs of the affected type, return 0
        if (affectedJobs.size() < 2) {
            return 0.0;
        }
    
        // Calculate the total earnings of the affected jobs
        double totalEarnings = affectedJobs.stream().mapToDouble(Job::getEarnings).sum();
    
        // Return half of the total earnings
        return totalEarnings / 2;
    }
    
}

