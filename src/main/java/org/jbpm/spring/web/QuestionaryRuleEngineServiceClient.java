package org.jbpm.spring.web;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.drools.core.command.runtime.rule.QueryCommand;
import org.drools.core.runtime.impl.ExecutionResultImpl;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.internal.runtime.helper.BatchExecutionHelper;
import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallerFactory;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.ServiceResponse.ResponseType;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;

import redhat.checklist.Answer;
import redhat.checklist.QandAContext;
import redhat.checklist.Question;
import redhat.checklist.Questionare;


//import citi.PRSruleengine.FieldRequest;

public class QuestionaryRuleEngineServiceClient {

	
	public ExecutionResultImpl execute( KieServicesClient  kieServicesClient,Command<?> batchCommand,Marshaller marshaller ){
		
	
     //String containerId = "questionare";
		String containerId = "checklist";
	 RuleServicesClient ruleClient = kieServicesClient.getServicesClient(RuleServicesClient.class);
     ServiceResponse<String> executeResponse =
     		ruleClient.executeCommands(containerId, batchCommand);
     ExecutionResultImpl results = null;
     if(executeResponse.getType() == ResponseType.SUCCESS) {
     		System.out.println("Commands executed with success! Response:");
     
     		System.out.println(executeResponse.getResult());
     	
     		 String result = executeResponse.getResult();
         

     		 results = marshaller.unmarshall(result, ExecutionResultImpl.class);
     } else{
	        	System.out.println("Error executing rules. Message: ");
	        	System.out.println(executeResponse.getMsg());
	        }
         return results;
     }
	public  QandAContext initiateQuestionare(){
		
		   /*String serverUrl = "http://localhost:8080/kie-server/services/rest/server";
	       String user = "bpmsAdmin";
	       String password = "jboss123$";
	       String containerId = "questionare";*/
		   String serverUrl = "http://kie-app-brmsdemo1.jeyocp.sc.osecloud.com/kie-server/services/rest/server";
	       String user = "kieserver";
	       String password = "kieserver1!";
	       String containerId = "checklist";
		 
	       
	       Set<Class<?>> classes = new HashSet<Class<?>>();
	       Marshaller marshaller = MarshallerFactory.getMarshaller(classes,  MarshallingFormat.XSTREAM, QuestionaryRuleEngineServiceClient.class.getClassLoader());
	       
	       KieServicesClient  kieServicesClient = QuestionaryRuleEngineServiceClient.configure(serverUrl, user, password);
	      
	      	Command<?> batchCommand =  initialQuestion();
	      
	      	String xStreamXml = BatchExecutionHelper.newXStreamMarshaller().toXML(batchCommand); // actual XML request
	        System.out.println("\t######### Rules request"  + xStreamXml);
	        
	        ExecutionResultImpl results = execute(kieServicesClient,batchCommand,marshaller);
           
	        Questionare questionare = (Questionare)results.getValue("questionare");
            
            Command<?> batchCommand1 =  getQuestionareContxt(questionare.getId());
            
            String xStreamXml1 = BatchExecutionHelper.newXStreamMarshaller().toXML(batchCommand1); // actual XML request
	        System.out.println("\t######### Rules request1"  + xStreamXml1);
	        
	        ExecutionResultImpl results1 = execute(kieServicesClient,batchCommand1,marshaller);
            
           QueryResults qresults = (QueryResults)results1.getValue("questionareQueryResult");
 	       QandAContext qaContext = null;
 	       for ( QueryResultsRow row : qresults ) {
 	    	  qaContext = ( QandAContext ) row.get( "queryContext" ); //you can retrieve all the bounded variables here
 	    	   
 	       }
 	       if (qaContext !=null){
 	    	   Question qa = qaContext.getNextQuestion();
 	    	   System.out.println("Question for id" + questionare.getId() + "--" +qa.getQuestion());
 	    	 
 	       }
 	       return qaContext;
	         
	}
	
