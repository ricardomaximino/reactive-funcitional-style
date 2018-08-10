package com.brasajava.routerfunctionstyle.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.brasajava.routerfunctionstyle.domain.Lead;
import com.brasajava.routerfunctionstyle.domain.LeadDTO;
import com.brasajava.routerfunctionstyle.domain.LeadIDDTO;

@Component
public class LeadConverterImpl implements LeadConverter {

  @Override
  public LeadDTO toLeadDto(Lead lead) {
    LeadDTO dto = new LeadDTO();
    BeanUtils.copyProperties(lead, dto);
    return dto;
  }

  @Override
  public Lead toLead(LeadDTO dto) {
    Lead lead = new Lead();
    BeanUtils.copyProperties(dto, lead);
    return lead;
  }

  @Override
  public LeadIDDTO toLeadIdDto(Lead lead) {
    LeadIDDTO id = new LeadIDDTO();
    BeanUtils.copyProperties(lead, id);
    return id;
  }
}
