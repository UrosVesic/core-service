package rs.urosvesic.coreservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.urosvesic.coreservice.model.ReportStatus;
import rs.urosvesic.coreservice.service.PostReportService;

@RequestMapping("/api/report")
@RequiredArgsConstructor
@RestController
public class PostReportController {

    private final PostReportService service;

    @PatchMapping("/{postId}")
    public ResponseEntity changeReportStatus(@RequestBody ReportStatus reportStatus, @PathVariable Long postId){
        service.changeReportStatus(reportStatus,postId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
