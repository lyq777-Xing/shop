package net.zjitc.service;

import net.zjitc.common.ResponseResult;
import net.zjitc.exception.ReportsException;

public interface ReportsService {
    ResponseResult reportsOne() throws ReportsException;
}
