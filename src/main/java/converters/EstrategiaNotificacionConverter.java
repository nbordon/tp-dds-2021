package converters;

import entidades.EstrategiasNotificacion.EstrategiaDeNotificacion;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EstrategiaNotificacionConverter implements AttributeConverter<EstrategiaDeNotificacion, String> {

    @Override
    public String convertToDatabaseColumn(EstrategiaDeNotificacion reaccionIncidente) {
        return reaccionIncidente.getClass().getName();
    }

    @Override
    public EstrategiaDeNotificacion convertToEntityAttribute(String s) {
        EstrategiaDeNotificacion Estrategia = null;
        try {
            Class c = Class.forName(s);
            Estrategia = (EstrategiaDeNotificacion) c.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return Estrategia;
    }
}