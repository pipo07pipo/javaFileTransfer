/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package greetingserver;

/**
 *
 * @author soulq
 */
// File Name GreetingServer.java
import java.net.*;
import java.io.*;

public class GreetingServer extends Thread {
   private ServerSocket serverSocket;
   
   public GreetingServer(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(10000);
   }

   public void run() {
      while(true) {
         try {
            System.out.println("Waiting for client on port " + 
               serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();  // listenning on port
            
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());  // create data input steam 

            //open file to write data form client 1-byte
            File file = new File("D:/Downloads/write.pdf");
            try (FileOutputStream fop = new FileOutputStream(file)) {
                    // if file doesn't exists, then create it
                    if (!file.exists()) {
                            file.createNewFile();
                    }
                    
                    int content;    
                    while ((content = in.read()) != -1) {
                        fop.write(content);  //  write int to file 
                     }
                    fop.flush();
                    fop.close();
                    System.out.println("Done");
            } catch (IOException e) {
                    e.printStackTrace();
            }
            System.out.println("Receive file complete");
            
//            //open file to write data form client array byte buffer
//            File file = new File("D:/Downloads/write.pdf");
//            try (FileOutputStream fop = new FileOutputStream(file)) {
//                    // if file doesn't exists, then create it
//                    if (!file.exists()) {
//                            file.createNewFile();
//                    }
//                    //get the content in bytes
//                    byte [] contentInBytes = new byte[in.available()];
//                    in.read(contentInBytes);
//                    fop.write(contentInBytes);
//                    fop.flush();
//                    fop.close();
//                    System.out.println("Done");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println("Receive file complete");
                    
                    
            
            // MD5 
            FileInputStream fis_md5 = new FileInputStream(file);
            String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis_md5);
            fis_md5.close();
            System.out.println("MD5 is " + md5);
            // end MD5
        
            //read file
//            System.out.println(in.readUTF());
//            DataOutputStream out = new DataOutputStream(server.getOutputStream());
//            out.writeUTF("Thank you for connecting to สวัสดีครับ " + server.getLocalSocketAddress()
//               + "\nลาก่อน!");
            
            server.close();
            
         }catch(SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }
   
   public static void main(String [] args) {
      int port = Integer.parseInt(args[0]);  // port number 
      try {
         Thread t = new GreetingServer(port);  // fork new steam 
         t.start();
      }catch(IOException e) {
         e.printStackTrace();
      }
   }
}