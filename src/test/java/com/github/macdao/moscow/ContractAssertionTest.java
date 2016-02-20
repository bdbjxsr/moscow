package com.github.macdao.moscow;

import com.github.macdao.moscow.http.OkHttpClientExecutor;
import com.github.macdao.moscow.http.RestExecutor;
import com.github.macdao.moscow.http.RestTemplateExecutor;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class ContractAssertionTest {
    private static final ContractContainer contractContainer = new ContractContainer(Paths.get("src/test/resources/contracts"));

    @Parameterized.Parameters
    public static Collection<RestExecutor> data() {
        return Arrays.asList(new RestTemplateExecutor(), new OkHttpClientExecutor());
    }

    @Parameterized.Parameter
    public RestExecutor restExecutor;

    @Rule
    public final TestName name = new TestName();

    @Test
    public void should_response_text_foo() throws Exception {
        assertContract();
    }

    @Test
    public void request_text_foo_should_response_text_bar() throws Exception {
        assertContract();
    }

    @Test(expected = IllegalArgumentException.class)
    public void bad_contract_name() throws Exception {
        assertContract();
    }

    @Test
    public void request_file_should_response_text_bar2() throws Exception {
        assertContract();
    }

    @Test
    public void request_uri_foo_should_response_text_bar3() throws Exception {
        assertContract();
    }

    @Test
    public void request_param_should_response_text_bar4() throws Exception {
        assertContract();
    }

    @Test
    public void request_put_foo2_should_response_text_bar3() throws Exception {
        assertContract();
    }

    @Test
    public void request_json_should_response_text_bar4() throws Exception {
        assertContract();
    }

    @Test
    public void request_json_should_response_text_bar() throws Exception {
        assertContract();
    }

    @Test
    public void request_text_bar_should_response_201() throws Exception {
        final String barId = assertContract().get("bar-id");

        assertThat(barId, is("bar-id-1"));
    }

    @Test
    public void request_text_bar2_should_response_headers() throws Exception {
        assertContract();
    }

    @Test
    public void request_text_bar3_should_response_json() throws Exception {
        new ContractAssertion(contractContainer.findContracts(methodName()))
                .setScheme("http")
                .setHost("127.0.0.1")
                .setPort(12306)
                .assertContract();
    }

    @Test
    public void request_text_bar4_should_response_foo() throws Exception {
        new ContractAssertion(contractContainer.findContracts(methodName()))
                .setPort(12306)
                .setNecessity(true)
                .assertContract();
    }

    @Test(expected = ExecutionTimeoutException.class)
    public void request_text_bar5_should_response_timeout() throws Exception {
        new ContractAssertion(contractContainer.findContracts(methodName()))
                .setPort(12306)
                .setExecutionTimeout(100)
                .assertContract();
    }

    @Test
    public void request_text_bar6_should_response_201_and_body() throws Exception {
        assertContract();
    }

    @Test
    public void request_text_bar7_should_response_401() throws Exception {
        assertContract();
    }

    private Map<String, String> assertContract() {
        return new ContractAssertion(contractContainer.findContracts(methodName()))
                .setPort(12306)
                .setRestExecutor(restExecutor)
                .assertContract();
    }

    private String methodName() {
        return name.getMethodName().substring(0,name.getMethodName().length()-3);
    }
}