package br.com.poc.example.poctestcosmos.infra;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
class ApplicationReadyLoadData {

    @Autowired
    private List<LoadData> loaders;

    @EventListener(ApplicationReadyEvent.class)
    public void ready(ApplicationReadyEvent event) {

        loaders.stream().forEach(loadData -> {
            var loadDataName = loadData.name();
            log.info("Load Data From: {}", loadDataName);
            try {
                loadData.load();
            } catch (Exception ex) {
                log.error("Failed to load data from {} \n {}", loadDataName, ex);
            }
        });
    }
}
