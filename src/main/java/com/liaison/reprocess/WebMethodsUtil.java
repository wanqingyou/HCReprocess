package com.liaison.reprocess;

import com.wm.app.b2b.client.Context;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataFactory;
import com.wm.data.IDataUtil;

public class WebMethodsUtil {
	private final String server;
	private final String user;
	private final String password;
	private final String packageName;

	public WebMethodsUtil(String server, String user, String password, String packageName) {
		if (server == null) {
			throw new NullPointerException("server is null");
		}
		this.server = server;
		this.user = user;
		this.password = password;
		this.packageName = packageName;
	}

	/**
	 * Connects to the specified B2B Integration Server using the specified username and password,
	 * and returns the Context object for the established connection.
	 * @throws ConnectionException if connection cannot be established
	 */
	private static Context getConnectedContext(String server, String username, String password) throws Exception {
		Context context = new Context();
		try {
			context.connect(server, username, password);
			return context;
		}
		catch (Exception ex) {
			throw new Exception("Failed to connect to Hub: " + ex.getMessage(), ex);
		}
	}

	public IData invokeService(String serviceName, IData inputRecord) throws Exception {
		try {
			Context context = getConnectedContext(this.server, this.user, this.password);
			IData output = context.invoke(packageName, serviceName, inputRecord);
			context.disconnect();
			context = null;
			return output;
		}
		catch (Exception ex) {
			throw new Exception("Failed to invoke service 11: " + serviceName + " (ServiceException) - " + ex.getMessage(), ex);
		}
	}

	public IData invokeService(String packageName, String serviceName, IData inputRecord) throws Exception {
		try {
			Context context = getConnectedContext(this.server, this.user, this.password);
			IData output = context.invoke(packageName, serviceName, inputRecord);
			context.disconnect();
			context = null;
			return output;
		}
		catch (Exception ex) {
			throw new Exception("Failed to invoke service 22: " + serviceName + " (ServiceException) - " + ex.getMessage(), ex);
		}
	}

	public static String getString(IData pipeline, String param) {
		return IDataUtil.getString(pipeline.getCursor(), param);
	}

	public static IData buildRetryInputRecord(String msgID, String actionType, String userID) {
		IData inputRecord = IDataFactory.create();
		IDataCursor idc = inputRecord.getCursor();
		idc.insertAfter("actionType", actionType);
		idc.insertAfter("msgID", msgID);
		idc.insertAfter("userID", userID);
		idc.destroy();
		return inputRecord;
	}
}
