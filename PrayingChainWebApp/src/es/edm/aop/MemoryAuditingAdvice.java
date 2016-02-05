package es.edm.aop;

import java.text.DecimalFormat;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class MemoryAuditingAdvice{

	public Object auditMemory(ProceedingJoinPoint method) throws Throwable {

		//Now showing used Memory Before running the proxied method
		Runtime runtime = Runtime.getRuntime();
		double freeMemoryBefore = runtime.freeMemory();
		double totalMemoryBefore = runtime.totalMemory();
		double usedMemoryBefore = (totalMemoryBefore-freeMemoryBefore)/(1024*1024);
		String usedMemoryBeforeSt = new DecimalFormat("#.##").format(usedMemoryBefore) + "Mb";
		System.out.println("");
		System.out.println("Memory Used before calling " + method.getSignature().getName() + ": " + usedMemoryBeforeSt);

		try {

			Object returnValue = method.proceed();

			return returnValue;

		} finally {

			//Now showing used Memory After running the proxied method
			double freeMemoryAfter = runtime.freeMemory();
			double totalMemoryAfter = runtime.totalMemory();
			double usedMemoryAfter = (totalMemoryAfter-freeMemoryAfter)/(1024*1024);
			String usedMemoryAfterSt = new DecimalFormat("#.##").format(usedMemoryAfter) + "Mb";
			System.out.println("Memory Used After calling " + method.getSignature().getName() + ": " + usedMemoryAfterSt);
			
			//Showing totao used memory by the proxied method
			double usedMemory = (usedMemoryAfter-usedMemoryBefore);
			String usedMemorySt = new DecimalFormat("#.##").format(usedMemory) + "Mb";
			System.out.println("Total Memory Used by " + method.getSignature().getName() + ": " + usedMemorySt);

			//Now calling the Garbage Collector
			runtime.gc();
			
			//Now showing used Memory AfterGC running the Garbage collector
			double freeMemoryAfterGC = runtime.freeMemory();
			double totalMemoryAfterGC = runtime.totalMemory();
			double usedMemoryAfterGC = (totalMemoryAfterGC-freeMemoryAfterGC)/(1024*1024);
			String usedMemoryAfterGCSt = new DecimalFormat("#.##").format(usedMemoryAfterGC) + "Mb";
			System.out.println("Memory Used After calling Garbage Collector: " + usedMemoryAfterGCSt);
		}
	}
	
	public void writeMethod(JoinPoint method){
		System.out.println("Se va a llamar a un método..." + method.getSignature().getName());
		
	}

}
