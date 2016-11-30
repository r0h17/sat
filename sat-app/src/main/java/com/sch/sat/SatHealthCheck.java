package com.sch.sat;

import com.codahale.metrics.health.HealthCheck;

public class SatHealthCheck extends HealthCheck {
	
	
	@Override
	protected Result check() throws Exception {
		return Result.healthy();
	}

}
