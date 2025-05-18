package org.dgtic.maquetado.controller;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.dgtic.maquetado.model.Inventario;
import org.dgtic.maquetado.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

@Controller
@RequestMapping("/almacen")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/inventario")
    public String verInventario(Model model) {
        List<Inventario> inventario = inventarioService.listarTodo();
        model.addAttribute("inventario", inventario);
        return "almacen";
    }


    @GetMapping("/pdf")
    public void exportarPDF(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Inventario-Almacen.pdf");

        List<Inventario> inventario = inventarioService.listarTodo();

        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        Font tituloFont = new Font(Font.HELVETICA, 18, Font.BOLD, Color.BLACK);
        Paragraph titulo = new Paragraph("Inventario del Almacén", tituloFont);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{3, 3, 3, 2, 2, 3});
        tabla.setSpacingBefore(10);

        Stream.of("Producto", "Modelo", "Sucursal", "Stock Actual", "Stock Mínimo", "Fecha Registro")
                .forEach(col -> {
                    Font fontCabecera = new Font(Font.HELVETICA, 12, Font.BOLD);
                    Phrase cabecera = new Phrase(col, fontCabecera);
                    tabla.addCell(cabecera);
                });

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Inventario reg : inventario) {
            tabla.addCell(reg.getProducto().getNombre());
            tabla.addCell(reg.getProducto().getModelo());
            tabla.addCell(reg.getSucursal().getNombre());
            tabla.addCell(String.valueOf(reg.getStockActual()));
            tabla.addCell(String.valueOf(reg.getStockMinimo()));
            tabla.addCell(reg.getFechaRegistro().toLocalDate().format(formatoFecha));
        }

        documento.add(tabla);
        documento.close();
    }
}
