package com.liaison.reprocess;

public class RunTest {
	public static void main(String[] args) throws Exception {
		String url = "at4p-lvdxphub10.liaison.prod:7878";
		String user = "consoleuser";
		String pwd = "consoleuser";
		TranxManager tranxManager = new TranxManager(url, user, pwd);
		String messageID = "P4F575781538";
		String actionID = "RP";
		System.out.println(">>> retryMessage " + messageID + " for " + actionID);
		tranxManager.retryMessage(messageID, actionID, "wyou");
		System.out.println("<<< retryMessage");

	}

}
