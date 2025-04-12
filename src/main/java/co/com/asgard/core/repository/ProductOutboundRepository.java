package co.com.asgard.core.repository;

import co.com.asgard.core.model.ProductOutbound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOutboundRepository extends JpaRepository<ProductOutbound, Long> {
}