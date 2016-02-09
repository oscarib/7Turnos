package es.edm.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path="/Webhook")
public class MailingServiceIntegrationController_MailchimpImpl {
	
	private static Pattern DATA_PATTERN = Pattern.compile("data\\[(\\w+)\\](\\[(\\w+)\\](\\[(\\d+)\\]\\[(\\w+)\\])?)?");
	private static int INDEX_PARAM_NAME = 1;
	private static int INDEX_MAPPED_PARAM_NAME = 3;
	private static int INDEX_MAPPED_ARRAY_INDEX = 5;
	private static int INDEX_MAPPED_ARRAY_PARAM_NAME = 6;
	
	Map<String, Object> data = new HashMap<String, Object>();
	  
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getProcess(WebRequest request){
		return new ModelAndView("/web/MailchimpAnswer.jsp");
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView postProcess(WebRequest request){
		data = parseRequest(request);
		System.out.println("Entró!");
		for (String parameterKey : data.keySet()){
			System.out.println("Imprimiendo parametro " + parameterKey);
		}
		return new ModelAndView("/web/MailchimpAnswer.jsp");
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> parseRequest(WebRequest request) {
	    Map<String, String[]> parameterMap = request.getParameterMap();
	    Map<String, Object> convertible = new HashMap<String, Object>();
	    for (String parameterKey : parameterMap.keySet()) {
	      Matcher matcher = DATA_PATTERN.matcher(parameterKey);
	      if (matcher.matches()) {
	        String key = matcher.group(INDEX_PARAM_NAME);
	        Object value = parameterMap.get(parameterKey)[0];
	        if (null == matcher.group(INDEX_MAPPED_PARAM_NAME)) {
	          // simple values
	          convertible.put(key, value);
	        } else if (null == matcher.group(INDEX_MAPPED_ARRAY_INDEX)) {
	          // mapped values
	          Map<String, Object> map = getMapParam(convertible, key);
	          map.put(matcher.group(INDEX_MAPPED_PARAM_NAME), value);
	        } else {
	          // merge values in an array
	          // If you like good coding don't read on!
	          Map<String, Object> map = getMapParam(convertible, key);
	          // now check for the array
	          String arrayKey = matcher.group(INDEX_MAPPED_PARAM_NAME);
	          int arrayIndex = Integer.valueOf(matcher.group(INDEX_MAPPED_ARRAY_INDEX));
	          if (null == map.get(arrayKey)) {
	            map.put(arrayKey, new Object[] {});
	          }
	          Object[] array = (Object[]) map.get(arrayKey);
	          while (array.length <= arrayIndex) {
	            Object[] newArray = new Object[array.length + 1];
	            System.arraycopy(array, 0, newArray, 0, array.length);
	            array = newArray;
	          }
	          if (null == array[arrayIndex]) {
	            array[arrayIndex] = new HashMap<String, Object>();
	          }
	          map.put(arrayKey, array);
	          // noinspection unchecked
	          ((Map<String, Object>) array[arrayIndex]).put(matcher.group(INDEX_MAPPED_ARRAY_PARAM_NAME), value);
	        }
	      }
	    }
	    return convertible;
	  }
	
	 @SuppressWarnings({ "unchecked" })
	  private Map<String, Object> getMapParam(Map<String, Object> convertible, String key) {
	    if (!convertible.containsKey(key)) {
	      convertible.put(key, new HashMap<String, Object>());
	    }
	    return (Map<String, Object>) convertible.get(key);
	  }
}
