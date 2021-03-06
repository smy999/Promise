package com.pjt3.promise.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pjt3.promise.entity.Pharmacy;
import com.pjt3.promise.entity.PharmacyDistanceInterface;
import com.pjt3.promise.entity.QPharmacy;

@Repository
public class PharmacyRepositorySupport {
	
	@Autowired
	PharmacyRepository pharmacyRepository;
	
	QPharmacy qPharmacy = QPharmacy.pharmacy;

	LocalDate nowDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
	
	LocalTime nowTime = LocalTime.now(ZoneId.of("Asia/Seoul"));
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
	
	public List<PharmacyDistanceInterface> getPharmacyList(double lat, double lon, int week, String curTime){
		
		List<PharmacyDistanceInterface> pharmacyList = new ArrayList<>();
		
		// 월
		if (week == 1) {
			pharmacyList = pharmacyRepository.findByLatLongMon(lat, lon, curTime);
		}
		
		// 화
		else if (week == 2) {
			pharmacyList = pharmacyRepository.findByLatLongTue(lat, lon, curTime);
		}
		
		// 수
		else if (week == 3) {
			pharmacyList = pharmacyRepository.findByLatLongWed(lat, lon, curTime);
		}
		
		// 목
		else if (week == 4) {
			pharmacyList = pharmacyRepository.findByLatLongThu(lat, lon, curTime);
		}
		
		// 금
		else if (week == 5) {
			pharmacyList = pharmacyRepository.findByLatLongFri(lat, lon, curTime);
		}
		
		// 토
		else if (week == 6) {
			pharmacyList = pharmacyRepository.findByLatLongSat(lat, lon, curTime);
		}
		
		// 일
		else {
			pharmacyList = pharmacyRepository.findByLatLongSun(lat, lon, curTime);
		}
		
		return pharmacyList;
	}
	
}
