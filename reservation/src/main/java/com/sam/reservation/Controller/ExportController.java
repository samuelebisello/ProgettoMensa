package com.sam.reservation.Controller;

import com.sam.reservation.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

@Controller
@CrossOrigin("*")
public class ExportController {

    @Autowired
    ReservationService service;

    /**
     * Handle request to download an Excel document
     */
    @CrossOrigin("*")
    @GetMapping(value = "/api/report/download")
    public String download(Model model, @RequestParam("year") String year, @RequestParam("month") String month) {
        model.addAttribute("occorrenzePiatti", service.getDataForReport(year + "-" + month));
        return "";
    }
}