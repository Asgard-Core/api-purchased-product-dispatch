package co.com.asgard.core.repository;

import co.com.asgard.core.dto.AssignedOrderDTO;
import co.com.asgard.core.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findByCarrierId(Long carrierId);
    Optional<Shipment> findByOrderId(Long orderId);

      @Query("""
        SELECT new co.com.asgard.core.dto.AssignedOrderDTO(
            o.id,
            sl.shippingAddress,
            os.statusName,
            o.createdAt,
            s.shippedAt
        )
        FROM Shipment s
        JOIN s.order o
        JOIN o.status os
        JOIN ShippingLabel sl ON sl.order.id = o.id
        WHERE s.carrier.id = :carrierId
    """)
    List<AssignedOrderDTO> findAssignedOrdersByCarrier(@Param("carrierId") Long carrierId);
}