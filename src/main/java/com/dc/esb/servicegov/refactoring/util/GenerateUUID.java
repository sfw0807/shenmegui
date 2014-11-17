package com.dc.esb.servicegov.refactoring.util;

import java.util.UUID;

public class GenerateUUID {
	public static String genRandom(){
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

}
