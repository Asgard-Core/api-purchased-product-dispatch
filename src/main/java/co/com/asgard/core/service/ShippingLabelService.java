package co.com.asgard.core.service;

import co.com.asgard.core.dto.ShippingLabelRequestDTO;
import co.com.asgard.core.dto.ShippingLabelResponseDTO;

public interface ShippingLabelService {
    ShippingLabelResponseDTO generateLabel(ShippingLabelRequestDTO dto);
}