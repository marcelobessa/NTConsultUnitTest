package org.camunda.bpm.getstarted.loanapproval;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.init;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.execute;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.job;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;

import java.util.HashMap;
import java.util.Map;

//import org.camunda.bpm.getstarted.loanapproval.InMemProcessEngineConfiguration;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import io.flowcov.camunda.junit.FlowCovProcessEngineRuleBuilder;


//@RunWith(SpringJUnit4ClassRunner.class)
//@Deployment(resources = { "process_test.bpmn" })
//@ContextConfiguration(classes = {InMemProcessEngineConfiguration.class})
public class ProcessTest {
	
//	@Autowired
//    private ProcessEngine processEngine;
//
//    @Rule
//    @ClassRule
//    public static ProcessEngineRule rule;
//
//    @PostConstruct
//    public void init() {
//        if (rule == null) {
//            rule = FlowCovProcessEngineRuleBuilder
//                .create(processEngine)
//                .build();
//        }
//    }
//    
//    @Test
//	public void contextLoads() {
//	}
//	
//	private final TaskService taskService = mock(TaskService.class);
//	private final Task task = mock(Task.class);
//
//	@Rule
//	public final ExpectedException thrown = ExpectedException.none();
//
//	@Test
//	  public void mock_taskQuery() {
//	    // bind query-mock to service-mock and set result to task.
//	    final TaskQuery taskQuery = null; //QueryMocks1.mockTaskQuery(taskService).singleResult(task);
//
//	    final Task result = taskService.createTaskQuery().active().activityInstanceIdIn("foo").excludeSubtasks().singleResult();
//
//	    assertThat(result).isEqualTo(task);
//
//	    verify(taskQuery).active();
//	    verify(taskQuery).activityInstanceIdIn("foo");
//	    verify(taskQuery).excludeSubtasks();
//	  }
	
	
	@Rule
	  @ClassRule
	  public static ProcessEngineRule rule = FlowCovProcessEngineRuleBuilder.create().build();

	  @Before
	  public void setup() {
	    init(rule.getProcessEngine());
	  }

	  @Test
	  @Deployment(resources="cheescake-process.bpmn")
	  public void testHappyPath() {

	   Map<String, Object> variables = new HashMap<String, Object>();
	    variables.put("endOfVisit", "2020-11-11T12:13:14Z");

	    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("myNYChesscakeProcess");
	    assertThat(processInstance).isWaitingAt("NYTripEndedEvent");
	    execute(job());
	    assertThat(processInstance).isEnded().hasPassed("CheesecakeTestingEndedEndEvent1");

	  }
	

}
