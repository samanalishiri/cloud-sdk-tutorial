import com.tutorial.aws.bucket.service.S3Facade;

open module sthree.tutorial {
    exports com.tutorial.aws.bucket.service;
    exports com.tutorial.aws.bucket.impl;
    exports com.tutorial.aws.bucket.utils;

    requires software.amazon.awssdk.services.s3;
    requires software.amazon.awssdk.core;
    requires software.amazon.awssdk.regions;
    requires software.amazon.awssdk.auth;
    requires software.amazon.awssdk.utils;
    requires io.vavr;

    uses S3Facade;
}
