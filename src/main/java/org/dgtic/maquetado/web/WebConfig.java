package org.dgtic.maquetado.web;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path imagenesPath = Paths.get("imagenes/productos");
        String imagenesAbsolutePath = imagenesPath.toFile().getAbsolutePath();

        registry.addResourceHandler("/productos/**")
                .addResourceLocations("file:" + imagenesAbsolutePath + "/");
    }
}
