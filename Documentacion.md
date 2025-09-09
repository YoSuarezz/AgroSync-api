## Guia de implementacion de funcionalidades en el proyecto

### Resumen 
Este documento proporciona una guia paso a paso para implementar nuevas funcionalidades en el proyecto.

### 🏗️ Estructura del proyecto

El proyecto esta estructurado de la siguiente manera:

```
├───application
│   ├───primaryports
│   │   ├───dto
│   │   ├───interactor
│   │   └───mapper
│   ├───secondaryports
│   │   ├───entity
│   │   ├───mapper
│   │   └───repository
│   └───usecase
├───crosscutting
│   ├───enums
│   ├───exceptions
│   └───helpers
├───domain
├───infrastructure
│   ├───primaryadapters
│   │   └───controller
│   │       ├───response
│   │       └───rest
│   └───secondaryadapters
│       └───data
│           └───sql
│               └───repository
└───init
└───config
```

### 🛠️ Pasos para implementar una nueva funcionalidad

#### Paso 1: Definir los modelos (DTO, Entity y Domain)
En el proyecto se utilizan DTOs, Entities y Domain Models para representar los datos en diferentes capas.

En este caso la mayoria de los modelos ya estan definidos pero de igual manera hay que revisar si es necesario modificarlos o crear nuevos.

- **DTO (Data Transfer Object)**: Se encuentran en `application/primaryports/dto`. Son utilizados para transferir datos entre la capa de presentacion y la capa de aplicacion.
  - **Cosas a tener en cuenta:**
    - Los DTO deben de tener validacion para la entrada de datos para seguir la practica anti-nulos. Aplicando constructores y setters con validacion de datos. Por ejemplo:
      - **Constructor vacio**: Validacion con los Helpers
        - ``` 
           public UsuarioDTO() {
              setId(id);
              setNombre(TextHelper.EMPTY);
              setTelefono(TextHelper.EMPTY);
              setTipo_usuario(TipoUsuarioDTO.create());
            }
      - **Setters con validacion**: Validacion con los Helpers
        - ```
           public String getTelefono() {
              return TextHelper.applyTrim(telefono);
           }
        - ```
            public UsuarioDTO setTipo_usuario(TipoUsuarioDTO tipo_usuario) {
               this.tipo_usuario = ObjectHelper.getDefault(tipo_usuario, TipoUsuarioDTO.create());
               return this;
            }
      - **setters encadenables**: En algunos casos los setters son encadenables para facilitar la creacion de objetos.
        - ```
           public UsuarioDTO setNombre(String nombre) {
              this.nombre = TextHelper.applyTrim(nombre);
              return this;
           }
- **Entity**: Se encuentran en `application/secondaryports/entity`. Representan las tablas de la base de datos y son utilizados por los repositorios para interactuar con la base de datos.
    - **Cosas a tener en cuenta:**
      - Los Entities siguen la misma logica que los DTOs en cuanto a validacion de datos y constructores. y además se sigue una práctica de Fabricacion estatica con diferentes parametros para facilitar la creación de objetos. Por ejemplo:
          - ``` 
                public static TIpoUsuarioEntity create(Long id, String nombre) {
                    return new TIpoUsuarioEntity(id, nombre);
                }

                public static TIpoUsuarioEntity create(Long id) {
                    return new TIpoUsuarioEntity(id, TextHelper.EMPTY);
                }

                public static TIpoUsuarioEntity create() {
                    return new TIpoUsuarioEntity();
                }

- **Domain Model**: Se encuentran en `domain`. Representan los conceptos del negocio y contienen la logica de negocio.
    - **Cosas a tener en cuenta:**
      - En la capa de dominio no se deben de tener constructores vacios, ya que esto puede llevar a la creacion de objetos invalidos. Por lo tanto, todos los Domain Models deben de tener constructores con todos los parametros necesarios para crear un objeto valido. Ademas, los setters no son encadenables y no deben de tener validacion de datos, ya que la validacion debe de hacerse en la capa de aplicacion (DTOs y Entities). Por ejemplo:
        - ```
           public Usuario(Long id, String nombre, String telefono, TipoUsuario tipo_usuario) {
              this.id = id;
              this.nombre = nombre;
              this.telefono = telefono;
              this.tipo_usuario = tipo_usuario;
           }
        - ```
           public Usuario setNombre(String nombre) {
              this.nombre = nombre;
              return this;
           }
#### Paso 2: Crear los mappers
Los mappers son responsables de convertir entre diferentes modelos (DTO, Entity y Domain Model).
- **DTO to Domain Mapper**: Se encuentran en `application/primaryports/mapper`. Son utilizados para convertir DTOs a Domain Models y viceversa.
  - ```
    @Mapper
    public interface TipoUsuarioDTOMapper {

       TipoUsuarioDTOMapper INSTANCE = Mappers.getMapper(TipoUsuarioDTOMapper.class);

       TipoUsuarioDomain toDomain(TipoUsuarioDTO dto);

       TipoUsuarioDTO toDTO(TipoUsuarioDomain domain);

       List<TipoUsuarioDTO> toDTOCollection(List<TipoUsuarioDomain> domainList);
    }

