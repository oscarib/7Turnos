package es.edm.aop;

import java.text.DecimalFormat;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

@SuppressWarnings("unused")
public class MemoryAuditingAdvice{

	public Object auditMemory(ProceedingJoinPoint method) throws Throwable {

		//Now showing used Memory Before running the proxied method
		Runtime runtime = Runtime.getRuntime();
		/* We don't care right now
		double freeMemoryBefore = runtime.freeMemory();
		double totalMemoryBefore = runtime.totalMemory();
		double usedMemoryBefore = (totalMemoryBefore-freeMemoryBefore)/(1024*1024);
		String usedMemoryBeforeSt = new DecimalFormat("#.##").format(usedMemoryBefore) + "Mb";
		System.out.println("");
		System.out.println("Memory Used before calling " + method.getSignature().getName() + ": " + usedMemoryBeforeSt);
		//*/
		try {

			Object returnValue = method.proceed();

			return returnValue;

		} finally {

			/* We don't care right now
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
			//*/

			//Now calling the Garbage Collector
			runtime.gc();

			/* We don't care right now
			//Now showing used Memory AfterGC running the Garbage collector
			double freeMemoryAfterGC = runtime.freeMemory();
			double totalMemoryAfterGC = runtime.totalMemory();
			double usedMemoryAfterGC = (totalMemoryAfterGC-freeMemoryAfterGC)/(1024*1024);
			String usedMemoryAfterGCSt = new DecimalFormat("#.##").format(usedMemoryAfterGC) + "Mb";
			System.out.println("Memory Used After calling Garbage Collector: " + usedMemoryAfterGCSt);
			//*/
		}
	}
	
	public void writeMethod(JoinPoint method){
		System.out.println("Se va a llamar a un método..." + method.getSignature().getName());
		
	}

}