	public  QandAContext getNextQuestion(String questionarId , Answer answer){
		
		   /*String serverUrl = "http://localhost:8080/kie-server/services/rest/server";
	       String user = "bpmsAdmin";
	       String password = "jboss123$";
	       String containerId = "questionare";*/
		
		  String serverUrl = "http://kie-app-brmsdemo1.jeyocp.sc.osecloud.com/kie-server/services/rest/server";
	       String user = "kieserver";
	       String password = "kieserver1!";
	       String containerId = "checklist";
	       
	       Set<Class<?>> classes = new HashSet<Class<?>>();
	       Marshaller marshaller = MarshallerFactory.getMarshaller(classes,  MarshallingFormat.XSTREAM, QuestionaryRuleEngineServiceClient.class.getClassLoader());
	       
	       KieServicesClient  kieServicesClient = QuestionaryRuleEngineServiceClient.configure(serverUrl, user, password);
	       KieCommands commandsFactory = KieServices.Factory.get().getCommands();
	       
	       	Command<?> insert = commandsFactory.newInsert(answer, "answer");
	      	
	     	 QueryCommand queryCommand = new QueryCommand("questionareQueryResult","questionareQuery",new String[]{questionarId});
	     	 Command<?> fireAllRules = commandsFactory.newFireAllRules();
	     	 
	     	 Command<?> batchCommand = commandsFactory.newBatchExecution(Arrays.asList(insert,fireAllRules,queryCommand));
	      
	      
	      	String xStreamXml = BatchExecutionHelper.newXStreamMarshaller().toXML(batchCommand); // actual XML request
	        System.out.println("\t######### Rules request"  + xStreamXml);
	        
	        ExecutionResultImpl results = execute(kieServicesClient,batchCommand,marshaller);
                  
	        QueryResults qresults = (QueryResults)results.getValue("questionareQueryResult");
	        QandAContext qaContext = null;
	        for ( QueryResultsRow row : qresults ) {
	        	qaContext = ( QandAContext ) row.get( "queryContext" ); //you can retrieve all the bounded variables here
	    	   
	    	}
	        
	      /*  List<Answer> ansr =  qaContext.getAnswers();
	        
	        for (Iterator iterator = ansr.iterator(); iterator.hasNext();) {
				Answer answer2 = (Answer) iterator.next();
				
				System.out.println("answer-qid"  + answer2.getQuestionId() );
				 System.out.println("answer"  + answer2.getAnswer() );
			}*/
	       
	       return qaContext;
	         
	}
	

    public static void executeRule(String[] args) throws Exception {
       long start = System.currentTimeMillis();
       String serverUrl = "http://localhost:8080/kie-server/services/rest/server";
       String user = "bpmsAdmin";
       String password = "jboss123$";
       String containerId = "questionare";

        Set<Class<?>> classes = new HashSet<Class<?>>();
        Marshaller marshaller = MarshallerFactory.getMarshaller(classes,  MarshallingFormat.XSTREAM, QuestionaryRuleEngineServiceClient.class.getClassLoader());
       
        KieServicesClient  kieServicesClient = QuestionaryRuleEngineServiceClient.configure(serverUrl, user, password);
        
      
        //Command<?> batchCommand =  initialQuestion();
        //f93b1365-474c-46e3-bdb3-ea76ec0511ef
       // Command<?> batchCommand =  getAllQuestionare();
        //Command<?> batchCommand =  getAllQuestionareContext();
        Command<?> batchCommand =  getAllQuestionare();
        
        
       
       // BatchExecutionCommand command = new BatchExecutionCommandImpl(batchCommand);
        String xStreamXml = BatchExecutionHelper.newXStreamMarshaller().toXML(batchCommand); // actual XML request
        System.out.println("\t######### Rules request"  + xStreamXml);
        
       RuleServicesClient ruleClient = kieServicesClient.getServicesClient(RuleServicesClient.class);
        ServiceResponse<String> executeResponse =
        		ruleClient.executeCommands(containerId, batchCommand);
        
        if(executeResponse.getType() == ResponseType.SUCCESS) {
        	System.out.println("Commands executed with success! Response:");
        
        	//System.out.println(executeResponse.getResult());
        	
        	String resut = executeResponse.getResult();
            

            ExecutionResultImpl results = marshaller.unmarshall(resut, ExecutionResultImpl.class);
           
          
            System.out.println("\t######### Rules executed" + executeResponse.toString());
            
          
            
            
            System.out.println("Execution completed in " + (System.currentTimeMillis() - start));
            
        	
        }else{
        	System.out.println("Error executing rules. Message: ");
        	System.out.println(executeResponse.getMsg());
        }
        
      
        

    }
    
