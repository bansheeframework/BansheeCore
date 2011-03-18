package com.bansheeproject.features;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.bansheeproject.Response;
import com.bansheeproject.engine.BansheeDispatcher;
import com.bansheeproject.engine.InvocationContext;
import com.bansheeproject.exceptions.BansheeInvocationFaultException;
import com.bansheeproject.exceptions.InstallFeatureException;
import com.bansheeproject.exceptions.ServiceInvokeException;
import com.bansheeproject.features.security.CertificateCallback;
import com.bansheeproject.log.BansheeLogger;


/**
 * Installs timeouts over dispatchers.
 * 
 * @author Alexandre Saudate
 * @since 1.0
 */
public class ServiceTimeout implements Feature{
	
	
	private long requestTimeout;
	
	public ServiceTimeout(long value) {
		if (value <= 0) {
			throw new IllegalArgumentException("The timeout value cannot be less or equal than zero.");
		}
		this.requestTimeout = value;
	}
	

	public void install(InvocationContext context)
			throws InstallFeatureException {
		
		
		BansheeDispatcher dispatcher = context.getDispatcher();
		
		TimeoutDispatcher timeoutDispatcher = new TimeoutDispatcher(dispatcher, requestTimeout);
		
		
		context.setDispatcher(timeoutDispatcher);

		
	}
	
	
	
	
	private static class InvokeTask implements Callable<String> {

		private final BansheeDispatcher delegate;
		
		private final String param;
		
		public InvokeTask(BansheeDispatcher delegate, String param) {
			this.delegate = delegate;
			this.param = param;
		}
		
		public String call() throws Exception {
			return delegate.invoke(param);
		}
		
	}
	
	
	private static class InvokeOneWayTask implements Callable<Response> {

		private final BansheeDispatcher delegate;
		
		private final String param;
		
		public InvokeOneWayTask(BansheeDispatcher delegate, String param) {
			this.delegate = delegate;
			this.param = param;
		}
		
		public Response call() throws Exception {
			delegate.invokeOneWay(param);
			return null;
		}
		
	}
	
	private static class DaemonThreadFactory implements ThreadFactory {

		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			thread.setName(thread.getName() + " - Banshee");
			return thread;
		}
		
	}
	
	private static ExecutorService executorService = Executors.newCachedThreadPool(new DaemonThreadFactory());
	
	private static class TimeoutDispatcher extends BansheeDispatcher {
		
		private final BansheeDispatcher delegate;
		
		
		public TimeoutDispatcher(BansheeDispatcher delegate, long timeout) {
			this.delegate = delegate;
			this.timeout = timeout;
		}
		
		private long timeout = Long.MAX_VALUE;

		public void changeInvocationAddress(String address) {
			delegate.changeInvocationAddress(address);
		}

		public String invoke(final String param) throws ServiceInvokeException,
				BansheeInvocationFaultException {
			
			try {
				
				Future<String> task = executorService.submit(new InvokeTask(delegate, param));
				
				return task.get(timeout, TimeUnit.MILLISECONDS);
				
				
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (ExecutionException e) {
				if (e.getCause()  instanceof BansheeInvocationFaultException)
					throw (BansheeInvocationFaultException)e.getCause();
				else 
					throw new RuntimeException(e);
				
			} catch (TimeoutException e) {
 
				throw new ServiceInvokeException(e);
			}
			
			
		}

		
		
		
		public void invokeOneWay(final String param)
				throws ServiceInvokeException, BansheeInvocationFaultException {
			
			
			try {
				
				
				
				Future<Response> task = executorService.submit(new InvokeOneWayTask(delegate, param));
				task.get(timeout, TimeUnit.MILLISECONDS);				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new ServiceInvokeException(e);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
				
			} catch (TimeoutException e) {
 
				throw new ServiceInvokeException(e);
			}
	
			
			
			
			
		}

		public void setCertificateSelectors(String reference, InputStream keyStoreData,
				String keyStorePassword, InputStream trustStoreData,
				String trustStorePassword, CertificateCallback callback)
				throws GeneralSecurityException, IOException {
			delegate.setCertificateSelectors(reference, keyStoreData, keyStorePassword,
					trustStoreData, trustStorePassword, callback);
		}

		public void setCertificateSelectors(String reference, InputStream keyStoreData,
				String keyStorePassword, InputStream trustStoreData,
				String trustStorePassword) throws GeneralSecurityException,
				IOException {
			delegate.setCertificateSelectors(reference, keyStoreData, keyStorePassword,
					trustStoreData, trustStorePassword);
		}

		public void setLogger(BansheeLogger logger) {
			delegate.setLogger(logger);
		}

		public void setTimeout(long timeout) {
			this.timeout = timeout;
		}
	}
	
	
	
}
