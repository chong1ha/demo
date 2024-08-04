package com.example.week1.dummy.database.job;

import com.example.core.common.util.CommonUtil;
import com.example.week1.common.job.AbstractTask;
import com.example.week1.dummy.database.model.DummyDomain;
import com.example.week1.dummy.database.service.DummyDomainService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * DummyJob B
 *
 * @author kang-geonha
 * @version 1.0
 * @since 2024/07/23 12:15 AM
 */
@Component
public class DummyJobB extends AbstractTask {

    public DummyJobB(DummyDomainService dummyDomainService) {
        super(dummyDomainService);
    }

    @Override
    public void init() throws Exception {
        System.out.println("Thread ID: " + Thread.currentThread().getId() +" DummyJob B: init()");
    }


    @Override
    public List<Map<String, Object>> collect(long time, DummyDomain domain) throws Exception {
        System.out.println("Thread ID: " + Thread.currentThread().getId() +" DummyJob B: Data collected at: " + CommonUtil.longToOffsetDateTime(time));

        // getDummyId 호출
        String dummyName = "Name1";
        Character serviceYn = 'Y';
        String dummyId = this.getDummyId(serviceYn, dummyName);
        System.out.println("DummyJob B: Retrieved Dummy ID: " + dummyId);

        return List.of();
    }

    @Override
    public void save(List<Map<String, Object>> data) throws Exception {
        System.out.println("DummyJob B: Data saved: " + data);
    }
}
