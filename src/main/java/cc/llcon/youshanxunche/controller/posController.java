package cc.llcon.youshanxunche.controller;


import cc.llcon.youshanxunche.pojo.Pos;
import cc.llcon.youshanxunche.pojo.Result;
import cc.llcon.youshanxunche.service.PosService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/pos")
@RestController
@Slf4j
public class posController {

    @Autowired
    PosService posService;

    @GetMapping("/{uuid}/latest")
    public Result latest(@PathVariable String uuid, HttpServletRequest request) {
        Pos pos = posService.latest(uuid, request);
        if (pos == null) {
            return Result.error("无记录");
        }

        return Result.success(pos);
    }

}

