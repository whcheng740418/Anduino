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
import gnu.io.CommPortIdentifier;

import gnu.io.PortInUseException;

import gnu.io.SerialPort;

import gnu.io.UnsupportedCommOperationException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;

import java.util.Enumeration;

import java.util.TooManyListenersException;

public class ComTest {

    /**
     *
     * @param args
     *
     * @throws Exception
     *
     */
    @SuppressWarnings("unchecked")

    public static void main(String[] args) throws Exception {

        String port = "/dev/ttyACM0";
        System.out.println(port);

        for (Enumeration<CommPortIdentifier> e = CommPortIdentifier
                .getPortIdentifiers(); e.hasMoreElements();) {

            CommPortIdentifier portId = e.nextElement();

            if (portId.getName().equals(port)) {

                System.out.println("\r");

                System.out.println("找到端口： " + port);

                sendAtTest(portId);

            }

        }

    }

    private static void sendAtTest(CommPortIdentifier portId)
            throws PortInUseException, UnsupportedCommOperationException,
            TooManyListenersException, IOException, InterruptedException {

        System.out.println("打开端口 …");

        final SerialPort serialPort = (SerialPort) portId.open("wavecom", 100);

        serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);

        serialPort.setSerialPortParams(9600,// 波特率

                SerialPort.DATABITS_8,// 数据位数

                SerialPort.STOPBITS_1, // 停止位

                SerialPort.PARITY_NONE);// 奇偶位

        System.out.println("端口已打开。\n发送AT指令 …");

        BufferedReader inputStream = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

//        OutputStream outputStream = serialPort.getOutputStream();
//
//        outputStream.write("AT\r".getBytes());

        Thread.sleep(1000);
        
        while(inputStream.ready()){
            System.out.println(inputStream.readLine());
        }

        serialPort.close();

        System.out.println("关闭端口。");

    }

}
