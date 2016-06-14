package com.liaison.reprocess;

import com.wm.data.IData;

public class TranxManager {
	private final String url;
	private final String user;
	private final String pwd;
	private WebMethodsUtil wmUtilForPublic = null;

	public TranxManager(String url, String user, String pwd) throws Exception {
		this.user = user;
		this.url = url;
		this.pwd = pwd;
	}

	private void sendRetryMessageRequestToHub(String messageID, String actionType, String userID) throws Exception {
		IData output;
		String errorCode;

		output = getWMUtilForPublicInterface().invokeService("Retry", WebMethodsUtil.buildRetryInputRecord(messageID, actionType, userID));
		if (output == null) {
			throw new Exception("Failed to obtain output from Hub Retry service");
		}

		errorCode = WebMethodsUtil.getString(output, "errorCode");

		if (errorCode != null) {
			throw new Exception("Retry service failure: " + errorCode);
		}
		else {
			System.out.println("Retry service invoked for " + messageID + " of " + actionType);
		}
	}

	private WebMethodsUtil getWMUtilForPublicInterface() throws Exception {
		if (this.wmUtilForPublic == null) {
			this.wmUtilForPublic = new WebMethodsUtil(this.url, this.user, this.pwd, "FxTxnManager.Public");
		}
		return this.wmUtilForPublic;
	}

	public void retryMessage(String messageID, String actionID, String userID) throws Exception {
		if ((messageID == null) || (actionID == null)) {
			throw new NullPointerException("messageID and actionID cannot be null");
		}
		//		staticLog.debug(">>> retryMessage " + messageID + " for " + actionID);
		sendRetryMessageRequestToHub(messageID, actionID, userID);
		//		staticLog.debug("<<< retryMessage");
	}

}
