package com.kkoch.user.domain.alarm.repository;

import com.kkoch.user.domain.alarm.QAlarm;
import com.kkoch.user.domain.member.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.kkoch.user.domain.alarm.QAlarm.alarm;
import static com.kkoch.user.domain.member.QMember.*;

@Repository
public class AlarmCommandRepository {

    private final JPAQueryFactory queryFactory;

    public AlarmCommandRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public int updateOpen(String memberKey) {
        List<Long> alarmIds = queryFactory
            .select(alarm.id)
            .from(alarm)
            .join(alarm.member, member)
            .where(
                alarm.open.isFalse(),
                member.memberKey.eq(memberKey)
            )
            .fetch();

        return (int) queryFactory
            .update(alarm)
            .where(alarm.id.in(alarmIds))
            .set(alarm.open, true)
            .execute();
    }

}
