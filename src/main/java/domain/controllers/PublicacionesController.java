package domain.controllers;

import domain.entities.Mascotas.Mascota;
import domain.entities.Mascotas.MascotaEncontradaSinChapita;
import domain.entities.Organizacion.Organizacion;
import domain.entities.Usuario;
import domain.entities.publicaciones.*;
import domain.repositories.Repositorio;
import domain.repositories.factories.FactoryRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublicacionesController {
    private Repositorio<PublicacionMascotaEnAdopcion> repoPublicacionMascotaEnAdopcion;
    private Repositorio<PublicacionMascotaEncontradaSinChapita> repoPublicacionMascotaEncontradaSC;
    private Repositorio<PublicacionIntencionDeAdopcion> repoPublicacionesIntencionDeAdopcion;
    private Repositorio<Mascota> repoMascotas;
    private Repositorio<Organizacion> repoOrganizacion;


    public PublicacionesController() {
        this.repoPublicacionMascotaEnAdopcion = FactoryRepositorio.get(PublicacionMascotaEnAdopcion.class);
        this.repoPublicacionMascotaEncontradaSC = FactoryRepositorio.get(PublicacionMascotaEncontradaSinChapita.class);
        this.repoPublicacionesIntencionDeAdopcion = FactoryRepositorio.get(PublicacionIntencionDeAdopcion.class);

        this.repoMascotas = FactoryRepositorio.get(Mascota.class);
        this.repoOrganizacion = FactoryRepositorio.get(Organizacion.class);
    }

    public ModelAndView mostrarTodosEncontradasSC(Request request, Response response) {
        HashMap<String, Object> parametros = new HashMap<>();
        try {
            LoginController.cargarPerfiles(parametros, request);
        } catch (Exception e) {
            System.out.println("no hay perfiles");
        }
        List<PublicacionMascotaEncontradaSinChapita> publicacionesMascotaEncontradaSinChapita;
        publicacionesMascotaEncontradaSinChapita = repoPublicacionMascotaEncontradaSC.buscarTodos();

        String sexoMascota;
        if(request.queryParams("sexoMascota") != null) {
            sexoMascota = request.queryParams("sexoMascota");
            parametros.put("filtroSexo",sexoMascota);
        } else {
            sexoMascota = "";
        }

        String tipoMascota;
        if(request.queryParams("tipoMascota") != null) {
            tipoMascota = request.queryParams("tipoMascota");
            parametros.put("filtroTipo",tipoMascota);
        } else {
            tipoMascota = "";
        }

        Boolean noHayFiltro = tipoMascota.isEmpty() && sexoMascota.isEmpty();

        List<PublicacionMascotaEncontradaSinChapita> publicacionesMascotaEncontradaSinChapitaAprobadas = new ArrayList<>();
        for (int i = 0; i < publicacionesMascotaEncontradaSinChapita.size(); i++) {
            MascotaEncontradaSinChapita mascotaEncontradaSinChapita = publicacionesMascotaEncontradaSinChapita.get(i).getMascotaEncontradaSinChapita();
            String mascotaEncontradaSinChapitaTipo = mascotaEncontradaSinChapita.getTipoMascota().toString();
            String mascotaEncontradaSinChapitaSexo = mascotaEncontradaSinChapita.getSexo();

            if(noHayFiltro) {
                if (publicacionesMascotaEncontradaSinChapita.get(i).esAprobada()) {
                    publicacionesMascotaEncontradaSinChapitaAprobadas.add(publicacionesMascotaEncontradaSinChapita.get(i));
                }
            } else {
                if (publicacionesMascotaEncontradaSinChapita.get(i).esAprobada() &&
                        sexoMascota.equals(mascotaEncontradaSinChapitaSexo) || sexoMascota.isEmpty() &&
                        tipoMascota.equals(mascotaEncontradaSinChapitaTipo) || tipoMascota.isEmpty()
                ) {
                    publicacionesMascotaEncontradaSinChapitaAprobadas.add(publicacionesMascotaEncontradaSinChapita.get(i));
                }
            }
        }

        parametros.put("publicacionesMascotaEncontradaSC", publicacionesMascotaEncontradaSinChapitaAprobadas);
        return new ModelAndView(parametros, "mascotasEncontradas.hbs");
    }

    public ModelAndView mostrarEncontradaSC(Request request, Response response) {
        Integer idPublicacionMascotaEncontrada = new Integer(request.params("id"));
        if (idPublicacionMascotaEncontrada != null) {
            PublicacionMascotaEncontradaSinChapita publicacionMascotaEncontradaSinChapita = repoPublicacionMascotaEncontradaSC.buscar(new Integer(request.params("id")));
            List<String> fotosMascotaEncontrada = publicacionMascotaEncontradaSinChapita.getMascotaEncontradaSinChapita().getFotos();
            HashMap<String, Object> parametros = new HashMap<>();
            try {
                LoginController.cargarPerfiles(parametros, request);
            } catch (Exception e) {
                System.out.println("no hay perfiles");
            }
            parametros.put("segundoNivel",true);
            parametros.put("fotosMascota", fotosMascotaEncontrada);
            parametros.put("publicacionMascotaEncontrada", publicacionMascotaEncontradaSinChapita);
            parametros.put("mascotaEncontrada", publicacionMascotaEncontradaSinChapita.getMascotaEncontradaSinChapita());
            return new ModelAndView(parametros, "detalle-mascota-encontrada.hbs");
        }
        return new ModelAndView(null, "mascotasEncontradas.hbs");
    }

    public ModelAndView mostrarMascotaEnAdopcion(Request request, Response response) {
        Integer idPublicacionMascotaEnAdopcion = new Integer(request.params("id"));

        if (idPublicacionMascotaEnAdopcion != null) {
            PublicacionMascotaEnAdopcion publicacionMascotaEnAdopcion = repoPublicacionMascotaEnAdopcion.buscar(new Integer(request.params("id")));
            Integer idMascotaEnAdopcion = publicacionMascotaEnAdopcion.getMascotaEnAdopcion().getId();
            if (idMascotaEnAdopcion != null) {
                Mascota mascotaEnAdopcion = repoMascotas.buscar(idMascotaEnAdopcion);
                HashMap<String, Object> parametros = new HashMap<>();
                try {
                    LoginController.cargarPerfiles(parametros, request);
                } catch (Exception e) {
                    System.out.println("no hay perfiles");
                }
                parametros.put("segundoNivel",true);
                parametros.put("fotosMascota",mascotaEnAdopcion.getFotosUrl());
                parametros.put("publicacionMascotaEnAdopcion", publicacionMascotaEnAdopcion);
                parametros.put("mascota", mascotaEnAdopcion);
                parametros.put("caracteristicas", mascotaEnAdopcion.getCaracteristicas());
                parametros.put("comodidades",publicacionMascotaEnAdopcion.getRespuestasPreguntas());

                return new ModelAndView(parametros, "detalle-mascota-en-adopcion.hbs");
            }
        }
        return new ModelAndView(null, "publicacionesAdopcion.hbs");
    }

    public ModelAndView mostrarTodosAdopcion(Request request, Response response) {
        HashMap<String, Object> parametros = new HashMap<>();
        List<PublicacionMascotaEnAdopcion> publicacionesMascotaEnAdopcion;

        String sexoMascota;
        if(request.queryParams("sexoMascota") != null) {
            sexoMascota = request.queryParams("sexoMascota");
            parametros.put("filtroSexo",sexoMascota);
        } else {
            sexoMascota = "";
        }

        String tipoMascota;
        if(request.queryParams("tipoMascota") != null) {
            tipoMascota = request.queryParams("tipoMascota");
            parametros.put("filtroTipo",tipoMascota);
        } else {
            tipoMascota = "";
        }

        Boolean noHayFiltro = tipoMascota.isEmpty() && sexoMascota.isEmpty();

        publicacionesMascotaEnAdopcion = repoPublicacionMascotaEnAdopcion.buscarTodos();
        List<PublicacionMascotaEnAdopcion> publicacionesMascotaEnAdopcionAprobadas = new ArrayList<>();
        for (int i = 0; i < publicacionesMascotaEnAdopcion.size(); i++) {
            Mascota mascotaEnAdopcion = publicacionesMascotaEnAdopcion.get(i).getMascotaEnAdopcion();
            String mascotaEnAdopcionTipo = mascotaEnAdopcion.getTipoMascota().toString();
            String mascotaEnAdopcionSexo = mascotaEnAdopcion.getSexo();

            if(noHayFiltro) {
                if (publicacionesMascotaEnAdopcion.get(i).esAprobada()) {
                    publicacionesMascotaEnAdopcionAprobadas.add(publicacionesMascotaEnAdopcion.get(i));
                }
            } else {
                if (publicacionesMascotaEnAdopcion.get(i).esAprobada() &&
                        sexoMascota.equals(mascotaEnAdopcionSexo) || sexoMascota.isEmpty() &&
                        tipoMascota.equals(mascotaEnAdopcionTipo) || tipoMascota.isEmpty()
                ) {
                    publicacionesMascotaEnAdopcionAprobadas.add(publicacionesMascotaEnAdopcion.get(i));
                }
            }
        }
        parametros.put("publicacionesMascotaEnAdopcion", publicacionesMascotaEnAdopcionAprobadas);
        return new ModelAndView(parametros, "publicacionesAdopcion.hbs");
    }

    public ModelAndView mostrarTodosIntencionAdopcion(Request request, Response response) {
        HashMap<String, Object> parametros = new HashMap<>();
        List<PublicacionIntencionDeAdopcion> publicacionesIntencionDeAdopcion;

        String sexoMascota;
        if(request.queryParams("sexoMascota") != null) {
            sexoMascota = request.queryParams("sexoMascota");
            parametros.put("filtroSexo",sexoMascota);
        } else {
            sexoMascota = "";
        }

        String tipoMascota;
        if(request.queryParams("tipoMascota") != null) {
            tipoMascota = request.queryParams("tipoMascota");
            parametros.put("filtroTipo",tipoMascota);
        } else {
            tipoMascota = "";
        }

        Boolean noHayFiltro = tipoMascota.isEmpty() && sexoMascota.isEmpty();

        publicacionesIntencionDeAdopcion = repoPublicacionesIntencionDeAdopcion.buscarTodos();
        List<PublicacionIntencionDeAdopcion> publicacionesIntencionDeAdopcionAprobadas = new ArrayList<>();
        for (int i = 0; i < publicacionesIntencionDeAdopcion.size(); i++) {
            String mascotaEnAdopcionTipo = publicacionesIntencionDeAdopcion.get(i).getTipoMascota().toString();
            String mascotaEnAdopcionSexo = publicacionesIntencionDeAdopcion.get(i).getSexoMascota();

            if(noHayFiltro) {
                if (publicacionesIntencionDeAdopcion.get(i).esAprobada()) {
                    publicacionesIntencionDeAdopcionAprobadas.add(publicacionesIntencionDeAdopcion.get(i));
                }
            } else {
                if (publicacionesIntencionDeAdopcion.get(i).esAprobada() &&
                        sexoMascota.equals(mascotaEnAdopcionSexo) || sexoMascota.isEmpty() &&
                        tipoMascota.equals(mascotaEnAdopcionTipo) || tipoMascota.isEmpty()
                ) {
                    publicacionesIntencionDeAdopcionAprobadas.add(publicacionesIntencionDeAdopcion.get(i));
                }
            }
        }

        parametros.put("publicacionesIntencionAdopcion", publicacionesIntencionDeAdopcionAprobadas);
        return new ModelAndView(parametros, "publicacionesIntencion.hbs");

    }

    public ModelAndView mostrarIntencionDeAdopcion(Request request, Response response) {
        Integer idPublicacionIntencion = new Integer(request.params("id"));

        if (idPublicacionIntencion != null) {
            PublicacionIntencionDeAdopcion publicacionIntencionDeAdopcion = repoPublicacionesIntencionDeAdopcion.buscar(new Integer(request.params("id")));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("publicacionIntencion", publicacionIntencionDeAdopcion);
            parametros.put("personaInteresada",publicacionIntencionDeAdopcion.getPersonaInteresada());
            parametros.put("caracteristicas",publicacionIntencionDeAdopcion.getRespuestasCaracteristicasDeMascota());
            parametros.put("comodidades",publicacionIntencionDeAdopcion.getRespuestasComodidades());
            return new ModelAndView(parametros, "detalle-intencion.hbs");
        }
        return new ModelAndView(null, "publicacionesIntencion.hbs");

    }

    public ModelAndView mostrarTodosParaAprobar(Request request, Response response) {
        HashMap<String, Object> parametros = new HashMap<>();
        LoginController.cargarUsuario(parametros,request);
        LoginController.cargarPerfiles(parametros,request);

        List<PublicacionMascotaEnAdopcion> publicacionesMascotaEnAdopcion;
        publicacionesMascotaEnAdopcion = repoPublicacionMascotaEnAdopcion.buscarTodos();
        List<PublicacionMascotaEncontradaSinChapita> publicacionesMascotaEncontradaSinChapita;
        publicacionesMascotaEncontradaSinChapita = repoPublicacionMascotaEncontradaSC.buscarTodos();
        List<PublicacionIntencionDeAdopcion> publicacionesIntencionDeAdopcion;
        publicacionesIntencionDeAdopcion = repoPublicacionesIntencionDeAdopcion.buscarTodos();
        List<Publicacion> publicacionesParaAprobar = new ArrayList<>();

        for (int i = 0; i < publicacionesMascotaEnAdopcion.size(); i++) {

            if (publicacionesMascotaEnAdopcion.get(i).esPendiente()) {

                publicacionesMascotaEnAdopcion.get(i).setFotosURL(publicacionesMascotaEnAdopcion.get(i).getMascota().getFotosUrl());
                publicacionesParaAprobar.add(publicacionesMascotaEnAdopcion.get(i));
            }
        }
        for (int i = 0; i < publicacionesMascotaEncontradaSinChapita.size(); i++) {
            if (publicacionesMascotaEncontradaSinChapita.get(i).esPendiente()) {
                publicacionesMascotaEncontradaSinChapita.get(i).setFotosURL(publicacionesMascotaEncontradaSinChapita.get(i).getMascotaEncontradaSinChapita().getFotos());
                publicacionesParaAprobar.add(publicacionesMascotaEncontradaSinChapita.get(i));
            }
        }
        for (int i = 0; i < publicacionesIntencionDeAdopcion.size(); i++) {
            if (publicacionesIntencionDeAdopcion.get(i).esPendiente()) {
                publicacionesParaAprobar.add(publicacionesIntencionDeAdopcion.get(i));

            }
        }
        parametros.put("publicacionesParaAprobar", publicacionesParaAprobar);
        return new ModelAndView(parametros, "publicacionesParaAprobar.hbs");
    }

    public ModelAndView mostrarPublicacionParaAprobar(Request request, Response response) {
        Integer idPublicacionParaAprobar = new Integer(request.params("id"));

        if (idPublicacionParaAprobar != null) {
            int tipoPublicacion = tipoPublicacion(idPublicacionParaAprobar);
            switch (tipoPublicacion) {
                case 1:
                    PublicacionMascotaEnAdopcion publicacionMascotaEnAdopcion = repoPublicacionMascotaEnAdopcion.buscar(new Integer(request.params("id")));
                    Integer idMascotaEnAdopcion = publicacionMascotaEnAdopcion.getMascotaEnAdopcion().getId();
                    List<String> fotosMascota;
                    if (idMascotaEnAdopcion != null) {
                        Mascota mascotaEnAdopcion = repoMascotas.buscar(idMascotaEnAdopcion);
                        HashMap<String, Object> parametros = new HashMap<>();
                        try {
                            LoginController.cargarPerfiles(parametros, request);
                        } catch (Exception e) {
                            System.out.println("no hay perfiles");
                        }
                        fotosMascota = mascotaEnAdopcion.getFotosUrl();
                        parametros.put("segundoNivel",true);
                        parametros.put("fotosMascota",fotosMascota);
                        parametros.put("publicacionMascotaEnAdopcion", publicacionMascotaEnAdopcion);
                        parametros.put("mascota", mascotaEnAdopcion);
                        parametros.put("caracteristicas", mascotaEnAdopcion.getCaracteristicas());
                        parametros.put("comodidades",publicacionMascotaEnAdopcion.getRespuestasPreguntas());
                        return new ModelAndView(parametros, "detalle-para-aprobar-adopcion.hbs");
                    }

                case 2:
                    PublicacionMascotaEncontradaSinChapita publicacionMascotaEncontradaSinChapita = repoPublicacionMascotaEncontradaSC.buscar(new Integer(request.params("id")));
                    HashMap<String, Object> parametros = new HashMap<>();
                    try {
                        LoginController.cargarPerfiles(parametros, request);
                    } catch (Exception e) {
                        System.out.println("no hay perfiles");
                    }
                    List<String> fotosMascotaEncontrada = publicacionMascotaEncontradaSinChapita.getMascotaEncontradaSinChapita().getFotos();
                    parametros.put("fotosMascota", fotosMascotaEncontrada);
                    parametros.put("segundoNivel",true);
                    parametros.put("publicacionMascotaEncontrada", publicacionMascotaEncontradaSinChapita);
                    parametros.put("mascotaEncontrada",publicacionMascotaEncontradaSinChapita.getMascotaEncontradaSinChapita());
                    return new ModelAndView(parametros, "detalle-para-aprobar-encontrada.hbs");

                case 3:
                    PublicacionIntencionDeAdopcion publicacionIntencionDeAdopcion = repoPublicacionesIntencionDeAdopcion.buscar(new Integer(request.params("id")));
                    HashMap<String, Object> parametros2 = new HashMap<>();
                    try {
                        LoginController.cargarPerfiles(parametros2, request);
                    } catch (Exception e) {
                        System.out.println("no hay perfiles");
                    }
                    parametros2.put("segundoNivel",true);
                    parametros2.put("publicacionIntencion", publicacionIntencionDeAdopcion);
                    parametros2.put("personaInteresada",publicacionIntencionDeAdopcion.getPersonaInteresada());
                    parametros2.put("caracteristicas",publicacionIntencionDeAdopcion.getRespuestasCaracteristicasDeMascota());
                    parametros2.put("comodidades",publicacionIntencionDeAdopcion.getRespuestasComodidades());
                    return new ModelAndView(parametros2, "detalle-para-aprobar-intencion.hbs");

                default:
                    return new ModelAndView(null, "publicacionesParaAprobar.hbs");
            }
        }
        return new ModelAndView(null, "publicacionesParaAprobar.hbs");
    }


    public int tipoPublicacion(int idPublicacion) {
        if (repoPublicacionMascotaEnAdopcion.buscar(idPublicacion) != null) {
            return 1;
        } else {
            if (repoPublicacionMascotaEncontradaSC.buscar(idPublicacion) != null) {
                return 2;
            } else {
                if (repoPublicacionesIntencionDeAdopcion.buscar(idPublicacion) != null) {
                    return 3;
                }
            }
        }
        return 0;
    }

    public Response aprobarPublicacion(Request request, Response response){
        Integer idPublicacionParaAprobar = new Integer(request.params("id"));
        if (idPublicacionParaAprobar != null) {
            int tipoPublicacion = tipoPublicacion(idPublicacionParaAprobar);
            switch (tipoPublicacion) {
                case 1:
                    PublicacionMascotaEnAdopcion publicacionMascotaEnAdopcion = repoPublicacionMascotaEnAdopcion.buscar(new Integer(request.params("id")));
                    publicacionMascotaEnAdopcion.setEstado(EstadoPublicacion.APROBADA);
                    repoPublicacionMascotaEnAdopcion.modificar(publicacionMascotaEnAdopcion);
                    break;
                case 2:
                    PublicacionMascotaEncontradaSinChapita publicacionMascotaEncontradaSinChapita = repoPublicacionMascotaEncontradaSC.buscar(new Integer(request.params("id")));
                    publicacionMascotaEncontradaSinChapita.setEstado(EstadoPublicacion.APROBADA);
                    repoPublicacionMascotaEncontradaSC.modificar(publicacionMascotaEncontradaSinChapita);
                    break;
                case 3:
                    PublicacionIntencionDeAdopcion publicacionIntencionDeAdopcion = repoPublicacionesIntencionDeAdopcion.buscar(new Integer(request.params("id")));
                    publicacionIntencionDeAdopcion.setEstado(EstadoPublicacion.APROBADA);
                    repoPublicacionesIntencionDeAdopcion.modificar(publicacionIntencionDeAdopcion);
                    break;
            }
        }
        return response;
    }
    public Response desaprobarPublicacion(Request request, Response response){
        Integer idPublicacionParaAprobar = new Integer(request.params("id"));
        if (idPublicacionParaAprobar != null) {
            int tipoPublicacion = tipoPublicacion(idPublicacionParaAprobar);
            switch (tipoPublicacion) {
                case 1:
                    PublicacionMascotaEnAdopcion publicacionMascotaEnAdopcion = repoPublicacionMascotaEnAdopcion.buscar(new Integer(request.params("id")));
                    publicacionMascotaEnAdopcion.setEstado(EstadoPublicacion.FINALIZADA);
                    repoPublicacionMascotaEnAdopcion.modificar(publicacionMascotaEnAdopcion);
                    break;
                case 2:
                    PublicacionMascotaEncontradaSinChapita publicacionMascotaEncontradaSinChapita = repoPublicacionMascotaEncontradaSC.buscar(new Integer(request.params("id")));
                    publicacionMascotaEncontradaSinChapita.setEstado(EstadoPublicacion.FINALIZADA);
                    repoPublicacionMascotaEncontradaSC.modificar(publicacionMascotaEncontradaSinChapita);
                    break;
                case 3:
                    PublicacionIntencionDeAdopcion publicacionIntencionDeAdopcion = repoPublicacionesIntencionDeAdopcion.buscar(new Integer(request.params("id")));
                    publicacionIntencionDeAdopcion.setEstado(EstadoPublicacion.FINALIZADA);
                    repoPublicacionesIntencionDeAdopcion.modificar(publicacionIntencionDeAdopcion);
                    break;
            }
        }
        return response;
    }
}
