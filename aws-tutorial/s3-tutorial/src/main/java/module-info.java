import com.tutorial.aws.bucket.contract.S3Facade;

open module sthree.tutorial {
    exports com.tutorial.aws.bucket.contract;
    exports com.tutorial.aws.bucket.contract.object;
    exports com.tutorial.aws.bucket.contract.bucket;
    exports com.tutorial.aws.bucket.implementation;
    exports com.tutorial.aws.bucket.implementation.object;
    exports com.tutorial.aws.bucket.implementation.bucket;
    exports com.tutorial.aws.bucket.factory;
    exports com.tutorial.aws.bucket.utils;

    requires software.amazon.awssdk.services.s3;
    requires software.amazon.awssdk.core;
    requires software.amazon.awssdk.regions;
    requires software.amazon.awssdk.auth;
    requires software.amazon.awssdk.utils;
    requires io.vavr;

    uses S3Facade;
}
