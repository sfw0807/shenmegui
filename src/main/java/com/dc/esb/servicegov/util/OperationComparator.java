package com.dc.esb.servicegov.util;

import java.util.Comparator;

import com.dc.esb.servicegov.entity.Operation;

public class OperationComparator implements Comparator<Operation>{
	public int compare(Operation operation1, Operation operation2) {
		int flag = operation1.getOperationId().compareTo(operation2.getOperationId());
		if(flag==0){
			return operation1.getOperationName().compareTo(operation2.getOperationName());
		}else{
			return flag;
		}
	}
}
