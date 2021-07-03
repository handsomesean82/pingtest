package sean.pingftpserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

@RestController
public class MyController {

    Logger logger = LoggerFactory.getLogger(getClass());
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
        return "Hello Ping";
    }


    @GetMapping("/telnet")
    @ResponseBody
    public String Telnet(@RequestParam String ipAddress, @RequestParam int port){
        try{
            Socket pingSocket = null;
            PrintWriter out = null;
            BufferedReader in = null;

            try {
                pingSocket = new Socket(ipAddress, port);
                out = new PrintWriter(pingSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(pingSocket.getInputStream()));
            } catch (IOException e) {
                logger.error("error telnet to " + ipAddress + " " + port + " with error: " + e.getMessage());
                e.printStackTrace();
            }

            out.println("ping");
            logger.info("telnet to " + ipAddress + " port: " + port);
            System.out.println(in.readLine());
            out.close();
            in.close();
            pingSocket.close();
        }catch (Exception e){

        }
        return "Hello Telnet";
    }



}



