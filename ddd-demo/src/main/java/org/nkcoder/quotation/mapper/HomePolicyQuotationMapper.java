package org.nkcoder.quotation.mapper;

import org.nkcoder.common.BaseMapper;
import org.nkcoder.quotation.command.EnquiryHomePolicyCommand;
import org.nkcoder.quotation.domain.model.HomePolicyQuotation;

public class HomePolicyQuotationMapper extends BaseMapper {
    public HomePolicyQuotationMapper() {
        classMap(EnquiryHomePolicyCommand.class, HomePolicyQuotation.class)
                .byDefault()
                .register();
    }
}
