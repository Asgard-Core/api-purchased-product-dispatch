package co.com.asgard.core.dto;

public class ReportResponseDTO {

    private int totalPedidosDespachados;
    private int entregasExitosas;
    private int pedidosRetrasados;
    private String causaRetraso;
    private double porcentajeEficiencia;

    public ReportResponseDTO() {
    }

    public ReportResponseDTO(int totalPedidosDespachados, int entregasExitosas, int pedidosRetrasados, String causaRetraso, double porcentajeEficiencia) {
        this.totalPedidosDespachados = totalPedidosDespachados;
        this.entregasExitosas = entregasExitosas;
        this.pedidosRetrasados = pedidosRetrasados;
        this.causaRetraso = causaRetraso;
        this.porcentajeEficiencia = porcentajeEficiencia;
    }

    public int getTotalPedidosDespachados() {
        return totalPedidosDespachados;
    }

    public void setTotalPedidosDespachados(int totalPedidosDespachados) {
        this.totalPedidosDespachados = totalPedidosDespachados;
    }

    public int getEntregasExitosas() {
        return entregasExitosas;
    }

    public void setEntregasExitosas(int entregasExitosas) {
        this.entregasExitosas = entregasExitosas;
    }

    public int getPedidosRetrasados() {
        return pedidosRetrasados;
    }

    public void setPedidosRetrasados(int pedidosRetrasados) {
        this.pedidosRetrasados = pedidosRetrasados;
    }

    public String getCausaRetraso() {
        return causaRetraso;
    }

    public void setCausaRetraso(String causaRetraso) {
        this.causaRetraso = causaRetraso;
    }

    public double getPorcentajeEficiencia() {
        return porcentajeEficiencia;
    }

    public void setPorcentajeEficiencia(double porcentajeEficiencia) {
        this.porcentajeEficiencia = porcentajeEficiencia;
    }

    // Método toString para representación en cadena
    @Override
    public String toString() {
        return "ReportResponseDTO{" +
                "totalPedidosDespachados=" + totalPedidosDespachados +
                ", entregasExitosas=" + entregasExitosas +
                ", pedidosRetrasados=" + pedidosRetrasados +
                ", causaRetraso='" + causaRetraso + '\'' +
                ", porcentajeEficiencia=" + porcentajeEficiencia +
                '}';
    }
}
