package co.edu.udea.calidad.paquetrack.interactions;

import co.edu.udea.calidad.paquetrack.utils.Memory;
import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Interaction: POST JSON CON el token Bearer recordado por el Actor.
 * El token se toma de la memoria del actor (no de variables globales).
 */
public class SendAuthenticatedPost implements Interaction {

    private final String path;
    private Object body;

    public SendAuthenticatedPost(String path) {
        this.path = path;
    }

    public static SendAuthenticatedPost to(String path) {
        return instrumented(SendAuthenticatedPost.class, path);
    }

    public SendAuthenticatedPost withBody(Object requestBody) {
        this.body = requestBody;
        return this;
    }

    @Override
    @Step("{0} envia POST autenticado a #path")
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall(Memory.ACCESS_TOKEN);
        actor.attemptsTo(
                Post.to(path).with(request -> {
                    request.contentType(ContentType.JSON);
                    if (token != null) {
                        request.header("Authorization", "Bearer " + token);
                    }
                    if (body != null) {
                        request.body(body);
                    }
                    return request;
                })
        );
    }
}
