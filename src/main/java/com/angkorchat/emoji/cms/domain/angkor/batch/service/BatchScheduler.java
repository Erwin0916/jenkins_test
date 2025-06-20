package com.angkorchat.emoji.cms.domain.angkor.batch.service;

import com.angkorchat.emoji.cms.domain.angkor.settlement.service.CmsSettlementService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@RequiredArgsConstructor
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
public class BatchScheduler {
    private static final Logger log = LoggerFactory.getLogger(BatchScheduler.class);
    private final CmsSettlementService cmsSettlementService;

    @Scheduled(cron = "0 0 4 * * *") // 매일 04시
    public void dailySettlementBatch() {
        emojiDailySettlement();
    }

    @Scheduled(cron = "0 0 4 1 * *") // 매달 1일 04 시
    public void monthlySettlementBatch() {
        emojiMonthlySettlement();
    }

    private void emojiDailySettlement() {
        cmsSettlementService.batchSettlement();
    }

    private void emojiMonthlySettlement() {
        cmsSettlementService.batchAvailableAmountSettlement();
    }
}
