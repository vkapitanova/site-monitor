package io.aiven.monitor.web;

import io.aiven.monitor.dao.MetricsEntity;
import io.aiven.monitor.dao.MetricsRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Profile("web")
public class MetricsController {
    private final MetricsRepository repository;

    public MetricsController(MetricsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/metrics")
    public String index(@RequestParam(name="offset_id", required=false, defaultValue="999999999999999") String offsetId, Model model) {
        List<MetricsEntity> metrics = repository.findByIdLessThanOrderByIdDesc(offsetId, PageRequest.of(0, 20));
        model.addAttribute("metrics", metrics);
        model.addAttribute("lastId", metrics.get(metrics.size() - 1).getId());
        return "metrics";
    }

}