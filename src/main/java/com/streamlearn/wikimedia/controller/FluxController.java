package com.streamlearn.wikimedia.controller;

import com.streamlearn.wikimedia.service.FluxListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FluxController{


    private final FluxListener fluxListener;

    public FluxController(FluxListener fluxListener){
        this.fluxListener =fluxListener;
    }

    @GetMapping(value = "/logging-stream/fire-and-forget")
    public void fluxListener(){
        fluxListener.recentChangeListenerFireAndForget();
    }

    @GetMapping(value = "/logging-stream/synchronous")
    public void fluxListenerSynchronous(){
        fluxListener.recentChangeListenerSynchronous();
    }

    @GetMapping(value = "/logging-stream/asynchronous")
    @Async
    public void fluxListenerAsynchronous(){
        fluxListener.recentChangeListenerAsynchronous();
    }
    @GetMapping(value = "/logging-stream/asynchronous/stop-on-error")
    @Async
    public void fluxListenerAsynchronousStopOnError(){
        fluxListener.recentChangeListenerAsynchronousStopOnError();
    }
    @GetMapping(value = "/logging-stream/asynchronous/dead-letter-queue")
    @Async
    public void fluxListenerAsynchronousDeadLetterQueue(){
        fluxListener.recentChangeListenerAsynchronousDeadLetterQueue();
    }
}
