package com.example.presentation.soap;

import com.example.business.dtos.AttendanceDto;
import com.example.business.services.AttendanceService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;

@WebService
public class AttendanceWebService {
    private final AttendanceService service = new AttendanceService();

    @WebMethod
    public List<AttendanceDto> getAllAttendance() {
        return service.getAllAttendance();
    }

    @WebMethod
    public AttendanceDto getAttendance(@WebParam(name = "id") int attendanceId) {
        return service.getAttendanceById(attendanceId);
    }

    @WebMethod
    public AttendanceDto createAttendance(@WebParam(name = "attendance") AttendanceDto attendance) {
        return service.addAttendance(attendance);
    }

    @WebMethod
    public void deleteAttendance(@WebParam(name = "id") int attendanceId) {
        service.deleteAttendance(attendanceId);
    }

    @WebMethod
    public void updateAttendance(@WebParam(name = "attendance") AttendanceDto attendanceDto) {
        service.updateAttendance(attendanceDto);
    }
}
