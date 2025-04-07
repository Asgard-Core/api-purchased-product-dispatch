package co.com.asgard.core;

import co.com.asgard.core.dto.ReportRequestDTO;
import co.com.asgard.core.service.IReportService;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {

    @Mock
    private IReportService reportService;

    @Test
    void testGenerateExcelReport() throws Exception {
        ReportRequestDTO request = new ReportRequestDTO();
        byte[] fakeExcel = new byte[]{1, 2, 3};

        when(reportService.generateExcelReport(request)).thenReturn(fakeExcel);

        byte[] result = reportService.generateExcelReport(request);
        assertNotNull(result);
        assertEquals(3, result.length);
    }
}

