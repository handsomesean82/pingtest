package sean.pingftpserver.controller;

import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;

@RestController
public class MyController {


    @GetMapping("/ping")
    @ResponseBody
    public String HelloWorld(@RequestParam String ipAddress){
        try{
            //String ipAddress = "127.0.0.1";
            InetAddress geek = InetAddress.getByName(ipAddress);
            System.out.println("Sending Ping Request to " + ipAddress);
            if (geek.isReachable(5000))
                System.out.println("Host is reachable");
            else
                System.out.println("Sorry ! We can't reach to this host");
        }catch (Exception e){

        }
        return "HelloWorld123";
    }
}



