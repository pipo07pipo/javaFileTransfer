/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author soulq
 */
import java.net.*;
import java.io.*;

public class Client {

   public static void main(String [] args) {
       // send file to server 
      String serverName = args[0];  // ip number
      int port = Integer.parseInt(args[1]);  // port number 
      try {
         System.out.println("Connecting to " + serverName + " on port " + port);
         Socket client = new Socket(serverName, port);  // connect on socket
         
         System.out.println("Just connected to " + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();  // create output steam 
         DataOutputStream out = new DataOutputStream(outToServer);  // create data output
         
        // open & read file to send to server 1-byte
        File file = new File("D:/Downloads/sale.pdf");
        try (FileInputStream fis = new FileInputStream(file)) {
            int content;
            while ((content = fis.read()) != -1) {
               out.write(content);  // send int to server
            }
            System.out.println("Send file complete");
            //System.out.println("MD5 is " + md5);

        } catch (IOException e) {
                e.printStackTrace();
        }
        
//        // open & read file to send to server array bytes
//        File file = new File("D:/Downloads/sale.pdf");
//        try (FileInputStream fis = new FileInputStream(file)) {
//            byte[] buf = new byte[fis.available()];
//            fis.read(buf);
//            out.write(buf);
//            System.out.println("Send file complete");
//                
//        } catch (IOException e) {
//                e.printStackTrace();
//        }
        
        // MD5 
        FileInputStream fis_md5 = new FileInputStream(file);
        String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis_md5);
        fis_md5.close();
        System.out.println("MD5 is " + md5);
        // end MD5
        
         //out.writeUTF("สวัสดี Hello from " + client.getLocalSocketAddress());
         
//         InputStream inFromServer = client.getInputStream();
//         DataInputStream in = new DataInputStream(inFromServer);
//         
//         System.out.println("Server says " + in.readUTF());

         client.close();
      }catch(IOException e) {
         e.printStackTrace();
      }
   }
}