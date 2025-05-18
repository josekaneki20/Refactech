package org.dgtic.maquetado.controller;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.dgtic.maquetado.model.ItemCarrito;
import org.dgtic.maquetado.model.Pedido;
import org.dgtic.maquetado.service.ItemCarritoService;
import org.dgtic.maquetado.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/factura")
public class FacturaController {
    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ItemCarritoService itemCarritoService;

    @PostMapping("/descargar")
    public void descargarFacturaPdf(@RequestParam Long idPedido,
                                    HttpServletResponse response,
                                    Principal principal) throws IOException, DocumentException {

        Optional<Pedido> optionalPedido = pedidoService.buscarPorId(idPedido);
        if (optionalPedido.isEmpty()) {
            response.sendRedirect("/catalogoPaginacion");
            return;
        }

        Pedido pedido = optionalPedido.get();

        // Verifica que el pedido pertenezca al cliente autenticado
        String correo = principal.getName();
        if (!pedido.getCliente().getCorreoElectronico().equalsIgnoreCase(correo)) {
            response.sendRedirect("/catalogoPaginacion");
            return;
        }

        List<ItemCarrito> items = itemCarritoService.findByCarritoId(pedido.getCarrito().getIdCarrito());

        // Configurar respuesta
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=factura_" + pedido.getIdPedido() + ".pdf");

        // Crear documento PDF
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Encabezado
        document.add(new Paragraph("Factura de Compra"));
        document.add(new Paragraph("Pedido N.º: " + pedido.getIdPedido()));
        document.add(new Paragraph("Fecha: " + pedido.getFecha()));
        document.add(new Paragraph("Cliente: " +
                pedido.getCliente().getNombre() + " " +
                pedido.getCliente().getApellidoPaterno() + " " +
                pedido.getCliente().getApellidoMaterno()));
        document.add(new Paragraph(" "));

        // Tabla de productos
        PdfPTable table = new PdfPTable(4);
        table.addCell("Producto");
        table.addCell("Cantidad");
        table.addCell("Precio unitario");
        table.addCell("Total");

        for (ItemCarrito item : items) {
            table.addCell(item.getProducto().getNombre());
            table.addCell(String.valueOf(item.getCantidad()));
            table.addCell("$" + item.getProducto().getPrecio());
            table.addCell("$" + (item.getProducto().getPrecio() * item.getCantidad()));
        }

        document.add(table);

        // Total final
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Total pagado: $" + pedido.getTotalCompra()));

        document.close();
    }


}
