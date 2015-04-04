/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ardunio;

/**
 *
 * @author whcheng
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import gnu.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Communicator1 implements SerialPortEventListener {

    private Enumeration ports = null;
    
    private SerialPort serialPort = null;
    
    private BufferedReader input = null;
    
    private String PORT_NAMES = "/dev/ttyACM0";

    public static void main(String argv[]) throws Exception {
        Communicator1 main = null;
        main = new Communicator1();
        main.initialize();
        Thread t=new Thread() {
            public void run() {
                while(true){
                    try {
                        Thread.sleep(3000000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Communicator1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        t.start();
        System.out.println("Started");
    }

    //search for all the serial ports
    //pre: none
    //post: adds all the found ports to a combo box on the GUI
    public void initialize() throws Exception {
        ports = CommPortIdentifier.getPortIdentifiers();
        CommPortIdentifier curPort = null;
        while (ports.hasMoreElements()) {
            curPort = (CommPortIdentifier) ports.nextElement();
            if(curPort.getName().equals(PORT_NAMES)){
                serialPort = (SerialPort) curPort.open(PORT_NAMES, 100);
                break;
            }
            //get only serial ports
        }
        if (serialPort!=null) {
            serialPort.setSerialPortParams(9600,// 波特率

                    SerialPort.DATABITS_8,// 数据位数

                    SerialPort.STOPBITS_1, // 停止位

                    SerialPort.PARITY_NONE);// 奇偶位
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        }

    }

    @Override
    public void serialEvent(SerialPortEvent evt) {
        if (evt.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine=input.readLine();
                System.out.println(inputLine);
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }
    
    public void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

}
