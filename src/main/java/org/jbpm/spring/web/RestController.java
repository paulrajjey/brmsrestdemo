package org.jbpm.spring.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.QueryParam;


import org.joda.time.DateTime;
import org.json.simple.JSONObject;
import org.kie.api.runtime.manager.audit.VariableInstanceLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import redhat.checklist.Answer;
import redhat.checklist.QandAContext;

@Controller
public class RestController {
	
	
	
	@RequestMapping(value = "/initQuestionare", method = RequestMethod.POST,produces={"application/json"} )
	public @ResponseBody QandAContext initQuestionare(
			){
		
		QuestionaryRuleEngineServiceClient questionaryClient = new QuestionaryRuleEngineServiceClient();
		
		QandAContext qacontect = questionaryClient.initiateQuestionare();
		return qacontect;
	}
	
	@RequestMapping(value = "/nextQuestion", method = RequestMethod.POST,produces={"application/json"} )
	public @ResponseBody QandAContext nextQuestion(@RequestParam("id") String questionareID,@RequestParam("qaContext.nextQuestion.id") String qid,
			@RequestParam("qaContext.nextQuestion.question") String question,
			@RequestParam("qaContext.nextQuestion.answer") String answer
			){
		System.out.println("nextQuestion");
		System.out.println("questionareID" + questionareID);
		System.out.println("qid" + qid) ;
		System.out.println("question" + question);
		System.out.println("answer" + answer);
		
		Answer ansr = new Answer();
		ansr.setAnswer(answer);
		ansr.setQuestionId(qid);
		ansr.setQuestion(question);
		ansr.setQuestionareID(questionareID);
		QuestionaryRuleEngineServiceClient questionaryClient = new QuestionaryRuleEngineServiceClient();
		
		QandAContext qacontect = questionaryClient.getNextQuestion(questionareID,ansr);
		String nextQid  = qacontect.getNextQuestion().getId();
		BPMQACheckListRestClientService bpmClient = null;
		if("Done".equals(nextQid)){
			
			 System.out.println("QA completed for he quetionare " +  qacontect.getId());
			 Map<String, Object> params = new HashMap<String, Object>() ;
			 params.put("checkListContext", qacontect);
			 params.put("isCheckListReviewed", "N");
			 
			try {
				bpmClient = new BPMQACheckListRestClientService();
				long processid = bpmClient.startProcess(params);
				System.out.println("Checklist process has been started" +  processid);
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
		
		return qacontect;
	}
}
