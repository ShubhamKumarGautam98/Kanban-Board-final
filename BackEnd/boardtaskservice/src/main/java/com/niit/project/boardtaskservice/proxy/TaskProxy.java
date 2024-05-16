package com.niit.project.boardtaskservice.proxy;


import com.niit.project.boardtaskservice.domain.UserDetails;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="userauthentication",url="localhost:8081")
public interface TaskProxy {
    @RequestMapping("/api/v1/saveUser")
    public ResponseEntity<?> registerUser(@RequestBody UserDetails user);


}
