package com.simplay.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Pigeon {

	public static void main(String[] args) {
		Socket echoSocket;
		try {
			echoSocket = new Socket("192.168.1.125", 21337);
		    PrintWriter out =
			        new PrintWriter(echoSocket.getOutputStream(), true);
		    out.println("hello pigeon from minecraft");
		    out.close();
		    echoSocket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
