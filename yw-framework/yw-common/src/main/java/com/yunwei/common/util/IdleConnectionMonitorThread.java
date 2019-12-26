package com.yunwei.common.util;

import java.util.concurrent.TimeUnit;

import org.apache.http.conn.HttpClientConnectionManager;


/**
 * 功能说明: httpclient连接回收器<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author kongdy<br>
 * 开发时间: 2016年9月20日<br>
 */
public class IdleConnectionMonitorThread extends Thread {

	private final HttpClientConnectionManager connMgr;
	private volatile boolean shutdown;

	public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
		super();
		this.connMgr = connMgr;
	}

	@Override
	public void run() {
		try {
			while (!shutdown) {
				synchronized (this) {
					wait(5000);
					// 关闭失效的连接
					connMgr.closeExpiredConnections();
					// 可选的, 关闭30秒内不活动的连接
					connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
					shutdown = true;
				}
			}
		} catch (InterruptedException ex) {
			// terminate
		}
	}

	public void shutdown() {
		shutdown = true;
		synchronized (this) {
			notifyAll();
		}
	}

}
