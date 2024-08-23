package com.example.week3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gunha
 * @version 1.0
 * @since 2024-08-23 오후 4:32
 */
@Component
public class ymlRunner implements ApplicationRunner {

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

    private void load(String fileName) throws IOException {

        File yamlFile = new File(fileName);
        String yamlStr = Files.readString(yamlFile.toPath());

        Map<String, Object> yaml = new Yaml().load(yamlStr);
        System.out.println(yaml);

        // 최상위 키 출력
        Set<String> keys = yaml.keySet();
        System.out.println(keys);

        for (String k : keys) {
            List<Map<String, Object>> compInfos = (List<Map<String, Object>>)yaml.get(k);
            System.out.println(compInfos);
        }
    }
}