- **Entity to Domain Mapper**: Se encuentran en `application/secondaryports/mapper`. Son utilizados para convertir Entities a Domain Models y viceversa.
    - ```
      @Mapper
      public interface TipoUsuarioEntityMapper {

         TipoUsuarioEntityMapper INSTANCE = Mappers.getMapper(TipoUsuarioEntityMapper.class);

         TIpoUsuarioEntity toEntity(TipoUsuarioDomain domain);

         TipoUsuarioDomain toDomain(TIpoUsuarioEntity entity);

         List<TIpoUsuarioEntity> toEntityCollection(List<TipoUsuarioDomain> domainCollection);

         List<TipoUsuarioDomain> toDomainCollection(List<TIpoUsuarioEntity> entityCollection);
      }

**Nota:** Se utiliza MapStruct para la generacion automatica de los mappers. Asegurate de seguir la misma estructura y convenciones al crear nuevos mappers. Lo ideal es que en cada capa los nombres de los atributos sean iguales para facilitar el mapeo automatico de MapStruct.

#### Paso 3: Crear los repositorios
Los repositorios son responsables de interactuar con la base de datos y realizar operaciones CRUD.
- **Repositorio**: Se encuentran en `application/secondaryports/repository`. Son interfaces que definen los metodos para interactuar con la base de datos.
  - ```
    @Repository
    public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>, JpaSpecificationExecutor<UsuarioEntity> {

        boolean existsByNombreIgnoreCase(String nombre);

        boolean existsByNombreAndIdNot(String nombre, Long id);

        boolean existsByTelefono(String telefono);

        boolean existsByTelefonoAndIdNot(String telefono, Long id);
    }

**Nota:** El uso de JpaSpecificationExecutor se incluye para facilitar la implementacion de busquedas avanzadas y paginadas. Se debera poner según convenga. Ademas aqui se definen metodos que no son CRUD basicos, como por ejemplo busquedas por campos unicos.

