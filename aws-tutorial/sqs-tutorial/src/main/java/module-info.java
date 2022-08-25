module sqs.tutorial {
    exports com.saman.tutorial.aws.sqs.service;
    exports com.saman.tutorial.aws.sqs.impl;
    exports com.saman.tutorial.aws.sqs.utils;

    requires software.amazon.awssdk.auth;
    requires software.amazon.awssdk.core;
    requires software.amazon.awssdk.regions;
    requires software.amazon.awssdk.services.sqs;
    requires io.vavr;
}
