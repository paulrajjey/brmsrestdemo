package org.jbpm.spring.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.audit.AuditService;
import org.kie.api.runtime.manager.audit.ProcessInstanceLog;
import org.kie.api.runtime.manager.audit.VariableInstanceLog;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.remote.client.api.RemoteRuntimeEngineFactory;
import org.kie.remote.jaxb.gen.JaxbStringObjectPairArray;
import org.kie.remote.jaxb.gen.util.JaxbStringObjectPair;



/**
 * @author Jey
 *
 */
public class BPMRestClientService {

	
	// jBPM Process and Project constants
	private final String P_EMPLOYEE = "employeeName";
	private final String P_RESULT = "result";
	private final String T_APPROVAL_VAR = "_approval";
	private final String LANG = "en-UK";
	
	//private final String DEPLOYMENT_ID = System.getProperties().getProperty("deploymentId","");;
	private final String USERNAME = "bpmsAdmin";
	private final String PASSWORD = "jboss123$";
	private final String PROCESS_ID = "creditdispute.creditDisputeProcess";
	private final String SERVER_URL = "http://localhost:8080/business-central";
	
	// jBPM classes
	private RuntimeEngine engine;
	private KieSession ksession;
	private TaskService taskService;
	private AuditService auditService;
	
	private KieServices ks;
	private KieContainer kContainer;
	
	private static KieServices promoKieService;
	private static KieContainer promoKieContainer;
	
	static {
	 
	 promoKieService =  KieServices.Factory.get();
	 ReleaseId rId1 = promoKieService.newReleaseId("redhat", "promo", "LATEST");
	 promoKieContainer = promoKieService.newKieContainer(rId1);
	 KieScanner scanner =  promoKieService.newKieScanner(promoKieContainer);
	 scanner.start(5000L);
 }

	public BPMRestClientService() throws MalformedURLException {
		String deployment = System.getProperties().getProperty("deploymentId","redhat:creditdispute:1.5");
		System.out.println("deployment" + deployment);
		engine = RemoteRuntimeEngineFactory.newRestBuilder()
				.addDeploymentId(deployment).addUserName(USERNAME)
				.addPassword(PASSWORD).addUrl(new URL(SERVER_URL)).build();
		taskService = engine.getTaskService();
		ksession = engine.getKieSession();
		auditService = engine.getAuditService();
		
		ks = KieServices.Factory.get();
		 ReleaseId rId = ks.newReleaseId("redhat", "creditdispute", "1.6");
		
    	//ReleaseId rId = ks.newReleaseId("test", "Test", "1.4") ;
    	 kContainer = ks.newKieContainer(rId);
    	 
	}

	/*public List<RewardTask> getTasks() {
		// retrieve all tasks since the USERNAME should be in groups PM and HR
		List<RewardTask> tasks = taskService
				.getTasksAssignedAsPotentialOwner(USERNAME, LANG)
				.stream()
				.map(t -> {
					RewardTask rt = new RewardTask();
					rt.setCreated(t.getCreatedOn());
					rt.setName(t.getName());
					rt.setEmployeeName(getEmployeeName(t.getProcessInstanceId()));
					rt.setTaskId(t.getId());
					return rt;
				}).collect(Collectors.toList());
		return tasks;
	}*/

	public void doTask(long taskId, boolean approve) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(T_APPROVAL_VAR, approve);
		taskService.claim(taskId, USERNAME);
		taskService.start(taskId, USERNAME);
		taskService.complete(taskId, USERNAME, params);
	}

	public ProcessInstance instanceDetail(long id) {
		ProcessInstance instance = ksession.getProcessInstance(id);
		
		return instance;
	}
	public void sendSignal(String name, String s,long in) {
		ksession.signalEvent(name, s, in);
	}
	public long startProcess(Map<String, Object> params) {
		
		
		ProcessInstance instance = ksession.startProcess(PROCESS_ID, params);
		System.out.println("process started with instance id " + instance.getId());
		System.out.println("process started with status " + instance.getState());
		return instance.getId();

	}
	
public long startProcess(String procesid , Map<String, Object> params) {
		
		
		ProcessInstance instance = ksession.startProcess(procesid, params);
		System.out.println("process started with instance id " + instance.getId());
		System.out.println("process started with status " + instance.getState());
		return instance.getId();

	}

	
	
