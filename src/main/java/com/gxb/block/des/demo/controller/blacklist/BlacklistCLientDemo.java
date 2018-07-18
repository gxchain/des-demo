package com.gxb.block.des.demo.controller.blacklist;

import com.alibaba.fastjson.JSON;
import com.gxb.sdk.des.client.BlacklistGatewayClient;
import com.gxb.sdk.des.exception.GxbApiException;
import com.gxb.sdk.des.model.LoanInfo;
import com.gxb.sdk.des.model.vo.QuestionReport;
import com.gxb.sdk.des.model.vo.QuestionTokenResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liruobin
 * @since 2018/6/12 下午12:08
 */
@Slf4j
public class BlacklistCLientDemo {

    BlacklistGatewayClient client = new BlacklistGatewayClient("5Ka9YjFQtfUUX2DdnqkaPWH1rVeSe...", "1.2.19");

    @Test
    public void getQuestionToken() throws Exception {
        //黑名单共债查询结果
        List<LoanInfo> loanInfoList = new ArrayList<>();
        LoanInfo loanInfo = LoanInfo.builder().platform("1.2.902895").
                appId("jx").
                loanType(1).
                loanAmount("1-1.5").
                loanStatus(3).
                repayStatus("M(2)").
                arrears(1000.00).
                contractDate("2017-05-01").
                build();

        LoanInfo loanInfo2 = LoanInfo.builder().platform("1.2.922685").
                appId("").
                loanType(2).
                loanAmount("4-4.5").
                loanStatus(2).
                repayStatus("NORMAL").
                arrears(0.0).
                contractDate("2016-05-01").
                build();

        loanInfoList.add(loanInfo);
        loanInfoList.add(loanInfo2);
        try {

            //请求网关获取答题token，入参为DES的requestId、商户回调地址、黑名单共债查询结果
            //回调地址格式http://XXX.com/XXX/XXX?a=&b=&token=
            QuestionTokenResult result = client.getQuestionToken("QmRNBXRuoNzTsmfr973uTFuDRoas5NmQcCFModrWeJp88D", "http://127.0.0.1/question?token=", loanInfoList);
            log.info(JSON.toJSONString(result));
        } catch (GxbApiException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void getQuestionReport() throws Exception {
        try {
            //在收到网关访问回调地址的请求后根据答题token获取答题报告
            List<QuestionReport> questionReports = client.getQuestionReport("GXBBL9571018478134208b936c08c8fffc102a");
            log.info(JSON.toJSONString(questionReports));
        } catch (GxbApiException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }
}
