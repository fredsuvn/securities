package com.tousie.securities.service.test;

import com.sonluo.spongebob.spring.server.ApiService;
import com.sonluo.spongebob.spring.server.ApiServiceMapping;
import com.sonluo.spongebob.spring.server.Request;
import com.sonluo.spongebob.spring.server.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

@ApiService
@ApiServiceMapping("test")
public class TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    @Resource
    private TestAsync testAsync;

    @ApiServiceMapping("test")
    public ResponseTest test(RequestTest requestTest) {
        ResponseTest responseTest = new ResponseTest();
        responseTest.setBar(requestTest.getBar());
        responseTest.setFoo(requestTest.getFoo());
        return responseTest;
    }

    @ApiServiceMapping("session")
    public ResponseTest session(Request<RequestTest> request) {
        ResponseTest responseTest = new ResponseTest();
        Session session = request.getSession(true);
        logger.info("Session.id = {}.", session.getId());
//        responseTest.setBar(request.getContent().getBar());
//        responseTest.setFoo(request.getContent().getFoo());
        return responseTest;
    }

    @ApiServiceMapping("async")
    public ResponseTest async(String text, Session session) {
        ResponseTest responseTest = new ResponseTest();
        for (int i = 0; i < 100; i++) {
            testAsync.showText(text);
        }
        responseTest.setBar(text);
        responseTest.setFoo(session.getId());
        return responseTest;
    }

    public static class RequestTest {
        private String foo;
        private String bar;

        public String getFoo() {
            return foo;
        }

        public void setFoo(String foo) {
            this.foo = foo;
        }

        public String getBar() {
            return bar;
        }

        public void setBar(String bar) {
            this.bar = bar;
        }
    }

    public static class ResponseTest {
        private String foo;
        private String bar;

        public String getFoo() {
            return foo;
        }

        public void setFoo(String foo) {
            this.foo = foo;
        }

        public String getBar() {
            return bar;
        }

        public void setBar(String bar) {
            this.bar = bar;
        }
    }
}
