package org.nkcoder.quotation.mapper;

import org.nkcoder.common.BaseMapper;
import org.nkcoder.quotation.command.EnquiryCarPolicyCommand;
import org.nkcoder.quotation.domain.model.CarPolicyQuotation;

public class CarPolicyQuotationMapper extends BaseMapper {
    public CarPolicyQuotationMapper() {
        classMap(EnquiryCarPolicyCommand.class, CarPolicyQuotation.class)
                .byDefault()
                .register();
    }
}
