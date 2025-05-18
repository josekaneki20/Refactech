package org.dgtic.maquetado.controller;

import org.apache.commons.io.FilenameUtils;
import org.dgtic.maquetado.model.*;
import org.dgtic.maquetado.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private MarcaService marcaService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private EstadoProductoService estadoProductoService;
    @Autowired
    private FotosProductoService fotosProductoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private SucursalService sucursalService;
    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/muestraCatalogo")
    public String listaProductos(Model model) {
        List<Producto> productos = productoService.listaProductos();
        List<Inventario> inventarios = inventarioService.listarTodo();

        Map<Long, Inventario> inventarioMap = inventarios.stream()
                .collect(Collectors.toMap(i -> i.getProducto().getIdProducto(), i -> i));

        model.addAttribute("productos", productos);
        model.addAttribute("inventarioMap", inventarioMap);

        return "Catalogo";
    }

    @GetMapping("/registro")
    //@RequestMapping(value = "Productos",method = RequestMethod.GET)
    public String paginaProductos(Model  modelo){
        modelo.addAttribute("productos","Productos");
        List<Marca> marcas= marcaService.listarMarcas();// Se cargan las marcas
        modelo.addAttribute("listaMarcas",marcas);
        modelo.addAttribute("producto",new Producto());
        modelo.addAttribute("categorias", categoriaService.listaCategorias()); // Se cargan las categorías
        modelo.addAttribute("estados", estadoProductoService.listaEstadoproductos());
        modelo.addAttribute("sucursales", sucursalService.listarSucursales());

        return "Productos";
    }

    @PostMapping("/productoguardar")
    public String guardarProducto(@RequestParam String nombre,
                                  @RequestParam String descripcion,
                                  @RequestParam String modelo,
                                  @RequestParam Double precio,
                                  @RequestParam Categoria categoria,
                                  @RequestParam Marca marca,
                                  @RequestParam EstadoProducto estado,
                                  @RequestParam("imagen") MultipartFile imagen,
                                  @RequestParam Long sucursalId,
                                  @RequestParam int stock,
                                  Model model) throws IOException {

        Producto producto = new Producto(nombre, descripcion, modelo, precio, categoria, marca, estado);
        List<String> errores = new ArrayList<>();

        if (productoService.buscarPorNombre(nombre).isPresent()) {
            errores.add("Ya existe un producto registrado con ese nombre.");
        }

        if (nombre.isBlank() || descripcion.isBlank() || modelo.isBlank()) {
            errores.add("Los campos no pueden estar vacíos");
        }

        if (precio < 0) {
            errores.add("El precio no puede ser negativo");
        }

        if (!errores.isEmpty()) {
            model.addAttribute("errores", errores);
            model.addAttribute("sucursales", sucursalService.listarSucursales());
            model.addAttribute("categorias", categoriaService.listaCategorias());
            model.addAttribute("listaMarcas", marcaService.listarMarcas());
            model.addAttribute("estados", estadoProductoService.listaEstadoproductos());
            return "Productos";
        }

        productoService.guardarProducto(producto);

        if (!imagen.isEmpty()) {
            String extension = FilenameUtils.getExtension(imagen.getOriginalFilename());
            String nombreSeguro = UUID.randomUUID().toString() + "." + extension;
            Path ruta = Paths.get(System.getProperty("user.dir"), "imagenes", "productos", nombreSeguro);

            Files.createDirectories(ruta.getParent());
            Files.write(ruta, imagen.getBytes());

            FotosProducto foto = new FotosProducto();
            foto.setUrlFoto("productos/" + nombreSeguro);
            foto.setProducto(producto);
            fotosProductoService.guardarFoto(foto);
        }

        Sucursal sucursal = sucursalService.buscarPorId(sucursalId)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        Inventario inventario = new Inventario(
                producto,
                sucursal,
                stock,
                1,
                new java.sql.Date(System.currentTimeMillis())
        );

        inventarioService.guardarInventario(inventario);
        model.addAttribute("mensajeExito", "Producto registrado con éxito.");
        return "redirect:/productos/registro";
    }


    @PostMapping("/eliminar")
    public String eliminarProducto(@RequestParam("id") Long id) {
        productoService.eliminarProducto(id);
        return "redirect:/productos/muestraCatalogo";
    }

    @GetMapping("/modificar/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model) {
        Producto producto = productoService.obtenerProductoPorId(id);
        Inventario inventario = inventarioService.obtenerPorProducto(id).orElse(new Inventario());

        if (producto == null) {
            return "redirect:/Catalogo";
        }

        model.addAttribute("producto", producto);
        model.addAttribute("listaMarcas", marcaService.listarMarcas());
        model.addAttribute("categorias", categoriaService.listaCategorias());
        model.addAttribute("estados", estadoProductoService.listaEstadoproductos());
        model.addAttribute("inventario", inventario);

        return "ActualizarProducto";
    }

    @PostMapping("/modificar/{idProducto}")
    public String modificarProducto(@PathVariable Long idProducto,
                                    @ModelAttribute("producto") Producto producto,
                                    @RequestParam("stockActual") int stockActual,
                                    @RequestParam("stockMinimo") int stockMinimo,
                                    @RequestParam(value = "imagen", required = false) MultipartFile imagen,
                                    RedirectAttributes redirectAttributes) throws IOException {

        Producto existente = productoService.obtenerProductoPorId(idProducto);
        if (existente == null) {
            redirectAttributes.addFlashAttribute("error", "Producto no encontrado.");
            return "redirect:/productos/muestraCatalogo";
        }

        existente.setNombre(producto.getNombre());
        existente.setDescripcion(producto.getDescripcion());
        existente.setModelo(producto.getModelo());
        existente.setPrecio(producto.getPrecio());
        existente.setMarca(producto.getMarca());
        existente.setCategoria(producto.getCategoria());
        existente.setEstado(producto.getEstado());

        // Substituir imagen si se proporcionó
        if (imagen != null && !imagen.isEmpty()) {
            // Eliminar imágenes anteriores (opcional)
            List<FotosProducto> anteriores = fotosProductoService.obtenerFotosPorProducto(existente.getIdProducto());
            for (FotosProducto foto : anteriores) {
                Path ruta = Paths.get(System.getProperty("user.dir"), "imagenes", foto.getUrlFoto());
                Files.deleteIfExists(ruta);
                fotosProductoService.eliminarFoto(foto.getIdFoto());
            }

            // Guardar nueva imagen
            String extension = FilenameUtils.getExtension(imagen.getOriginalFilename());
            String nombreSeguro = UUID.randomUUID().toString() + "." + extension;
            Path ruta = Paths.get(System.getProperty("user.dir"), "imagenes", "productos", nombreSeguro);

            Files.createDirectories(ruta.getParent());
            Files.write(ruta, imagen.getBytes());

            FotosProducto nuevaFoto = new FotosProducto();
            nuevaFoto.setProducto(existente);
            nuevaFoto.setUrlFoto("productos/" + nombreSeguro);
            fotosProductoService.guardarFoto(nuevaFoto);
        }

        productoService.guardarProducto(existente);

        Optional<Inventario> inventarioOpt = inventarioService.obtenerPorProducto(idProducto);
        if (inventarioOpt.isPresent()) {
            Inventario inventario = inventarioOpt.get();
            inventario.setStockActual(stockActual);
            inventario.setStockMinimo(stockMinimo);
            inventarioService.guardarInventario(inventario);
        }

        redirectAttributes.addFlashAttribute("mensaje", "Producto actualizado correctamente.");
        return "redirect:/productos/muestraCatalogo";
    }


}
