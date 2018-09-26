package com.springboot.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.service.ActivitiService;

@Service
public class ActivitiServiceImpl implements ActivitiService {

	private final static Logger logger = LoggerFactory.getLogger(ActivitiServiceImpl.class);
	
	@Resource
    private RuntimeService runtimeService;
	@Resource
    private TaskService taskService;



	
	@Override
	public void startProcess() {
		Map<String,Object> map = new HashMap<String,Object>();
        map.put("apply","lisi");

		ExecutionEntity pi1 = (ExecutionEntity) runtimeService.startProcessInstanceByKey("myProcess");
		List<Task> tasks = taskService.createTaskQuery().taskAssignee("per1").list();
		logger.info("task size==={}",tasks.size());
		for(Task t : tasks){
			logger.info(t.getName());
		}
		String taskId = tasks.get(0).getId();
		logger.info("taskid==={}",taskId);
		taskService.complete(taskId);
	}

}
