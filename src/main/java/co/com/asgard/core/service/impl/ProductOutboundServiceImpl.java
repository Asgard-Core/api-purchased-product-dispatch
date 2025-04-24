package co.com.asgard.core.service.impl;

import co.com.asgard.core.config.EntityServiceException;
import co.com.asgard.core.dto.ProductOutboundRequestDTO;
import co.com.asgard.core.dto.ProductOutboundResponseDTO;
import co.com.asgard.core.model.AppUser;
import co.com.asgard.core.model.Product;
import co.com.asgard.core.model.ProductOutbound;
import co.com.asgard.core.repository.AppUserRepository;
import co.com.asgard.core.repository.ProductOutboundRepository;
import co.com.asgard.core.repository.ProductRepository;
import co.com.asgard.core.service.ProductOutboundService;
import co.com.asgard.core.dto.ProductOutboundStatusUpdateDTO;
import co.com.asgard.core.enums.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductOutboundServiceImpl implements ProductOutboundService {

    private final ProductRepository productRepo;
    private final AppUserRepository userRepo;
    private final ProductOutboundRepository outboundRepo;

    // Constructor
    public ProductOutboundServiceImpl(ProductRepository productRepo, AppUserRepository userRepo, ProductOutboundRepository outboundRepo) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.outboundRepo = outboundRepo;
    }

    @Override
    public void actualizarEstadoPedido(Long id, String nuevoEstado) {
        // Buscar el pedido por ID
        ProductOutbound productOutbound = outboundRepo.findById(id)
                .orElseThrow(() -> new EntityServiceException(HttpStatus.NOT_FOUND, "Pedido no encontrado con id: " + id));

        // Convertir el nuevo estado a un valor del enum OrderStatus
        OrderStatus status = OrderStatus.valueOf(nuevoEstado.toUpperCase());

        // Actualizar el estado y la fecha de la última actualización
        productOutbound.actualizarEstado(status);  // Método de la clase ProductOutbound
        outboundRepo.save(productOutbound);  // Guardar el pedido con el estado actualizado
    }

    @Override
    public void updateStatus(ProductOutboundStatusUpdateDTO dto) {
        // Buscar el pedido por ID
        ProductOutbound outbound = outboundRepo.findById(dto.getId())
            .orElseThrow(() -> new EntityServiceException(HttpStatus.NOT_FOUND, "Registro no encontrado con id: " + dto.getId()));

        // Actualizar el estado utilizando el método de la clase ProductOutbound
        outbound.actualizarEstado(dto.getStatus());  // Método de la clase ProductOutbound
        outboundRepo.save(outbound);  // Guardar el registro con el nuevo estado
    }

    public ProductOutboundResponseDTO registerOutbound(ProductOutboundRequestDTO dto) {
        // Buscar el producto
        Product product = productRepo.findByCode(dto.getProductCode())
                .orElseThrow(() -> new EntityServiceException(HttpStatus.NOT_FOUND, "Producto con código " + dto.getProductCode() + " no encontrado"));

        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new EntityServiceException(HttpStatus.BAD_REQUEST, "Cantidad debe ser mayor a cero");
        }

        if (product.getCurrentStock() < dto.getQuantity()) {
            throw new EntityServiceException(HttpStatus.BAD_REQUEST, "Stock insuficiente para esta salida");
        }

        // Buscar el responsable
        AppUser user = userRepo.findById(dto.getResponsibleId())
                .orElseThrow(() -> new EntityServiceException(HttpStatus.NOT_FOUND, "Responsable no encontrado"));

        // Disminuir el stock
        product.setCurrentStock(product.getCurrentStock() - dto.getQuantity());
        productRepo.save(product);

        // Crear el código de registro único
        String codeRegister = "OUT" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Crear el objeto ProductOutbound y asignar valores
        ProductOutbound outbound = new ProductOutbound();
        outbound.setProduct(product);
        outbound.setQuantity(dto.getQuantity());
        outbound.setDestination(dto.getDestination());
        outbound.setDate(dto.getDate());
        outbound.setResponsible(user);
        outbound.setCodeRegister(codeRegister);

        // Guardar el nuevo registro
        outboundRepo.save(outbound);

        // Crear y devolver el DTO de respuesta
        ProductOutboundResponseDTO responseDTO = new ProductOutboundResponseDTO();
        responseDTO.setCodeRegister(codeRegister);
        responseDTO.setProductName(product.getName());
        responseDTO.setQuantity(dto.getQuantity());
        responseDTO.setDestination(dto.getDestination());
        responseDTO.setDate(dto.getDate());
        responseDTO.setResponsibleName(user.getFullName());

        return responseDTO;
    }
}
