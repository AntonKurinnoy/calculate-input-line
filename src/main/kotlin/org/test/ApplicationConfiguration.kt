package org.test

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.test.service.evaluator.mathjs.MathJsProperties

/**
 * @author Slava Gornostal
 */
@Configuration
@EnableConfigurationProperties(MathJsProperties::class)
class ApplicationConfiguration