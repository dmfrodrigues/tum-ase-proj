package com.asedelivery.common.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.asedelivery.common.*")
@EntityScan("com.asedelivery.common.*")
public class AseLibAutoConfig {}
