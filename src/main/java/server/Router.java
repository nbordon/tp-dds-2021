package server;


import domain.controllers.ControllersMascota;
import domain.controllers.LoginController;
import domain.controllers.MascotaEncontradaController;
import domain.controllers.PublicacionesController;
import domain.controllers.*;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;
import spark.utils.IndexHelper;
import spark.utils.StringHelper;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .withHelper("increment", IndexHelper.increment)
                .withHelper("append", StringHelper.append)
                .build();
    }

    public static void init() {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure() {
        MascotaEncontradaController mascotaEncontradaController = new MascotaEncontradaController();
        LoginController loginController = new LoginController();
        PublicacionesController publicacionesController = new PublicacionesController();
        ControllersMascota controllersMascota = new ControllersMascota();
        UsuariosController usuariosController = new UsuariosController();
        AdopcionController adopcionController=new AdopcionController();
        CaracterisiticaDeMascotaRequeridaController caracterisiticaDeMascotaRequeridaController = new CaracterisiticaDeMascotaRequeridaController();
        PreguntasAdopcionController preguntasAdopcionController = new PreguntasAdopcionController();
        NotificadorRecomendacionController notificadorRecomendacionController = new NotificadorRecomendacionController();

        Spark.get("/health", ((request, response) -> "Status UP"));

        Spark.get("/", loginController::mostrarHome,engine);
        Spark.get("/login", loginController::login,engine);
        Spark.get("/loginError", loginController::loginError,engine);
        Spark.post("/login",loginController::iniciarSesion);
        Spark.get("/home",loginController::mostrarHome,engine);

        Spark.get("/logout", loginController::logout);

        Spark.get("/mascota-encontrada/:idMascota", mascotaEncontradaController::mascotaEncontrada, engine);
        Spark.post("/mascota-encontrada/:idMascota", mascotaEncontradaController::infoMascotaEncontradaConChapita, engine);

        Spark.post("/mascota-encontrada/rescatista/:idMascotaEncontrada", mascotaEncontradaController::infoRescatista, engine);

        Spark.get("/mascota-encontrada", mascotaEncontradaController::mascotaEncontrada, engine);
        Spark.post("/mascota-encontrada/","multipart/form-data", mascotaEncontradaController::infoMascotaEncontradaSinChapita, engine);

        Spark.get("/listado-mascotas", controllersMascota::mostrarTodos, Router.engine);
        Spark.get("/detalle-mascota/:id", controllersMascota::mostrar, Router.engine);

        Spark.get("/registrar-mascota", controllersMascota::registrar, Router.engine);
        Spark.post("/registrar-mascota", controllersMascota::guardar);
        Spark.post("/registrar-mascota", controllersMascota::crear);

        Spark.get("/intencion-adopcion",adopcionController::mostrarIntencion,Router.engine);
        Spark.post("/intencion-adopcion",adopcionController::guardarIntencion);

        Spark.get("/adopcion/:id",adopcionController::mostrarAdoptar,Router.engine);
        Spark.post("/adopcion/:id",adopcionController::guardarAdopcion);

        Spark.get("/AdopcionPorDuenio",adopcionController::mostrarAdoptar,Router.engine);
        //Spark.post("/AdopcionPorDuenio",adopcionController::guardarAdopcionPorDuenio);

        /**
         * Endpoints caracteristica de mascota requerida
         * */
        Spark.get("/caracteristicas", caracterisiticaDeMascotaRequeridaController::mostrarTodo, Router.engine);
        Spark.get("/caracteristica/:id", caracterisiticaDeMascotaRequeridaController::mostrar, Router.engine);

        Spark.get("/caracteristica", caracterisiticaDeMascotaRequeridaController::nueva, Router.engine);
        Spark.post("/caracteristica", caracterisiticaDeMascotaRequeridaController::agregar);

        Spark.get("/editar-caracteristica/:id", caracterisiticaDeMascotaRequeridaController::editar, Router.engine);
        Spark.post("/editar-caracteristica/:id", caracterisiticaDeMascotaRequeridaController::modificar);

        Spark.delete("/caracteristica/:id", caracterisiticaDeMascotaRequeridaController::eliminar);

        /**
         * Endpoints pregunta de adopcion
         * */
        Spark.get("/preguntas", preguntasAdopcionController::mostrarTodo, Router.engine);
        Spark.get("/pregunta/:id", preguntasAdopcionController::mostrar, Router.engine);

        Spark.get("/pregunta", preguntasAdopcionController::nueva, Router.engine);
        Spark.post("/pregunta", preguntasAdopcionController::agregar);

        Spark.get("/editar-pregunta/:id", preguntasAdopcionController::editar, Router.engine);
        Spark.post("/editar-pregunta/:id", preguntasAdopcionController::modificar);

        Spark.delete("/pregunta/:id", preguntasAdopcionController::eliminar);

        Spark.delete("/listado-mascotas/:id", controllersMascota::eliminar);

        Spark.post("/editar-mascota/:id", controllersMascota::editarMascota);
        Spark.get("/editar-mascota/:id", controllersMascota::editar, Router.engine);

        //Publicaciones Mascotas EncontradasSC
        Spark.get("/mascotas-encontradas", publicacionesController::mostrarTodosEncontradasSC, Router.engine);
        Spark.get("/detalle-mascota-encontrada/:id", publicacionesController::mostrarEncontradaSC, Router.engine);

        //Publicaciones Mascotas En Adopcion
        Spark.get("/mascotas-en-adopcion", publicacionesController::mostrarTodosAdopcion, Router.engine);
        Spark.get("/detalle-mascota-en-adopcion/:id", publicacionesController::mostrarMascotaEnAdopcion, Router.engine);

        //Publicaciones Intencion Adopcion
        Spark.get("/publicaciones-intencion-adopcion", publicacionesController::mostrarTodosIntencionAdopcion, Router.engine);
        Spark.get("/detalle-intencion/:id", publicacionesController::mostrarIntencionDeAdopcion, Router.engine);

        //Publicaciones Parar Voluntario
        Spark.get("/publicaciones-voluntario", publicacionesController::mostrarTodosParaAprobar, Router.engine);
        Spark.get("/detalle-para-aprobar/:id", publicacionesController::mostrarPublicacionParaAprobar, Router.engine);
        Spark.put("/detalle-para-aprobar/:id", publicacionesController::aprobarPublicacion);
        Spark.put("/detalle-para-aprobar/:id", publicacionesController::desaprobarPublicacion);

        //NotificadorRecomendaciones
        Spark.get("/notificador-manual",notificadorRecomendacionController::notificadorManual);

        Spark.post("/usuarios/registrar", usuariosController::registrarUsuario, Router.engine);
        Spark.get("/registrar-usuario", usuariosController::registrar,Router.engine);
        Spark.post("/agregar-contacto/:idPersona", usuariosController::agregarContacto, Router.engine);

        Spark.get("*", (request, response) -> {
            response.status(404);
            response.redirect("/page-not-found/index.html");
            return response;
        });
    }



}