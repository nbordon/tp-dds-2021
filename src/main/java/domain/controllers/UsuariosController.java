package domain.controllers;


        import Api.services.entities.Ubicacion;
        import domain.entities.*;
        import domain.entities.EstrategiasNotificacion.EstrategiaDeNotificacion;
        import domain.entities.EstrategiasNotificacion.EstrategiaEmail.EstrategiaEmail;
        import domain.entities.EstrategiasNotificacion.EstrategiaSms.EstrategiaDeSms;
        import domain.entities.EstrategiasNotificacion.EstrategiaWhatsApp.EstrategiaDeWhatsApp;
        import domain.repositories.Repositorio;
        import domain.repositories.factories.FactoryRepositorio;
        import exception.VerificadorException;
        import spark.ModelAndView;
        import spark.Request;
        import spark.Response;
        import utils.localizador.LocalizadorDeOrganizacion;

        import java.time.LocalDate;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class UsuariosController {
    private Repositorio<Persona> repoPersona;
    private Repositorio<InformacionPersonal> repoInfoPersonal;
    private Repositorio<Contacto> repoContactos;

    public UsuariosController() {
        this.repoPersona = FactoryRepositorio.get(Persona.class);
        this.repoInfoPersonal = FactoryRepositorio.get(InformacionPersonal.class);
        this.repoContactos = FactoryRepositorio.get(Contacto.class);
    }

    public ModelAndView registrarUsuario(Request request, Response response) throws VerificadorException {
        InformacionPersonal infoPersonal = new InformacionPersonal();
        Persona persona = new Persona();

        infoPersonal.setNombre(request.queryParams("inputNombre"));
        infoPersonal.setApellido(request.queryParams("inputApellido"));
        infoPersonal.setFechaNacimiento(LocalDate.parse(request.queryParams("inputFechaDeNacimiento")));

        switch(request.queryParams("tipoDoc")){
            case "1":
                infoPersonal.setTipoDoc(TipoDeDocumento.DNI);
                break;
            case "2":
                infoPersonal.setTipoDoc(TipoDeDocumento.PASAPORTE);
                break;
            case "3":
                infoPersonal.setTipoDoc(TipoDeDocumento.LC);
                break;
            default:
                infoPersonal.setTipoDoc(TipoDeDocumento.DNI);
                break;

        }
        infoPersonal.setEmail(request.queryParams("inputEmail"));
        Contacto contactoPrincipal = new Contacto();
        contactoPrincipal.setEsPrincipal(true);
        contactoPrincipal.setNumeroDeTelefono(request.queryParams("inputNroTelefono"));
        contactoPrincipal.setEmail(request.queryParams("inputEmail"));
        contactoPrincipal.setNombre(request.queryParams("inputNombre"));
        contactoPrincipal.setApellido(request.queryParams("inputApellido"));
        List<Contacto> contactos = new ArrayList<Contacto>();
        infoPersonal.setContactos(contactos);
        infoPersonal.setNroDocumento(new Integer(request.queryParams("inputNroDocumento")));



        persona.setNombreUsuario(request.queryParams("inputNombreUsuario"));
        persona.setEmail(request.queryParams("inputEmail"));
        //persona.setOrganizacion();
        LocalizadorDeOrganizacion localizadorDeOrganizacion = new LocalizadorDeOrganizacion();
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setLatitud(1111.0);
        ubicacion.setLongitud(11111.0);
        localizadorDeOrganizacion.obtenerOrganizacionMasCercana(ubicacion);

        Password pass = new Password(request.queryParams("inputContrasenia"));
        persona.setContrasenia(pass.getPassword());

        persona.setInformacionPersonal(infoPersonal);

        List<EstrategiaDeNotificacion> estrategias = new ArrayList<EstrategiaDeNotificacion>();
        if(request.queryParams("checkSms") != null){
            EstrategiaDeNotificacion sms = new EstrategiaDeSms();
            estrategias.add(sms);
        }

        if(request.queryParams("checkWsp")!= null){
            EstrategiaDeNotificacion wsp = new EstrategiaDeWhatsApp();
            estrategias.add(wsp);
        }

        if(request.queryParams("checkEmail")!= null){
            EstrategiaDeNotificacion email = new EstrategiaEmail();
            estrategias.add(email);
        }

        infoPersonal.setFormaComunicacion(estrategias);
        repoContactos.agregar(contactoPrincipal);
        repoInfoPersonal.agregar(infoPersonal);
        repoPersona.agregar(persona);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("persona", persona);

        return new ModelAndView(parametros, "agregarContacto.hbs");
    }

    public ModelAndView agregarContacto(Request request, Response response) {
        Persona persona = repoPersona.buscar(new Integer(request.params("idPersona")));
        Contacto nuevoContacto = new Contacto();
        nuevoContacto.setApellido(request.queryParams("inputApellido"));
        nuevoContacto.setNombre(request.queryParams("inputNombre"));
        nuevoContacto.setEsPrincipal(false);
        nuevoContacto.setNumeroDeTelefono(request.queryParams("inputTelefono"));
        nuevoContacto.setEmail(request.queryParams("inputEmail"));

        repoContactos.agregar(nuevoContacto);
        persona.getInformacionPersonal().getContactos().add(nuevoContacto);
        repoInfoPersonal.modificar(persona.getInformacionPersonal());

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("persona", persona);
        return new ModelAndView(parametros, "agregarContacto.hbs");
    }
}
