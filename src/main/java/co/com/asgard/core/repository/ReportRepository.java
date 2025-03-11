package co.com.asgard.core.repository;

import co.com.asgard.core.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("SELECT r FROM Report r WHERE " +
            "(COALESCE(:fechaInicio, r.fechaDespacho) <= r.fechaDespacho) AND " +
            "(COALESCE(:fechaFin, r.fechaDespacho) >= r.fechaDespacho) AND " +
            "(COALESCE(:transportistaId, r.transportista.id) = r.transportista.id) AND " +
            "(COALESCE(:clienteId, r.cliente.id) = r.cliente.id) AND " +
            "(COALESCE(:estadoPedido, r.estadoEntrega) = r.estadoEntrega)")
    List<Report> findReports(@Param("fechaInicio") LocalDateTime fechaInicio,
                             @Param("fechaFin") LocalDateTime fechaFin,
                             @Param("transportistaId") Long transportistaId,
                             @Param("clienteId") Long clienteId,
                             @Param("estadoPedido") String estadoPedido);

}