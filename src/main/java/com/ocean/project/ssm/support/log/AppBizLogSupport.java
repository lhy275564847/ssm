package com.ocean.project.ssm.support.log;

import com.ocean.project.ssm.domain.Log;
import com.ocean.project.ssm.service.LogService;
import com.ocean.project.ssm.support.orm.query.PageQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务日志记录
 *
 * @author haiyang.li on 2017/9/30.
 */
@Component
public class AppBizLogSupport {

    @Resource
    private LogService logService;

    public void createAppLog(Log log){
        logService.create(log);
    }

    public List<Log> findAppLog(){
        return logService.findAll(new PageQuery());
    }
}
