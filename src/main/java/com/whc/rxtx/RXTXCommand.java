/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whc.rxtx;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 *
 * @author whcheng
 */
public class RXTXCommand implements SerialPortEventListener {
    
    private static BufferedReader input = null;
    
    private static RXTXCommand rXTXCommand = null;
    
    private String text = "";
    
    private static SerialPort serialPort = null;
    
    
    
    public static RXTXCommand getInstance(){
        if(rXTXCommand==null){
            rXTXCommand = new RXTXCommand();
        }
        return rXTXCommand;
    }
    
    public ArrayList<String> listPort(){
        ArrayList<String> result = new ArrayList<>();
        Enumeration enumeration = CommPortIdentifier.getPortIdentifiers();
        while (enumeration.hasMoreElements()) {
            CommPortIdentifier curPort = (CommPortIdentifier) enumeration.nextElement();
            result.add(curPort.getName());
        }
        return result;
    }
    
    
    public SerialPort connect(String serialName,int baud,int databits,int stopbits,int parity) throws Exception{
        System.out.println(serialName);
        CommPortIdentifier commPortIdentifier = CommPortIdentifier.getPortIdentifier(serialName);
        if(serialPort!=null) close();
        serialPort = (SerialPort) commPortIdentifier.open("", 100);
        serialPort.setSerialPortParams(baud,databits,stopbits,parity);// 奇偶位
        input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
        serialPort.addEventListener(this);
        serialPort.notifyOnDataAvailable(true);
        return serialPort;
    }
    
    public void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
            text="";
        }
    }
    
    @Override
    public void serialEvent(SerialPortEvent evt) {
        if (evt.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                text=input.readLine();
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        text = text;
    }
    
    
    
    
}
