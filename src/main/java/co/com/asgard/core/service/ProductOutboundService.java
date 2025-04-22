package co.com.asgard.core.service;

import co.com.asgard.core.dto.ProductOutboundRequestDTO;
import co.com.asgard.core.dto.ProductOutboundResponseDTO;

public interface ProductOutboundService {
    ProductOutboundResponseDTO registerOutbound(ProductOutboundRequestDTO dto);
}