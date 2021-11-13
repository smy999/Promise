package com.pjt3.promise.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PharmacyGetRes {
	
	int pharmId;
	String pharmName;
	String pharmTel;
	String pharmAddr;
	Double pharmLat;
	Double pharmLong;
	int distance;
}
