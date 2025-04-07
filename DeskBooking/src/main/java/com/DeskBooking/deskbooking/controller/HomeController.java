package com.DeskBooking.deskbooking.controller;

import com.DeskBooking.deskbooking.DTO.ShortScheduleInformation;
import com.DeskBooking.deskbooking.service.impl.SchedulesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {

    public final SchedulesServiceImpl schedulesService;

    @PostMapping("/schedule")
    public ResponseEntity<List<ShortScheduleInformation>> viewAllSchedulesShort(@RequestBody String data) throws JSONException {
        //Catching JSON object with "user" field
        JSONObject j = new JSONObject(data);
        return ResponseEntity.ok().body(schedulesService.getAllSchedulesShort(j.getString("user")));
    }

    @PostMapping("/schedule/limited")
    public ResponseEntity<List<ShortScheduleInformation>> viewLimitedSchedulesShort(@RequestBody String data) throws JSONException {
        //Catching JSON object with "user", "dateFrom", "dateTo" fields
        JSONObject j = new JSONObject(data);
        String user = j.getString("user");
        Timestamp t1 = Timestamp.valueOf(j.getString("dateFrom").replaceFirst("T"," ").substring(0,19));
        Timestamp t2 = Timestamp.valueOf(j.getString("dateTo").replaceFirst("T"," ").substring(0,19));

        return ResponseEntity.ok().body(schedulesService.getFromToSchedulesShort(user, t1, t2));
    }

    @PostMapping("/schedule/disable")
    public ResponseEntity<?> disableSchedule(@RequestBody String data) throws JSONException {
        //Catching JSON object with "id" field
        JSONObject j = new JSONObject(data);
        schedulesService.disableSchedule(Long.valueOf(j.getString("id")));
        return ResponseEntity.ok().build();
    }
}