#### Paso 4: Crear las excepciones personalizadas para la capa de domain
Las excepciones personalizadas son importantes para manejar errores de negocio de manera clara y consistente.
Todas lass excepciones siguen la misma estructura y convencion. Ejemplo:
- ```
  public class CaracteresNombreUsuarioNoValidosException extends RuleAgroSyncException {

    private static final long serialVersionUID = 1L;

    private CaracteresNombreUsuarioNoValidosException(final String userMessage) {
        super(userMessage, userMessage, new Exception());
    }

    public static CaracteresNombreUsuarioNoValidosException create() {
        var userMessage = "El nombre debe de tener menos de 50 caracteres";
        return new CaracteresNombreUsuarioNoValidosException(userMessage);
    }
  }
**Nota**: Todas las excepciones personalizadas extienden de RuleAgroSyncException, la cual a su vez extiende de RuntimeException. Esto permite manejar las excepciones de manera uniforme en toda la aplicacion.

#### Paso 5: Crear las reglas de negocio en la capa de domain
- **Rules**: Aquí se crean las reglas de negocio que se van a utilizar en los casos de uso.
    - Crear la interfaz correspondiente la cual extiende de DomainRule.
    - ```
      public interface ActualizarNombreUsuarioNoExisteRule extends DomainRule<UsuarioDomain> {
      }
    - Al domain rule se le pasa lo necesario para validar la regla, puede ser un String, un Long, un objeto o el dominio completo.
    - Crear la clase que implementa la interfaz por cada regla de negocio.
    - Cada clase debe tener un metodo validar() que lanza una excepcion si la regla no se cumple.
    - Inyectar los repositorios necesarios para realizar las validaciones.
    - Ejemplo:
    - ```
        @Service
        public class ActualizarNombreUsuarioNoExisteRuleImpl implements ActualizarNombreUsuarioNoExisteRule {

           private final UsuarioRepository usuarioRepository;

           public ActualizarNombreUsuarioNoExisteRuleImpl(UsuarioRepository usuarioRepository) {
              this.usuarioRepository = usuarioRepository;
           }

           @Override
           public void validate(UsuarioDomain data) {
             if (usuarioRepository.existsByNombreAndIdNot(data.getNombre(), data.getId())) {
                 throw NombreUsuarioExisteException.create();
             }
           }
        }

#### Paso 6: Crear las rules validator
Estas son las responsables de agrupar las reglas de negocio necesarias para ejecutar un caso de uso.
   - Paso 1: Crear la interfaz del rule validator la cual extiende de UseCaseRuleValidator.
     - ```
        public interface RegistrarNuevoUsuarioRulesValidator extends RulesValidator<UsuarioDomain> {
        }
   - **Nota**: Al UseCaseRuleValidator se le pasa el Domain Model completo, ya que es el necesario para validar varias reglas de negocio.
   - Paso 2: Crear la clase que implementa la interfaz del rule validator.
   - Cada clase debe tener un metodo validar() que llama a las reglas de negocio necesarias.
   - Inyectar las reglas de negocio necesarias.
   - Ejemplo:
   - ```
      @Service
      public class RegistrarNuevoUsuarioRulesValidatorImpl implements RegistrarNuevoUsuarioRulesValidator {

         private final NombreUsuarioNoExisteRule nombreUsuarioNoExisteRule;
         private final CaracteresNombreUsuarioNoValidosRule caracteresNombreUsuarioNoValidosRule;
         private final TelefonoUsuarioNoExisteRule telefonoUsuarioNoExisteRule;
         private final CaracteresTelefonoUsuarioNoValidosRule caracteresTelefonoUsuarioNoValidosRule;

         public RegistrarNuevoUsuarioRulesValidatorImpl(NombreUsuarioNoExisteRule nombreUsuarioNoExisteRule,
                                                        CaracteresNombreUsuarioNoValidosRule caracteresNombreUsuarioNoValidosRule,
                                                        TelefonoUsuarioNoExisteRule telefonoUsuarioNoExisteRule,
                                                        CaracteresTelefonoUsuarioNoValidosRule caracteresTelefonoUsuarioNoValidosRule) {
            this.nombreUsuarioNoExisteRule = nombreUsuarioNoExisteRule;
            this.caracteresNombreUsuarioNoValidosRule = caracteresNombreUsuarioNoValidosRule;
            this.telefonoUsuarioNoExisteRule = telefonoUsuarioNoExisteRule;
            this.caracteresTelefonoUsuarioNoValidosRule = caracteresTelefonoUsuarioNoValidosRule;
         }

         @Override
         public void validate(UsuarioDomain data) {
            nombreUsuarioNoExisteRule.validate(data);
            caracteresNombreUsuarioNoValidosRule.validate(data.getNombre());
            telefonoUsuarioNoExisteRule.validate(data.getTelefono());
            caracteresTelefonoUsuarioNoValidosRule.validate(data.getTelefono());
         }
      }

#### Paso 7: Crear los casos de uso
Esta capa trabaja siempre con la logica del negocio por lo que su modelo siempre será el Domain Model.
- **Use Case**: Se encuentran en `application/usecase`. Son clases que implementan la logica de negocio utilizando los repositorios y mappers.

    - Paso 1: Crear la interfaz del caso de uso la cual extiende de la base.
      - UseCaseWithoutInput: Para casos de uso que no requieren parametros de entrada.
      - UseCaseWithoutReturn: Para casos de uso que no retornan un valor.
      - UseCaseWithReturn: Para casos de uso que retornan un valor.
      - Ejemplo:
      - ```
        public interface RegistrarNuevoUsuario extends UseCaseWithoutReturn<UsuarioDomain> {
        }
    - Paso 2: Crear la clase que implementa la interfaz del caso de uso.
      - Inyectar los repositorios necesarios.
      - Inyectar las rulesValidator correspondientes.
      - Implementar el metodo ejecutar() con la logica de negocio.
    - Paso 3: Ejecutar las validaciones de negocio utilizando las rulesValidator.
    - Paso 4: Utilizar los mappers para convertir entre Domain y Entity.
    - Paso 5: Realizar las operaciones necesarias utilizando los repositorios.
    - Paso 6: Retornar el resultado si es necesario.
    - Ejemplo:
      ```
      @Service
      public class RegistrarNuevoUsuarioImpl implements RegistrarNuevoUsuario {

         private final UsuarioRepository usuarioRepository;
         private final RegistrarNuevoUsuarioRulesValidator registrarNuevoUsuarioRulesValidator;

         public RegistrarNuevoUsuarioImpl(UsuarioRepository usuarioRepository, RegistrarNuevoUsuarioRulesValidator registrarNuevoUsuarioRulesValidator) {
            this.usuarioRepository = usuarioRepository;
            this.registrarNuevoUsuarioRulesValidator = registrarNuevoUsuarioRulesValidator;
         }

         @Override
            public void ejecutar(UsuarioDomain data) {
            registrarNuevoUsuarioRulesValidator.validar(data);
            UsuarioEntity usuarioEntity = UsuarioEntityMapper.INSTANCE.toEntity(data);

            usuarioRepository.save(usuarioEntity);
         }
      }
      
#### Paso 8: Crear los interactor
En esta capa se trabaja con los DTOs para la entrada y salida de datos.
- **Interactor**: Se encuentran en `application/primaryports/interactor`. Los interactors son los responsables de ejecutar el caso de uso y hacer la transaccion con la base de datos.
  - Paso 1: Crear la interfaz del interactor la cual extiende de la base.
    - InteractorWithoutInput: Para interactores que no requieren parametros de entrada.
    - InteractorWithoutReturn: Para interactores que no retornan un valor.
    - InteractorWithReturn: Para interactores que retornan un valor.
    - Ejemplo:
      - ```
        public interface RegistrarNuevoUsuarioInteractor extends InteractorWithoutReturn<UsuarioDTO> {
        }
  - Paso 2: Crear la clase que implementa la interfaz del interactor.
    - Inyectar el caso de uso correspondiente.
    - Implementar el metodo ejecutar() para llamar al caso de uso.
    - Utilizar los mappers para convertir entre DTO y Domain.
    - Retornar el resultado si es necesario.
    - Ejemplo:
      ```
          @Service
          @Transactional
          public class RegistrarNuevoUsuarioInteractorImpl implements RegistrarNuevoUsuarioInteractor {

             private final RegistrarNuevoUsuario registrarNuevoUsuario;

             public RegistrarNuevoUsuarioInteractorImpl(RegistrarNuevoUsuario registrarNuevoUsuario) {
                this.registrarNuevoUsuario = registrarNuevoUsuario;
             }

             @Override
             public void ejecutar(UsuarioDTO data) {
                UsuarioDomain usuarioDomain = UsuarioDTOMapper.INSTANCE.toDomain(data);
                registrarNuevoUsuario.ejecutar(usuarioDomain);
             }
          }
      
#### Paso 9: Crear él response correspondiente
Esta clase es la que se va a retornar al cliente.
- **Response**: Se encuentran en `infrastructure/primaryadapters/adapter/response`.
  - Paso 1: Crear la clase del responso la cual extiende de Response.
  - Ejemplo:
    ```
      public class UsuarioResponse extends Response<UsuarioDTO> {

       public UsuarioResponse() {
         setMensajes(new ArrayList<String>());
         setDatos(new ArrayList<>());
       }
      }

