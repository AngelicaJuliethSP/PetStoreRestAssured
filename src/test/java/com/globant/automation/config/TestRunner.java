package com.globant.automation.config;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuración de la suite API.
 *
 * Resolución de variables (en orden de prioridad, igual que los otros
 * frameworks del proyecto):
 *   1. Propiedad del sistema: -Durl.base=...
 *   2. Variable de entorno: BASE_URL
 *   3. src/test/resources/config.properties (url.base)
 *
 * Si la URL no puede resolverse, la suite falla en @BeforeSuite con un
 * mensaje claro en lugar de fallar después con un NPE difícil de rastrear.
 */
public class TestRunner {

    public static final String PROPERTIES_FILE = "src/test/resources/config.properties";
    public static final Properties PROPERTIES = new Properties();

    private static String baseUrl;

    @BeforeSuite
    public void setUpEnvironment() {
        loadProperties();
        baseUrl = getConfigVariable("url.base");
        Assert.assertNotNull(baseUrl,
                "La URL base no pudo resolverse: configure -Durl.base, la variable "
                        + "de entorno BASE_URL o url.base en src/test/resources/config.properties");
    }

    private void loadProperties() {
        try (InputStream input = new FileInputStream(PROPERTIES_FILE)) {
            PROPERTIES.load(input);
        } catch (IOException e) {
            System.out.println(String.format("Error loading the properties file: %s", e.getMessage()));
        }
    }

    private String getConfigVariable(String key) {
        // Prioridad: propiedad del sistema -D > variable de entorno > config.properties
        String value = System.getProperty(key);
        if (value == null || value.isBlank()) {
            String envKey = key.replace("url.base", "BASE_URL");
            value = System.getenv(envKey);
        }
        if (value == null || value.isBlank()) {
            value = PROPERTIES.getProperty(key);
        }
        return (value == null || value.isBlank()) ? null : value;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }
}