    public static Command<?> getAllQuestionareContext(){
       	
        
      	 KieCommands commandsFactory = KieServices.Factory.get().getCommands();
      
      	 QueryCommand queryCommand = new QueryCommand("allQuestionareContextQueryResult","allQuestionareContextQuery");
      	 Command<?> fireAllRules = commandsFactory.newFireAllRules();
      	 
      	 Command<?> batchCommand = commandsFactory.newBatchExecution(Arrays.asList(queryCommand));
      	 return batchCommand;
      }
    
    public static Command<?> getAllQuestionare(){
       	
        
     	 KieCommands commandsFactory = KieServices.Factory.get().getCommands();
     
     	 QueryCommand queryCommand = new QueryCommand("allQuestionareResult","allQuestionareQuery");
     	 
     	 Command<?> batchCommand = commandsFactory.newBatchExecution(Arrays.asList(queryCommand));
     	 return batchCommand;
     }
    
    public static Command<?> getQuestionareContxt(String id){
       	
        
     	 KieCommands commandsFactory = KieServices.Factory.get().getCommands();
     
     	QueryCommand queryCommand = new QueryCommand("questionareQueryResult","questionareQuery",new String[]{id});
     	 Command<?> fireAllRules = commandsFactory.newFireAllRules();
     	 
     	 Command<?> batchCommand = commandsFactory.newBatchExecution(Arrays.asList(queryCommand));
     	 return batchCommand;
     } 
    
    public static Command<?> nextQuestion(String questionarId,String qid,String answerValue){
   	
     Questionare questionare = new Questionare();
     
     questionare.setId(questionarId);
     
     Answer answer = new Answer();
     answer.setQuestionId(qid);
     answer.setQuestionareID(questionarId);
     answer.setAnswer(answerValue);
     
   	 KieCommands commandsFactory = KieServices.Factory.get().getCommands();
   
   	 Command<?> insert = commandsFactory.newInsert(answer, "answer");
   	
   	 QueryCommand queryCommand = new QueryCommand("questionareQueryResult","questionareQuery",new String[]{questionarId});
   	 Command<?> fireAllRules = commandsFactory.newFireAllRules();
   	 
   	 Command<?> batchCommand = commandsFactory.newBatchExecution(Arrays.asList(insert,fireAllRules,queryCommand));
   	return batchCommand;
   } 
    
    public static Command<?> initialQuestion(){
    	 Questionare questionare = new Questionare();
    	 KieCommands commandsFactory = KieServices.Factory.get().getCommands();
     	
    	 Command<?> insert = commandsFactory.newInsert(questionare, "questionare");
    	
    	 Command<?> fireAllRules = commandsFactory.newFireAllRules();
         
   
    	 Command<?> batchCommand = commandsFactory.newBatchExecution(Arrays.asList(insert,fireAllRules));
    	return batchCommand;
    }
    
public static KieServicesClient configure(String url, String username, String password) {
		
		//default marshalling format is JAXB
		KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(url, username, password);
		
	
		
		//alternatives
		
		config.setMarshallingFormat(MarshallingFormat.XSTREAM);
		//config.setMarshallingFormat(MarshallingFormat.JSON);

	
		Set<Class<?>> allClasses = new HashSet<Class<?>>();
		//allClasses.add(com.highmark.domain.RiskLevel.class);
		/*allClasses.add(org.drools.core.command.runtime.rule.FireAllRulesCommand.class);
		allClasses.add(org.drools.core.command.runtime.rule.InsertObjectCommand.class);
		allClasses.add(org.drools.core.common.DefaultFactHandle.class);
		allClasses.add(org.drools.core.command.runtime.rule.GetObjectCommand.class);*/
		//config.addJaxbClasses(allClasses);
		return KieServicesFactory.newKieServicesClient(config);
		//
	}

}