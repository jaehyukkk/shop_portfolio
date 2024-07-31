package com.bubaum.pairing_server.scheduler.component

import com.bubaum.pairing_server.membergrade.service.MemberGradeService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Scheduler(
    private val memberGradeService: MemberGradeService
) {

    //매주 월요일
    @Scheduled(cron = "0 0 0 * * MON")
    fun scheduler() {
//        println("매주 월요일 00:00:00에 실행됩니다.")
        memberGradeService.updateMemberGradeProcedure()
    }
}
