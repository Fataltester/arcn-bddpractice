# Laboratorio de BDD con Selenium, ChromeDriver y Java en GitHub Codespaces
# Juan David Martinez

## Descripción
Este proyecto es parte del laboratorio de **Behavior Driven Development (BDD)** de la Escuela Colombiana de Ingeniería Julio Garavito (ECI). Implementa pruebas automatizadas de búsqueda en Google usando:

- **Cucumber**: Framework para BDD con reportes en HTML, JSON y JUnit
- **Selenium WebDriver**: Herramienta para automatización de navegadores web
- **ChromeDriver**: Driver automático gestionado por WebDriverManager
- **Java 11**: Lenguaje de programación
- **Maven**: Gestor de dependencias y construcción del proyecto

### Caso de Uso: Búsqueda en Google
El proyecto implementa un scenario simple pero real: verificar que se puede buscar en Google y encontrar resultados. Esto demuestra los conceptos fundamentales de BDD automatizado.

## Objetivos de la Práctica

1. **Entender BDD**: Aprender los principios de Behavior Driven Development
2. **Escribir Scenarios en Gherkin**: Expresar comportamientos en lenguaje legible (Given-When-Then)
3. **Automatizar con Selenium**: Implementar step definitions que interactúan con navegadores web
4. **Usar WebDriverManager**: Automatizar la gestión de drivers (ChromeDriver)
5. **Generar Reportes**: Crear y visualizar reportes de ejecución en múltiples formatos

## Estructura del Proyecto

```
bdd-java/
├── src/
│   ├── main/java/arcn/eci/tddproject/
│   │   └── App.java              # Aplicación principal
│   └── test/java/arcn/eci/tddproject/
│       ├── features/
│       │   └── google_search.feature  # Scenarios en Gherkin
│       ├── runners/
│       │   └── TestRunner.java        # Runner de Cucumber
│       └── steps/
│           └── SearchSteps.java       # Step definitions
├── pom.xml                       # Configuración Maven
└── target/                       # Artefactos compilados y reportes
```

## Requisitos Previos

- **Java 11** o superior
- **Maven 3.6** o superior
- **Conexión a Internet** (WebDriverManager descarga ChromeDriver automáticamente)
- **Linux, macOS o Windows** (compatible con todos los sistemas operativos)

**Nota**: No es necesario descargar ChromeDriver manualmente. WebDriverManager se encarga automáticamente.

## Instalación y Configuración

1. **Clonar el repositorio**:
```bash
git clone https://github.com/Fataltester/arcn-bddpractice.git
cd arcn-bddpractice/bdd-java
```

2. **Instalar dependencias**:
```bash
mvn clean install
```

## Ejecución de las Pruebas

### Compilar el proyecto
```bash
mvn clean install
```

### Ejecutar todas las pruebas
```bash
mvn clean test
```

### Ejecutar solo los tests de Cucumber
```bash
mvn test -Dtest=TestRunner
```

### Ejecutar con output colorido (opcional)
```bash
mvn test -Dcucumber.options="--color"
```

### Ver los reportes generados
Después de ejecutar las pruebas, abre:
- `target/HtmlReports/report.html` en tu navegador
- `target/JSonReports/report.json` para datos en JSON
- `target/JUnitReports/report.xml` para integración con herramientas CI/CD

## Estructura de un Feature File

Archivo: `src/test/java/arcn/eci/tddproject/features/google_search.feature`

```gherkin
Feature: Google Search

  Scenario: Search for a term
    Given I am on the Google search page
    When I search for "GitHub"
    Then I should see "GitHub" in the results
```

## Step Definitions

Las implementaciones de los pasos se encuentran en `SearchSteps.java`, donde se utiliza Selenium WebDriver con configuración optimizada:

```java
@Given("I am on the Google search page")
public void i_am_on_the_google_search_page() {
    // Usar WebDriverManager para manejar ChromeDriver automáticamente
    WebDriverManager.chromedriver().setup();
    
    // Configurar opciones de Chrome (headless, sandbox, etc.)
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless=new");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    
    driver = new ChromeDriver(options);
    driver.get("https://www.google.com");
}

@When("I search for {string}")
public void i_search_for(String query) {
    WebElement searchBox = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.name("q"))
    );
    searchBox.sendKeys(query);
    searchBox.submit();
}

@Then("I should see {string} in the results")
public void i_should_see_in_the_results(String text) {
    wait.until(ExpectedConditions.titleContains(text));
    assertTrue(driver.getTitle().contains(text));
    driver.quit();
}
```

## Reportes

Después de ejecutar `mvn clean test`, Cucumber genera automáticamente reportes en:

- **HTML Report**: `target/HtmlReports/report.html` - Abre en navegador para ver resultados visuales
- **JSON Report**: `target/JSonReports/report.json` - Datos estructurados para procesamiento
- **JUnit Report**: `target/JUnitReports/report.xml` - Integración con Jenkins, GitHub Actions, etc.
- **Surefire**: `target/surefire-reports/` - Reportes de Maven Surefire


## Dependencias Principales

Ver `pom.xml` para la lista completa. Las principales son:

- `io.cucumber:cucumber-java`: Framework Cucumber
- `io.cucumber:cucumber-junit`: Integración con JUnit
- `selenium-java`: Selenium WebDriver
- `junit:junit`: Framework de pruebas
- `io.github.bonigarcia:webdrivermanager`: Gestión automática de ChromeDriver

## Características Implementadas

### WebDriverManager
Se utiliza **WebDriverManager** para gestionar automáticamente la descarga y configuración de ChromeDriver:

```java
WebDriverManager.chromedriver().setup();
```

### ChromeOptions (Headless Mode)
Se configura Chrome en modo headless para ejecutar sin interfaz gráfica:

```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless=new");
options.addArguments("--no-sandbox");
options.addArguments("--disable-dev-shm-usage");
options.addArguments("--disable-gpu");
options.addArguments("--window-size=1920,1080");
```

### Generación Automática de Reportes
Cucumber genera reportes en múltiples formatos:
- **JSON**: Para consumo programático
- **HTML**: Para visualización en navegador
- **JUnit XML**: Para integración con herramientas CI/CD

## Tips y Mejores Prácticas

1. **WebDriverManager**: Usa WebDriverManager para evitar problemas de compatibilidad con ChromeDriver
2. **Modo Headless**: Ejecuta pruebas en headless para CI/CD (más rápido y sin interfaz)
3. **Wait conditions explícitas**: Usa `ExpectedConditions` en lugar de `Thread.sleep()`
4. **Step definitions reutilizables**: Intenta que los pasos sean genéricos y reutilizables
5. **Page Object Model**: Considera usar el patrón POM para mantener el código limpio
6. **Datos de prueba**: Separa los datos de las pruebas del código
7. **ChromeOptions**: Configura opciones según el entorno (headless en CI, interfaz gráfica en local)

## Solución de Problemas

### Error: "ChromeDriver version mismatch"
**Solución**: WebDriverManager descarga la versión correcta automáticamente. Si el problema persiste:
```bash
mvn clean install -U
```

### Error: "No such file or directory: chromedriver"
**Solución**: Asegúrate de que `WebDriverManager.chromedriver().setup()` se ejecute antes de crear el ChromeDriver:
```java
WebDriverManager.chromedriver().setup();
driver = new ChromeDriver(options);
```

### Timeout esperando elementos
**Solución**: Aumenta el timeout en WebDriverWait o verifica que el elemento sea accesible:
```java
wait = new WebDriverWait(driver, Duration.ofSeconds(15));
```

### Pruebas llentas en ambiente local (sin headless)
**Solución**: Comenta la opción headless en SearchSteps.java para ver el navegador:
```java
// options.addArguments("--headless=new");
```

### Fallo de conexión a Google
**Solución**: Verifica tu conexión a internet o usa una página de prueba local con un servidor HTTP

### Error de compilación Maven
```bash
mvn clean compile
mvn dependency:resolve
```

## Autor
Basado en el laboratorio de ARCN - Escuela Colombiana de Ingeniería Julio Garavito
Laboratorio realizado por Juan David Martínez
