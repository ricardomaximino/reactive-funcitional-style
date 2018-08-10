package com.brasajava.routerfunctionstyle.converter;

import com.brasajava.routerfunctionstyle.domain.Lead;
import com.brasajava.routerfunctionstyle.domain.LeadDTO;
import com.brasajava.routerfunctionstyle.domain.LeadIDDTO;

public interface LeadConverter {

  LeadDTO toLeadDto(Lead lead);

  Lead toLead(LeadDTO dto);

  LeadIDDTO toLeadIdDto(Lead lead);
}