### Paso 10: Crear el controlador REST
El controlador REST es el punto de entrada para las solicitudes HTTP.
- **Controller**: Se encuentran en `infrastructure/primaryadapters/controller`.
    - Paso 1: Crear la clase del controlador con la anotacion @RestController y definir el path base con @RequestMapping.
    - Paso 2: Inyectar los interactors necesarios.
    - Paso 3: Crear los endpoints utilizando las anotaciones correspondientes (@GetMapping, @PostMapping, @PutMapping, @DeleteMapping).
    - Paso 4: Manejar las excepciones y retornar él response adecuado.
    - Los controladores siguen una estructura y convencion similar. Ejemplo:
    ```
    @PostMapping()
    public ResponseEntity<UsuarioResponse> registrarUsuario(@RequestBody UsuarioDTO usuario) {

        var httpStatusCode = HttpStatus.ACCEPTED;
        var usuarioResponse = new UsuarioResponse();

        try {
            registrarNuevoUsuarioInteractor.ejecutar(usuario);
            usuarioResponse.getMensajes().add("Se ha registrado el usuario correctamente");

        } catch (final AgroSyncException excepcion) {
            httpStatusCode = HttpStatus.BAD_REQUEST;
            usuarioResponse.getMensajes().add(excepcion.getMensajeUsuario());
        } catch (final Exception excepcion) {
            httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
            var userMessage = "Error al registrar el usuario";
            usuarioResponse.getMensajes().add(userMessage);
        }
        return new ResponseEntity<>(usuarioResponse, httpStatusCode);
    }

**Nota**: Asegúrate de manejar las excepciones de manera adecuada y retornar mensajes claros al cliente.

### Paso 11: Borrar la logica antigua
Una vez que la nueva funcionalidad ha sido implementada y probada, es importante eliminar cualquier logica antigua que ya no sea necesaria para mantener el codigo limpio y facil de mantener.
- Revisar el codigo antiguo y eliminar cualquier clase, metodo o logica que ya no se utilice.
- Asegurarse de que la eliminacion de la logica antigua no afecte la funcionalidad del sistema.
- Realizar pruebas para verificar que todo funciona correctamente después de la eliminacion.

### Cosas para tener en cuenta
- Seguir las convenciones de codificacion y estructura del proyecto para mantener la coherencia.
- Cada capa tiene su propia carpeta para los modelos para facilitar la localizacion y el mantenimiento. Esto quiere decir que si se va a crear una nueva funcionalidad se requiere crear su carpeta y dento de ella su respectiva logica.
- Mantener la separacion de responsabilidades entre las capas para facilitar el mantenimiento y la escalabilidad del sistema.
- Utilizar excepciones personalizadas para manejar errores de negocio de manera clara y consistente.