package co.edu.udea.calidad.paquetrack.interactions;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Interaction (la "menuda"): envia un POST JSON SIN autenticacion.
 * Para endpoints publicos / emisores de token (login, register, refresh).
 */
public class SendPost implements Interaction {

    private final String path;
    private Object body;

    public SendPost(String path) {
        this.path = path;
    }

    public static SendPost to(String path) {
        return instrumented(SendPost.class, path);
    }

    public SendPost withBody(Object requestBody) {
        this.body = requestBody;
        return this;
    }

    @Override
    @Step("{0} envia POST a #path")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to(path).with(request -> {
                    request.contentType(ContentType.JSON);
                    if (body != null) {
                        request.body(body);
                    }
                    return request;
                })
        );
    }
}
