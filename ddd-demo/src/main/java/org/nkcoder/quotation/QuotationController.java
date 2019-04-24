package org.nkcoder.quotation;

import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.nkcoder.quotation.command.EnquiryCarPolicyCommand;
import org.nkcoder.quotation.command.EnquiryHomePolicyCommand;
import org.nkcoder.quotation.domain.model.CarPolicyQuotation;
import org.nkcoder.quotation.domain.model.HomePolicyQuotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quote")
public class QuotationController {

    @Autowired
    private QuotationApplicationService quotationApplicationService;

    @PostMapping(value = "/home")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "POST", notes = "Enquiry home policy")
    public HomePolicyQuotation enquiryHomePolicy(@RequestBody @Valid EnquiryHomePolicyCommand command) {
        return quotationApplicationService.calculateQuote(command);
    }

    @PostMapping(value = "/car")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "POST", notes = "Enquiry car policy")
    public CarPolicyQuotation enquiryHomePolicy(@RequestBody @Valid EnquiryCarPolicyCommand command) {
        return quotationApplicationService.calculateQuote(command);
    }
}
