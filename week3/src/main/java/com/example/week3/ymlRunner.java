package com.example.week3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;

/**
 * @author gunha
 * @version 1.0
 * @since 2024-08-23 오후 4:32
 */
@Component
public class ymlRunner implements ApplicationRunner {

    private final ApplicationContext context;

    public ymlRunner(ApplicationContext context) {
        this.context = context;
    }

    @Autowired
    private Environment env;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (args.getSourceArgs().length >= 1) {
            //load(args.getSourceArgs()[0]);
        } else {
            load(env.getProperty("WORKFLOW_FILE"));
        }
    }

    private void load(String fileName) throws Exception {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        // load
        File yamlFile = new File(fileName);
        Map<String, Object> yamlMap = mapper.readValue(yamlFile, Map.class);

        // 분리 (jobs 와 runners)
        Map<String, Object> runner = null;
        if (yamlMap.containsKey("on")) {

            Object value = yamlMap.get("on");

            if (value instanceof Map) {
                runner = (Map<String, Object>) value;
                yamlMap.remove("on");
            } else {
                throw new Exception("'on' key is present but is not a Map");
            }
        } else {
            throw new Exception("'on' data is not present in the YAML file");
        }

        // 최상위 키 출력 (runner(="on") 제외한 cronJobs 목록)
        Set<String> keys = yamlMap.keySet();
        for (String key : keys) {

            // LinkedHashMap
            @SuppressWarnings("unchecked")
            Map<String, Object> jobsMap = (Map<String, Object>) yamlMap.get(key);

            if (jobsMap == null) {
                continue;
            }

            // Jobs
            Jobs jobs = new Jobs();
            jobs.setSteps(convertJobsMap(jobsMap));

            // Jobs 객체 출력
            System.out.println("Jobs: " + jobs);
            System.out.println(jobs);
        }

        //

    }

    /**
     * jobsMap 순회하여 각 항목을 JobStep 객체로 변환하여 새로운 Map에 저장
     *
     * @param jobsMap (root)
     */
    private static Map<String, JobStep> convertJobsMap(Map<String, Object> jobsMap) {

        // result Map
        Map<String, JobStep> steps = new HashMap<>();

        // jobsMap 엔트리
        for (Map.Entry<String, Object> entry : jobsMap.entrySet()) {

            // step-01, step-02
            String stepKey = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {

                @SuppressWarnings("unchecked")
                Map<String, Object> stepMap = (Map<String, Object>) value;

                JobStep jobStep = new JobStep();

                if (stepMap.containsKey("runs")) {

                    // runs 요소 처리
                    @SuppressWarnings("unchecked")
                    List<Map<String, String>> runsList = (List<Map<String, String>>) stepMap.get("runs");
                    List<Job> jobsList = new ArrayList<>();
                    for (Map<String, String> jobMap : runsList) {
                        Job job = new Job();
                        job.setName(jobMap.get("name"));
                        job.setClassName(jobMap.get("class"));
                        jobsList.add(job);
                    }

                    jobStep.setRuns(jobsList);
                }

                // needs 요소 처리
                if (stepMap.containsKey("needs")) {
                    jobStep.setNeeds((String) stepMap.get("needs"));
                }

                steps.put(stepKey, jobStep);
            }
        }

        return steps;
    }







    private Map<String, Object> makeRunners(List<Map<String, Object>> compInfo) throws Exception {

        Map<String, Object> tasks = new HashMap<>();

        if (compInfo == null) {
            return tasks;
        }

        for (Map<String, Object> componentInfo : compInfo) {
            String className = (String) componentInfo.get("class");
            String taskName = (String) componentInfo.get("name");

            if (className == null || taskName == null) {
                throw new Exception("class or name is not set");
            }

            Object task = (Object) context.getBean(className);
            if (task == null) {
                throw new Exception("Bean with class name " + className + " is not found");
            }

            tasks.put(taskName, task);
        }

        return tasks;
    }
}
