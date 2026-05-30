package co.edu.udea.calidad.paquetrack.interactions;

import co.edu.udea.calidad.paquetrack.utils.Memory;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Interaction: GET CON el token Bearer recordado por el Actor.
 * Soporta query params opcionales.
 */
public class SendAuthenticatedGet implements Interaction {

    private final String path;
    private String[] queryParams = new String[0];

    public SendAuthenticatedGet(String path) {
        this.path = path;
    }

    public static SendAuthenticatedGet to(String path) {
        return instrumented(SendAuthenticatedGet.class, path);
    }

    /** Pares clave,valor: withQuery("from","2026-01-01","to","2026-12-31"). */
    public SendAuthenticatedGet withQuery(String... keyValues) {
        this.queryParams = keyValues;
        return this;
    }

    @Override
    @Step("{0} envia GET autenticado a #path")
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall(Memory.ACCESS_TOKEN);
        actor.attemptsTo(
                Get.resource(path).with(request -> {
                    if (token != null) {
                        request.header("Authorization", "Bearer " + token);
                    }
                    for (int i = 0; i + 1 < queryParams.length; i += 2) {
                        request.queryParam(queryParams[i], queryParams[i + 1]);
                    }
                    return request;
                })
        );
    }
}
