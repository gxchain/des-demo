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

    BlacklistGatewayClient client = new BlacklistGatewayClient("5Ka9YjFQtfUUX2DdnqkaPWH1rVeSe...", "1.2.19", "https://survey.gxb.io");

    @Test
    public void getQuestionToken() throws Exception {
        List<LoanInfo> loanInfoList = new ArrayList<>();
        LoanInfo loanInfo = LoanInfo.builder().platform(client.getAccountId()).
                platformName("XX贷").
                loanType(1).
                loanAmount("1-1.5").
                loanStatus(3).
                repayStatus("M(2)").
                arrears(1000.00).
                contractDate("2017-05-01").
                build();

        LoanInfo loanInfo2 = LoanInfo.builder().platform(client.getAccountId()).
                platformName("OO贷").
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
            List<QuestionReport> questionReports = client.getQuestionReport("GXBBL9571018478134208b936c08c8fffc102a");
            log.info(JSON.toJSONString(questionReports));
        } catch (GxbApiException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }
}
