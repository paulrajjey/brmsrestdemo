package org.jbpm.spring.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.audit.AuditService;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.remote.client.api.RemoteRuntimeEngineFactory;



/**
 * @author Jey
 *
 */
public class BPMQACheckListRestClientService {

	
	
	
	//private final String DEPLOYMENT_ID = System.getProperties().getProperty("deploymentId","");;
	private final String USERNAME = "bpmsAdmin";
	private final String PASSWORD = "jboss123$";
	private final String PROCESS_ID = "checklist.CheckListProcess";
	private final String SERVER_URL = "http://localhost:8080/business-central";
	
	// jBPM classes
	private RuntimeEngine engine;
	private KieSession ksession;
	private TaskService taskService;
	private AuditService auditService;
	
	private KieServices ks;
	private KieContainer kContainer;
	
	

	public BPMQACheckListRestClientService() throws MalformedURLException {
		//String deployment = System.getProperties().getProperty("deploymentId","redhat:checklist:1.9");
		String deployment = "redhat:checklist:1.9";
		System.out.println("deployment" + deployment);
		engine = RemoteRuntimeEngineFactory.newRestBuilder()
				.addDeploymentId(deployment).addUserName(USERNAME)
				.addPassword(PASSWORD).addUrl(new URL(SERVER_URL)).build();
		taskService = engine.getTaskService();
		ksession = engine.getKieSession();
		auditService = engine.getAuditService();
		
		
	}

	

	public void doTask(long taskId, boolean approve) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("", approve);
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


}