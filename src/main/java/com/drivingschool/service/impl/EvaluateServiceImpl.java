package com.drivingschool.service.impl;

import com.drivingschool.mapper.EvaluateMapper;
import com.drivingschool.service.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluateServiceImpl implements EvaluateService {
    @Autowired
    private EvaluateMapper evaluateMapper;
}
