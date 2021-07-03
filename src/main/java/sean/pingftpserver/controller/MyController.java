package sean.pingftpserver.controller;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.*;
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

    @GetMapping("/ftp")
    @ResponseBody
    public String Ftp(@RequestParam String ipAddress, @RequestParam int port, @RequestParam String user, @RequestParam String password, @RequestParam String remoteFile, @RequestParam String localFile){
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ipAddress, port);
            boolean login = ftpClient.login(user, password);
            if(login)
                logger.info("login successfully");
            else {
                logger.error("login failed");
                return "FTP login failed";
            }
            /*boolean remotePassive =*/ ftpClient.enterLocalPassiveMode();
            /*if(remotePassive)
                logger.info("Remote passive mode entered");
            else {
                logger.error("Failed to enter remote passive mode");
                return "error entering remote passive mode";
            }*/
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            File downloadFile = new File(localFile);
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
            boolean success = ftpClient.retrieveFile(remoteFile, outputStream);
            outputStream.close();

            if(success){
                logger.info("File " + remoteFile + " has been downloaded to local " + localFile);
            }else{
                logger.error("Failed to download file");
            }
        }catch (Exception e){
            logger.error("Error: " + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return "Hello FTP";
    }



}



