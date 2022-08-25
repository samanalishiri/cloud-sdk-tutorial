import com.saman.tutorial.aws.sqs.bucket.service.S3Facade;

open module sthree.tutorial {
    exports com.saman.tutorial.aws.sqs.bucket.service;
    exports com.saman.tutorial.aws.sqs.bucket.impl;
    exports com.saman.tutorial.aws.sqs.bucket.utils;

    requires software.amazon.awssdk.services.s3;
    requires software.amazon.awssdk.core;
    requires software.amazon.awssdk.regions;
    requires software.amazon.awssdk.auth;
    requires software.amazon.awssdk.utils;
    requires io.vavr;

    uses S3Facade;
}