public String updateProcess(long id , Map<String, Object> params) {
		
	
		//ProcessInstance instance = ksession.getProcessInstance(id);
		JaxbStringObjectPairArray varJaxbList = new JaxbStringObjectPairArray();
		
		for ( Map.Entry <String , Object> entry : params.entrySet()){
			String key = entry.getKey();
			Object value = entry.getValue();
			JaxbStringObjectPair varJaxb = new JaxbStringObjectPair(key, value);
			
			varJaxbList.getItems().add(varJaxb);
		}
		
		org.kie.remote.jaxb.gen.SetProcessInstanceVariablesCommand cmd = new org.kie.remote.jaxb.gen.SetProcessInstanceVariablesCommand();
		cmd.setProcessInstanceId(id);
		
		cmd.setVariables(varJaxbList);
		ksession.execute(cmd);
		//ProcessInstance instance = ksession.startProcess(PROCESS_ID, params);
		System.out.println("process variable updated ");
		
		return "success";

	}
	
	

	public String getEmployeeName(long piid) {
		return getVariableValue(piid, P_EMPLOYEE);
	}

	public void clearHistory() {
		auditService.clear();
	}

	

	private String getVariableValue(long piid, String varName) {
		String value = null;
		List<? extends VariableInstanceLog> variables = auditService
				.findVariableInstances(piid, varName);
		
		if (variables.size() > 0)
			value = variables.get(0).getValue();
		return value;
	}
	
	public List<? extends VariableInstanceLog> getVariables(long piid) {
		List<? extends VariableInstanceLog> variables = auditService
				.findVariableInstances(piid);
		
		
		//if (variables.size() > 0)
			//value = variables.get(0).getValue();
		return variables;
	}
	
	
	private String getProcessSummary(ProcessInstanceLog pi) {
		Long piid = pi.getProcessInstanceId();
		String employee = getVariableValue(piid.longValue(), P_EMPLOYEE);
		String result = getVariableValue(piid.longValue(), P_RESULT);
		String status = "";
		switch (pi.getStatus().intValue()) {
		case ProcessInstance.STATE_ABORTED:
			status = "aborted";
			break;
		case ProcessInstance.STATE_ACTIVE:
			status = "active";
			break;
		case ProcessInstance.STATE_COMPLETED:
			status = "completed";
			break;
		case ProcessInstance.STATE_PENDING:
			status = "pending";
			break;
		case ProcessInstance.STATE_SUSPENDED:
			status = "suspended";
			break;
		default:
			status = "unknown";
			break;
		}
		//if (Objects.isNull(result))
			//result = "reward still waiting for approval";
		String summary = "Reward process for employee '%s' is %s and result is '%s'.";
		return "";//String.format(summary, employee, status, result);
	}

	public static void   main(String [] ar) throws MalformedURLException{
	
		BPMRestClientService service = new BPMRestClientService();
		/*List<? extends VariableInstanceLog> variables  = service.getVariables(2);
		for (Iterator iterator = variables.iterator(); iterator.hasNext();) {
			VariableInstanceLog variableInstanceLog = (VariableInstanceLog) iterator.next();
			String key = variableInstanceLog.getVariableId();
			String value = variableInstanceLog.getValue();
			System.out.println("in all cases " + key);
			System.out.println("in all cases " + value);
			
			
			
		}*/
		Map<String,Object> params = new HashMap<String, Object>();
		Random random = new Random(1000);
		System.out.println("creatting new case");
		String caseId = String.valueOf(random.nextInt());
		params.put("id", caseId);
		
		try{
			
		} catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.println("process data " + params.toString());
		
		try {
			service = new BPMRestClientService();
			long id = service.startProcess(params);
			//service.sendSignal("receiptReceived", "Y",39);
			//TwilioSMS sms = new TwilioSMS();
			//String message = "Dispute case id ( " + id +  " ) has been created for you request. Dispute amount will be credited in your account when the case is closed";
			//sms.sendSMS(null, message);
		} catch (MalformedURLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} catch (Exception exc){
			exc.printStackTrace();
		}
		//service.getTaskInfo(1);
	}

}