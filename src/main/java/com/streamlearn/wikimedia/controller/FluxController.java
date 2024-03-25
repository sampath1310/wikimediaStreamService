package com.streamlearn.wikimedia.controller;

import com.streamlearn.wikimedia.service.FluxListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FluxController{


    private final FluxListener fluxListener;

    @Autowired
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
}
