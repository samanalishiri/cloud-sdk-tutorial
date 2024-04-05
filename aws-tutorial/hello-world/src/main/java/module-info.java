open module helloworld {
    exports com.tutorial.aws.credential;

    requires software.amazon.awssdk.auth;
    requires io.vavr;
    requires org.slf4j;
}
