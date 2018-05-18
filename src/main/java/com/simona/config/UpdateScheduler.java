//package com.simona.config;
//
//import com.simona.service.MenuService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//@Configuration
//@EnableScheduling
//public class UpdateScheduler {
//
//    @Autowired
//    private SimpMessagingTemplate template;
//
//    @Autowired
//    private MenuService monitoringService;
//
//    @Scheduled(fixedRate = 60000)//60 sec
//    public void publishUpdates(){
//
//        template.convertAndSend("/topic/monitoring", monitoringService.getMonitoringObjects());
//
//    }
//
//}
