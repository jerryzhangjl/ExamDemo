package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.TaskInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
*类名和方法不能修改
 */
public class Schedule {
	
	//注册服务列表 nodeId ,taskid列表
	Map<Integer, List<Integer>> register = new HashMap<Integer, List<Integer>>();
	//任务队列 taskid
	List<Integer> queue = new LinkedList<Integer>();
	// taskid和资源消耗率
	Map<Integer, Integer> csInfo = new HashMap<Integer, Integer>();

    public int init() {
    	register = new HashMap<Integer, List<Integer>>();
    	queue = new LinkedList<Integer>();
    	csInfo = new HashMap<Integer, Integer>();
        return ReturnCodeKeys.E001;
    }


    public int registerNode(int nodeId) {
    	if(nodeId<=0)
    	{
    		return ReturnCodeKeys.E004;
    	}
    	if(register.containsKey(nodeId))
    	{
    		return ReturnCodeKeys.E005;
    	}
    	List<Integer> list = new ArrayList<Integer>();
    	register.put(nodeId, list);
    	return ReturnCodeKeys.E003;
    }

    public int unregisterNode(int nodeId) {
    	if(nodeId<=0)
    	{
    		return ReturnCodeKeys.E004;
    	}
    	if(!register.containsKey(nodeId))
    	{
    		return ReturnCodeKeys.E007;
    	}
    	List<Integer> list = register.get(nodeId);
    	if(null != list && list.size()>0)
    	{
    		for(int i=0;i<list.size();i++)
    		{
    			queue.add(list.get(i));
    		}
    	}
    	return ReturnCodeKeys.E006;
    }


    public int addTask(int taskId, int consumption) {
    	if(taskId<=0)
    	{
    		return ReturnCodeKeys.E009;
    	}
    	if(queue.contains(taskId))
    	{
    		return ReturnCodeKeys.E010;
    	}
    	queue.add(taskId);
    	csInfo.put(taskId, consumption);
        return ReturnCodeKeys.E008;
    }


    public int deleteTask(int taskId) {
    	if(taskId<=0)
    	{
    		return ReturnCodeKeys.E009;
    	}
    	if(!queue.contains(taskId))
    	{
    		return ReturnCodeKeys.E012;
    	}
    	queue.remove(Integer.valueOf(taskId));
    	if(null !=register && register.size()>0)
    	{
    		 for (Map.Entry<Integer, List<Integer>> entry : register.entrySet()) {  
    			   List<Integer> values = entry.getValue();  
    			   for(Integer tInfo : values)
    			   {
    				   if(tInfo == taskId)
    				   {
    					   values.remove(tInfo);
    				   }
    			   }
    			  }  
    	}
        return ReturnCodeKeys.E011;
    }


    public int scheduleTask(int threshold) {
    	if(threshold<=0)
    	{
    		return ReturnCodeKeys.E002;
    	}
    	// 计算注册服务器数量
    	int nodesize = register.size();
    	// 计算任务数量
    	int quesize = queue.size();
    	List<Integer> list = new ArrayList<Integer>();
    	if(quesize > 0)
    	{
    		for(int note : queue)
    		{	
    			//占用率
    			int rate = csInfo.get(note);
    			list.add(rate);
    		}
    	}
    	
    	
        return ReturnCodeKeys.E000;
    }


    public int queryTaskStatus(List<TaskInfo> tasks) {
        // TODO 方法未实现
        return ReturnCodeKeys.E000;
    }

}
